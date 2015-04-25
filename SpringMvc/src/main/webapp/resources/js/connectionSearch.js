$(function() {
	var contentPos = $('.content').position().top;
			
	$('#connectionSearch').animate({ "top": "+=" + contentPos + "px" }, "slow" );
	
	$('#widgets > h2 > a').on('click', function (e) {
		e.preventDefault();
		
		var divId = $(this).attr('id').replace('Link', '');
		
		var widgetDiv = document.getElementById(divId);
	     
		if (widgetDiv.style.display == "none"){
			widgetDiv.style.display = "";
			
			var top = $('#' + divId).position().top - $('#connectionSearch').position().top;
			$('#connectionSearch').animate({ "top": "+=" + top + "px" }, "slow" );
			
			$('#connectionSearchType').val($('#' + divId + ' > input').val());
			
		} else {
			widgetDiv.style.display = "none";
		}
	});
	
	$("#connectionSearchInput")
		.focusin(function() {
			this.value = '';
		})
		.focusout(function() {
			this.value = 'Search...';
		})
		.on('input', function() {
		
			var value = $(this).val();
			
			if(value.length > 2){
				
				var searchUrl = '/home/search';
				
				if($('#connectionSearchType').val() == 'persons'){
					searchUrl = '/persons/search';
				} else if($('#connectionSearchType').val() == 'movies') {
					searchUrl = '/movies/search';
				}
				
				$.getJSON(searchUrl, { term: value, count: 6 } )
					.done(function(data) {
						
						$("#connectionSearchResult").empty();
						
						$.each(data, function(i, item) {
							$("#connectionSearchResult").append('<p id="' + data[i].uri + '" class="ui-state-default">' + data[i].label + '</p>');
						});
						
						$("#connectionSearchResult > p").draggable({ opacity: 0.7, helper: "clone" });
					});
			}
	});
});