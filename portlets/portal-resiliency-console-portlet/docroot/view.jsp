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

<portlet:renderURL var="viewSummaryURL">
	<portlet:param name="mvcPath" value="/view_summary.jsp" />
</portlet:renderURL>

<p>
	<a href="<%= viewSummaryURL %>">SPIProvider and SPI summary info.</a>
</p>

<portlet:renderURL var="createURL">
	<portlet:param name="mvcPath" value="/create_spi.jsp" />
</portlet:renderURL>

<p>
	<a href="<%= createURL %>">Create a new Tomcat SPI instance.</a>
</p>