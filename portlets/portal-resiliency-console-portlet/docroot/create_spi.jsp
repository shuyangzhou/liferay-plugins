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

<portlet:actionURL var="createSPIURL">
	<portlet:param name="javax.portlet.action" value="createSPI" />
</portlet:actionURL>

<form action="<%=createSPIURL%>" method="post">
	<table>
		<tr>
			<th>SPIProvider :</th>
			<th>
				<select name="spiProviderName">

					<%
					for (SPIProvider spiProvider : MPIUtil.getSPIProviders()) {
					%>

						<option value="<%= spiProvider.getName() %>"><%= spiProvider.getName() %></option>

					<%
					}
					%>

				</select>
			</th>
		</tr>
		<tr>
			<th>Id :</th>
			<th><input name="id" size="15" type="text" value="Tomcat SPI 1" /></th>
		</tr>
		<tr>
			<th>Java executable :</th>
			<th><input name="javaExecutable" size="10" type="text" value="java" /></th>
		</tr>
		<tr>
			<th>Agent Class Name :</th>
			<th>
				<select name="agentClassName">

					<%
					for (String agentClassName : SPIAgentFactoryUtil.getAgentClassNames()) {
					%>

						<option value="<%= agentClassName %>"><%= agentClassName %></option>

					<%
					}
					%>

				</select>
			</th>
		</tr>
		<tr>
			<th>Jvm arguments :</th>
			<th>
				<input name="jvmArguments" size="50" type="text" value="-Xmx512m -XX:PermSize=200m" />
				Debug : <input name="debug" size="50" type="checkbox" />
				Suspend : <input name="suspend" size="50" type="checkbox" />
				JPDA port :
				<select name="jpdaPort">
					<option>8001</option>
					<option>8002</option>
					<option>8003</option>
					<option>8004</option>
					<option>8005</option>
					<option>8006</option>
					<option>8007</option>
					<option>8008</option>
					<option>8009</option>
					<option>8010</option>
				</select>
			</th>
		</tr>
		<tr>
			<th>Connector port :</th>
			<th>
				<select name="connectorPort">
					<option>8081</option>
					<option>8082</option>
					<option>8083</option>
					<option>8084</option>
					<option>8085</option>
					<option>8086</option>
					<option>8087</option>
					<option>8088</option>
					<option>8089</option>
					<option>8090</option>
				</select>
			</th>
		</tr>

		<%
			String catalinaHome = System.getProperty("catalina.home");

			if (Validator.isNull(catalinaHome)) {
				// For non-tomcat flavor App Servers, ask user to input base folder
		%>

		<tr>
			<th>Base directory :</th>
			<th><input name="baseDir" size="50" type="text" value="<%= SystemProperties.get(PropsKeys.LIFERAY_HOME) %>" /></th>
		</tr>

		<%
			}
			else {
				// For tomcat flavor App Servers, SPI uses MPI's base folder
		%>

				<input name="baseDir" size="50" type="hidden" value="<%= catalinaHome %>" />

		<%
			}
		%>

		<tr>
			<th>Core Portlets :</th>
			<th>
				<% List<Portlet> corePortlets = PortletUtil.getCorePortlets(); %>

				<select multiple="true" name="corePortletIds" size="10">

				<%
					for (Portlet portlet : corePortlets) {
				%>

					<option value="<%= portlet.getPortletId() %>"><%= portlet.getDisplayName() %></option>

				<%
					}
				%>

				</select>
			</th>
		</tr>
		<tr>
			<th>Plugin webapps :</th>
			<th>
				<% List<String> servletContextNames = PortletUtil.getPluginServletContextNames(); %>

				<select multiple="true" name="pluginServletContextNames" size="<%= servletContextNames.size() < 10 ? servletContextNames.size() : 10 %>">

				<%
					for (String servletContextName : servletContextNames) {
				%>

					<option><%= servletContextName %></option>

				<%
					}
				%>

				</select>
			</th>
		</tr>
		<tr>
			<th>Ping interval :</th>
			<th><input name="pingInterval" size="10" type="text" value="5000" /></th>
		</tr>
		<tr>
			<th>Register timeout :</th>
			<th><input name="registerTimeout" size="10" type="text" value="10000" /></th>
		</tr>
		<tr>
			<th>Shutdown timeout :</th>
			<th><input name="shutdownTimeout" size="10" type="text" value="10000" /></th>
		</tr>
		<tr>
			<th></th>
			<th><input type="submit" value="Submit" /></th>
		</tr>
	</table>
</form>