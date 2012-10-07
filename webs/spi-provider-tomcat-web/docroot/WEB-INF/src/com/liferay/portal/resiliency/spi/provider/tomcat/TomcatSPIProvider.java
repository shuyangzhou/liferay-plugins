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

package com.liferay.portal.resiliency.spi.provider.tomcat;

import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.provider.BaseSPIProvider;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPI;
import com.liferay.util.resiliency.spi.provider.SPIClassPathContextListener;

/**
 * @author Shuyang Zhou
 */
public class TomcatSPIProvider extends BaseSPIProvider {

	public static final String NAME = "Tomcat SPI Provider";

	public RemoteSPI createRemoteSPI(SPIConfiguration spiConfiguration)
		throws PortalResiliencyException {

		return new TomcatRemoteSPI(spiConfiguration);
	}

	public String getClassPath() {
		return SPIClassPathContextListener.SPI_CLASSPATH;
	}

	public String getName() {
		return NAME;
	}

}