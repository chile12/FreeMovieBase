<%-- 
    Document   : persons
    Created on : 02.12.2014, 14:18:26
    Author     : Ramon Bernert
--%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">

	<h1>Filme</h1>
					
	<c:forEach items="${movies}" var="movie">
		<a href="/MovieDB/persons/get?id=${movie.id}">${movie.title}</a><br />
	</c:forEach>

	</tiles:putAttribute>
</tiles:insertDefinition>