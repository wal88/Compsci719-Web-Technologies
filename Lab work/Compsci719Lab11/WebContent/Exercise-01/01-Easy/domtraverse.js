"use strict";

// Exercise 4.1
var bodyElement = $(document.body);
var divs = $("div");
divs.first().css("background-color", "pink");

// Exercise 4.2
/*
 * //One way to get the last p and make it red: var paragraphs =
 * bodyElement.getElementsByTagName("p"); lastParagraph =
 * paragraphs[paragraphs.length-1]; lastParagraph.style.color="red";
 */

// Another way, better:
var lastParagraph = $("p").last(); // Note, getElementById
														// is a method of the
														// document node, not a
														// method of any element
														// nodes!!!
lastParagraph.css("color", "red");

// Exercise 4.3
$("div:eq( 1 )").hide();
/*
 * // Another way. But unlike setting visibility, this way does not preserve the
 * space allocated to the div (the 'block' display of divs):
 * divs[1].style.display="none";
 */

// Exercise 4.4
// One way:
// we get back a list of all elements with class=subtitle, even though the list
// returned only happens to have one item this time.
var subtitles = $(".subtitle");
// get the first subtitle, get its first child (<small>), get its first child
// which is a textnode and then the latter's value
$(".subtitle:eq(0):first").text("Hello World");

// Exercise 4.5
var myButton = $("#makeBold");
// var myButtons = bodyElement.getElementsByTagName("button");
// console.log(myButtons[0]);

// When you click the button, set the style for all paragraphs. Accomplished
// without looping,
// uses createElement() to create a <style> element. See
// http://www.w3schools.com/jsref/dom_obj_style.asp
myButton.click( function() {
	var styleElement = $("p");
	styleElement.css("font-weight", "bold");
	
});
