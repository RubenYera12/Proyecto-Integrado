var app = {
    backend: 'http://localhost:8080/api/referee',
    table : null,
    init: function() {
        app.initDatatable('#categories');
        app.table.column(9).visible(false);
        $("#create").click(function(){
            app.create({
                name : $('#nombre').val(),
                firstname : $('#apellido').val(),
                licenseNum : $('#numeroLicencia').val(),
                email : $('#email').val(),
                telfNumber : $('#telefono').val(),
                city : $('#ciudad').val(),
                birthDate : $('#fechaNac').val(),
                category_id : $('#category_id').val(),
                nevera : $('#nevera').val()
            });
        });
        $("#update").click(function(){
            app.update({
                id: $('#id').val(),
                name : $('#nombre').val(),
                firstname : $('#apellido').val(),
                licenseNum : $('#numeroLicencia').val(),
                email : $('#email').val(),
                telfNumber : $('#telefono').val(),
                city : $('#ciudad').val(),
                birthDate : $('#fechaNac').val(),
                category_id : $('#category_id').val(),
                nevera : $('#nevera').val()
            });
        });

    },
    initDatatable : function(id) {
        app.table = $(id).DataTable({
            ajax : {
                url : app.backend + '/getAll',
                dataSrc : function(json) {
                    return json;
                }
            },
            dom: 'Bfrtip',
            columns : [
                {data : "id"},
                {data : "name"},
                {data : "firstname"},
                {data : "licenseNum"},
                {data : "email"},
                {data : "telfNumber"},
                {data : "city"},
                {data : "birthDate"},
                {data : "category.name"},
                {data : "category.id"},
                {data : "nevera"}
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
                        if(confirm('¿Seguro que quieres eliminar la categoría '+data.id+'?')){
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
        $('#numeroLicencia').val(data.licenseNum);
        $('#email').val(data.email);
        $('#telefono').val(data.telfNumber);
        $('#ciudad').val(data.city);
        $('#fechaNac').val(data.birthDate);
        $('#categoria').val(data.category.name);
        $('#category_id').val(data.category.id);
        $('#nevera').val(data.nevera);
    },
    cleanForm : function() {
             $('#id').val('');
             $('#nombre').val('');
             $('#apellido').val('');
             $('#numeroLicencia').val('');
             $('#email').val('');
             $('#telefono').val('');
             $('#ciudad').val('');
             $('#fechaNac').val('');
             $('#categoria').val('');
             $('#nevera').val('');
    },
    create : function(data) {
        $.ajax({
            url: app.backend + '/create',
            data : JSON.stringify(data),
            method: 'POST',
            dataType : 'json',
            contentType: "application/json; charset=utf-8",
            success : function(json) {
                $("#msg").text('Se guardó la categoría correctamente');
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
              url: app.backend + '/update/'+data.id,
              data : JSON.stringify(data),
              method: 'POST',
              dataType : 'json',
              contentType: "application/json; charset=utf-8",
              success : function(json) {
                  $("#msg").text('Se actualizó la categoría correctamente');
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
            url: app.backend + '/delete/'+id,
            data : JSON.stringify(id),
            method: 'DELETE',
            contentType: "application/json; charset=utf-8",
            success: function(result) {
            alert(result)
                $("#msg").text('Se eliminó la categoría correctamente');
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