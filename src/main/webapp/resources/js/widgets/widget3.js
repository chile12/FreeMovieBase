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

      var svg = d3.select("#events").append("svg")
          .attr("width", width + margin.left + margin.right)
          .attr("height", height + margin.top + margin.bottom)
        .append("g")
          .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
        
        d3.json("/MovieDB/persons/getEvents?id=" + personID, function(error, data) {
        	
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