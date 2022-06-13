var app = {
    backend: 'http://localhost:8080/api/',
    table : null,
    init: function() {
        app.initDatatable('#categories');
        app.table.column(0).visible(false);
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

        $( "#selectable" ).selectable();
        $( "#selectable2" ).selectable();
        $("#add").on("click",app.loadTeams);
        $("#addPlayers").on("click",app.addPlayers);
    },
    initDatatable : function(id) {
        app.table = $(id).DataTable({
            ajax : {
                url : app.backend + 'designation/findAll',
                dataSrc : function(json) {
                    return json;
                }
            },
            dom: 'Bfrtip',
            columns : [
                {data : "id"},
                {data : "match.local.name"},
                {data : "match.visitor.name"},
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
        $('#id').val(data.id);
        $('#match').val(data.match.local.name+" vs "+data.match.visitor.name)
        $('#referee').val(data.mainReferee.name).removeData().data(data.mainReferee);
        $('#assistant1').val(data.assistantReferee1.name).removeData().data(data.assistantReferee1);
        $('#assistant2').val(data.assistantReferee2.name).removeData().data(data.assistantReferee2);
        $('#sueldoArbitro').val(data.priceReferee)
        $('#sueldoAsistente').val(data.priceAssistant)
    },
    addPlayers : function() {
        $('#selectable2').children('li.ui-selected').appendTo('#selectable');
        $('#playersModal').removeClass("show");
        $('#playersModal').css("display","none");
    }
    ,
    cleanForm : function() {
        $('#id').val('');
        $('#match').val('')
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