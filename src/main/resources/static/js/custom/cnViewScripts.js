// Toggle between showing and hiding the sidenav, and add overlay effect
function w3_open() {
	var overlayBg = document.getElementById("myOverlay");
	// Get the Sidenav
	var mySidenav = document.getElementById("mySidenav");
	if (mySidenav.style.display === 'block') {
		mySidenav.style.display = 'none';
		overlayBg.style.display = "none";
	} else {
		mySidenav.style.display = 'block';
		overlayBg.style.display = "block";
	}
}

// Close the sidenav with the close button
function w3_close() {
	// Get the Sidenav
	var mySidenav = document.getElementById("mySidenav");
	mySidenav.style.display = "none";
	document.getElementById("myOverlay").style.display = "none";
}

function slide_right(id) {
	var elements = $('.'+id);
	for(var i = 0; i<elements.length; i++){
		if($(elements[i]).hasClass('w3-hide')) {
			$(elements[i]).removeClass('w3-hide');
			$(elements[i == elements.length-1 ? 0:i+1]).addClass('w3-hide');
			break;
		}
	}
}
function slide_left(id) {
	var elements = $('.'+id);
	for(var i = 0; i<elements.length; i++){
		if($(elements[i]).hasClass('w3-hide')) {
			$(elements[i]).removeClass('w3-hide');
			$(elements[i == 0 ? elements.length-1:i-1]).addClass('w3-hide');
			break;
		}
	}
}