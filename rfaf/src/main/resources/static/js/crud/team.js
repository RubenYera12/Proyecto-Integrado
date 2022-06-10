var app = {
    backend: 'http://localhost:8080/api/',
    table : null,
    init: function() {
        app.initDatatable('#categories');
        app.table.column(0).visible(false);
        app.loadCompetition();
        $("#create").click(function(){
            var playerList = [];
            $("#selectable").children().each(function (index, value) { 
                var player = {
                    id: value.id
                }
                playerList.push(player)
            });
            console.log(playerList);
            app.create({
                id: $('#id').val(),
                name : $('#nombre').val(),
                coach : $('#entrenador').val(),
                stadium : $('#estadio').val(),
                competition : {id:$("#competicion").val()},
                players : playerList,
                image : 'img/'+$("#imagen").val().substr(12)
            });
        });
        $("#update").click(function(){
            var playerList = [];
            $("#selectable").children().each(function (index, value) { 
                var player = {
                    id: value.id
                }
                playerList.push(player)
            });
            console.log(playerList);
            app.update({
                id: $('#id').val(),
                name : $('#nombre').val(),
                coach : $('#entrenador').val(),
                stadium : $('#estadio').val(),
                competition : {id:$("#competicion").val()},
                players : playerList,
                image : 'img/'+$("#imagen").val().substr(12)
            });
        });

        $( "#selectable" ).selectable();
        $( "#selectable2" ).selectable();
        $("#add").on("click",app.loadTeamPlayers);
        $("#addPlayers").on("click",app.addPlayers);
    },
    initDatatable : function(id) {
        app.table = $(id).DataTable({
            ajax : {
                url : app.backend + 'team/getAll',
                dataSrc : function(json) {
                    json.forEach(element =>{
                        if(element.competition==null){
                            element.competition={name:"Sin competición",id:""}
                        }
                    })
                    console.log(json);
                    return json;
                }
            },
            "columnDefs" : [{
            "targets" : 1 ,
            "data": "image",
            "render" : function ( data, type, row, meta ) {
            return '<img height="50px" width="50px" src="'+data+'"/>';
            }}] ,
            dom: 'Bfrtip',
            columns : [
                {data : "id"},
                {data : "image"},
                {data : "name"},
                {data : "coach"},
                {data : "stadium"},
                {data : "competition.name"},
                {data : "players.length"}
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
                        if(confirm('¿Seguro que quieres eliminar el equipo '+data.name+'?')){
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
        $('#entrenador').val(data.coach);
        $('#estadio').val(data.stadium);
        console.log(data.image);
        app.loadURLToInputFiled(data.image);
        $( "#selectable" ).selectable();
        $( "#selectable" ).empty();
        data.players.forEach(element =>{
            $("<li>").addClass("form-control").attr("id",element.id).text(element.name+" "+element.firstname+" "+element.number)
            .append($("<span>").addClass("ui-icon ui-icon-arrowthick-2-n-s")).appendTo($("#selectable"));
        });
        $("#competicion").val(data.competition.id)

    },
    loadCompetition : function(){
        var select = $('#competicion');
        $.ajax({url: app.backend+'competition/findAll', success: function(result){
            select.append($('<option>').attr( "value",'').text("Sin competicion"));
            result.forEach(element => {
                select.append($('<option>').attr( "id",element.id).val(element.id).text(element.name));
                console.log(element.id)
            });
        }});
    },
    loadTeamPlayers :function () {
        $.ajax({
            url: app.backend + 'players/findNoTeamPlayers',
            method: 'GET',
            success : function(result) {
                result.forEach(jugador =>{
                    //Comprueba que no esté el jugador en ningun modal
                    if($("#selectable2 #"+jugador.id).length==0 && $("#selectable #"+jugador.id).length==0 ){
                        $("<li>").addClass("form-control").attr("id",jugador.id).text(jugador.name+" "+jugador.firstname+" "+jugador.number)
                        .appendTo($("#selectable2"));
                    }
                })
                
            },
            error: function(request) {
                 alert(request.responseJSON.message);
            }

        })
        $('#playersModal').addClass("show");
        $('#playersModal').css("display","block");
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
          
    },
    addPlayers : function() {
        $('#selectable2').children('li.ui-selected').appendTo('#selectable');
        $('#playersModal').removeClass("show");
        $('#playersModal').css("display","none");
    }
    ,
    cleanForm : function() {
        $('#id').val('');
        $('#nombre').val('');
        $('#entrenador').val('');
        app.loadURLToInputFiled('defadefaultTeam.png');
        $('#estadio').val('');
        $('#competicion').val('');
        $("#selectable").empty();
    },
    loadURLToInputFiled : function(url){
        app.getImgURL(url, (imgBlob)=>{
          // Load img blob to input
          // WIP: UTF8 character error
          let fileName = url.substr(4);
          let file = new File([imgBlob], fileName,{type:"image/png", lastModified:new Date().getTime()}, 'utf-8');
          let container = new DataTransfer(); 
          container.items.add(file);
          document.querySelector('#imagen').files = container.files;
        })
      },
      // xmlHTTP return blob respond
      getImgURL : function(url, callback){
        var xhr = new XMLHttpRequest();
        xhr.onload = function() {
            callback(xhr.response);
        };
        xhr.open('GET', url);
        xhr.responseType = 'blob';
        xhr.send();
      },
    create : function(data) {
        $.ajax({
            url: app.backend + 'team/create',
            data : JSON.stringify(data),
            method: 'POST',
            dataType : 'json',
            contentType: "application/json; charset=utf-8",
            success : function(json) {
                $("#msg").text('Se guardó el equipo correctamente');
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
        console.log(data.category_id);
          $.ajax({
              url: app.backend + 'team/update/'+data.id,
              data : JSON.stringify(data),
              method: 'POST',
              dataType : 'json',
              contentType: "application/json; charset=utf-8",
              success : function(json) {
                  console.log(data.image)
                  $("#msg").text('Se actualizó el equipo correctamente');
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
            url: app.backend + 'team/delete/'+id,
            data : JSON.stringify(id),
            method: 'DELETE',
            contentType: "application/json; charset=utf-8",
            success: function(result) {
            alert(result)
                $("#msg").text('Se eliminó el equipo correctamente');
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