function init(position) {
	var mymap = L.map('map').setView(
			[ position.coords.latitude, position.coords.longitude ], 11);
	L.tileLayer(
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
	var height = mymap.getBounds().getNorth()
			- mymap.getBounds().getSouth();
	var center = mymap.getCenter();
	var lastWidth=width,lastHeight=height,lastCenter=center;
	function UpdateStatistics(width,heigth,center)
	{
	$.get("./StatisticsServlet", {
		"latitude" : center.lat,
		"longitude" : center.lng,
		"width" : width,
		"height" : height
	}).done(function(data) {
		console.log(data);
		//set new markers
	}).fail(function() {
		console.log("error");
	});
	};
	UpdateStatistics(width,height,center);
	mymap.on('dragend', function onDragEnd() {
		var width = mymap.getBounds().getEast() - mymap.getBounds().getWest();
		var height = mymap.getBounds().getNorth()
				- mymap.getBounds().getSouth();
		var center = mymap.getCenter();
		if(Math.abs(lastCenter.lat-center.lat)>width/2||
				Math.abs(lastCenter.lng-center.lng)>height/2||
				Math.abs(lastWidth-width)>0.5||Math.abs(lastHeight-height)>0.5)
		{
			UpdateStatistics(width,height,center);
			lastCenter=center;
			lastWidth=width;
			lastHeight=height;
		}
		
	});
	/*
	 * var x = new XMLHttpRequest(); x.onreadystatechange= function (){
	 * 
	 * if(x.readyState==4 && x.status==200){ var ris =
	 * JSON.parse(x.responseText); addMarkers(ris); } } x.open("GET",
	 * "\GetLocalita?idcampagna=" +
	 * document.getElementById("idcampagna").innerHTML,true) x.send();
	 */

}


window.onload = function() {
	var position={};
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(init);
	} else {
		position.coords = {
			"latitude" : 0,
			"longitude" : 0
		};
		init(position);
	}

}
var idlocalita;
var listaImm;
var c = 0;
var greenIcon = L.icon({
	iconUrl : '/icone/green.png',
	shadowUrl : '/icone/ombra.png',

	iconSize : [ 25, 41 ],
	iconAnchor : [ 12, 41 ],
	popupAnchor : [ 1, -34 ],
	tooltipAnchor : [ 16, -28 ],
	shadowSize : [ 41, 41 ]
});

var yellowIcon = L.icon({
	iconUrl : '/icone/yellow.png',
	shadowUrl : '/icone/ombra.png',

	iconSize : [ 25, 41 ],
	iconAnchor : [ 12, 41 ],
	popupAnchor : [ 1, -34 ],
	tooltipAnchor : [ 16, -28 ],
	shadowSize : [ 41, 41 ]
});

var redIcon = L.icon({
	iconUrl : '/icone/red.png',
	shadowUrl : '/icone/ombra.png',

	iconSize : [ 25, 41 ],
	iconAnchor : [ 12, 41 ],
	popupAnchor : [ 1, -34 ],
	tooltipAnchor : [ 16, -28 ],
	shadowSize : [ 41, 41 ]
});



function reverseGeocode(c) {
	fetch(
			'http://nominatim.openstreetmap.org/reverse?format=json&lon='
					+ c.lng + '&lat=' + c.lat).then(function(response) {
		setInfoLocalita(response.json())
	});
}

var bounds = new L.LatLngBounds();
var popup = L.popup();
var x
function onMapout(e) {
	var marker = L.marker(x).addTo(mymap).bindPopup(
			document.getElementById("locazione").value).openPopup();
	marker.valueOf()._icon.style.color = 'green';
	marker.on('click', onClick_Marker);
}

function showImm(n) {
	if (listaImm.length > 0) {

		if (c + n > listaImm.length - 1) {
			c = 0
		} else if (c + n < 0) {
			c = listaImm.length - 1
		} else {
			c = c + n;
		}
		document.getElementById("immagine").src = "/ImmaginiCampagna/"
				+ listaImm[c].id + listaImm[c].formato;
		document.getElementById("datiimmagine").innerHTML = "provenienza: "
				+ listaImm[c].provenienza + ", " + listaImm[c].data_recupero
				+ ", risoluzione: " + listaImm[c].risoluzione;
	}
}

function getAnn() {
	var x = new XMLHttpRequest();
	x.onreadystatechange = function() {

		if (x.readyState == 4 && x.status == 200) {
			var ris = JSON.parse(x.responseText);
			showAnn(ris);
		}
	}
	x.open("GET", "\getAnnotazioni?idimmagine=" + listaImm[c].id
			+ "&idlocalita=" + idlocalita + "&idcampagna="
			+ document.getElementById("idcampagna").innerHTML, true);
	x.send();
}

function onClick_Marker(e) {
	var marker = e.target;
	coordinate = marker.getLatLng();
	idlocalita = marker.id;
	reverseGeocode(coordinate);
	var x = new XMLHttpRequest();
	x.onreadystatechange = function() {

		if (x.readyState == 4 && x.status == 200) {
			var obj = JSON.parse(x.responseText);
			var errore = obj[1];
			if (errore) {
				document.getElementById("errore").style.display = "block";
			} else {
				document.getElementById("errore").style.display = "none";
				listaImm = obj[0];
				c = 0;
				showImm(0);
				getAnn();
			}
		}
	}
	x.open("GET", "\GetDatiImmagine?idcampagna="
			+ document.getElementById("idcampagna").innerHTML + "&idlocalita="
			+ idlocalita, true);
	x.send();
}

function addMarkers(loc) {
	var maxlat = -360;
	var ref
	for (let i = 0; i < loc.length; ++i) {
		var marker;
		switch (loc[i].colore) {
		case "yellow":
			marker = L.marker([ loc[i].latitudine, loc[i].longitudine ], {
				icon : yellowIcon
			}).addTo(mymap).bindPopup("<b>" + loc[i].nome + "</b>");
			break;
		case "green":
			marker = L.marker([ loc[i].latitudine, loc[i].longitudine ], {
				icon : greenIcon
			}).addTo(mymap).bindPopup("<b>" + loc[i].nome + "</b>");
			break;
		case "red":
			marker = L.marker([ loc[i].latitudine, loc[i].longitudine ], {
				icon : redIcon
			}).addTo(mymap).bindPopup("<b>" + loc[i].nome + "</b>");
			break;
		}
		marker.id = loc[i].ID_localita;
		marker.nome = loc[i].nome;
		marker.on('click', onClick_Marker)
		if (maxlat < loc[i].latitudine) {
			maxlat = marker.getLatLng().lat;
			ref = marker.getLatLng().lng;
		}
		bounds.extend(marker.getLatLng());
		if (i == loc.length - 1) {
			var latlng = new L.latLng();
			latlng.lat = maxlat + 0.04;
			latlng.lng = ref;
			bounds.extend(latlng);
			marker.fire("click");
			marker.openPopup();
		}
	}
	mymap.fitBounds(bounds);
}
