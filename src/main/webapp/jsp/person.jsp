<%-- 
    Document   : starring
    Created on : 17.11.2014, 22:12:44
    Author     : Ramon Bernert
--%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<tiles:insertDefinition name="personTemplate">
	<tiles:putAttribute name="stylesheets">
		<c:forEach items="${widgets}" var="widget">
			<c:if test="${not empty widget.cssPath}">
			<link rel="stylesheet" href="<c:url value="${widget.cssPath}" />" />
			</c:if>
		</c:forEach>
	</tiles:putAttribute>
	<tiles:putAttribute name="scripts">
		<script>
			var personID = '${person.mID}';
			
			$(function() {
				$('#widgets > h2 > a').on('click', function (e) {
					e.preventDefault();
					
					var divId = $(this).attr('id').replace('Link', '');
					
					var widgetDiv = document.getElementById(divId);
				     
					if (widgetDiv.style.display == "none"){
						widgetDiv.style.display = "";
					} else {
						widgetDiv.style.display = "none";
					}
				});
			});
		</script>
	
		<c:forEach items="${widgets}" var="widget">
			<c:if test="${not empty widget.scriptPath}">
			<script src="<c:url value="${widget.scriptPath}" />" charset="utf-8"></script>
			</c:if>
		</c:forEach>
		
	</tiles:putAttribute>
	
	<tiles:putAttribute name="lastBreadcrump">
		<li><a href="#"><c:out value="${person.name}"/></a></li>
	</tiles:putAttribute>

    <tiles:putAttribute name="body">
    
    <div class="content three_quarter first"> 
		<h1><c:out value="${person.name}"/></h1>
		<div class="group">
			<img class="imgl borderedbox inspace-5" style="max-width:200px; max-height:300px" src="<c:url value="${person.imagePath}"/>" alt="" />
			<p>
				<c:out value="${person.description}"/>
			</p>
		</div>
		
		<div class="group" id="widgets">
			<c:forEach items="${widgets}" var="widget">
			
				<h2><a href="#" id="widgetLink${widget.id}">${widget.name}</a></h2>
				<div id="widget${widget.id}" style="display:none">
					<c:if test="${not empty widget.jspPath}">
					<jsp:include page="${widget.jspPath}" />
					</c:if>
				</div>
			</c:forEach>
		</div>

	</div>

	</tiles:putAttribute>
	
	<tiles:putAttribute name="secondaryNav">
		<h2>Secondary Navigation</h2>
		<ul>
			<li>Test</li>
		</ul>
	</tiles:putAttribute>
</tiles:insertDefinition>