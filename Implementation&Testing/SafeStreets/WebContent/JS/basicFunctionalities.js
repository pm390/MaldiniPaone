////////////////////////////////////////////////////////
//initial state
///////////////////////////////////////////////////////

/*function disableF5(e) {
 if ((e.which || e.keyCode) == 116)
 e.preventDefault();
 };
 $(document).on("keydown", disableF5);// disable reload of page with F5 avoid
 */
// mistakenful reloads
$("#closeLongDescription").hide();
$("#closeLongAssignmentDescription").hide();
$("#home").show();
$("#login").hide();
$("#registrationAuthority").hide();// Shown only if municipality gets access
$("#suggestions").hide();
$("#registrationMunicipality").hide();
$("#additionalFunctions").hide();
$(".toAdditionalFunctions").hide();
$("#registrationManager").hide();
$(".registerManager").hide();// Shown only if manager gets access
$(".getSuggestions").hide();// Shown only if municipality gets access
$(".onlyAuthority").hide();
$(".onlyCitizen").hide();

$(".registration").click(function(e) {
	e.preventDefault();
	if ($(".registration").hasClass("inactive")){
		$(".registration").removeClass("inactive");
		$(".registration").val("vai al login");
		$(".onlyRegistration").show();
		$("#loginForm").prop('action', './/Registration');
		$("#loginSubmitButton").val("Registrati");
		$("#LoginTitle").html("Registrazione");
		
	} else {
		$(".registration").addClass("inactive");
		$(".registration").val("Registrazione");
		$(".onlyRegistration").hide();
		$("#loginForm").prop('action', './/Login');
		$("#loginSubmitButton").val("Login");
		$("#LoginTitle").html("Login");
	}
});

$(".toLogin").click(function() {
	$("#home").hide();
	$("#login").show();
});
$("#dataTitle").click(function() {
	$("#statisticDiv").show();
	$("#reportDiv").hide();
	$("#assignementDiv").hide();
	$("#dataTitle").addClass("selected");
	$("h3.onlyCitizen").removeClass("selected");
	$("h3.onlyAuthority").removeClass("selected");
});
$("h3.onlyCitizen").click(function() {
	$("#statisticDiv").hide();
	$("#reportDiv").show();
	$("#dataTitle").removeClass("selected");
	$("h3.onlyCitizen").addClass("selected");
	$("h3.onlyAuthority").removeClass("selected");
});
$("h3.onlyAuthority").click(function() {
	$("#statisticDiv").hide();
	$("#assignementDiv").show();
	$("#dataTitle").removeClass("selected");
	$("h3.onlyCitizen").removeClass("selected");
	$("h3.onlyAuthority").addClass("selected");
});

// //////////////////////////////////////////////////////
// utility function
// /////////////////////////////////////////////////////
/**
 * Focuses the first input not hidden child of formId which is not filled
 * 
 * @param formId
 *            the id of the form
 * @param e
 *            the event to be prevented if modifications occurs
 * @param classname
 *            the class to apply to elements which are not filled
 * @return true if modifications occurs , false otherwise
 */
function focusFirst(formId, e, classname) {
	var modified = false;
	// get all inputs
	var input = $("#" + formId + " input");
	// for each input check .
	// if hidden is ok so continue to next iteration
	// otherwise set the given classname if any to the input
	// focus the element and save that the element was focused
	// if input is filled remove the classname if any
	for (let i = 0; i < input.length; ++i) {
		if (input[i].value === "") {
			if ($(input[i]).is(":hidden") || $(input[i]).attr("type") == "file")
				continue;
			if (classname)
				$(input[i]).addClass(classname);// if defined add the error
			// class
			if (!modified)
				$(input[i]).focus(); // if no focus were made than focus
			if (!classname) {// if no classname the method can terminate
				if (e)
					e.preventDefault();
				return true;
			}
			modified = true;
		} else {
			$(input[i]).removeClass(classname);
		}
	}
	if (modified && e) {
		e.preventDefault(); // if there is an event to be prevented and a
		// modification occured
		// prevent the default action
	}
	return modified;
}

// //////////////////////////////////////////////////////
// login form submission handler
// /////////////////////////////////////////////////////
$(".login").click(
		function(e) {
			if (focusFirst("loginForm", e, "error"))// if true some not compiled
			// field got focus
			{// so must avoid submit by returning;
				return;
			}
			// get action
			var action = $("#loginForm").prop('action');
			// disable submit until response returns
			var target = $(event.target).attr('disabled', 'disabled');
			e.preventDefault();
			// send post request
			$.post(action, $("#loginForm").serialize()).done(
					function(data) {
						var json = data;
						$(target).removeAttr('disabled');// enable the submit
						// button
						if (!json["error"]&&json.userType) {// login success
							$("#login").hide();
							$("#home").show();
							$(".toLogin").hide();
						}
						if(json["error"]){// error occured. printed as alert
							alert(json["errorCode"].toString()
									+ json["errorMessage"]);
							return;
						}
						if(!json.userType)
							{
							alert("registration succeded");
							$(".registration").trigger("click");
							return;
							}
						if (json["userType"] == "manager") {// show manager
							// functionalities
							$("#registerMunicipalityForm").attr("action",
									"./RegistrationByManager");
							$(".toAdditionalFunctions").show();
							$(".registerManager").show();
							$(".registerAuthority").hide();
							$(".onlyManager").show();
							$("#homeTitle").html("Statistiche");
							$(".onlyAuthority").hide();
							$(".onlyCitizen").hide();
						} else if (json["userType"] == "municipality") {// show
							// municipality
							// functionalities
							$("#registerMunicipalityForm").attr("action",
									"./RegistrationByMunicipality");
							$(".toAdditionalFunctions").show();
							$(".getSuggestions").show();
							$(".registerManager").hide();
							$(".registerAuthority").show();
							$(".onlyManager").hide();
							$("#homeTitle").html("Statistiche");
							$(".onlyAuthority").hide();
							$(".onlyCitizen").hide();
						} else if (json["userType"] == "authority") {// show
							// authority
							// functionalities
							$("#registerMunicipalityForm").attr("action",
									"./RegistrationByMunicipality");
							$(".toAdditionalFunctions").show();
							$(".getSuggestions").hide();
							$(".registerMunicipality").hide();
							$(".registerManager").hide();
							$(".registerAuthority").hide();
							$(".onlyManager").hide();
							$("#homeTitle").html("Segnalazioni");
							$(".onlyAuthority").show();
							$(".onlyCitizen").hide();
							$("#statisticDiv").hide();
							becameAuthority();
						} else if (json["userType"] == "citizen") {// show
							// citizen
							// functionalities
							$("#registerMunicipalityForm").attr("action",
									"./RegistrationByMunicipality");
							$(".toAdditionalFunctions").show();
							$(".getSuggestions").hide();
							$(".registerMunicipality").hide();
							$(".registerManager").hide();
							$(".registerAuthority").hide();
							$(".onlyManager").hide();
							$("#homeTitle").html("Statistiche");
							$(".onlyAuthority").hide();
							$(".onlyCitizen").show();
							$("#statisticDiv").hide();
						}

					}).fail(function() {// failure
				alert("Server non disponibile");
				$(target).removeAttr('disabled');
			});
		});

// //////////////////////////////////////////////////////
// Send report
// /////////////////////////////////////////////////////

// //////////////////////////////////////////////////////
// bind button clicks with right parts of page showing and disappearing
// /////////////////////////////////////////////////////
$(".toHome").click(function(e) {
	$(event.target).parent().hide();
	$("#home").show();
});
$(".registerAuthority").click(function(e) {
	$(event.target).parent().hide();
	$("#registrationAuthority").show();
	$(".toAdditionalFunctions").show();
	focusFirst("registerAuthorityForm", null);
});
$(".modifyUserData").click(function(e) {
	$(event.target).parent().hide();
	$("#modifyAccount").show();
	$(".toAdditionalFunctions").show();
	focusFirst("modificationForm", null);
});
$(".registerMunicipality").click(function(e) {
	$(event.target).parent().hide();
	$("#registrationMunicipality").show();
	$(".toAdditionalFunctions").show();
	focusFirst("registerMunicipalityForm", null);
});
$(".registerManager").click(function(e) {
	$(event.target).parent().hide();
	$("#registrationManager").show();
	$(".toAdditionalFunctions").show();
	focusFirst("registerManagerForm", null);
});
$(".toAdditionalFunctions").click(function(e) {
	$(event.target).parent().hide();
	$("#additionalFunctions").show();
});

// //////////////////////////////////////////////////////
// user addition form managing ajax calls
// /////////////////////////////////////////////////////
var users = [ "Manager", "Municipality", "Authority" ];
for (var k = 0; k < users.length; ++k) {
	let j = k;
	$("#register" + users[j]).click(function(e) {
		var formId = "#register" + users[j] + "Form";
		var formChildren = "#register" + users[j] + "Form input";
		var submitId = "#register" + users[j]
		var input = $(formChildren);
		var modified = false;
		for (let i = 0; i < input.length; ++i) {
			if (input[i].value === "") {
				if ($(input[i]).is(":hidden"))
					continue;
				$(input[i]).addClass("error");
				if (!modified)
					$(input[i]).focus();
				modified = true;
			} else {
				$(input[i]).removeClass("error");
			}
		}
		if (modified) {
			e.preventDefault();
			return;
		}
		// get action
		var form = $(formId);
		var action = form.prop('action');
		e.preventDefault();
		$.post(action, form.serialize()).done(function(data) {
			var json = data;
			if (!json["error"]) {

				form[0].reset();
				fillCoordinates(posizione);
				alert("success");
			} else {
				alert(json["errorCode"].toString() + json["errorMessage"]);
			}

		}).fail(function() {
			alert("Server non disponibile");
			$(target).removeAttr('disabled');
		});
	});
}

// //////////////////////////////////////////////////////
// get coordinates
// /////////////////////////////////////////////////////
var posizione = {};

if (navigator.geolocation) {
	navigator.geolocation.getCurrentPosition(fillCoordinates, noCoordinates);
} else {
	posizione.coords = {// fallback if no geolocation
		"latitude" : 41.9109,
		"longitude" : 12.4818
	};
	fillCoordinates(posizione);
}
// fallback in case of geolocation error
function noCoordinates(error) {
	posizione.coords = {
		"latitude" : 41.9109,
		"longitude" : 12.4818
	};
	fillCoordinates(posizione);
}
// set coordinates to all inputs which has coordinates value
function fillCoordinates(position) {
	posizione = position;
	$(".latitude").each(function(index, element) {
		$(element).val(position.coords.latitude);
	})
	$(".longitude").each(function(index, element) {
		$(element).val(position.coords.longitude);
	})
}

// //////////////////////////////////////////////////////
// modify account
// /////////////////////////////////////////////////////
$("#modification").click(function(e) {
	var input = $("#modificationForm input");
	var modified = false;
	for (let i = 0; i < input.length; ++i) {
		if (input[i].value === "" && $(input[i]).attr("name") != "newEmail") {
			// check if input is empty and not newEmail which is not a mandatory
			// value
			if ($(input[i]).is(":hidden"))
				continue;
			$(input[i]).addClass("error");
			if (!modified)
				$(input[i]).focus();
			modified = true;
		} else {
			$(input[i]).removeClass("error");
		}
	}
	if (modified) {
		e.preventDefault();
		return;
	}
	var passwordsHolders = $("#modificationForm .newPassword");
	// check new password is equal to the rewriting of it
	if ($(passwordsHolders[0]).val() != $(passwordsHolders[1]).val()) {
		$(passwordsHolders[1]).addClss("error");
		$(passwordsHolders[1]).focus();
	}
	// get action
	var action = $("#modificationForm").prop('action');
	var target = $(event.target).attr('disabled', 'disabled');
	e.preventDefault();
	$.post(action, $("#modificationForm").serialize()).done(function(data) {
		var json = data;
		$(target).removeAttr('disabled');
		if (!json["error"]) {
			$("#modifyAccount").hide();
			$("#additionalFunctions").show();
			$("#modificationForm")[0].reset();
		} else {
			alert(json["errorCode"].toString() + json["errorMessage"]);
		}

	}).fail(function() {
		alert("Server non disponibile");
		$(target).removeAttr('disabled');
	});
})

// //////////////////////////////////////////////////////
// get suggestions
// /////////////////////////////////////////////////////
var gotSuggestions = false;
$(".getSuggestions").click(function(e) {
	if (gotSuggestions) {
		$("#suggestions").show();
		return;
	}
	let target = $(event.target);
	var action = target.next().prop('action');
	$.get(action, $("#suggestionForm").serialize()).done(function(data) {
		var json = data;
		if (json["error"]) {
			alert(json["errorCode"].toString() + json["errorMessage"]);
			return;
		}
		if (json.suggestions.length == 0) {
			alert("nessun suggerimento trovato");
			target.hide();
			setTimeout(function() {
				target.show();
			}, 3 * 60 * 1000);// reappears in 3 minutes(mesured in millisec)
			return;
		}
		$(event.target).parent().hide();
		$("#suggestions").show();
		gotSuggestions = true;
		for (var i = 0; i < json.suggestions.length; ++i) {
			var suggestion = json.suggestions[i];
			$("#suggestionList").append('<li>' + suggestion + '</a></li>');// append
			// suggestion
			// to
			// the
			// list
		}
	}).fail(function() {
		alert("Server non disponibile");
		$(target).removeAttr('disabled');
	});

});

$("input[type=file]").change(
		function(event) {
			var label = $("label[for='" + event.target.id + "']").html(
					"cliccare per modificare");
		});
