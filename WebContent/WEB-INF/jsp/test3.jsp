<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCwQZTkX_y6SBpD491UPL_M4te1_YJEXIA"
  type="text/javascript"></script>
  <script>
  function init() {
	  var myLatlng = new google.maps.LatLng(-34.397, 150.644);
	var mapOptions = {
	  zoom: 8,
	  center: myLatlng,
	  mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	var map = new google.maps.Map(document.getElementById("gMap"),
	    mapOptions);

	// Place a draggable marker on the map
	var marker = new google.maps.Marker({
	    position: myLatlng,
	    map: map,
	    draggable:true,
	    title:"Drag me!"
	});
	marker.setMap(map);
   }
  google.maps.event.addDomListener(window, 'load', init);
  </script>
</head>
<body onload="">
<div id="gMap" style="width:600px;height:480px"></div>
</body>
</html>