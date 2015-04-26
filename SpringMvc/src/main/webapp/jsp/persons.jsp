<%-- 
    Document   : persons
    Created on : 02.12.2014, 14:18:26
    Author     : Ramon Bernert
--%>


<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<tiles:insertDefinition name="personTemplate">
    <tiles:putAttribute name="body">

	<script>
	
	var ids = [];
	
	$(function() {
		$(".one_quarter").each(function(index) {
			var imagePath = $(this).find("a > img").attr("src");
			
			var id = $(this).find("input").val();
			
			if(imagePath){
				
				if(imagePath.indexOf('images/gallery.gif') == -1){
					console.log(id);
					ids.push(id);
				}
				else {
					console.log(index + ": " + imagePath);
				}
			}
		});
		
		if(ids.length > 0){
			setTimeout(loadImages, 500);
		}
	});
	
	function loadImages(){
		
		if(ids.length > 0){
			$.getJSON(baseUrl + 'persons/getImages', { uris: JSON.stringify(ids) } )
			.done(function(data) {
				
				$.each(data, function(i, item) {
					
					var i = ids.indexOf(item.id);
					if (i >= 0) {
						ids.splice(i, 1);
					}
					
					$(".one_quarter > a > img").attr("src", item.image);
				});
			});
		
			setTimeout(loadImages, 500);
		}
	}
	</script>

  	<h1>The last Oscar winners</h1>
  	<ul class="nospace clear">
  	
  		<c:set var="i" value="0"/>
		<c:forEach items="${persons}" var="person">
	 	<li class="one_quarter ${i % 4 == 0 ? " first" : ""}">
	 		<input type="hidden" value="${person.mID}"/>
	 		<a href="<c:url value="/persons/get?uri=${person.mID}" />">${person.name}<br />
 				<img class="imgl borderedbox inspace-5" style="height:80px" src="<c:url value="${person.imagePath}"/>" alt="" />
 			</a>
 			<c:set var="i" value="${i + 1}"/>
		</li>
		</c:forEach>
  	</ul>

	</tiles:putAttribute>
</tiles:insertDefinition>