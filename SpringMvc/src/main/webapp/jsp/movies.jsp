<%-- 
    Document   : persons
    Created on : 02.12.2014, 14:18:26
    Author     : Ramon Bernert
--%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<tiles:insertDefinition name="movieTemplate">
    <tiles:putAttribute name="body">

	<h1>The last Oscar winners</h1>
  	<ul class="nospace clear">
  		<c:set var="i" value="0"/>
		<c:forEach items="${movies}" var="movie">
	 	<li class="one_quarter ${i % 4 == 0 ? " first" : ""}">
	 		<a href="<c:url value="/movies/get?uri=${movie.mID}" />">${movie.title}<br />
 				<img class="imgl borderedbox inspace-5" style="height:80px" src="<c:url value="${movie.imagePath}"/>" alt="" />
 			</a>
 			<c:set var="i" value="${i + 1}"/>
		</li>
		</c:forEach>
  	</ul>

	</tiles:putAttribute>
</tiles:insertDefinition>