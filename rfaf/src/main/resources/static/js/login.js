const backend ='http://localhost:8080/api/';
const urlSearchParams = new URLSearchParams(window.location.search);
const acta_id = urlSearchParams.get("acta_id");

$(document).ready(function(){

    $("#enviar").on("click",function(){
        var email = $("#email").val();
        var password = $("#password").val();
        var recuerdame = $("#recuerdame").is( ":checked" );

        $.ajax({
            url: backend+"login",
            headers : {
                email:email,
                password:password
            },
            success: function (response) {
                $(location).prop('href', $(location).attr('href'))
            },
            error: function(request,msg,error) {
                $("#msg").text('Usuario o contraseña incorrectos');
                $("#msg").show();
                setTimeout(function(){
                    $("#msg").hide();
                }, 5000);
            }
        })
    })

})