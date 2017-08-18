function displayTime() {
	document.write(new Date());
}

function exercise4() {
	var divNodeList = document.getElementsByTagName("div");
	divNodeList[0].style.backgroundColor = "pink";

	var pList = document.getElementsByTagName("p");
	pList[(pList.length - 1)].style.color = "red";

	divNodeList[1].style.visibility = "hidden";

	pList[0].innerHTML = "Hello World!";

}

function pBold() {

	var pList = document.getElementsByTagName("p");

	if (pList[0].style.fontWeight != 'bold') {
		for (var i = 0; i < pList.length; i++) {
			pList[i].style.fontWeight = 'bold';
		}
	} else {
		for (var i = 0; i < pList.length; i++) {
			pList[i].style.fontWeight = 'normal';
		}
	}

}