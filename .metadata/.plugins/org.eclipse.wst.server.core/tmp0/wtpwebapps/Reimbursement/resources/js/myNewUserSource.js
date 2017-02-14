function Person(first, last, email, ide) {
	this.firstName = first;
	this.lastName = last;
	this.email = email;
}

function saveUser() {
	if (validInput()) {
		$.ajax({
			cache : false,
			url : 'master.do',
			type : 'GET',
			data : {
				"newUser" : JSON.stringify(new Person($("#first_name").val(), $("#last_name").val(), $("#email").val())),
				"role" : $("#roles").val()
			},
			success : function(result, status, xhr) {
				if(xhr.responseText === "Creation Successful")
				{
					$("#first_name").val("");
					$("#last_name").val("");
					$("#email").val("");
				}
				$('#message').text(xhr.responseText);
			},
			error : function(xhr, status) {
				$('#message').text("Creation Failed");
			},
			complete : function(xhr, status) {
				console.log("complete");
			}
		});
	}
	else{
		alert("Invalid User Input");
	}
}

function validInput(){
	if($("#first_name").val().length > 0 && $("#last_name").val().length > 0 
	&& $("#email").val().length > 0 && validateEmail($("#email").val())) {
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
	var userButton = document.getElementById("saveUserButton")
			.addEventListener("click", saveUser, false);
}