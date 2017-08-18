"use script";

function makePageInteractive() {
	var container = $("#container");
	var baubles = $(".bauble");
	var i = -1;

	baubles.on("mouseover", function(){  // could also do this
			// baubles[i].onclick
			// 'this' in this function refers to the current bauble

			// You can adjust the class attribute (className property) of THIS
			// bauble by appending
			// another class to it, and then have the animation attached to this
			// additional class

			// We don't want to overwrite any existing classes on the bauble,
			// and don't want to append
			// the same class multiple times (as classnames should appear only
			// once in any element's class attribute list)
			// So ideally, we use the element.classList.add() method. But this
			// is not supported
			// in all browsers, so use feature sensing

			// http://stackoverflow.com/questions/507138/how-do-i-add-a-class-to-a-given-element#comment323025_507157
			// http://www.w3schools.com/jsref/prop_element_classlist.asp
			// https://developer.mozilla.org/en/docs/Web/API/Element/classList
			 
				 $( this ).addClass("fall");		
			
			

			/*
			 * // I opted for adding a className because the actual class can be
			 * styled in CSS instead // of setting the details of the CSS style
			 * properties in this JavaScript code. // But an alternative is to
			 * set the CSS animation attributes here in the JavaScript itself: //
			 * http://stackoverflow.com/questions/708895/how-to-set-the-style-webkit-transform-dynamically-using-javascript
			 * 
			 * this.style.webkitAnimation = "dropbounce 4s forwards";
			 * this.style.animation = "dropbounce 4s forwards";
			 * 
			 */
	});
}

// window.onload = makePageInteractive; // don't call the function here, assign
// it as the handler.
// That is, don't use the round function call brackets: window.onload =
// makePageInteractive();

$(document).ready(function() { 
	
	makePageInteractive();
});
