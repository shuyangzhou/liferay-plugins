/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.resiliency.console.portlet;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.rpc.IntrabandRPCUtil;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIProvider;
import com.liferay.portal.kernel.resiliency.spi.remote.SystemPropertiesProcessCallable;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.ActionCommand;

import java.io.File;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;

/**
 * @author Shuyang Zhou
 */
public class CreateSPIActionCommand implements ActionCommand {

	@Override
	public boolean processCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortletException {

		String spiProviderName = ParamUtil.getString(
			portletRequest, "spiProviderName");

		String id = ParamUtil.getString(portletRequest, "id");
		String javaExecutable = ParamUtil.getString(
			portletRequest, "javaExecutable");
		String jvmArguments = ParamUtil.getString(
			portletRequest, "jvmArguments");
		String agentClassName = ParamUtil.getString(
			portletRequest, "agentClassName");
		boolean debug = ParamUtil.getBoolean(portletRequest, "debug");
		boolean suspend = ParamUtil.getBoolean(portletRequest, "suspend");
		int jpdaPort = ParamUtil.getInteger(portletRequest, "jpdaPort");
		int connectorPort = ParamUtil.getInteger(
			portletRequest, "connectorPort");
		String[] portletIds = ParamUtil.getParameterValues(
			portletRequest, "corePortletIds");
		String[] servletContextNames = ParamUtil.getParameterValues(
			portletRequest, "pluginServletContextNames");
		long pingInterval = ParamUtil.getLong(portletRequest, "pingInterval");
		long registerTimeout = ParamUtil.getLong(
			portletRequest, "registerTimeout");
		long shutdownTimeout = ParamUtil.getLong(
			portletRequest, "shutdownTimeout");
		String extraSettings = ParamUtil.getString(
			portletRequest, "extraSettings");
		String overridePortalProperties = ParamUtil.getString(
			portletRequest, "overridePortalProperties");

		TimeZone timeZone = TimeZone.getDefault();

		jvmArguments += " -Duser.timezone=" + timeZone.getID();

		if (debug) {
			jvmArguments +=
				" -agentlib:jdwp=transport=dt_socket,address=" + jpdaPort +
					",server=y,suspend=" + (suspend ? "y" : "n");
		}

		String baseDir = System.getProperty("java.io.tmpdir") + "/" + id;

		File baseDirFile = new File(baseDir);

		FileUtil.deltree(baseDirFile);

		if (!baseDirFile.mkdirs()) {
			throw new PortletException("Unable to create base dir " + baseDir);
		}

		SPIProvider spiProvider = MPIHelperUtil.getSPIProvider(spiProviderName);

		SPIConfiguration spiConfiguration = new SPIConfiguration(
			id, javaExecutable, jvmArguments, agentClassName, connectorPort,
			baseDir, portletIds, servletContextNames, pingInterval,
			registerTimeout, shutdownTimeout, extraSettings);

		ServletContext rootServletContext = ServletContextPool.get(
			PortalUtil.getServletContextName());

		String rootDir = rootServletContext.getRealPath(StringPool.BLANK);

		try {
			SPI spi = spiProvider.createSPI(spiConfiguration);

			if (_log.isInfoEnabled()) {
				_log.info("Created SPI : " + spi);
			}

			Properties properties = new Properties();

			properties.load(new UnsyncStringReader(overridePortalProperties));

			Map<String, String> propertiesMap = new HashMap<String, String>();

			for (String name : properties.stringPropertyNames()) {
				propertiesMap.put(
					"portal:".concat(name), properties.getProperty(name));
			}

			IntrabandRPCUtil.execute(
				spi.getRegistrationReference(),
				new SystemPropertiesProcessCallable(propertiesMap));

			spi.init();

			if (_log.isInfoEnabled()) {
				_log.info("Initialized SPI : " + spi);
			}

			spi.addWebapp(PortalUtil.getPathContext(), rootDir);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Add ROOT webapp baseDir : " + rootDir + " to SPI : " +
					spi);
			}

			spi.start();

			if (_log.isInfoEnabled()) {
				_log.info("Started SPI : " + spi);
			}

			for (String servletContextName :
					spiConfiguration.getServletContextNames()) {

				ServletContext servletContext = ServletContextPool.get(
					servletContextName);

				String contextPath = servletContext.getContextPath();
				String docBasePath = servletContext.getRealPath(
					StringPool.BLANK);

				spi.addWebapp(contextPath, docBasePath);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Add webapp contextPath : " + contextPath +
						" baseDir : " + baseDir + " to SPI : " + spi);
				}
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(
		CreateSPIActionCommand.class);

}