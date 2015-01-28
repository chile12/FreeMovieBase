<%-- 
    Document   : starring
    Created on : 17.11.2014, 22:12:44
    Author     : Ramon Bernert
--%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">

	<style>
        .axis path,
        .axis line {
          fill: none;
          stroke: white;
          shape-rendering: crispEdges;
        }

        .x.axis path {
          display: none;
        }

        .axis text {
        	fill:white;
        }
        
        .line {
          fill: none;
          stroke: white;
          stroke-width: 1.5px;
        }
        
        .lineTv {
          fill: none;
          stroke: yellow;
          stroke-width: 1.5px;
        }
        
        .lineProducer {
          fill: none;
          stroke: red;
          stroke-width: 1.5px;
        }
    </style>

	<h1><c:out value="${person.name}"/></h1>
	<img class="imgl" src="<c:out value="${person.imagePath}"/>" alt="" width="125" />
	<p>
		<c:out value="${person.description}"/>
	</p>
	<div>
		<script src="<c:url value="/resources/js/groupMenu.js" />"></script>
		<h2><a href="javascript:toggle('element1')">Allgemein</a></h2>
		<div id="element1" style="display:none">
			<table summary="Allgemeine Infos" cellpadding="0" cellspacing="0">
			<tbody>
				<tr class="light">
					<td>Geboren</td>
					<td><fmt:formatDate value="${person.birthday}" pattern="dd.MM.yyyy" /></td>
				</tr>
				<tr class="dark">
					<td>Geschlecht</td>
					<td>${person.gender}</td>
				</tr>
				<tr class="light">
					<td>mID</td>
					<td>${person.mID}</td>
				</tr>
				<tr class="dark">
					<td>...</td>
					<td>...</td>
				</tr>
			</tbody>
			</table>	
		</div>
	
		<h2><a href="javascript:toggle('element2')">Weiteres...</a></h2>
		<div id="element2" style="display:none">
		    ...
		</div>
		
		<h2><a href="javascript:toggle('events')" id="eventLink">Ereignisse</a></h2>
		<div id="events" style="display:none"></div>
	
		<script src="<c:url value="/resources/js/d3.v3.js" />" charset="utf-8"></script>
		<script src="<c:url value="/resources/js/jquery-1.10.1.min.js" />" charset="utf-8"></script>
		<script>
	
		var eventsLoaded = false;
	
		$(function() {
			$('#eventLink').on('click', function (e) {
				e.preventDefault();
	
				var e2 = document.getElementById('events');
		     
				if (e2.style.display == "none"){
					e2.style.display = "";
				} else {
					e2.style.display = "none";
				}
	
				if(!eventsLoaded){
	       
				var margin = {top: 20, right: 20, bottom: 30, left: 50},
				    width = 600 - margin.left - margin.right,
				    height = 500 - margin.top - margin.bottom;
	
				var parseDate = d3.time.format("%Y-%m-%d").parse;
				var x = d3.time.scale()
						.range([0, width]);
	
				var y = d3.scale.linear()
						.range([height, 0]);
	
	          var xAxis = d3.svg.axis()
	              .scale(x)
	              .orient("bottom");
	
	          var yAxis = d3.svg.axis()
	              .scale(y)
	              .orient("left");
	
	          var lineMovie = d3.svg.line()
	              .x(function(d) { return x(d.date); })
	              .y(function(d) { return y(d.movies); });
	
	          var lineTv = d3.svg.line()
	              .x(function(d) { return x(d.date); })
	              .y(function(d) { return y(d.tv); });
	
	          var lineProducer = d3.svg.line()
	              .x(function(d) { return x(d.date); })
	              .y(function(d) { return y(d.producer); });
	
	          var svg = d3.select("#events").append("svg")
	              .attr("width", width + margin.left + margin.right)
	              .attr("height", height + margin.top + margin.bottom)
	            .append("g")
	              .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
	            
	            d3.json("/MovieDB/persons/getEvents?id=${person.id}", function(error, data) {
	
	          	  data.forEach(function(d) {
	          		  d.date = parseDate(d.date);
	                    d.movies = +d.movies;
	                    d.tv = +d.tv;
	                    d.producer = +d.producer;
	          	  });
	
	            x.domain(d3.extent(data, function(d) { return d.date; }));
	            y.domain(d3.extent(data, function(d) { return d.movies; }));
	
	            svg.append("g")
	                .attr("class", "x axis")
	                .attr("transform", "translate(0," + height + ")")
	                .call(xAxis);
	
	            svg.append("g")
	                .attr("class", "y axis")
	                .call(yAxis)
	              .append("text")
	                .attr("transform", "rotate(-90)")
	                .attr("y", 6)
	                .attr("dy", ".71em")
	                .style("text-anchor", "end")
	                .text("Ereignisse");
	
	              svg.append("path")
	                .datum(data)
	                .attr("class", "line")
	                .attr("d", lineMovie);
	
	              //svg.append("path")
	                //.datum(data)
	                //.attr("class", "lineTv")
	                //.attr("d", lineTv);
	
	              //svg.append("path")
	                //.datum(data)
	                //.attr("class", "lineProducer")
	                //.attr("d", lineProducer);
	
	              svg.selectAll(".dot")
	              .data(data)
	              .enter().append("circle")
	              .attr("class", "dot")
	              .attr("r", 3.5)
	              .attr("cx", function(d) { return x(d.date); })
	              .attr("cy", function(d) { return y(d.movies); })
	              .style("fill", "white");
	
	              svg.selectAll(".dot")
	              .data(data)
	              .enter().append("circle")
	              .attr("class", "dot")
	              .attr("r", 3.5)
	              .attr("cx", function(d) { return x(d.date); })
	              .attr("cy", function(d) { return y(d.tv); })
	              .style("fill", "yellow");
	
	              svg.selectAll(".dot")
	              .data(data)
	              .enter().append("circle")
	              .attr("class", "dot")
	              .attr("r", 3.5)
	              .attr("cx", function(d) { return x(d.date); })
	              .attr("cy", function(d) { return y(d.producer); })
	              .style("fill", "red");
	              
	            });
	            
	            eventsLoaded = true;
		    }
		});
	});
	       </script>

	</div>

	</tiles:putAttribute>
	
	<tiles:putAttribute name="secondaryNav">
		<h2>Secondary Navigation</h2>
		<ul>
			<li><a href="/MovieDB/persons/update?id=${person.id}">Aktualisieren</a></li>
		</ul>
	</tiles:putAttribute>
</tiles:insertDefinition>