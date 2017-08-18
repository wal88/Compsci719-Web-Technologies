function val() {
    var eadd = document.getElementById('Email').value;
    var el = eadd.length;
    var po = eadd.indexOf("@")+1;
    var tova = eadd.substr(po,el);
    if(tova != "aucklanduni.ac.nz"){
        alert("Email is not uni email");
        eadd=="";
        return false;
    }
return true;
}

