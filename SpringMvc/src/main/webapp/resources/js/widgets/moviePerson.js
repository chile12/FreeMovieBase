var width = 722,
    radius = 24,
    movieRadius = 32;

$(function() {
    var diagonal = d3.svg.diagonal()
        .projection(function(d) { return [d.y, d.x]; });

    width = $("#widgets").width();

    var svg = d3.select("#moviePersonGraph").append("svg")
        .attr("width", width)
        .attr("height", 200)
        .append("g")
        .attr("transform", "translate(" + 50 + "," + 0 + ")");

    var data = {"id":movieID,"name":movieTitle,"children":[],"image":imagePath,"type":"movie1"};

    drawMoviePersonGraphByData(data);

    $('#widgetLink4').on('click', function (e) {
        e.preventDefault();
    });
    $("#moviePersonGraph").droppable({
        accept: "#connectionSearchResult > p",
        activeClass: "ui-state-hover",
        hoverClass: "ui-state-active",
        drop: function(event, ui) {

            var id = ui.draggable.attr('id');

            ui.draggable.fadeOut("slow");

            drawMoviePersonGraph(movieID, id);
        }
    });

    function drawMoviePersonGraph(movie1, movie2){

        $.getJSON(baseUrl + "/persons/byMovies", { movie1: movie1, movie2: movie2 })
            .done(function(data) {

                drawMoviePersonGraphByData(data);

            });
    }

    function drawMoviePersonGraphByData(data){

        var movieCount = data["children"].length,
            height = movieCount * 65;

        var height = 120;

        if(movieCount > 0){
            height = movieCount * 65;
        }

        var tree = d3.layout.cluster()
            .size([height, width - 120])
            .separation(function(a, b) { return (a.parent == b.parent ? 1 : 2) / a.depth - 100; });

        $("svg").attr("height", height + 10);

        var nodes = tree.nodes(data).reverse();

        var movie1Node = nodes.filter(function(d) { return d['type'] === 'movie1'; })[0];

        if(movieCount == 0){
            movie1Node.x = 50;
            movie1Node.y = 20;
        }
        else {

            var movie2Node = nodes.filter(function(d) { return d['type'] === 'movie2'; })[0];

            var dummyNodes = nodes.filter(function(d) { return d['type'] === 'dummy'; });

            movie2Node.x = movie1Node.x;

            dummyNodes.forEach(function(node) {
                node.x = movie1Node.x;
            });
        }

        var defs = svg.append("defs").attr("id", "imgdefs");

        nodes.forEach(function(node) {

            var imagePattern = defs.append("pattern")
                .attr("id", node.id)
                .attr("height", 1)
                .attr("width", 1)
                .attr("x", "0")
                .attr("y", "0");

            imagePattern.append("image")
                .attr("x", -10)
                .attr("y", 0)
                .attr("height", 90)
                .attr("width", 90)
                .attr("xlink:href", node.image);
        });

        var links = tree.links(nodes);

        svg.selectAll('.link').remove();

        if(movieCount > 0){
            var links = tree.links(nodes);

            svg.selectAll('.link').remove();

            var link = svg.selectAll(".link")
                .data(links)
                .enter().append("path")
                .attr("class", "link")
                .attr("d", diagonal);
        }

        svg.selectAll('.node').remove();
        svg.selectAll('.movieNode').remove();

        var node = svg.selectAll(".node")
            .data(nodes)
            .enter().append("g")
            .attr("class",  function(d) { return (d.type == 'movie1' || d.type == 'movie2') ? "movieNode" : "node"; })
            .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; })
            .on("mouseover", mouseover)
            .on("mouseout", mouseout)
            .on("click", nodeClick);

        node.append("circle")
            .attr("r", function(d) { return (d.type == 'movie1' || d.type == 'movie2') ? movieRadius : radius; })
            .attr("fill", function(d) { return "url(#" + d.id + ")"; });

        node.append("text")
            .attr("dx", -30)
            .attr("dy", function(d) { return (d.type == 'movie1' || d.type == 'movie2') ? 50 : 30; })
            .text(function(d) { return d.name; });

    }

    function mouseover(d) {
        d3.select(this).select("circle").transition()
            .duration(750)
            .attr("r", function(d) { return (d.type == 'movie1' || d.type == 'movie2') ? movieRadius + movieRadius / 2 : radius + radius / 2; });

        d3.select(this).select("text").transition()
            .duration(750)
            .attr("dy", function(d) { return (d.type == 'movie1' || d.type == 'movie2') ? 70 : 60; });
    }

    function mouseout() {
        d3.select(this).select("circle").transition()
            .duration(750)
            .attr("r", function(d) { return (d.type == 'movie1' || d.type == 'movie2') ? movieRadius : radius; })

        d3.select(this).select("text").transition()
            .duration(750)
            .attr("dy", function(d) { return (d.type == 'movie1' || d.type == 'movie2') ? 50 : 30; });
    }

    function nodeClick(d){

        if(d.type == 'movie1' || d.type == 'movie2') {
            url = 'movies/get?uri=';
        }
        else{
            url = 'persons/get?uri=';
        }

        window.location.href = baseUrl + url + d.id;
    }
});