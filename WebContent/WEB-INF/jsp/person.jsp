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
			var personName = '${person.name}';
			
			$(function() {
				$('#widgets > h2 > a').on('click', function (e) {
					e.preventDefault();
					
					var divId = $(this).attr('id').replace('Link', '');
					
					var widgetDiv = document.getElementById(divId);
				     
					if (widgetDiv.style.display == "none"){
						widgetDiv.style.display = "";
						
						var top = $('#' + divId).position().top;
						
						$('#personSearch').animate({ "top": "+=" + top + "px" }, "slow" );
						
					} else {
						widgetDiv.style.display = "none";
					}
				});
				
				$("#personSearchInput")
					.focusin(function() {
						this.value = '';
					})
					.focusout(function() {
						this.value = 'Search...';
					})
					.on('input', function() {
					
						var value = $(this).val();
						
						if(value.length > 2){
							$.getJSON("/MovieDB/home/search", { term: value, count: 6 } )
								.done(function(data) {
									
									$("#personSearchResult").empty();
									
									$.each(data, function(i, item) {
										$("#personSearchResult").append('<p id="' + data[i].uri + '" class="ui-state-default">' + data[i].label + '</p>');
									});
									
									$("#personSearchResult > p").draggable({ opacity: 0.7, helper: "clone" });
								});
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
	
		<div id="personSearch" style="position: absolute;">
			<h2>Connect other persons</h2>
			<input type="text" id="personSearchInput" value="Search...">
			
			<div id="personSearchResult"></div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>