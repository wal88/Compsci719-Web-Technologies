function val() {
	console.log("a");
    var s = document.getElementById('ca').value;
    var chars = s.split(' ');
    if (chars.length > 1) {
        alert('no space allowed in caption!');
        console.log("b");
        return false;
    }
   var ext = $('#ck').val().split('.').pop().toLowerCase();
    if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
        alert('invalid extension!');
        console.log("c");
        return false;
    }
    else if($("#ck")[0].files[0].size>5242880){
            alert('invalid size!');
            console.log("d");
            return false;
    }
    else{
    	console.log("e");
    	return true;
    	
    }



}

