var app = {
    backend: 'http://localhost:8080/api/',
    table : null,
    init: function() {
        app.initDatatable('#categories');
        app.table.column(0).visible(false);
        app.table.column(6).visible(false);
        app.loadTeam();
        $("#create").click(function(){
            app.create({
                name : $('#nombre').val(),
                firstname : $('#apellido').val(),
                licencia : $('#licencia').val(),
                date : $('#fecha').val(),
                number : $('#numero').val(),
                sancion : $('#sancion').val(),
                team : {id:$('#equipo').val()}
            });
        });
        $("#update").click(function(){
            app.update({
                id: $('#id').val(),
                name : $('#nombre').val(),
                firstname : $('#apellido').val(),
                licencia : $('#licencia').val(),
                date : $('#fecha').val(),
                number : $('#numero').val(),
                sancion : $('#sancion').val(),
                team : {id:$('#equipo').val()}
            });
        });

    },
    initDatatable : function(id) {
        app.table = $(id).DataTable({
            ajax : {
                url : app.backend + 'players/findAll',
                dataSrc : function(json) {
                    json.forEach(element =>{
                        if(element.team==null){
                            element.team={name:"Agente libre",id:""}
                        }
                    })
                    console.log(json);
                    return json;
                }
            },
            dom: 'Bfrtip',
            columns : [
                {data : "id"},
                {data : "licencia"},
                {data : "name"},
                {data : "firstname"},
                {data : "date"},
                {data : "team.name"},
                {data : "team.id"},
                {data : "number"},
                {data : "sancion"}
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
                        if(confirm('¿Seguro que quieres eliminar el jugador '+data.name+" "+data.firstname+'?')){
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
        $('#nombre').val(data.name);
        $('#apellido').val(data.firstname);
        $('#licencia').val(data.licencia);
        try {
            $('#equipo').val(data.team.id);
        } catch (error) {
            $('#equipo').val('');
        }
        $('#fecha').val(data.date);
        $('#numero').val(data.number);
        $('#sancion').val(data.sancion);
    },
    loadTeam : function(){
        var select = $('#equipo');
        $.ajax({url: app.backend+'team/getAll', success: function(result){
            select.append($('<option>').attr( "value",'').text("Agente libre"));
            result.forEach(element => {
                select.append($('<option>').attr( "value",element.id).text(element.name));
            });
          }});
    },
    cleanForm : function() {
        $('#id').val('');
        $('#nombre').val('');
        $('#apellido').val('');
        $('#licencia').val('');
        $('#equipo').val('');
        $('#fecha').val('');
        $('#numero').val('');
        $('#sancion').val('');
    },
    create : function(data) {
        $.ajax({
            url: app.backend + 'players/create',
            data : JSON.stringify(data),
            method: 'POST',
            dataType : 'json',
            contentType: "application/json; charset=utf-8",
            success : function(json) {
                $("#msg").text('Se guardó el jugador correctamente');
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
        console.log(data.id);
          $.ajax({
              url: app.backend + 'players/update/'+data.id,
              data : JSON.stringify(data),
              method: 'POST',
              dataType : 'json',
              contentType: "application/json; charset=utf-8",
              success : function(json) {
                  $("#msg").text('Se actualizó el jugador correctamente');
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
    delete : function(id) {
        $.ajax({
            url: app.backend + 'players/delete/'+id,
            data : JSON.stringify(id),
            method: 'DELETE',
            contentType: "application/json; charset=utf-8",
            success: function(result) {
            alert(result)
                $("#msg").text('Se eliminó el jugador correctamente');
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