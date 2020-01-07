$("#home").show();
$("#login").hide();
$("#registrationAuthority").hide();
$("#suggestions").hide();
$("#registrationMunicipality").hide();
$("#additionalFunctions").hide();
$(".toAdditionalFunctions").hide();
$("#registrationManager").hide();
$(".registerManager").hide();//Shown only if manager gets access
$(".getSuggestions").hide();
$(".toLogin").click(
function()
{
    $("#home").hide();
    $("#login").show();
}
);
$(".login").click(
function(e)
{
    //get action
    var action=$(event.target).parent().prop('action');
    e.preventDefault();
    $.post( action, $( "#loginForm" ).serialize()
    )
     .done(function(data) {
     var json = data;
     if(json["userType"]=="manager")
     {
        $(".registrationform").attr("action","./RegistrationByManager");
        $(".toAdditionalFunctions").show();
        $(".registerManager").show();
        $(".registerAuthority").hide();
     }
     else if(json["userType"]=="municipality")
     {
         $(".registrationform").attr("action","./RegistrationByMunicipality");
         $(".toAdditionalFunctions").show();
         $(".getSuggestions").show();
         $(".registerManager").hide();
         $(".registerAuthority").show();
     }
     if(!json["error"])
     {
         $("#login").hide();
         $("#home").show();
         $(".toLogin").hide();
         alert( "second success" );
     }
     else{
         alert( json["errorCode"].toString()+json["errorMessage"] );
     }
     
    //TODO check state
    })
    .fail(function() {
    alert( "error" );
    //TODO add an error message on the page
    });
}
)
$(".toHome").click(
function(e)
{
    $(event.target).parent().hide();
    $("#home").show();
}
);
$(".registerAuthority").click(
function(e)
{
    $(event.target).parent().hide();
    $("#registrationAuthority").show();
    $(".toAdditionalFunctions").show();
}
);
$(".registerMunicipality").click(
function(e)
{
    $(event.target).parent().hide();
    $("#registrationMunicipality").show();
     $(".toAdditionalFunctions").show();
}
);
$(".registerManager").click(
function(e)
{
    $(event.target).parent().hide();
    $("#registrationManager").show();
     $(".toAdditionalFunctions").show();
}
);
$(".toAdditionalFunctions").click(
function(e)
{
    $(event.target).parent().hide();
    $("#additionalFunctions").show();
}
);
$(".getSuggestions").click(function(e)
{
    $(event.target).parent().parent().hide();
    var action=$(event.target).parent().prop('action');
    e.preventDefault();
    $.post( action, $( "#suggestionForm" ).serialize()
    )
     .done(function(data) {
     var json = data;
     for(var i=0;i<json.suggestions.length;++i)
     {
         var suggestion=json.suggestions[i];
         $("#suggestionList").append('<li>'+suggestion+'</a></li>');
     }
    })
    .fail(function() {
    alert( "error" );
    //TODO add an error message on the page
    });


}
);
$(".registrationform").click(
function(e)
{
    e.preventDefault();
}
)
$("#registerAuthority").click(
		function(e)
		{
		    //get action
		    var action=$(event.target).parent().prop('action');
		    e.preventDefault();
		    $.post( action, $( "#registerAuthorityForm" ).serialize()
		    )
		     .done(function(data) {
		     var json=data;
		     if(!json["error"])
		     {   
		         alert( "success" );
		     }
		     else{
		         alert( json["errorCode"].toString()+json["errorMessage"] );
		     }
		     
		    //TODO check state
		    })
		    .fail(function() {
		    alert( "error" );
		    //TODO add an error message on the page
		    });
		}
		)