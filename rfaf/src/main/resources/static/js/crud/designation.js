var app = {
    backend: 'http://localhost:8080/api/',
    table : null,
    init: function() {
        app.initDatatable('#categories');
        app.loadReferee();
        $("#create").click(function(){
            var teamList = [];
            $("#selectable").children().each(function (index, value) { 
                var team = {
                    id: value.id
                }
                teamList.push(team)
            });
            console.log(teamList);
            console.log($("#imagen").val())
            app.create({
                name : $('#nombre').val(),
                zone : $('#zone').val(),
                teamList : teamList,
                image : 'img/'+$("#imagen").val().substr(12)
            });
        });
        $("#update").click(function(){
            var teamList = [];
            $("#selectable").children().each(function (index, value) { 
                var team = {
                    id: value.id
                }
                teamList.push(team)
            });
            console.log(teamList);
            app.update({
                id: $('#id').val(),
                name : $('#nombre').val(),
                zone : $('#zone').val(),
                teamList : teamList,
                image : 'img/'+$("#imagen").val().substr(12)
            });
        });
        $("#addReff").on("click",function(){
            $("#btnAddReferee").show();
            $("#btnAddAs1").hide();
            $("#btnAddAs2").hide();
            app.showRef();
        });
        $("#addAssistant1").on("click",function(){
            $("#btnAddAs1").show();
            $("#btnAddReferee").hide();
            $("#btnAddAs2").hide();
            app.showRef();
        });
        $("#addAssistant2").on("click",function(){
            $("#btnAddAs2").show();
            $("#btnAddAs1").hide();
            $("#btnAddReferee").hide();
            app.showRef();
        });
    },
    initDatatable : function(id) {
        app.table = $(id).DataTable({
            ajax : {
                url : app.backend + 'designation/findAll',
                dataSrc : function(json) {
                    return json;
                }
            },
            "columnDefs" : [{
            "targets" : 1 ,
            "data": "match",
            "render" : function ( data, type, row, meta ) {
            return data.local.name+" vs "+data.visitor.name;
            }}] ,
            dom: 'Bfrtip',
            columns : [
                {data : "id"},
                {data : "match"},
                {data : "mainReferee.email"},
                {data : "assistantReferee1.email"},
                {data : "assistantReferee2.email"},
                {data : "priceReferee"},
                {data : "priceAssistant"}
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
                        if(confirm('¿Seguro que quieres eliminar la designación '+data.id+'?')){
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
        console.log(data);
        $('#id').val(data.id).removeData().data(data.id);
        $('#match').val(data.match.local.name+" vs "+data.match.visitor.name).removeData().data(data.match)
        $('#referee').val(data.mainReferee.firstname+", "+data.mainReferee.name).removeData().data(data.mainReferee);
        $('#assistant1').val(data.assistantReferee1.firstname+", "+data.assistantReferee1.name).removeData().data(data.assistantReferee1);
        $('#assistant2').val(data.assistantReferee1.firstname+", "+data.assistantReferee2.name).removeData().data(data.assistantReferee2);
        $('#sueldoArbitro').val(data.priceReferee)
        $('#sueldoAsistente').val(data.priceAssistant)
    },
    loadReferee : function(){
        var select = $('#competicion');
        var tab = $('#tabs');
        var cabecera = $('#tabs ul#arbitros');
        $.ajax({url: app.backend+'category/findAll', success: function(result){
            result.forEach(element => {
                //Cargamos la competición y sus equipos en los tabs para elegir equpipo local y visitante
                cabecera.append($('<li>').append($('<a>').attr("href","#tabs-"+element.id).text(element.name)));
                var compHeader = $('<div>').attr('id','tabs-'+element.id);
                tab.append(compHeader);
                var teamlist = $("<ol>")
                teamlist.addClass("selectable")
                element.refereeList.forEach(team=>{
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
    showRef : function () {  
        $('#playersModal').addClass("show");
        $('#playersModal').css("display","block");
        $(".ui-selected").removeClass("ui-selected");
    },
    cleanForm : function() {
        $('#id').val('').removeData();
        $('#match').val('').removeData();
        $('#referee').val('').removeData();
        $('#assistant1').val('').removeData();
        $('#assistant2').val('').removeData();
        $('#sueldoArbitro').val('')
        $('#sueldoAsistente').val('')
    },
    create : function(data) {
        console.log(data)
        $.ajax({
            url: app.backend + 'designation/create',
            data : JSON.stringify(data),
            method: 'POST',
            dataType : 'json',
            contentType: "application/json; charset=utf-8",
            success : function(json) {
                $("#msg").text('Se guardó la designación correctamente');
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
        console.log(data);
          $.ajax({
              url: app.backend + 'designation/update/'+data.id,
              data : JSON.stringify(data),
              method: 'POST',
              dataType : 'json',
              contentType: "application/json; charset=utf-8",
              success : function(json) {
                  $("#msg").text('Se actualizó la designacióno correctamente');
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
            url: app.backend + 'designation/delete/'+id,
            data : JSON.stringify(id),
            method: 'DELETE',
            contentType: "application/json; charset=utf-8",
            success: function(result) {
            alert(result)
                $("#msg").text('Se eliminó la designación correctamente');
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
});