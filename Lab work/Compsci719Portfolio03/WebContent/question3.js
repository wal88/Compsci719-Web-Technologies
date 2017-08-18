/* Develop your answer to Question 3 here */
"use strict";

var current_banner_num = 1;

// Timer variable
var timeoutID;
// Variable to check if timer is running,
// so that the slider does not start the timer when the user has clicked Stop
var timerRunning = false;
// variable for the Speed at which images change
var fadeSpeed=3000;

$(document).ready(setup);

function setup() {

    $("#banner-base").css("visibility","hidden"); // Use hidden to keep width and height of div same as image
    
    // Show the first image, then show and set opacity to 0 for other images
    $("#banner-1").show();
    for (var i = 2; i<5; i++) {
    	$("#banner-"+i).show();
    	$("#banner-"+i).css("opacity", 0);
    }
    
    /* An alternative to the above is to adjust the z-Indexes without any other changes:
     * var zIndexNum = 4;
     * $(".absolute-pos img").each(function(){
     *	$(this).show();
     *	$(this).css("zIndex",zIndexNum--);
     * }); 
     * */
    
    //setup Start button, 
    //note the code to change the image has been moved into a new function: crossfadeImage()
    var start_button = $("#start").button();
    start_button.click(function(){
    	timeoutID = window.setInterval(crossfadeImage, fadeSpeed);
    	timerRunning = true;
    });

    //setup Stop button    
    var stop_button = $("#stop").button();
    stop_button.click(function() {
    	clearInterval(timeoutID);
    	timerRunning = false;
    });
    
    //setup crossfade speed Slider
    $("#slider").slider({
    	min: 0,
    	max: 1500,
    	
    	change : function(event, ui) {
    		fadeSpeed=3000-ui.value;
    		// Only restart the Timer if the timer is running:
    		if (timerRunning) {
    			clearInterval(timeoutID);
    			timeoutID = window.setInterval(crossfadeImage, fadeSpeed);
    		}
    	}
    });
    
}

function crossfadeImage() {
	$("#banner-"+current_banner_num).animate({opacity: 0}, 1500);
	current_banner_num==4 ? current_banner_num = 1 : current_banner_num++;
	$("#banner-"+current_banner_num).animate({opacity: 1}, 1500);	
    }
		    
