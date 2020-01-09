////////////////////////////////////////////////////////
//initialization of the map
///////////////////////////////////////////////////////
var lowReport = 5;// TODO change to
// higher number
// on release
var highReport = 10;//TODO change to higher number
var mymap;
var statisticList=new Array();
function init(position) {
	mymap = L.map('map').setView(
			[ position.coords.latitude, position.coords.longitude ], 12);
	L
			.tileLayer(
					'https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw',
					{
						noWrap : true,
						maxZoom : 18,
						minZoom : 1,
						attribution : 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, '
								+ '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, '
								+ 'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
						id : 'mapbox.streets'
					}).addTo(mymap);
	var width = mymap.getBounds().getEast() - mymap.getBounds().getWest();
	var height = mymap.getBounds().getNorth() - mymap.getBounds().getSouth();
	var center = mymap.getCenter();
	var lastWidth = width, lastHeight = height, lastCenter = center;
	// //////////////////////////////////////////////////////
	// statistics update
	// /////////////////////////////////////////////////////
	function UpdateStatistics(width, heigth, center) {
		$.get("./StatisticsServlet", {
			"latitude" : center.lat,
			"longitude" : center.lng,
			"width" : Math.ceil(width* 100) / 100,
			"height" : Math.ceil(height* 100) / 100
		}).done(function(data) {
			var json = data;
			if (json["error"]) {
				alert(json["errorCode"].toString() + json["errorMessage"]);
				return;
			}
			showNewStatistics(json);//shows statistics on the page from the given json
		}).fail(function() {
			console.log("error");
		});
	};
	//update the statistic when load
	UpdateStatistics(width, height, center);
	//set event listener of dragend to call the update statistics if the map has been moved enough
	mymap.on('dragend', function onDragEnd() {
		var width = mymap.getBounds().getEast() - mymap.getBounds().getWest();
		var height = mymap.getBounds().getNorth()
				- mymap.getBounds().getSouth();
		var center = mymap.getCenter();
		if (Math.abs(lastCenter.lat - center.lat) > width / 2
				|| Math.abs(lastCenter.lng - center.lng) > height / 2
				|| Math.abs(lastWidth - width) > 0.5
				|| Math.abs(lastHeight - height) > 0.5) {
			UpdateStatistics(width, height, center);
			lastCenter = center;
			lastWidth = width;
			lastHeight = height;
		}

	});

}

//position
var posizione = {};
//when load initialize map with navigator.geolocation
window.onload = function() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(init, noValidCoordinates);
	} else {
		position.coords = {//if no geolocation initialize map with coordinates of Rome
			"latitude" : 41.9109,
			"longitude" : 12.4818
		};
		init(position);
	}
}
//fallback if error in geolocation
function noValidCoordinates(error) {
	posizione.coords = {
		"latitude" : 41.9109,
		"longitude" : 12.4818
	};
	init(posizione);
}
//marker icon initialization
var greenIcon = L.icon({
	iconUrl : './icone/green.png',
	shadowUrl : './icone/ombra.png',

	iconSize : [ 25, 41 ],
	iconAnchor : [ 12, 41 ],
	popupAnchor : [ 1, -34 ],
	tooltipAnchor : [ 16, -28 ],
	shadowSize : [ 41, 41 ]
});

var yellowIcon = L.icon({
	iconUrl : './icone/yellow.png',
	shadowUrl : './icone/ombra.png',

	iconSize : [ 25, 41 ],
	iconAnchor : [ 12, 41 ],
	popupAnchor : [ 1, -34 ],
	tooltipAnchor : [ 16, -28 ],
	shadowSize : [ 41, 41 ]
});

var redIcon = L.icon({
	iconUrl : './icone/red.png',
	shadowUrl : './icone/ombra.png',

	iconSize : [ 25, 41 ],
	iconAnchor : [ 12, 41 ],
	popupAnchor : [ 1, -34 ],
	tooltipAnchor : [ 16, -28 ],
	shadowSize : [ 41, 41 ]
});
//useful function for further updates
function reverseGeocode(c) {
	fetch(
			'http://nominatim.openstreetmap.org/reverse?format=json&lon='
					+ c.lng + '&lat=' + c.lat).then(function(response) {
		var json=response.json();//if useful
		//TODO put something if useful
	});
}


//statistics description closer
var descriptionCloser=$("#closeLongDescription");
var identifier = 0;
//create div with description of a statistic object
function createDiv(id, rCount, aCount, rFA, dRC) {
	var div = "<div id='bigStat" + id.toString() + "' style='display:none'>"
			+ "<div class='column'>" + "<p>Segnalazioni Create: "
			+ rCount.toString() + "<\p>" + "<p>Compiti Creati: "
			+ aCount.toString() + "<\p>"
			+ "<p>Media Segnalazioni per compiti: " + rFA.toString() + "<\p>"
			+ "<p>Media Giornaliera di report: " + dRC.toString() + "<\p>"
			+ "</div>" + "</div>";
	return $(div);
}
var shownDiv = null;
//show statistics given a json input
function showNewStatistics(data) {
	for (var i = 0; i < data.statistics.length; ++i) {
		var statistic = data.statistics[i];
		if (statistic["reportCountLastWeek"] == 0)
			continue; // empty statistic
		else {
			let number = identifier++;// assign new number to each useful line
			let location = statistic["location"];
			if(statisticList.some(item =>Math.abs(item[0]-location["latitude"])<=0.02
											&&Math.abs(item[1]-location["longitude"])<=0.02)) continue;//already exist
			statisticList.push([ location["latitude"], location["longitude"] ]);//add location to the list of statistics
			let item = $("<li id='smallStat" + number.toString() //create list item to be added
					+ "'>latitudine: " + location["latitude"].toString()
					+ " longitudine: " + location["longitude"].toString()
					+ "</li>");
			let div = createDiv(number, statistic["reportCountLastWeek"],//create div 
					statistic["assignmentCountLastWeek"],
					statistic["reportForAssignmentCountLastWeek"],
					statistic["dailyReportCountLastWeek"]);
			$("#smallDescription").append(item);
			$("#longDescription").append(div);
			$(div).hide();// to avoid browser which doesn't allow inline style
			item.hover(function(e) { //when hover a statistics move to that point on the map
				mymap.flyTo([ location["latitude"], location["longitude"] ],
						mymap.getZoom() );
				}
			);
			item.click(function(e) { ///when click move on that point on the map and show description
				$("#longDescription").show();
				descriptionCloser.show();
				if (shownDiv)
					$(shownDiv).hide();
				$(div).show();
				shownDiv = div;
				$("#smallDescription").hide();
				mymap.flyTo([ location["latitude"], location["longitude"] ],
						mymap.getZoom() + 1);
			});
			
			//create marker
			var marker;
			var icona;
			//depending on the 
			if (statistic["reportCountLastWeek"] < lowReport)
			{
				icona=greenIcon;
			} else if (statistic["reportCountLastWeek"] < highReport) {
				icona=yellowIcon;
			} else {
				icona=redIcon;
			}
			marker = L.marker(
					[ location["latitude"], location["longitude"] ], {
						icon : icona
					}).addTo(mymap).bindPopup(
					"<b>" + statistic["reportCountLastWeek"].toString()
							+ " Report</b>");

			marker.on('click', function(e) {//when click on marker show description
				mymap.flyTo([ location["latitude"], location["longitude"] ],
						mymap.getZoom() + 1);
				descriptionCloser.show();
				$("#smallDescription").hide();
				$("#longDescription").show();
				if (shownDiv)
					$(shownDiv).hide();
				$(div).show();
				shownDiv = div;
			})
		}
	}
}

//description closer closes description and reshows the list of statistics
descriptionCloser.click(function() {
	descriptionCloser.hide();
	$("#longDescription").hide();
	$("#smallDescription").show();
	if (shownDiv)
		$(shownDiv).hide();
	shownDiv = null;
})
