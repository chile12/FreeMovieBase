<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<c:set var="controller" value="home" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<c:url value="/resources/css/layout.css" />" rel="stylesheet">
        
        <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
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
        
        
        <title>MOVIE_DB</title>
    </head>

    <body>
        <jsp:include page="/jsp/header.jsp" />
        
        <div class="wrapper">
            <div id="container">
                <div id="content">
                
                <a href="#" id="startJob">Start job</a>
				<br/>
				<div><span id="jobStatus"></span></div>
				<br />
                
                    <p>Hello! This is the default welcome page for a Spring Web MVC project.</p>
                    <p><i>To display a different welcome page for this project, modify</i>
                        <tt>index.jsp</tt> <i>, or create your own welcome page then change
                            the redirection in</i> <tt>redirect.jsp</tt> <i>to point to the new
                            welcome page and also update the welcome-file setting in</i>
                        <tt>web.xml</tt>.</p>
                </div>
            </div>
        </div>
    </body>
</html>