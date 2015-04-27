<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="<c:url value="/resources/js/topojson.v1.min.js" />"></script>
<script src="<c:url value="/resources/js/datamaps.world.min.js" />"></script>
<script src="<c:url value="/resources/js/topojson.js" />"></script>
<div id="movieMap"></div>
<script>

	$(function() {
		
		var width = $("#widgets").width();
		
	    $('#movieMap > svg').attr("width", width)
							.attr("height", 350);
	});

    var map = new Datamap({
    	width: width,
    	height: 350,
        element: document.getElementById('movieMap'),
        fills: {
            MEDIUM: '#56AED4',
            defaultFill: '#888888'
        },
        dataUrl: baseUrl + 'movies/getCountries?uri=' + movieID,
		data: { },
        geographyConfig: {
            popupTemplate: function(geo, data) {
                return ['<div class="hoverinfo"><strong>',
                        geo.properties.name,
                        '</strong></div>'].join('');
            }
        }
    });
    
    
    
</script>