$(function() {
	
	// autocomplete
	
	var availableTags = [ "HTML","CSS3","javaScript","jQuery","vue"];
	$( "#autocomplete" ).autocomplete({
		source: availableTags
	});
	
	// 모달팝업
	
	$( "#dialog-link" ).click(function( event ) {
		$( "#dialog" ).dialog( "open" );
		event.preventDefault();
	});
	
	$( "#dialog" ).dialog({
		autoOpen: false,
		width: 400,
		buttons: [
			{
				text: "Ok",
				click: function() {
					$( this ).dialog( "close" );
				}
			},
			{
				text: "Cancel",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		]
	});
	
	// datepicker
	$( "#datepicker" ).datepicker({
		inline: true
	});
	
	// selectmenu
	$( "#selectmenu" ).selectmenu({
		change : function(){
			var sel = $(":selected").val();
			if(sel != "none") {
			window.open(sel);
			}
			else return false;
		}
	});
	
	// $("#selectmenu").change(function(){
		
		// var sel = $(":selected").val();
		
		// if(sel != "none") {
			// window.open(sel);
		// }else
			// return false;
			
	// });
	
	// accordion
	$( "#accordion" ).accordion();
	
	
});