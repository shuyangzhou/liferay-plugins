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

import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.service.PortletLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class PortletUtil {

	public static List<Portlet> getCorePortlets() {
		List<Portlet> portlets = PortletLocalServiceUtil.getPortlets();

		Iterator<Portlet> iterator = portlets.iterator();

		while (iterator.hasNext()) {
			Portlet portlet = iterator.next();

			PortletApp portletApp = portlet.getPortletApp();

			if (portletApp.isWARFile()) {
				iterator.remove();
			}
		}

		Collections.sort(portlets, new Comparator<Portlet>() {

			public int compare(Portlet portlet1, Portlet portlet2) {
				return portlet1.getDisplayName().compareTo(
					portlet2.getDisplayName());
			}

		});

		return portlets;
	}

	public static List<String> getPluginServletContextNames() {
		Set<String> servletContextNameSet = ServletContextPool.keySet();

		List<String> servletContextNameList = new ArrayList<String>();

		for (String servletContextName : servletContextNameSet) {
			if (servletContextName.length() > 0) {
				servletContextNameList.add(servletContextName);
			}
		}

		Collections.sort(servletContextNameList);

		return servletContextNameList;
	}

}