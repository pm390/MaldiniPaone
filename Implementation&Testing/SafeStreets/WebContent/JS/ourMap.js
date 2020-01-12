////////////////////////////////////////////////////////
//initialization of the map
///////////////////////////////////////////////////////
var lowReport = 5;// TODO change to
// higher number
// on release
var highReport = 10;// TODO change to higher number
var mymap;
var statisticList=new Array();

var becameAuthority=function()
{
	var temp=L.marker(
			[ posizione.coords["latitude"], posizione.coords["longitude"] ], {
				icon : authorityIcon
			});
	temp.addTo(mymap).bindPopup(
	"<b> Posizione attuale </b>");
	$.get("./AssignmentActive").done(function(data) {
		var json = data;
		if (json["error"]) {
			alert(json["errorCode"].toString() + json["errorMessage"]);
			return;
		}
		if(json["activeIDs"])	
		{
			$("#assignmentId").val(json["activeIDs"]["id"]);
			var reports= json["activeIDs"]["reports"];
			var location= reports[0]["location"];
			mymap.flyTo([ location["latitude"], location["longitude"] ],15);
			$("#activeAssignment").show();
		}
	}).fail(function() {
		console.log("error");
	});
	
	
	function UpdateAssignements(center)
	{
		if($("noActive:hidden").length===0) // do if no active assignment div is
											// showing
		$.get("./AssignmentServlet", {
			"latitude" : center.lat,
			"longitude" : center.lng
		}).done(function(data) {
			var json = data;
			if (json["error"]) {
				alert(json["errorCode"].toString() + json["errorMessage"]);
				return;
			}
			showNewAssignments(json);// shows statistics on the page from the
										// given json
		}).fail(function() {
			console.log("error");
		});
	};
	mymap.on('dragend', function onDragEnd(e) {
		if($("noActive:hidden").length===0)
			{
				var center = mymap.getCenter();
				UpdateAssignements(center);
			}
	});	
	
	function updatePosition(position)
	{
		temp.setLatLng([ posizione.coords["latitude"], posizione.coords["longitude"] ]);
		var center={};
		center.lat=posizione.coords["latitude"];
		center.lng=posizione.coords["longitude"];
		UpdateAssignements(center);
	}

	// set interval
	setInterval(function()
			{
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(updatePosition, noValidCoordinates);
		} else {
			posizione.coords = {// if no geolocation initialize map with
								// coordinates of Rome
				"latitude" : 41.9109,
				"longitude" : 12.4818
			};
			updatePosition(posizione);
		}
			}, 1*60*1000);
};


function init(position) {
	mymap = L.map('map').setView(
			[ position.coords.latitude, position.coords.longitude ], 12);
	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
		noWrap: true,
		maxZoom: 18,
		minZoom:1,
		attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
		'<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
		'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
		id: 'mapbox.streets'
	}).addTo(mymap);

	// update position when click on map
	mymap.on('click', function(e)
			{
				$(".latitude.mapClickModifiable").val(e.latlng.lat);
				$(".longitude.mapClickModifiable").val(e.latlng.lng);
			});
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
			showNewStatistics(json);// shows statistics on the page from the
									// given json
		}).fail(function() {
			console.log("error");
		});
	};
	// update the statistic when load
	UpdateStatistics(width, height, center);
	// set event listener of dragend to call the update statistics if the map
	// has been moved enough
	mymap.on('dragend', function onDragEnd(e) {
		var width = mymap.getBounds().getEast() - mymap.getBounds().getWest();
		var height = mymap.getBounds().getNorth()
				- mymap.getBounds().getSouth();
		var center = mymap.getCenter();

		$(".latitude.mapClickModifiable").val(center.lat);
		$(".longitude.mapClickModifiable").val(center.lng);
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

// position
var posizione = {};
// when load initialize map with navigator.geolocation
window.onload = function() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(init, noValidCoordinates);
	} else {
		posizione.coords = {// if no geolocation initialize map with coordinates
							// of Rome
			"latitude" : 41.9109,
			"longitude" : 12.4818
		};
		init(posizione);
	}
}
// fallback if error in geolocation
function noValidCoordinates(error) {
	posizione.coords = {
		"latitude" : 41.9109,
		"longitude" : 12.4818
	};
	if(!mymap)init(posizione);
}
// marker icon initialization
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
var authorityIcon = L.icon({
	iconUrl : './icone/currentpositionicon.png',
	shadowUrl : './icone/ombra.png',

	iconSize : [ 25, 41 ],
	iconAnchor : [ 12, 41 ],
	popupAnchor : [ 1, -34 ],
	tooltipAnchor : [ 16, -28 ],
	shadowSize : [ 41, 41 ]
});
var violationIcon = L.icon({
	iconUrl : './icone/violationicon.png',
	shadowUrl : './icone/ombra.png',

	iconSize : [ 25, 41 ],
	iconAnchor : [ 12, 41 ],
	popupAnchor : [ 1, -34 ],
	tooltipAnchor : [ 16, -28 ],
	shadowSize : [ 41, 41 ]
});
// useful function for further updates
function reverseGeocode(c) {
	fetch(
			'http://nominatim.openstreetmap.org/reverse?format=json&lon='
					+ c.lng + '&lat=' + c.lat).then(function(response) {
		var json=response.json();// if useful
		// TODO put something if useful
	});
}


// statistics description closer
var descriptionCloser=$("#closeLongDescription");
var identifier = 0;
// create div with description of a statistic object
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
// show statistics given a json input
function showNewStatistics(data) {
	for (var i = 0; i < data.statistics.length; ++i) {
		var statistic = data.statistics[i];
		if (statistic["reportCountLastWeek"] == 0)
			continue; // empty statistic
		else {
			let number = identifier++;// assign new number to each useful line
			let location = statistic["location"];
			if(statisticList.some(item =>Math.abs(item[0]-location["latitude"])<=0.02
											&&Math.abs(item[1]-location["longitude"])<=0.02)) continue;// already
																										// exist
			statisticList.push([ location["latitude"], location["longitude"] ]);// add
																				// location
																				// to
																				// the
																				// list
																				// of
																				// statistics
			let item = $("<li id='smallStat" + number.toString() // create
																	// list item
																	// to be
																	// added
					+ "'>latitudine: " + location["latitude"].toString()
					+ " longitudine: " + location["longitude"].toString()
					+ "</li>");
			let div = createDiv(number, statistic["reportCountLastWeek"],// create
																			// div
					statistic["assignmentCountLastWeek"],
					statistic["reportForAssignmentCountLastWeek"],
					statistic["dailyReportCountLastWeek"]);
			$("#smallDescription").append(item);
			$("#longDescription").append(div);
			$(div).hide();// to avoid browser which doesn't allow inline style
			item.hover(function(e) { // when hover a statistics move to that
										// point on the map
				mymap.flyTo([ location["latitude"], location["longitude"] ],
						mymap.getZoom() );
				}
			);
			item.click(function(e) { // /when click move on that point on the
										// map and show description
				$("#longDescription").show();
				descriptionCloser.show();
				if (shownDiv)
					$(shownDiv).hide();
				$(div).show();
				shownDiv = div;
				$("#smallDescription").hide();
				// set zoom to the current zoom if enough zoomed else it
				// increases zoom until the zoom is 13
				mymap.setZoom((mymap.getZoom()<13)?Math.min(mymap.getZoom() + 1,13):mymap.getZoom());
			});
			
			var marker;
			var icona;
			// depending on the
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


			marker.on('click', function(e) {// when click on marker show
											// description
				mymap.flyTo([ location["latitude"], location["longitude"] ],
						// set zoom to the current zoom if enough zoomed else it
						// increases zoom until the zoom is 13
						(mymap.getZoom()<13)?Math.min(mymap.getZoom() + 1,13):mymap.getZoom());
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
var cleaner;
function showNewAssignments(data) {
	let shownDiv;
	$("#smallAssignmentDescription").html("");
	$("#longAssignmentDescription").html('<input type="button" id="closeLongAssignmentDescription" value="X">');
	var assignments=data.assignments;
	if(!assignments)return;
	for (var i = 0; i < assignments.length; ++i) {
		var assign = assignments[i];
		if (!assign.reports||assign.reports.lenght == 0)
			continue; // empty statistic
		else {
			let id = assign.id;// assignment
			let location = assign.reports[0]["location"];
			let div=$("<div>"+assign.reports[0].licensePlate+"</div>");
			let accept=$("<button value='Accetta'></button>");
			let photoContainer=$("<div class='horizontalScroll'></div>");
			let notesContainer=$("<div class='horizontalScroll'></div>");
			for(var j=0;j<assign.reports.lenght;++j)
				{
				for(var k=0;k<assign.reports.photoNames.lenght;++k)
					{
						let photo=("<img data-src='.\GetPhoto?file="+assign.reports.photoNames[k]["name"]+"'>");
						photoContainer.append(photo);
						$(this).attr("src",$(this).attr("data-src"));
					}
					// TODO show notes if useful
				}
			div.append(accept);
			div.append(photoContainer);
				let item = $("<li>latitudine: " + location["latitude"].toString()
					+ " longitudine: " + location["longitude"].toString()
					+ " numero report: "+ assign.reports.lenght
					+ "</li>");
				accept.click(
						function(e)
						{
							cleaner=function()
							{
								item.hide();
							}
							$("#activeAssignment").show();
							div.hide();
							$("#assignmentIdAccept").val(id);
							$("#assignmentId").val(id);
							$.post("./AssignmentServlet",$("#assignmentFormAccept").serialize())
							.done(
							function(data) {
										var json = data;
										// button
										if (!json["error"]) {// login success
											$("#noActive").hide();
											$("#activeAssignment").show();
										} else {// error occured. printed as
												// alert
											alert(json["errorCode"].toString()
									+ json["errorMessage"]);
							return;
						}
							
						}
					);
							});
				
			$("#smallAssignmentDescription").append(item);
			$("#longAssignmentDescription").append(div);
			$(div).hide();
			item.hover(function(e) { // when hover a statistics move to that
										// point on the map
				mymap.flyTo([ location["latitude"], location["longitude"] ],
						mymap.getZoom() );
				}
			);
			let shown=false;
			item.click(function(e) { // /when click move on that point on the
										// map and show description
				if(!shown)
					$(photoContainer).children().attr("src",$(photoContainer).children().attr("data-src"));
				shown=true;
				$("#longAssignmentDescription").show();
				descriptionCloser.show();
				if (shownDiv)
					$(shownDiv).hide();
				$(div).show();
				shownDiv = div;
				$("#smallAssignmentDescription").hide();
				// set zoom to the current zoom if enough zoomed else it
				// increases zoom until the zoom is 13
				mymap.setZoom((mymap.getZoom()<13)?Math.min(mymap.getZoom() + 1,13):mymap.getZoom());
			});
			
			// create marker
			var marker;
			
			marker = L.marker(
					[ location["latitude"], location["longitude"] ], {
						icon : violationIcon
					}).addTo(mymap).bindPopup(
					"<b> numero Report" +assign.reports.length.toString()
							+ " Report</b>");

			marker.on('click', function(e) {// when click on marker show
											// description
				item.trigger("click");
			})
		}
	}
}

// description closer closes description and reshows the list of statistics
descriptionCloser.click(function() {
	descriptionCloser.hide();
	$("#longDescription").hide();
	$("#smallDescription").show();
	if (shownDiv)
		$(shownDiv).hide();
	shownDiv = null;
})

$("#ModifyAssignment").click(function(e)
		{
	$.post("./AssignmentServlet",$("#assignmentForm").serialize())
	.done(
	function(data) {
				var json = data;
				// button
				if (!json["error"]) {// login success
					$("#noActive").show();
					$("#activeAssignment").hide();
					$("#longAssignmentDescription").hide();
					$("#smallAssignmentDescription").show();
					if($("State").val()!="created"&&cleaner)
					{
					cleaner();
					}
				} else {// error occured. printed as
						// alert
					alert(json["errorCode"].toString()
			+ json["errorMessage"]);
	return;
	
}
	
}
);
		})
$("#reportSender").submit
( function (e)
		{
		e.preventDefault();
		var data = new FormData($('#reportSender')[0]);
		jQuery.ajax({
		    url: $("#reportSender").attr('action'),
		    data: data,
		    cache: false,
		    contentType: false,
		    processData: false,
		    type: 'POST',
		    success: function(data) {
				var json = data;
				// button
				if (!json["error"]) {// login success
					alert("report is sent");
					$("#reportSender")[0].reset();
				} else {// error occured. printed as
						// alert
					alert(json["errorCode"].toString()
			+ json["errorMessage"]);
	return;
};
		}});
			
		});

