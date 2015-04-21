$(function() {
	$("#personMovieDroppable").droppable({
		accept: "#personSearchResult > p",
		activeClass: "ui-state-hover",
		hoverClass: "ui-state-active",
		drop: function(event, ui) {
			
			var id = ui.draggable.attr('id');
			
			$.getJSON("/MovieDB/movies/byActors", { actor1: personID, actor2: id } )
			.done(function(data) {
				
				$(this).addClass("ui-state-highlight");
				
				$("#personMovieDroppable > p").html('Filme mit ' + ui.draggable.text() + ':<br />');
				
				if(data.length == 0){
					$("#personMovieDroppable > p").append('Keine gemeinsamen Filme!');
				}
				else {
					$.each(data, function(i, item) {
						$("#personMovieDroppable > p").append('<a href="/MovieDB/movies/get?uri=' + data[i].uri + '">' + data[i].title + '</a><br />');
					});
				}
			});
		}
	});
});