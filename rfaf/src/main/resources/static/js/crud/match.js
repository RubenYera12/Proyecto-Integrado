var app = {
    backend: 'http://localhost:8080/api/',
    table : null,
    init: function() {
        app.initDatatable('#categories');
        app.table.column(0).visible(false);
        app.loadCompetition();
        $("#create").click(function(){
            app.create({
                id: $('#id').val(),
                local: $("#local").data(),
                visitor: $("#visitante").data(),
                matchDate: $("#fecha").val(),
                hour: $("#hora").val(),
                competition : {id:$("#competicion").val()},
                season: $("#temporada").val()
            });
        });
        $("#update").click(function () { 
            
            app.update({
                id: $('#id').val(),
                local: $("#local").data(),
                visitor: $("#visitante").data(),
                matchDate: $("#fecha").val(),
                hour: $("#hora").val(),
                competition : {id:$("#competicion").val()},
                season: $("#temporada").val()
            });
        });
        $("#addLocal").on("click",function(){
            $("#btnAddVisitor").hide();
            $("#btnAddLocal").show();
            app.loadTeam();
        });
        $("#addVisitor").on("click",function(){
            $("#btnAddLocal").hide();
            $("#btnAddVisitor").show();
            app.loadTeam();
        });
    },
    initDatatable : function(id) {
        app.table = $(id).DataTable({
            ajax : {
                url : app.backend + 'match/findAll',
                dataSrc : function(json) {
                    json.forEach(element =>{
                        if(element.competition==null){
                            element.competition={name:"Amistoso",id:""}
                        }
                    })
                    return json;
                }
            },
            dom: 'Bfrtip',
            columns : [
                {data : "id"},
                {data : "local.name"},
                {data : "visitor.name"},
                {data : "matchDate"},
                {data : "hour"},
                {data : "competition.name"},
                {data : "season"}
            ],
            buttons: [
                {
                    text : 'Crear',
                    action : function(e, dt, node, config) {
                        app.cleanForm();
                        $('#update').hide();
                        $('#create').show();
                        $('#personaModal').modal();
                    }
                },
                {
                    text : 'Editar',
                    action : function(e, dt, node, config) {
                        $('#create').hide();
                        $('#update').show();
                        var data = dt.rows('.table-active').data()[0];
                        app.setDataToModal(data);
                        $('#personaModal').modal();
                    }
                },
                {
                    text : 'Eliminar',
                    action : function(e, dt, node, config) {
                        var data = dt.rows('.table-active').data()[0];
                        if(confirm('¿Seguro que quieres eliminar el partido: '+data.local.name+' vs '+data.visitor.name+' ?')){
                            app.delete(data.id)
                        }
                    }
                },

            ]
        });

        $('#categories tbody').on('click', 'tr', function(){
            if ($(this).hasClass('table-active')) {
                $(this).removeClass('table-active');
            } else {
                app.table.$('tr.table-active').removeClass('table-active');
                $(this).addClass('table-active');
            }
        });
    },
    setDataToModal : function(data) {
        $('#id').val(data.id);
        $("#localId").val(data.local.id);
        $('#local').val(data.local.name).data(data.local);
        $("#visitanteId").val(data.visitor.id);
        $('#visitante').val(data.visitor.name).data(data.visitor);
        $('#fecha').val(data.matchDate);
        $( "#hora" ).val(data.hour);
        $("#competicion").val(data.competition.id).data(data.competition);
        $("#temporada").val(data.season);

    },
    loadCompetition : function(){
        var select = $('#competicion');
        var tab = $('#tabs');
        var cabecera = $('#tabs ul#competiciones');
        $.ajax({url: app.backend+'competition/findAll', success: function(result){
            select.append($('<option>').attr( "value",'').text("Amistoso"));
            //variable para controlar las competiciones
            result.forEach(element => {
                //Cargamos la competición en el formulario
                select.append($('<option>').attr( "id",element.id).val(element.id).text(element.name));

                //Cargamos la competición y sus equipos en los tabs para elegir equpipo local y visitante
                cabecera.append($('<li>').append($('<a>').attr("href","#tabs-"+element.id).text(element.name)));
                var compHeader = $('<div>').attr('id','tabs-'+element.id);
                tab.append(compHeader);
                var teamlist = $("<ol>")
                teamlist.addClass("selectable")
                element.teams.forEach(team=>{
                    var teamDiv = $('<li>').attr('id',team.id).addClass("form-control").text(team.name);
                    teamDiv.data("team",team);
                    teamDiv.data("competition",element);
                    teamDiv.on("click",function (e) { 
                        if(e.ctrlKey){
                            e.preventDefault();
                        }
                    });
                    $(teamlist).append(teamDiv);
                })
                $(compHeader).append(teamlist);
                teamlist.selectable({
                    tolerance: "fit"
                }); 
            });
            tab.tabs();
            //TODO: VALIDAR EQUIPOS SELECCIONADOS

        }});
        
    },
    loadTeam : function(){
        $('#playersModal').addClass("show");
        $('#playersModal').css("display","block");
        $(".ui-selected").removeClass("ui-selected");

    },
    cleanForm : function() {
        $('#id').val('');
        $("#localId").val('');
        $('#local').val('').removeData();
        $("#visitanteId").val('');
        $('#visitante').val('').removeData();
        $('#fecha').val('');
        $( "#hora" ).val('');
        $("#competicion").val('');
        $("#temporada").val('');
    },
    create : function(data) {
        $.ajax({
            url: app.backend + 'match/create',
            data : JSON.stringify(data),
            method: 'POST',
            dataType : 'json',
            contentType: "application/json; charset=utf-8",
            success : function(json) {
                $("#msg").text('Se guardó el partido correctamente');
                $("#msg").show();
                $('#personaModal').modal('hide');
                app.table.ajax.reload();
                setTimeout(function(){
                    $("#msg").hide();
                }, 5000);
            },
              error: function(request) {
                   alert(request.responseJSON.message);
              }
        })
    },
    update : function(data) {
        $.ajax({
            url: app.backend + 'match/update/'+data.id,
            data : JSON.stringify(data),
            method: 'POST',
            dataType : 'json',
            contentType: "application/json; charset=utf-8",
            success : function(json) {
                console.log(data.image)
                $("#msg").text('Se actualizó el partido correctamente');
                $("#msg").show();
                $('#personaModal').modal('hide');
                app.table.ajax.reload();
                setTimeout(function(){
                    $("#msg").hide();
                }, 5000);
            },
            error: function(request) {
                console.log(data);
                alert(request.responseJSON.message);
            }

        })
      },
    delete : function(id) {
        $.ajax({
            url: app.backend + 'match/delete/'+id,
            data : JSON.stringify(id),
            method: 'DELETE',
            contentType: "application/json; charset=utf-8",
            success: function(result) {
            alert(result)
                $("#msg").text('Se eliminó el partido correctamente');
                $("#msg").show();
                app.table.ajax.reload();
                setTimeout(function(){
                    $("#msg").hide();
                }, 5000);
            },
              error: function(request) {
                   alert(request.responseJSON.message);
              }

        })
    }
};

$(document).ready(function(){
    app.init();
    $("#btnAddLocal").on("click",function () {
        var local = $(".ui-selected");

        if(local.length>1){
            alert("No puedes seleccionar más de un equipo")
        }else{
            if(local.data().team.id===$("#visitante").data().id){
                alert("Ya has seleccionado al "+local.data().team.name+" como equipo visitante.")
            }else{
                $("#local").val(local.data().team.name).removeData().data(local.data().team)
                $('#playersModal').removeClass("show");
                $('#playersModal').css("display","none");
            }
        }
    })
    $("#btnAddVisitor").on("click",function () {
        var visitante = $(".ui-selected");
    
        if(visitante.length>1){
            alert("No puedes seleccionar más de un equipo.")
        }else{
            if(visitante.data().team.id===$("#local").data().id){
                alert("Ya has seleccionado al "+visitante.data().team.name+" como equipo local.")
            }else{
                $("#visitante").val(visitante.data().team.name).removeData().data(visitante.data().team)
                $('#playersModal').removeClass("show");
                $('#playersModal').css("display","none");
            }
        }
    })

    $('#closePlayers').on("click",function(){
        $('#playersModal').removeClass("show");
        $('#playersModal').css("display","none");
        console.log("Cerrado el modal de jugadores.");
    })
    $('#close').on("click",function(){
        $('#playersModal').removeClass("show");
        $('#playersModal').css("display","none");
        console.log("Cerrado el modal de jugadores.");
    })
});