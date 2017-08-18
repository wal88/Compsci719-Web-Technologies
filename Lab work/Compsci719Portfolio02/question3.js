"use strict";

var grouped_images =
    {"Christchurch":
     ["christchurch8105352.jpeg","christchurchcathedral8105329.jpg",
      "christchurchchathedral8105387.jpg","christchurchpress8105370.jpg",
      "christchurchurchartgallery8105441.jpg","christchurchurchartgallery8105444.jpg",
      "christchurchurchartgallery8105448.jpg"]
     ,
     "Queenstown":
     ["queenstown8135665.jpg","queenstown8135678.jpg","queenstownjetty8135650.jpg"]
     ,
     "Remarkables":
     ["remarkables8135623.jpg","remarkables8135627.jpg","remarkables8145706.jpg",
      "remarkables8155745.jpg","remarkables8155746.jpg","remarkables8155750.jpg",
      "remarkables8155753.jpg","remarkables8155754.jpg"]
    };


function generateThumbnailFilename(img_filename) {

    var img_root = img_filename.substring(0, img_filename.indexOf('.'));

    return "images/thumbnails/" + img_root + "_thumbnail.jpg";
}



function addLinkedImage(divElem, img_filename)
{
    // Complete this function.
    //
    // This function should do everything that the version developed in
	// Question 2 does, and *additionally* build a hyperlink around the
	// thumbnail image that is linked to the full-size image

    // Start by copying over the code you developed for Q2, and then extend!
	var thumbnailName = generateThumbnailFilename(img_filename);
	
	var hyperlinkElement = document.createElement("a");
	hyperlinkElement.href = "images/" + img_filename;
	
	var imgElement = document.createElement("img");
	imgElement.src = thumbnailName;

	hyperlinkElement.appendChild(imgElement);
	divElem.appendChild(hyperlinkElement);
}

function addHeading(divElem, text)
{
    // Complete this function.
    //
    // This functcion should create an 'h2' element, set its innerHTML
    // to be the 'text' passed in, and then append the heading as a
    // child element of 'divElem'    
	var h2Element = document.createElement("h2");
	h2Element.innerHTML = text;
	
	divElem.appendChild(h2Element);
}


function displayViewer()
{
    // Refer to Question 3 of the test to read about what this top-level
	// JavaScript function implements

    var mainviewerDiv = document.getElementById("mainviewer");

    for (var key in grouped_images){
    	//For each group, create a header div and add the heading
    	var currentDiv = document.createElement("div");
    	addHeading(currentDiv, key);
    	mainviewerDiv.appendChild(currentDiv);
    	    	
    	for (var i = 0; i < grouped_images[key].length; i++) {
    		
    		//test to see if current index is the first or a multiple of 3,
    		//if so, then create and use a new div
    		if (i%3 === 0) {
    			currentDiv = document.createElement("div");
    			currentDiv.className = "divcenter";
    		}
    		
    		addLinkedImage(currentDiv, grouped_images[key][i]);
    		
    		//check if i is the third or last index,
    		//if so append currentDiv to mainviewerDiv before new one is created
    		if (i%3 === 2 || i === grouped_images[key].length-1) {
    			mainviewerDiv.appendChild(currentDiv);
    		}
    	}
    	
//		var p_elem = document.createElement("p");
//		p_elem.innerHTML = "Group Heading: " + key;
//		mainviewerDiv.appendChild(p_elem);	

    }
}

