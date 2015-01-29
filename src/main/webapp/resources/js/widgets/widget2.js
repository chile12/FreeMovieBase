$(function() {
	$('#element1Link').on('click', function (e) {
			e.preventDefault();
			
			var e2 = document.getElementById('element1');
			
			if (e2.style.display == "none"){
				e2.style.display = "";
			} else {
				e2.style.display = "none";
			}
		});
	});