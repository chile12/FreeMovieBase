<script src="http://d3js.org/d3.v3.min.js"></script>
<script src="http://d3js.org/topojson.v1.min.js"></script>
<script src="http://datamaps.github.io/scripts/datamaps.world.min.js"></script>
<script src="http://datamaps.github.io/scripts/topojson.js"></script>
<div id="container" style="position: relative; width: 500px; height: 300px;"></div>

<script>
    var data;

    var map = new Datamap({
        element: document.getElementById('container'),
        fills: {
            HIGH: '#afafaf',
            LOW: '#123456',
            MEDIUM: 'blue',
            UNKNOWN: '#FFFFFF',
            defaultFill: 'green'
        },
        geographyConfig: {
            popupTemplate: function(geo, data) {
                console.log(data)
                return ['<div class="hoverinfo"><strong>',
                    'Number of things in ' + geo.properties.name,
                    ': ' + data[geo.id].numberOfThings,
                    '</strong></div>'].join('');
            }
        }
    });
    map.legend();


    d3.json("path/to/data.json", function(error, json) {
        data =
        map.updateChoropleth(data);
    });
</script>