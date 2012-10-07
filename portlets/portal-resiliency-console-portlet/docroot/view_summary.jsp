<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
List<SPIProvider> spiProviderList = MPIUtil.getSPIProviders();

for (SPIProvider spiProvider : spiProviderList) {
%>

	SPIProvider name : <%= spiProvider.getName() %><br />

<%
	List<SPI> spiList = MPIUtil.getSPIs(spiProvider.getName());

	if (spiList.size() > 0) {
%>

<ul>

<%
		for (SPI spi : spiList) {
%>

			SPIConfiguration : <%= spi.getSPIConfiguration() %>

			<portlet:actionURL var="destroySPIURL">
				<portlet:param name="javax.portlet.action" value="destroySPI" />
				<portlet:param name="spiProviderName" value="<%= spiProvider.getName() %>" />
				<portlet:param name="spiId" value="<%= spi.getSPIConfiguration().getId() %>" />
			</portlet:actionURL>

			<form action="<%=destroySPIURL%>" method="post">
				<input type="submit" value="Destroy" />
			</form>

<%
		}
%>

</ul>

<%
	}
}
%>