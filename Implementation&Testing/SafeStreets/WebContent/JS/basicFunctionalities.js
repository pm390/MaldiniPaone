$("#home").show();
$("#login").hide();
$("#registrationAuthority").hide();
$("#suggestions").hide();
$("#registrationMunicipality").hide();
$("#additionalFunctions").hide();
$(".toAdditionalFunctions").hide();
$("#registrationManager").hide();
$(".registerManager").hide();// Shown only if manager gets access
$(".getSuggestions").hide();

$(".toLogin").click(function() {
	$("#home").hide();
	$("#login").show();
});
$(".login").click(
		function(e) {
			var input = $("#loginForm input");
			for (let i = 0; i < input.length; ++i) {
				if (input[i].value === "") {
					if ($(input[i]).is(":hidden"))
						continue;
					$(input[i]).addClass("error");
					$(input[i]).focus();
					e.preventDefault();
					return;
				} else {
					$(input[i]).removeClass("error");
				}
			}
			//get action
			var action = $("#loginForm").prop('action');
			var target = $(event.target).attr('disabled', 'disabled');
			e.preventDefault();
			$.post(action, $("#loginForm").serialize()).done(
					function(data) {
						var json = data;
						if (json["userType"] == "manager") {
							$("#registerMunicipalityForm").attr("action",
									"./RegistrationByManager");
							$(".toAdditionalFunctions").show();
							$(".registerManager").show();
							$(".registerAuthority").hide();
							$(".onlyManager").show();
						} else if (json["userType"] == "municipality") {
							$("#registerMunicipalityForm").attr("action",
									"./RegistrationByMunicipality");
							$(".toAdditionalFunctions").show();
							$(".getSuggestions").show();
							$(".registerManager").hide();
							$(".registerAuthority").show();
							$(".onlyManager").hide();
						}
						$(target).removeAttr('disabled');
						if (!json["error"]) {
							$("#login").hide();
							$("#home").show();
							$(".toLogin").hide();
						}
						// TODO handle error
						else {
							alert(json["errorCode"].toString()
									+ json["errorMessage"]);
						}
						// TODO check state
					}).fail(function() {
				alert("error");
				$(target).removeAttr('disabled');
				// TODO add an error message on the page
			});
		})
$(".toHome").click(function(e) {
	$(event.target).parent().hide();
	$("#home").show();
});
$(".registerAuthority").click(function(e) {
	$(event.target).parent().hide();
	$("#registrationAuthority").show();
	$(".toAdditionalFunctions").show();
});
$(".registerMunicipality").click(function(e) {
	$(event.target).parent().hide();
	$("#registrationMunicipality").show();
	$(".toAdditionalFunctions").show();
});
$(".registerManager").click(function(e) {
	$(event.target).parent().hide();
	$("#registrationManager").show();
	$(".toAdditionalFunctions").show();
});
$(".toAdditionalFunctions").click(function(e) {
	$(event.target).parent().hide();
	$("#additionalFunctions").show();
});
var gotSuggestions = false;
$(".getSuggestions").click(function(e) {
	$(event.target).parent().hide();
	if (gotSuggestions) {
		$("#suggestions").show();
		return;
	}
	var action = $(event.target).next().prop('action');
	$.get(action, $("#suggestionForm").serialize()).done(function(data) {
		var json = data;
		if (json["error"]) {
			alert(json["errorCode"].toString() + json["errorMessage"]);
			return;
		}
		if (json.suggestions.length == 0) {
			alert("nessun suggerimento trovato");
			$(event.target).hide();
			return;
		}
		$("#suggestions").show();
		gotSuggestions = true;
		for (var i = 0; i < json.suggestions.length; ++i) {
			var suggestion = json.suggestions[i];
			$("#suggestionList").append('<li>' + suggestion + '</a></li>');
		}
	}).fail(function() {
		alert("error");
		// TODO add an error message on the page
	});

});



var users=["Manager","Municipality","Authority"];
for(var k=0;k<users.length;++k)
	{let j=k;
	$("#register"+users[j]).click(
			function(e) {
				var formId="#register"+users[j]+"Form";
				var formChildren="#register"+users[j]+"Form input";
				var submitId="#register"+users[j]
				var input = $(formChildren);
				for (let i = 0; i < input.length; ++i) {
					if (input[i].value === "") {
						if ($(input[i]).is(":hidden"))
							continue;
						$(input[i]).addClass("error");
						$(input[i]).focus();
						e.preventDefault();
						return;
					} else {
						$(input[i]).removeClass("error");
					}
				}
				// get action
				var form=$(formId);
				var action = form.prop('action');
				e.preventDefault();
				$.post(action, form.serialize()).done(
						function(data) {
							var json = data;
							if (!json["error"]) {

								form[0].reset();
								fillCoordinates(posizione);
								alert("success");
							} else {
								alert(json["errorCode"].toString()
										+ json["errorMessage"]);
							}

							// TODO check state
						}).fail(function() {
					alert("error");
					// TODO add an error message on the page
				});
			});
	}


var posizione = {};
if (navigator.geolocation) {
	navigator.geolocation.getCurrentPosition(fillCoordinates);
} else {
	posizione.coords = {
		"latitude" : 0,
		"longitude" : 0
	};
	fillCoordinates(posizione);
}
function fillCoordinates(position) {
	posizione=position;
	$(".latitude").each(function(index, element) {
		$(element).val(position.coords.latitude);
	})
	$(".longitude").each(function(index, element) {
		$(element).val(position.coords.longitude);
	})
}
