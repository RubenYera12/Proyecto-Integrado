const backend ='http://localhost:8080/api/';
const urlSearchParams = new URLSearchParams(window.location.search);
const acta_id = urlSearchParams.get("acta_id");

$(document).ready(function(){

    $.ajax({
        url: backend+"acta/full/"+acta_id,
        success: function (response) {
            loadActa(response);
            loadLocal(response);
            loadReferees(response);
            loadVisitor(response);
        }
    })

})

function loadActa(acta){
    $("#imgLocal").attr("src",acta.designation.match.local.image);
    $("#imgVisitante").attr("src",acta.designation.match.visitor.image);
    $(".nameLocal").text(acta.designation.match.local.name);
    $(".nameVisitante").text(acta.designation.match.visitor.name);
    $("#imgCompetition").attr("src",acta.designation.match.competition.image);
    $("#nameCompetition").text(acta.designation.match.competition.name);
    $("span#stadium").text(acta.designation.match.local.stadium);
    $("#fecha").text(acta.designation.match.fecha);
}

function loadLocal(acta){
    var cont = $("div.jugadoresLocales").load("partials/gameJugador",function(){
        var modelo = $("div.jugadoresLocales").find(".ct-item");
        cont.empty();
        localPlayers= acta.designation.match.local.players;
        $.each(localPlayers,function(ind,val){
            console.log(localPlayers[ind])
            var jugador = modelo.clone();
            jugador.find(".imgJugador").attr("src",localPlayers[ind].image_url);
            jugador.find("h5.nameJugador").text(localPlayers[ind].firstname+", "+localPlayers[ind].name+" "+localPlayers[ind].number);
            
            jugador.appendTo(cont);

        })
        
    });

}

function loadReferees(acta){
    var cont = $("div.arbitros").load("partials/gameJugador",function(){
        var modelo = $("div.arbitros").find(".ct-item");
        cont.empty();
        referee= acta.designation.mainReferee;
        
        console.log(referee)
        var jugador = modelo.clone();
        jugador.find(".imgJugador").attr("src",referee.image_url);
        jugador.find("h5.nameJugador").text(referee.firstname+", "+referee.name);
        
        jugador.appendTo(cont);

        asistente1= acta.designation.assistantReferee1;
        console.log(asistente1)
        var jugador = modelo.clone();
        jugador.find(".imgJugador").attr("src",asistente1.image_url);
        jugador.find("h5.nameJugador").text(asistente1.firstname+", "+asistente1.name);
        
        jugador.appendTo(cont);

        asistente2= acta.designation.assistantReferee1;
        console.log(asistente2)
        var jugador = modelo.clone();
        jugador.find(".imgJugador").attr("src",asistente2.image_url);
        jugador.find("h5.nameJugador").text(asistente2.firstname+", "+asistente2.name);
        
        jugador.appendTo(cont);
        
    });
}

function loadVisitor(acta){
    var cont = $("div.jugadoresVisitantes").load("partials/gameJugador",function(){
        var modelo = $("div.jugadoresVisitantes").find(".ct-item");
        cont.empty();
        localPlayers=acta.designation.match.visitor.players;
        $.each(localPlayers,function(ind,val){
            console.log(localPlayers[ind])
            var jugador = modelo.clone();
            jugador.find(".imgJugador").attr("src",localPlayers[ind].image_url);
            jugador.find("h5.nameJugador").text(localPlayers[ind].firstname+", "+localPlayers[ind].name+" "+localPlayers[ind].number);
            
            jugador.appendTo(cont);

        })
    });
    
}