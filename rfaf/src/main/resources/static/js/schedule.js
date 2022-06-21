const backend ='http://localhost:8080/api/';
const urlSearchParams = new URLSearchParams(window.location.search);
const competition_id = urlSearchParams.get("competition_id");
$(document).ready(function(){

    if(competition_id===null){
        $.ajax({
            url: backend+"acta",
            success: function (response) {
    
                loadGames(response)
            }
        });
    }else{
        $.ajax({
            url: backend+"acta/getByCompetition/"+competition_id,
            success: function (response) {
    
                loadGames(response)
            }
        });
    }
    $.ajax({
        url: backend+"competition/findAll/",
        success: function (response) {
            loadCompeticiones(response)
        }
    });



})

function loadCompeticiones(competiciones){
    competiciones.forEach(element => {
        $("#listaCompeticiones").append($("<a>").attr("href","partidos?competition_id="+element.id)
        .addClass("sl-item").append($("<img>").attr("src",element.image)).append($("<span>").text(element.name)));
    })
}

function loadGames(actas){
    var cont = $("#partidos").load("partials/partido",function(){
        var modelo = $("#partidos").find(".partido");
        cont.empty();
        
        $.each(actas,function(ind,val){
            var game=actas[ind]
            console.log(actas[ind])
            var jugador = modelo.clone();
            jugador.find(".imgLocal").attr("src",game.designation.match.local.image);
            jugador.find(".nombreLocal").text(game.designation.match.local.name);
            jugador.find(".estadio").text(game.designation.match.local.stadium);
            jugador.find(".fecha").text(game.designation.match.fecha);
            
            jugador.find(".imgVisitante").attr("src",game.designation.match.visitor.image);
            jugador.find(".nombreVisitante").text(game.designation.match.visitor.name);

            if(game.finalizado){
                jugador.find(".resultado").text(game.golesLocal+" : "+game.golesVisitante);
            }else{
                jugador.find(".resultado").text("VS");
            }
                        
            $(jugador).on("click",function() {
                $(location).prop('href', 'http://localhost:8080/game?acta_id='+game.id)
            })
            jugador.appendTo(cont);

        })
        
    });

}