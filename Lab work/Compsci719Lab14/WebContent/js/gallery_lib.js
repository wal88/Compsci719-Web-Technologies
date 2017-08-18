"use strict";

// To understand the following script, make sure to have gone through the examples in p.345 and p.347 of the textbook

// declaring AND initialising a JSON array, see http://www.w3schools.com/json/
// The imageCollection is declared in the <head>, so the rest of the page including any scripts further down will know about it
var imageCollection = [	
	{ "name":"arctic_fox", "description":"An Arctic fox." },
	{ "name":"grazing_wombat", "description":"A wombat grazing." },
	{ "name":"Himalayan_Pika", "description":"The Himalayan pika is a mammal too." },
	{ "name":"lynx", "description":"A lynx has pointy ears." },
	{ "name":"Pallas_Cat", "description":"A Pallas cat is also called a manul." },
	{ "name":"Pallas_Cat2", "description":"Pallas cats live at high altitudes and are wild animals." },
	{ "name":"pika", "description":"Pikas occur in certain mountainous regions of the world." },
	{ "name":"quokka", "description":"The quokka is a marsupial that looks like it's always smiling." },
	{ "name":"RedPanda", "description":"The nearest Red pandas are probably in your local zoo." },
	{ "name":"wombat2", "description":"Wombats are found in Australia." },
	{ "name":"wombats", "description":"A wombat family at lunch time." }
];

var selectedImage = 0; // index of image selected, between 0 and length of array non-inclusive


function changeImage(numImage) {
	
	var featuredImage = document.getElementById("featuredImage");			
	
	featuredImage.src = "images/" + imageCollection[numImage].name + ".jpg";
	
	// Setting the title and alt attributes
	featuredImage.alt = imageCollection[numImage].name;
	featuredImage.title = imageCollection[numImage].name;
	
	// now set the description associated with the image in div#descriptionText 
	var descriptionDiv = document.getElementById("descriptionText");		
	descriptionDiv.innerHTML = "<p>" + imageCollection[numImage].description + "</p>";		
	
	// finally, ensure that the thumb-holder is highlighted and the previous thumb-holder isn't highlighted any more
	
	var thumbHolders = thumbview.getElementsByClassName("thumb-holder");
	var prevClickedImageHolder = thumbHolders[selectedImage];
	prevClickedImageHolder.style.backgroundColor = "LightGray";
	
	var clickedImageHolder = thumbHolders[numImage];	
	clickedImageHolder.style.backgroundColor = "LightBlue";
	
	// leave this line at the end of this function
	selectedImage = numImage; // keep track for next time of what image has been selected
	
}

function loadRandomImage() {
	// Generate the random index into the array of images:
	// Math.random returns a random decimal point number between 0 and 1
	// We want a *whole* number (int) between 0 inclusive and the number of images exclusive
	// To get a whole number, use Math.floor() to round down.
	// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Math/random
	var numImage = Math.floor(Math.random() * (imageCollection.length)); 
	
	// Now we know the random image we want to show, so change the featured image:
	changeImage(numImage); 
}	

function writeOutImageElements() {
	var i = -1;		
	var divThumbholder = null;
	var imgElement = null;
	
	var thumbView = document.getElementById("thumbview");
	var numImages = Object.keys(imageCollection).length; 		
	
	console.log("Number of images: " + numImages);
			
	// We want to create children for thumbView of the form:
	// <div class="thumb-holder"><img src="images/thumbs/0.gif" alt="thumb" onclick="changeImage(0)" /></div>
	
	for(i = 0; i < numImages; i++) {
		divThumbholder = document.createElement("div");
		divThumbholder.setAttribute("class", "thumb-holder");
		
		imgElement = document.createElement("img");
		imgElement.setAttribute("src", "images/thumbs/" + i + ".gif");
		imgElement.setAttribute("alt", "thumb");
		imgElement.setAttribute("onclick", "changeImage(" + i + ")");
		
		divThumbholder.appendChild(imgElement);
		thumbView.appendChild(divThumbholder);		
	}
}

// moving from "<body onload=...>" to javascript file here.
$(window).load(function() {
	writeOutImageElements();
	loadRandomImage();
});