/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resiliency.mpi.MPIUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIProvider;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.bridges.mvc.ActionCommand;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;

/**
 * @author Shuyang Zhou
 */
public class CreateSPIActionCommand implements ActionCommand {

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
		String baseDir = ParamUtil.getString(portletRequest, "baseDir");
		String[] portletIds = ParamUtil.getParameterValues(
			portletRequest, "corePortletIds");
		String[] servletContextNames = ParamUtil.getParameterValues(
			portletRequest, "pluginServletContextNames");
		long pingInterval = ParamUtil.getLong(portletRequest, "pingInterval");
		long registerTimeout = ParamUtil.getLong(
			portletRequest, "registerTimeout");
		long shutdownTimeout = ParamUtil.getLong(
			portletRequest, "shutdownTimeout");

		if (debug) {
			jvmArguments +=
				" -agentlib:jdwp=transport=dt_socket,address=" + jpdaPort +
					",server=y,suspend=" + (suspend ? "y" : "n");
		}

		SPIProvider spiProvider = MPIUtil.getSPIProvider(spiProviderName);

		SPIConfiguration spiConfiguration = new SPIConfiguration(
			id, javaExecutable, jvmArguments, agentClassName, connectorPort,
			baseDir, portletIds, servletContextNames, pingInterval,
			registerTimeout, shutdownTimeout);

		ServletContext rootServletContext = ServletContextPool.get(
			StringPool.BLANK);
		String rootDir = rootServletContext.getRealPath(StringPool.BLANK);
		String webappsDir = rootDir.substring(0, rootDir.length() - 5);

		SPI spi = null;

		try {
			spi = spiProvider.createSPI(spiConfiguration);

			if (_log.isInfoEnabled()) {
				_log.info("Created SPI : " + spi);
			}

			spi.init();

			if (_log.isInfoEnabled()) {
				_log.info("Initialized SPI : " + spi);
			}

			spi.addWebapp(StringPool.BLANK, rootDir);

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
				String docBasePath = webappsDir.concat(contextPath);

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