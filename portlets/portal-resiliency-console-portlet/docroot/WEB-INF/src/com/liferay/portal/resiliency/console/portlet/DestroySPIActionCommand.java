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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.util.bridges.mvc.ActionCommand;

import java.rmi.RemoteException;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Shuyang Zhou
 */
public class DestroySPIActionCommand implements ActionCommand {

	public boolean processCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortletException {

		String spiProviderName = ParamUtil.getString(
			portletRequest, "spiProviderName");

		String spiId = ParamUtil.getString(portletRequest, "spiId");

		SPI spi = MPIUtil.getSPI(spiProviderName, spiId);

		try {
			spi.stop();
		}
		catch (RemoteException re) {
			_log.error("Failed to stop SPI.", re);
		}

		try {
			spi.destroy();
		}
		catch (RemoteException re) {
			_log.error("Failed to destroy SPI.", re);
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DestroySPIActionCommand.class);

}