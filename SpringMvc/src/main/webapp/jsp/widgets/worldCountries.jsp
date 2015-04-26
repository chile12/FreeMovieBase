
<script src="http://d3js.org/d3.v3.min.js"></script>
<script src="http://d3js.org/topojson.v1.min.js"></script>
<script src="http://rawgithub.com/markmarkoh/datamaps/master/dist/datamaps.none.min.js"></script>
<script src="/resources/js/worldD3.js"></script>
<div id="container" style="height: 500px; width: 900px;"></div>
<script>
    var map = new Datamap({
        element: document.getElementById('container'),
        fills: {
            HIGH: '#afafaf',
            LOW: '#123456',
            MEDIUM: 'blue',
            UNKNOWN: '#FFFFFF',
            defaultFill: 'green'
        },
        data: {
            "IRL": {
                "fillKey": "LOW",
                "numberOfThings": "2002"
            },
            "USA": {
                "fillKey": "MEDIUM",
                "numberOfThings": "10381"
            }
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
</script>