function displayTime() {
	document.write(new Date());
}

function getDetails() {

	var pList = document.getElementsByTagName("p");

	for (var i = 0; i < pList.length; i++) {

		switch (i) {
		case 0:
			pList[i].innerHTML = navigator.appCodeName;
			break;

		case 1:
			pList[i].innerHTML = navigator.appName;

			break;

		case 2:
			pList[i].innerHTML = navigator.appVersion;

			break;

		case 3:
			pList[i].innerHTML = navigator.cookieEnabled;
			break;

		case 4:
			pList[i].innerHTML = navigator.geolocation;

			break;

		case 5:
			pList[i].innerHTML = navigator.language;

			break;

		case 6:
			pList[i].innerHTML = navigator.onLine;

			break;

		case 7:
			pList[i].innerHTML = navigator.platform;

			break;

		case 8:
			pList[i].innerHTML = navigator.product;

			break;

		case 9:
			pList[i].innerHTML = navigator.userAgent;

			break;

		case 10:
			pList[i].innerHTML = navigator.javaEnabled();

			break;

		case 11:
			pList[i].innerHTML = navigator.taintEnabled();

			break;

		}
	}

}