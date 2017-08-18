"use strict";

function setup() {
	//check all dolphin checkboxes at the start
	$("#toggle-components input").each(function() {
		$(this).attr("checked", true);
	});
	
	// setup onclick event handlers for background radios
	$("div#choose-background input").on(
			"click",
			function() {
				var backgroundNum = $("div#choose-background input").index(
						$(this)) + 1;

				var backgroundFileName = "assets/images/background"
						+ backgroundNum;
				backgroundFileName += backgroundNum == 6 ? ".gif" : ".jpg";
				$("#background").attr("src", backgroundFileName);
			});
	
	//set onclick event handlers for dolphin checkboxes
	$("#toggle-components input").on("click", function() {
		var checkboxId = $(this).attr("id");
		var dolphinId = checkboxId.substr(6);
		console.log(dolphinId);
		$("#" + dolphinId).toggle();
	});
	
	//setup slider
	$("#size-control").slider({
		min : 10,
		max : 200,
		value : 100,
		slide : function(event, ui) {
			console.log(ui.value);
			// $("#container").css("width", ui.value + "%");
			//if (ui.value < 100) {
				var dolphinWidth = Math.pow( ui.value, 0.67);
				$(".dolphin").css("width", dolphinWidth + "%");
			//}
			if (ui.value == 100) {
				$(".dolphin").css("width", "");
			}
		}
	});
	
	//create slider's label
	var label = $("<label></label>").text("size:").attr("for", "size-control");
	label.insertBefore("#size-control");

}

$(document).ready(setup);