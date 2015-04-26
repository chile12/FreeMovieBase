var eventsLoaded = false;

$(function() {
    $('#widgetLink3').on('click', function (e) {
        e.preventDefault();

        if(!eventsLoaded){

            var margin = {top: 20, right: 20, bottom: 30, left: 50},
                width = width = $("#widgets").width() - margin.left - margin.right,
                height = 500 - margin.top - margin.bottom;

            var parseDate = d3.time.format("%Y-%m-%d+02:00").parse;
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
                .x(function(d) { return x(d.year.value); })
                .y(function(d) { return y(d.count.value); });

            var svg = d3.select("#widget3").append("svg")
                .attr("width", width + margin.left + margin.right)
                .attr("height", height + margin.top + margin.bottom)
                .append("g")
                .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

            d3.json(baseUrl + "/persons/getAvardsCount?uri=" + personID, function(error, data) {

                data = data.results.bindings;

                data.forEach(function(d) {
                    d.year.value = parseDate(d.year.value);
                    d.count.value = +d.count.value;
                });

                x.domain(d3.extent(data, function(d) { return d.year.value; }));
                y.domain(d3.extent(data, function(d) { return d.count.value; }));

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
                    .text("Awards");

                svg.append("path")
                    .datum(data)
                    .attr("class", "line")
                    .attr("d", lineMovie);

                svg.selectAll(".dot")
                    .data(data)
                    .enter().append("circle")
                    .attr("class", "dot")
                    .attr("r", 3.5)
                    .attr("cx", function(d) { return x(d.year.value); })
                    .attr("cy", function(d) { return y(d.count.value); })
                    .style("fill", "#888888");
            });

            eventsLoaded = true;
        }
    });
});