const backend ='http://localhost:8080/api/';
const urlSearchParams = new URLSearchParams(window.location.search);
const designation_id = urlSearchParams.get("designation_id");
const acta_id = urlSearchParams.get("acta_id");
var designation = {};
$(document).ready(function(){
    var btnTabActivo = $("#myTab li button.active");
    var tabActivo = $("#myTabContent div.active");
    $.ajax({
        url: backend+"designation/findById/"+designation_id,
        success: function (response) {
            designation=response;
            loadLocal(response);
            loadVisitor(response);
            loadActaDatos(response);
        }
    });

    $.ajax({
        url: backend+"acta/full/"+acta_id,
        success: function (response) {
            loadJugadores(response);
        }
    })
    
    $("#myTab li button").on("click",function() {
        btnTabActivo.attr("aria-selected","false").removeClass("active");
        tabActivo.removeClass("show active");

        btnTabActivo = $(this);
        btnTabActivo.attr("aria-selected","true").addClass("active");
        tabActivo = $("#"+this.id+"-pane");
        tabActivo.addClass("show active");
    });
    
    $("#guardar").on("click",guardar)
});

function guardar(){
    var horaInicio = $("#inicio").val();
    var segundaParte = $("#segundaParte").val();
    var desfibrilador = $("#desfibrilador").is(':checked');
    var jugadoresTitularesLocales =[];
    var jugadoresSuplentesLocales =[];
    var jugadoresTitularesVisitantes=[];
    var jugadoresSuplentesVisitantes=[];
    // $("#jugadoresLocales").children().each(element => {
    //     console.log($($($($(element).children()[2]).find(".active")).children()).val())
    // });

    $.each($("#jugadoresLocales").children(), function (ind, val) {
        var jugador = $($("#jugadoresLocales").children()[ind]).data(); 
        if($($($($($("#jugadoresLocales").children()[ind]).children()[2]).find(".active")).children()).val()=='true'){
            jugadoresTitularesLocales.push(jugador);
        }else{
            jugadoresSuplentesLocales.push(jugador);
        }
         
    });

    $.each($("#jugadoresVisitantes").children(), function (ind, val) {
        var jugador = $($("#jugadoresVisitantes").children()[ind]).data(); 
        if($($($($($("#jugadoresVisitantes").children()[ind]).children()[2]).find(".active")).children()).val()=='true'){
            jugadoresTitularesVisitantes.push(jugador);
        }else{
            jugadoresSuplentesVisitantes.push(jugador);
        }
         
    });

    var golesLocales=[];
    $.each($("#golesLocales").children(),function(ind,val){
        var gol;
        var min = $($("#golesLocales").children()[ind]).data().min;
        var player = $($("#golesLocales").children()[ind]).data().player;
        gol = {player:player,minuto:min}
        golesLocales.push(gol);
    })

    var golesVisitantes=[];
    $.each($("#golesVisitantes").children(),function(ind,val){
        var gol;
        var min = $($("#golesVisitantes").children()[ind]).data().min;
        var player = $($("#golesVisitantes").children()[ind]).data().player;
        gol = {player:player,minuto:min}
        golesVisitantes.push(gol);
    })

    var tarjetasAmarillas=[];
    var tarjetasRojas=[];
    $.each($("#tarjetasLocales").children(),function(ind,val){
        var tarjeta;
        var data = $($("#tarjetasLocales").children()[ind]).data();
        tarjeta = {player:data.player,minuto:data.min,motivo:data.motivo}
        if(data.tarjeta=='AMARILLA'){
            tarjetasAmarillas.push(tarjeta);
        }else{
            tarjetasRojas.push(tarjeta);
        }
        
    })

    $.each($("#tarjetasVisitantes").children(),function(ind,val){
        var tarjeta;
        var data = $($("#tarjetasVisitantes").children()[ind]).data();
        tarjeta = {player:data.player,minuto:data.min,motivo:data.motivo}
        if(data.tarjeta=='AMARILLA'){
            tarjetasAmarillas.push(tarjeta);
        }else{
            tarjetasRojas.push(tarjeta);
        }
        
    })

    var publico = $("#publico").val();
    var terreno = $("#terreno").val();
    var observaciones = $("#observaciones").val();

    var acta = {
        id:acta_id,
        designation:designation,
        horaInicio:horaInicio,
        horaSegundaParte:segundaParte,
        desfibrilador:desfibrilador,
        startingLocalPlayers:jugadoresTitularesLocales,
        substituteLocalPlayers:jugadoresSuplentesLocales,
        startingVisitorPlayers:jugadoresTitularesVisitantes,
        substituteVisitorPlayers:jugadoresSuplentesVisitantes,
        goalsLocal:golesLocales,
        goalsVisitor:golesVisitantes,
        yellowCards:tarjetasAmarillas,
        redCards:tarjetasRojas,
        publico:publico,
        deficienciasTerreno:terreno,
        otrasObservaciones:observaciones
    };
    console.log(acta)

    $.ajax({
        type: "POST",
        url: backend+"acta/"+acta_id,
        data: JSON.stringify(acta),
        dataType : 'json',
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            
        }
    });
}

function loadActaDatos(designation){
    console.log(designation)
    $("#imgLocal").attr("src",designation.match.local.image);
    $("#imgVisitante").attr("src",designation.match.visitor.image);
    $("#nameLocal").text(designation.match.local.name);
    $("#nameVisitante").text(designation.match.visitor.name);
    $("#imgCompetition").attr("src",designation.match.competition.image);
    $("#nameCompetition").text(designation.match.competition.name);
    $("span#stadium").text(designation.match.local.stadium);
    $("#fecha").text(designation.match.fecha);
}

function loadJugadores(acta){
    var selectLocal = $("#jugadorLocal")
    acta.designation.match.local.players.forEach(element => {
        selectLocal.append($('<option>')
        .attr( "value",element.id)
        .text(element.firstname+" "+element.name+" "+element.number)
        .data(element));
    });

    var selectVisitante = $("#jugadorVistante")
    acta.designation.match.visitor.players.forEach(element => {
        selectVisitante.append($('<option>')
        .attr( "value",element.id)
        .text(element.firstname+" "+element.name+" "+element.number)
        .data(element));
    });

    var selectLocal = $("#jugadorLocalTarjeta")
    acta.designation.match.local.players.forEach(element => {
        selectLocal.append($('<option>')
        .attr( "value",element.id)
        .text(element.firstname+" "+element.name+" "+element.number)
        .data(element));
    });

    var selectVisitante = $("#jugadorVistanteTarjeta")
    acta.designation.match.visitor.players.forEach(element => {
        selectVisitante.append($('<option>')
        .attr( "value",element.id)
        .text(element.firstname+" "+element.name+" "+element.number)
        .data(element));
    });

    $("#addGolLocal").on("click",function(){
        var min = $("#minGolLocal").val();
        var jugador = $("#"+$("#jugadorLocal").val()).data();
        var tablaGoles = $("#golesLocales");
        tablaGoles.append($("<tr>").append($("<td>").text("GOL min:"+min))
        .append($("<td>").text(jugador.name+" "+jugador.firstname))
        .data("min",min).data("player",jugador));

    })

    $("#addGolVisitante").on("click",function(){
        var min = $("#minGolVisitante").val();
        var jugador = $("#"+$("#jugadorVistante").val()).data();
        var tablaGoles = $("#golesVisitantes");
        tablaGoles.append($("<tr>").append($("<td>").text("GOL min:"+min))
        .append($("<td>").text(jugador.name+" "+jugador.firstname))
        .data("min",min).data("player",jugador));

    })

    $("#addTarjetaLocal").on("click",function(){
        var tarjeta = $("#colorTarjetaLocal").val();
        var min = $("#minTarjetaLocal").val();
        var jugador = $("#"+$("#jugadorLocalTarjeta").val()).data();
        var motivo = $("#motivoTarjetaLocal").val();

        var tablaGoles = $("#tarjetasLocales");
        tablaGoles.append($("<tr>").append($("<div>").html("Tarjeta "+tarjeta+" min:"+min+", "+jugador.name+" "+jugador.firstname+"<br> "+motivo))
        .data("tarjeta",tarjeta).data("min",min).data("player",jugador).data("motivo",motivo));

    })

    $("#addTarjetaVisitante").on("click",function(){
        var tarjeta = $("#colorTarjetaVisitante").val();
        var min = $("#minTarjetaVisitante").val();
        var jugador = $("#"+$("#jugadorVistanteTarjeta").val()).data();
        var motivo = $("#motivoTarjetaVisitante").val();

        var tablaGoles = $("#tarjetasVisitantes");
        tablaGoles.append($("<tr>").append($("<div>").html("Tarjeta "+tarjeta+" min:"+min+", "+jugador.name+" "+jugador.firstname+"<br> "+motivo))
        .data("tarjeta",tarjeta).data("min",min).data("player",jugador).data("motivo",motivo));

    })
}

function loadLocal(designacion){
    $("#localCoach").text(designacion.match.local.coach);
    var cont = $("#jugadoresLocales").load("partials/actaJugador",function(){
        var modelo = $("#jugadoresLocales").find(".jugador");
        cont.empty();
        localPlayers=designacion.match.local.players;
        $.each(localPlayers,function(ind,val){
            console.log(localPlayers[ind])
            var jugador = modelo.clone();
            jugador.find(".img").attr("src",localPlayers[ind].image_url);
            jugador.find("td.name").text(localPlayers[ind].firstname+", "+localPlayers[ind].name+" "+localPlayers[ind].number);
            
            jugador.attr("id",localPlayers[ind].id);
            jugador.data(localPlayers[ind])
            jugador.appendTo(cont);

        })
        
    });

}

function loadVisitor(designacion){
    $("#visitorCoach").text(designacion.match.visitor.coach);
    var cont = $("#jugadoresVisitantes").load("partials/actaJugador",function(){
        var modelo = $("#jugadoresVisitantes").find(".jugador");
        cont.empty();
        localPlayers=designacion.match.visitor.players;
        $.each(localPlayers,function(ind,val){
             console.log(localPlayers[ind])
             var jugador = modelo.clone();
             jugador.find(".img").attr("src",localPlayers[ind].image_url);
             jugador.find("td.name").text(localPlayers[ind].firstname+", "+localPlayers[ind].name+" "+localPlayers[ind].number);
            
             jugador.attr("id",localPlayers[ind].id);
             jugador.data(localPlayers[ind])
             jugador.appendTo(cont);

        })
    });
    
}