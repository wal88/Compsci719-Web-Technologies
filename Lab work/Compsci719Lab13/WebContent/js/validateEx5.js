/**
 * 
 */

var isImage = false;
var isRightSize = false;
var captionNotSpaces = false;

function checkImageAndCorrectSize(input) {

	if (input.files && input.files[0]) {
		var file = input.files[0];
		var fileType = file.type.toString();

		if (fileType.endsWith("png") || fileType.endsWith("jpeg")
				|| fileType.endsWith("gif")) {
			isImage = true;
		} else {
			alert("This file is not a supported image type. Supported types are PNG, JPEG and GIF. Please try again before Submitting.");
			isImage = false;
		}

		if (file.size < 5000000) {
			isRightSize = true;
		} else {
			alert("This image file is too big, please upload an image smaller than 5megabytes.")
			isRightSize = false;
		}
	}
}

function validate() {
	if (isImage) {
		if (isRightSize) {
			if (captionNotSpaces) {
				alert("Thanks. Your image and caption was accepted.");
				return true;
			} else {
				alert("Your caption only has spaces. Please enter a caption");
			}
		} else {
			alert("This file is too big.");
		}
	} else {
		alert("This file is not a supported image type. Supported types are PNG, JPEG and GIF.");
	}
}

function checkCaption() {

	var caption = document.getElementById("caption").value;
	console.log(caption);
	console.log(caption.replace(/\s/g, '').length);
	if (caption.replace(/\s/g, '').length != 0) {
		captionNotSpaces = true;
	} else {
	captionNotSpaces = false;
	}
}
