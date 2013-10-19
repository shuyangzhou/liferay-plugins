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

package com.liferay.portal.resiliency.console.servlet;

import com.liferay.portal.kernel.resiliency.spi.SPIRegistryUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Shuyang Zhou
 */
public class PortalResiliencyConsoleContextListener
	implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		SPIRegistryUtil.removeExcludedPortletId(_CONSOLE_PORTLET_ID);
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		SPIRegistryUtil.addExcludedPortletId(_CONSOLE_PORTLET_ID);
	}

	private static final String _CONSOLE_PORTLET_ID =
		"portalresiliencyconsole_WAR_portalresiliencyconsoleportlet";

}