<%-- 
    Document   : starring
    Created on : 17.11.2014, 22:12:44
    Author     : Ramon Bernert
--%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<tiles:insertDefinition name="movieTemplate">
	<tiles:putAttribute name="stylesheets">
		<c:forEach items="${widgets}" var="widget">
			<c:if test="${not empty widget.cssPath}">
			<link rel="stylesheet" href="<c:url value="${widget.cssPath}" />" />
			</c:if>
		</c:forEach>
	</tiles:putAttribute>
	<tiles:putAttribute name="scripts">
		<script>
			var movieID = '${movie.mID}';
			var movieTitle = '${movie.title}';
			var imagePath = '${movie.imagePath}';
		</script>
		
		<script src="<c:url value="/resources/js/connectionSearch.js" />" charset="utf-8"></script>
		
		<c:forEach items="${widgets}" var="widget">
			<c:if test="${not empty widget.scriptPath}">
			<script src="<c:url value="${widget.scriptPath}" />" charset="utf-8"></script>
			</c:if>
		</c:forEach>
		
	</tiles:putAttribute>
	
	<tiles:putAttribute name="lastBreadcrump">
		<li><a href="#"><c:out value="${movie.title}"/></a></li>
	</tiles:putAttribute>

    <tiles:putAttribute name="body">
    
    <div class="content three_quarter first"> 
		<h1><c:out value="${movie.title}"/></h1>
		<div class="group">
			<img class="imgl borderedbox inspace-5" src="<c:url value="${movie.imagePath}"/>" alt="" />
			<p>
				<c:out value="${movie.description}"/>
			</p>
		</div>
		
		<div class="group" id="widgets">
			<c:forEach items="${widgets}" var="widget">
			
				<h2><a href="#" id="widgetLink${widget.id}">${widget.name}</a></h2>
				<div id="widget${widget.id}" style="display:none">
					<input type="hidden" value="${widget.acceptedType }" />
					<c:if test="${not empty widget.jspPath}">
					<jsp:include page="${widget.jspPath}" />
					</c:if>
				</div>
			</c:forEach>
		</div>

	</div>

	</tiles:putAttribute>
	
	<tiles:putAttribute name="secondaryNav">
	
		<div id="connectionSearch" style="position: absolute;">
			<h2>Connect other movies</h2>
			<input type="text" id="connectionSearchInput" value="Search...">
			<input type="hidden" id="connectionSearchType" value="" />
			<div id="connectionSearchResult"></div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>