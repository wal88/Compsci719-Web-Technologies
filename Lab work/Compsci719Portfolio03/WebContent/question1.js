/* Develop your answer to Question 1 here */

// Question 1, a
$(document).ready(setup);

function setup() {

	// Question 1, b
	$("#title-name").css("text-transform", "uppercase");

	// Question 1, c
	$("h3").each(function() {
		$(this).css("font-style", "italic");
	});
	// a concise way without using $(this) is: $("h3").css("font-style", "italic");

	// Question 1, d
	$("#introduction p").css("font-size", "120%");

	// Question 1, e
	$("p").each(function() {
		var cleanedText = $(this).html().replace(/\[.+?\]/g, "");
		$(this).html(cleanedText);
	});
	//can also be done without looping using $("body").html()

	// Question 1, f
	var readMoreDiv = $("div#additional");
	var readMoreText = readMoreDiv.html();
	readMoreDiv.html("Click here to read more ...");
	readMoreDiv.on("click", function() {
		readMoreDiv.html(readMoreText);
	});
}
