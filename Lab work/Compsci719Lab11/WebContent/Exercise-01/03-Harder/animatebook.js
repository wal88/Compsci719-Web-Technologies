"use script";

// addEventHandler() adds event handlers to elements for specified events in a
// cross-browser way
// To work with older browsers, see
// http://stackoverflow.com/questions/1019078/how-to-set-onclick-attribute-with-value-containing-function-in-ie8

// VERY USEFUL, GENERIC ADD EVENT HANDLER FUNCTION, mostly likely will need this
// later on
function addEventHandler(element, nameOfEvent, handler) {

	if (element.addEventListener) {
		element.addEventListener(nameOfEvent, handler, false);
	} else if (element.attachEvent) {
		element.attachEvent("on" + nameOfEvent, handler);
	} else {
		nameOfEvent = "on" + nameOfEvent;
		element.nameOfEvent = handler; // e.g. window.onload = functionName;
	}
}

 function addEventHandler(element, nameOfEvent, handler) {
	
 var nameOfEvent = nameOfEvent.substr(3);
 element.on(nameOfEvent, function(){
 handler;
		
 });
 }

// Call this function when wanting to assign an eventhandler that works with a
// particular page:
// Note how this function returns a function, instead of calling a function or
// returning a value.
// That's because the caller (addEventListener/attachEvent) expects a function
// to be returned.
// http://stackoverflow.com/questions/750486/javascript-closure-inside-loops-simple-practical-example
// http://stackoverflow.com/questions/19586137/addeventlistener-using-for-loop-and-passing-values

function bringNextPageToFront(currPageNum) {
	var nextPageNum = currPageNum + 1;

		var nextPageId = "page" + nextPageNum;
		var nextPage = $("#" + nextPageId);
		if (nextPage) { // if a next page exists (not null), bring it to front
			nextPage.css("z-index", "1");
		}
	
}

// Called upon onload of window
// Attaches all other listeners to elements, since when the page has loaded,
// the elements would exist at last, including document
function doLoad() {

	// attach click event handlers to the thumbnails
	var pages = $(".page");

	var page;
	var pageNum = -1; // handier to keep track of page number here
	// so the next page can be brought up front when the current page has
	// finished turning

	// Programmatically:
	// - get rid of existing animation delays on pages
	// - add click handlers to animate each page turning over
	// - add handlers for end of animation on each page: make next page move up
	// front in the stacking order

	pages.on("click", function() {
		pageNum++;
		page = $(this);
		page.addClass("animatePage");
		page.css("animation-delay", "0s");
		console.log("inside on click");
		
		page.on("webkitAnimationEnd animationend", function() {
			bringNextPageToFront(pageNum);
		});
		
		
	});

	/*
	 * pages.each( function() {
	 * 
	 * page = $(this); page.css("animation-delay", "0s");
	 * 
	 * page.on("click", function() { page.addClass("animatePage"); });
	 * 
	 * page.on("webkitAnimationEnd animationend", function() {
	 * bringNextPageToFront(pageNum);
	 * 
	 * });
	 * 
	 * pageNum++; });
	 */

	/*
	 * for (pageNum = 0; pageNum < pages.length; pageNum++) { page =
	 * pages[pageNum]; // console.log("Removing delay on page:" + page.id);
	 * page.style.animationDelay = "0s"; // no delay, just animate when the //
	 * user clicks on the image addEventHandler(page, "click", function() { //
	 * 'this' will refer to the specific clicked page at the point the //
	 * function is executed if (this.classList) { // feature sensing for safely
	 * adding another // class to the page's class attribute
	 * this.classList.add("animatePage"); } else { this.className += "
	 * animatePage"; // fallback way. Don't // forget the space to // separate
	 * class // note that "class" is a reserved word, so the property //
	 * 'className' has been defined for the class attribute instead } //
	 * console.log("Animating: " + this.id); }); // When the animation on this
	 * page has ended, bring the next page up // front in the z-index stacking
	 * order // http://www.w3schools.com/jsref/event_animationend.asp
	 * addEventHandler(page, "webkitAnimationEnd",
	 * bringNextPageToFront(pageNum)); // Code for Chrome, Safari and // Opera
	 * addEventHandler(page, "animationend", bringNextPageToFront(pageNum)); //
	 * Standard // syntax }
	 */
}

$(document).ready(
	doLoad
);