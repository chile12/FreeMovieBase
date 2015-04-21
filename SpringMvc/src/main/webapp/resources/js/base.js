$(function() {
	$("#search")
		.focusin(function() {
			this.value = '';
		})
		.focusout(function() {
			this.value = '';
		})
		// don't navigate away from the field on tab when selecting an item
		.bind("keydown", function(event) {
			if (event.keyCode === $.ui.keyCode.TAB &&
					$(this).autocomplete("instance" ).menu.active) {
				event.preventDefault();
			}
		})
		.autocomplete({
			source: function(request, response) {
				$.getJSON("home/search", {
					term: request.term
				}, response);
			},
			search: function() {
				// custom minLength
				if (this.value.length < 2) {
					return false;
				}
			},
			focus: function() {
				// prevent value inserted on focus
				return false;
			},
			select: function(event, ui) {
				this.value = ui.item.value;
				
				var url;
				
				if(ui.item.type == 'person') {
					url = '/persons/get?uri=';
				}
				else if(ui.item.type == 'movie') {
					url = '/movies/get?uri=';
				}
				
				window.location.href = url + ui.item.uri;
				
				return false;
			}
		});
});