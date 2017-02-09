function Person(first, last, email, user, pass) {
	this.firstName = first;
	this.lastName = last;
	this.email = email;
	this.userName = user;
	this.password = pass;
}

function saveUser() {
	if (validInput()) {
		$.ajax({
			cache : false,
			url : 'master.do',
			type : 'POST',
			data : {
				"editUser" : JSON.stringify(new Person($("#first_name").val(), $("#last_name").val(), $("#email").val(), $("#username").val(), $("#password").val()))
			},
			success : function(result, status, xhr) {
				$('#message').text(xhr.responseText);
			},
			error : function(xhr, status) {
				$('#message').text("Edit Failed");
			},
			complete : function(xhr, status) {
				console.log("complete");
			}
		});
	}
	else{
		$('#message').text("Invalid Edit");
	}
}

function validInput(){
	if($("#first_name").val().length > 0 && $("#last_name").val().length > 0 
	&& $("#email").val().length > 0 && $("#username").val().length > 0 
	&& $("#password").val().length > 0 && validateEmail($("#email").val())) {
		return true;
	}
	return false;
}

function validateEmail(sEmail) {
	var filter = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/;
	if (filter.test(sEmail)) {
	return true;
	}
	else {
	return false;
	}
}

window.onload = function() {
	document.getElementById("saveEditButton").addEventListener("click", saveUser, false);
}