"use strict";

function displayTime() {
	document.write(new Date());
}

function getDetails() {

	var pList = document.getElementsByTagName("p");
	
	var propertyArray = new Array();
	var string = "";
	var i=0;
	for (var property in navigator) {
		string = string + i + ". ";
		i++;
		if (typeof property !== "function" && typeof property !== "object") {
			string = string + property + ": " + navigator[property] + "<br>";
			propertyArray.push(property);
		}
	}
	document.getElementById("main").innerHTML = string;

}