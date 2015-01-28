<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">

	<script src="<c:url value="/resources/js/d3.v3.js" />" charset="utf-8"></script>
	<script src="<c:url value="/resources/js/jquery-1.10.1.min.js" />" charset="utf-8"></script>
	<script src="<c:url value="/resources/js/autocomplete.js" />" charset="utf-8"></script>
	<script>
       
       	var movieId = ${id};
       
	  	$(function() {
	  		$('#startJob').on('click', function (e) {
	  			e.preventDefault();
			  	$.ajax({
		            dataType: 'text',
		            contentType: "application/text;charset=UTF-8",
		            type: 'GET',
					url: '/MovieDB/home/startJob',
					data: { id: movieId}
		        }).success(function(data, textStatus, jqXHR){
		        	
		        	movieId = data;
		        	
		        	$('#jobStatus').html("ID: " + data);
		        	
		        }).fail(function( jqXHR, textStatus ) {
		        	console.log(jqXHR.status);
		        	console.log( "Request failed: " + textStatus );
		        });
	  		});
	  	});
  	</script>


	<a href="#" id="startJob">Start job</a>
	<br/>
	<div><span id="jobStatus"></span></div>
	<br />
	
	<div id="search" style="width:100%; height:100%;">

	</div>
	<script>

    //Variable to hold autocomplete options
    var keys;

    //Load US States as options from CSV - but this can also be created dynamically
    d3.csv("resources/states.csv",function (csv) {
        keys=csv;
        start();
    });

    //Call back for when user selects an option
    function onSelect(d) {
        alert(d.State);
    }

    //Setup and render the autocomplete
    function start() {
        var mc = autocomplete(document.getElementById('search'))
                .keys(keys)
                .dataField("State")
                .placeHolder("Search States - Start typing here")
                .width(960)
                .height(500)
                .onSelected(onSelect)
                .render();
    }

	</script>
	
	</tiles:putAttribute>
</tiles:insertDefinition>