<%-- 
    Document   : starring
    Created on : 17.11.2014, 22:12:44
    Author     : Ramon Bernert
--%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<tiles:insertDefinition name="personTemplate">
	<tiles:putAttribute name="scripts">
	
	
		<script>
			var personID = ${person.id};
		</script>
	
		<c:forEach items="${scripts}" var="script">
			<script src="<c:url value="${script}" />" charset="utf-8"></script>
		</c:forEach>
		
	</tiles:putAttribute>

    <tiles:putAttribute name="body">
    
	<h1><c:out value="${person.name}"/></h1>
	<img class="imgl" src="<c:out value="${person.imagePath}"/>" alt="" width="125" />
	<p>
		<c:out value="${person.description}"/>
	</p>
	
	<div>
		<c:forEach items="${widgets}" var="widget">
			<jsp:include page="${widget}" />
		</c:forEach>
	</div>

	</tiles:putAttribute>
	
	<tiles:putAttribute name="secondaryNav">
		<h2>Secondary Navigation</h2>
		<ul>
			<li><a href="/MovieDB/persons/update?id=${person.id}">Aktualisieren</a></li>
		</ul>
	</tiles:putAttribute>
</tiles:insertDefinition>