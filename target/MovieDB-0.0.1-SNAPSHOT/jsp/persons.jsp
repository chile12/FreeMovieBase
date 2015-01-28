<%-- 
    Document   : persons
    Created on : 02.12.2014, 14:18:26
    Author     : Ramon Bernert
--%>


<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">

	<h1>Personen</h1>
	<c:forEach items="${persons}" var="person">
		<a href="/MovieDB/persons/get?id=${person.id}">${person.name}</a><br />
	</c:forEach>
	<br />
	<c:if test="${step > 0}">
	<a href="/MovieDB/persons?step=${step - 1}">zurück</a>
	</c:if>
	<c:if test="${morePersons}">
	<a href="/MovieDB/persons?step=${step + 1}">weiter</a>
	</c:if>

	</tiles:putAttribute>
</tiles:insertDefinition>