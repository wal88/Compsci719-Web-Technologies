

function setupOnclick() {

	for (var i = 0; i < 12; i++) {
		var pageName = "page" + i;
		var page = document.getElementById(pageName);
		page.addEventListener("click", delegateTurn(i), false);

	}
}

function delegateTurn(i) {
	return function(mouseEvent) {
		turn(i);
	}
}

function turn(i) {
	var pageName = "page" + i;
	var page = document.getElementById(pageName);
	page.className += " pageAnimation";
	
	var pageNameOfSecond = "page" + (i+1);
	var secondPage = document.getElementById(pageNameOfSecond);
	setTimeout(function(){
		secondPage.style.zIndex = 5;
	}, 500);
	
}



function setupOnclick() {
	 for (var i = 0; i<7; i++) {
	    var editButton = document.getElementById("edit"+i);
	    editButton.addEventListener("click", delegateEditComment(i), false);
	}
}

function delegateEditComment(i) {
	return function(mouseEvent) {
		editComment(i);
	}
}

// changes the website text to the edited text
function editComment(i) {
	var commentBox = document.getElementById("comment"+i);
	var editCommentPrompt = prompt("Please edit your comment", commentBox.innerHTML);
	 	if (editCommentPrompt != null) {
	 	commentBox.innerHTML = editCommentPrompt;
	 	/* var url = window.location.href;    
		if (url.indexOf('?') > -1){
	   		url += '&editComment='
		}else{
	   		url += '?param='
		}
		window.location.href = url;
	*/} 
}

function editComment(comment_id) {
	var commentBox = document.getElementById(comment_id);
	var editCommentPrompt = prompt("Please edit your comment", commentBox.innerHTML);
	 	if (editCommentPrompt != null) {
	 	commentBox.innerHTML = editCommentPrompt;
	 	var url = window.location.href;    
	 	url += (url.indexOf('?') == -1 ? '?' : '&') + 'editComment=' + comment_id + '&newContent=' + editCommentPrompt;
		window.location.href = url;
	}
}

function setupOnclick() {
	var commentBoxes = document.getElementsByClassName("comment");
	for (var i = 0; i<commentBoxes.length; i++) {
	    var comment_id = commentBoxes[i].getAttribute("id");
		var editButton = document.getElementById("edit"+comment_id);
	    editButton.addEventListener("click", delegateEditComment(comment_id), false);
	}
}

function delegateEditComment(comment_id) {
	return function(mouseEvent) {
		editComment(comment_id);
	}
}

// changes the website text to the edited text
if(document.getElementById("edit"+comment_id).innerHTML == "Done") {
	//Change the textarea element back to a p element
	var commentBoxEditting = document.getElementById(comment_id);
	var commentBox = document.createElement("p");
	commentBox.innerHTML = commentBoxEditting.innerHTML;
	commentBox.setAttribute("id", comment_id);
	commentBoxEditting.parentNode.replaceChild(commentBox, commentBoxEditting);
	//change Done button back to Edit
	var editButton = document.getElementById("edit" + comment_id);
	editButton.innerHTML = "Edit";
	//Send an Ajax request to update the database, note no response data is required
	var xmlrequest=new XMLHttpRequest();
 	xmlrequest.open("GET",'commentSubmit?editComment=' + comment_id + '&newContent=' + commentBox.innerHTML);
	xmlrequest.send(null);
}