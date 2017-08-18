// TODO Your JS student email validation code goes here (Exercise 4 part 3).
function isUniEmail () {
	console.log("ends with uni suffix? " + emailfield.value.endsWith("@aucklanduni.ac.nz"));
	if (emailfield.value.endsWith("@aucklanduni.ac.nz"))
		return true;
	else 
		return false;
}

function notUniEmailAlert () {
	alert("This email is not a University email.");
}

