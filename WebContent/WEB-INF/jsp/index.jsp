<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<tiles:insertDefinition name="homeTemplate">
    <tiles:putAttribute name="body">
	
		<h1>Willkommen bei MovieDB</h1>
		<ul class="nospace clear">
		<c:set var="i" value="0"/>
		<c:forEach items="${persons}" var="person">
	 	<li class="one_quarter ${i % 4 == 0 ? " first" : ""}">
	 		<a href="/MovieDB/persons/get?uri=${person.mID}">${person.name}</a><br />
			<img class="imgl borderedbox inspace-5" style="height:80px" src="<c:url value="${person.imagePath}"/>" alt="" />
			<c:set var="i" value="${i + 1}"/>
		</li>
		</c:forEach>
		<c:set var="i" value="0"/>
		<c:forEach items="${movies}" var="movie">
	 	<li class="one_quarter ${i % 4 == 0 ? " first" : ""}">
	 		<a href="/MovieDB/movies/get?uri=${movie.mID}">${movie.title}</a><br />
			<img class="imgl borderedbox inspace-5" style="height:80px" src="<c:url value="${movie.imagePath}"/>" alt="" />
			<c:set var="i" value="${i + 1}"/>
		</li>
		</c:forEach>
		</ul>
	
		<div class="wrapper row2">
			<div id="services" class="clear"> 
				<div class="group">
					<div class="one_third first">
						<article class="service">
							<h2 class="heading">Filme</h2>
							<p class="btmspace-10">Soooo viele Filme haben wir im Index!!</p>
							<p><a href="<c:url value="/movies" />">Read More »</a></p>
						</article>
					</div>
					<div class="one_third">
						<article class="service">
							<h2 class="heading">Schauspieler</h2>
							<p class="btmspace-10">Soooo viele Schauspieler haben wir im Index!!</p>
							<p><a href="<c:url value="/persons" />">Read More »</a></p>
						</article>
					</div>
					<div class="one_third">
						<article class="service">
							<h2 class="heading">Andere</h2>
							<p class="btmspace-10">Soooo viele andere Beiteiligte haben wir im Index!!</p>
							<p><a href="#">Read More »</a></p>
						</article>
					</div>
				</div>
			</div>
		</div>
		
		<div class="wrapper row3">
	
		</div>
	
	</tiles:putAttribute>
</tiles:insertDefinition>