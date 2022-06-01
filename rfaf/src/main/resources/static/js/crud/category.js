var app = {
    backend: 'http://localhost:8080/api/category',
    table : null,
    init: function() {
        app.initDatatable('#categories');

        $("#save").click(function(){
            app.save({
                id: $('#id').val(),
                nombre : $('#nombre').val()
            });
        });
    },
    initDatatable : function(id) {
        app.table = $(id).DataTable({
            ajax : {
                url : app.backend + '/findAll',
                dataSrc : function(json) {
                    return json;
                }
            },
            dom: 'Bfrtip',
            columns : [
                {data : "id"},
                {data : "name"},
            ],
            buttons: [
                {
                    text : 'Editar',
                    action : function(e, dt, node, config) {
                        var data = dt.rows('.table-active').data()[0];
                        app.setDataToModal(data);
                        $('#personaModal').modal();
                    }
                }
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
        $('#nombre').val(data.nombre);
    },
    save : function(data) {
        $.ajax({
            url: app.backend + '/save',
            data : JSON.stringify(data),
            method: 'POST',
            dataType : 'json',
            contentType: "application/json; charset=utf-8",
            success : function(json) {
                $("#msg").text('Se guardó la persona correctamente');
                $("#msg").show();
                $('#personaModal').modal('hide');
                app.table.ajax.reload();
            },
            error : function(error) {

            }
        })
    }
};

$(document).ready(function(){
    app.init();
});