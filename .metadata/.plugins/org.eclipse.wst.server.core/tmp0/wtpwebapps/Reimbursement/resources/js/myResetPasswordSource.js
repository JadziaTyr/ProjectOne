function resetPassword() {
	if (validEmail()){
		$.ajax({
			cache : false,
			url : 'master.do',
			type : 'POST',
			data : {
				"resetEmail" : $("#email").val()
			},
			success : function(result, status, xhr) {
				$('#message').text(xhr.responseText);
			},
			error : function(xhr, status) {
				$('#message').text("Email Failed");
			},
			complete : function(xhr, status) {
				console.log("complete");
			}
		});
	}
	else{
		$('#message').text("Invalid Email");
	}
}

function validEmail() {
	if ($("#email").val().length > 0 && validateEmail($("#email").val())) {
		return true;
	}
	return false;
}

function validateEmail(sEmail) {
	var filter = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/;
	if (filter.test(sEmail)) {
		return true;
	} else {
		return false;
	}
}

window.onload = function() {
	document.getElementById("resetPassword").addEventListener("click",
			resetPassword, false);
}