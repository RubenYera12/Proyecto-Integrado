var app = {
    backend: 'http://localhost:8080/api/',
    table : null,
    init: function() {
        app.initDatatable('#categories');
        app.loadReferee();
        
        $("#create").click(function(){
            app.create({
                match : $('#match').data().match,
                mainReferee : $('#referee').data(),
                assistantReferee1 : $('#assistant1').data(),
                assistantReferee2 : $('#assistant2').data(),
                priceReferee: $('#sueldoArbitro').val(),
                priceAssistant: $('#sueldoAsistente').val()
            });
        });
        $("#update").click(function(){
            app.update({
                id: $('#id').val(),
                match : $('#match').data(),
                mainReferee : $('#referee').data(),
                assistantReferee1 : $('#assistant1').data(),
                assistantReferee2 : $('#assistant2').data(),
                priceReferee: $('#sueldoArbitro').val(),
                priceAssistant: $('#sueldoAsistente').val()
            });
        });
        $("#addReff").on("click",function(){
            $("#addReferee").show();
            $("#btnAddAs1").hide();
            $("#btnAddAs2").hide();
            app.showRef();
        });
        $("#addAssistant1").on("click",function(){
            $("#addReferee").hide();
            $("#btnAddAs1").show();
            $("#btnAddAs2").hide();
            app.showRef();
        });
        $("#addAssistant2").on("click",function(){
            $("#addReferee").hide();
            $("#btnAddAs1").hide();
            $("#btnAddAs2").show();
            app.showRef();
        });

        $("#addMatch").on("click",app.showMatch)
        $( "#selectable2" ).selectable();
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
        var cabecera = $('#myTab');
        $.ajax({url: app.backend+'category/findAll', success: function(result){
            var cont = 0;
            result.forEach(element => {
                //Cargamos la competición y sus equipos en los tabs para elegir equpipo local y visitante
                var btn = $('<button>').addClass("nav-link").attr("id",element.name+"-tab")
                .attr("data-bs.toggle","tab").attr("data-bs-target","#"+element.name)
                .attr("role","tab").attr("aria-controls",element.name).attr("aria-selected","false").text(element.name);
                if(cont==0){btn.addClass("active").attr("aria-selected","true");}
                cabecera.append($('<li>').addClass("nav-item").attr("role","presentation").append(btn));
                var container = $("#myTabContent");
                var compHeader = $('<div>').attr('id',element.name).addClass("tab-pane fade").attr("role","tabpanel")
                .attr("aria-labelledby",element.name+"-tab");
                if(cont==0){compHeader.addClass("show active");}

                btn.on("click",function(){
                    $(".nav-link").removeClass("active").attr("aria-selected","false");
                    btn.addClass("active").attr("aria-selected","true");

                    $(".tab-pane").removeClass("show active")
                    compHeader.addClass("show active")
                })

                container.append(compHeader);
                var teamlist = $("<ol>")
                teamlist.addClass("selectable")
                element.refereeList.forEach(team=>{
                    var teamDiv = $('<li>').attr('id',team.id).addClass("form-control").text(team.firstname+" "+team.name);
                    teamDiv.data("referee",team);
                    teamDiv.data("category",element);
                    teamDiv.on("click",function (e) { 
                        if(e.ctrlKey){
                            e.preventDefault();
                        }
                    });
                    if(team.nevera){
                        teamDiv.hide();
                    }
                    $(teamlist).append(teamDiv);
                })
                $(compHeader).append(teamlist);
                teamlist.selectable({
                    tolerance: "fit"
                });
                
                cont++; 
            });
            $(".ui-selectee").on("click",function () {
                $(".selectable").children().each(function(){
                    $(this).removeClass("ui-selected");
                })

                $(this).addClass("ui-selected")
            })
            
            //TODO: VALIDAR EQUIPOS SELECCIONADOS

        }});
        
    },loadMatch :function () {
        $.ajax({
            url: app.backend + 'match/findNoDesignationMatches',
            method: 'GET',
            success : function(result) {
                result.forEach(match =>{
                    $("#selectable2").empty();
                    $("<li>").addClass("form-control").attr("id",match.id).text(match.local.name+" vs "+match.visitor.name).data("match",match)
                    .appendTo($("#selectable2"));
                    
                })
                
            },
            error: function(request) {
                 alert(request.responseJSON.message);
            }

        })
    },
    addMatch : function() {
        var match = $("#selectable2").children('li.ui-selected');
        console.log("wsdasfas")
        if(match.length>1){
            alert("No puedes elegir más de un partido para esta designación");
        }else{
            $("#match").val(match.data().match.local.name+" vs "+match.data().match.visitor.name).removeData().data(match.data())
            $('#matchModal').removeClass("show");
            $('#matchModal').css("display","none");
        }
    },showMatch : function(){
        app.loadMatch();
        $("#addPartido").on("click",app.addMatch);
        
        $('#matchModal').addClass("show");
        $('#matchModal').css("display","block");
        $('#closeMatch').on("click",function(){
            $('#matchModal').removeClass("show");
            $('#matchModal').css("display","none");
            console.log("Cerrado el modal de partidos.");
        })
        $('#closeMatche').on("click",function(){
            $('#matchModal').removeClass("show");
            $('#matchModal').css("display","none");
            console.log("Cerrado el modal de partidos.");
        })
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

    $("#addReferee").on("click",function () {
        var ref = $(".ui-selected");

        if(ref.length>1){
            alert("No puedes seleccionar más de un árbitro")
        }else{
            if(ref.data().referee.id===$("#assistant1").data().id||ref.data().referee.id===$("#assistant2").data().id){
                alert("Ya has seleccionado al "+ref.data().referee.name+" como arbitro.")
            }else{
                $("#referee").val(ref.data().referee.firstname+" "+ref.data().referee.name).removeData().data(ref.data().referee)
                $('#playersModal').removeClass("show");
                $('#playersModal').css("display","none");
            }
        }
    })
    $("#btnAddAs1").on("click",function () {
        var ref = $(".ui-selected");

        if(ref.length>1){
            alert("No puedes seleccionar más de un árbitro")
        }else{
            if(ref.data().referee.id===$("#referee").data().id||ref.data().referee.id===$("#assistant2").data().id){
                alert("Ya has seleccionado al "+ref.data().referee.name+" como arbitro.")
            }else{
                $("#assistant1").val(ref.data().referee.firstname+" "+ref.data().referee.name).removeData().data(ref.data().referee)
                $('#playersModal').removeClass("show");
                $('#playersModal').css("display","none");
            }
        }
    })

    $("#btnAddAs2").on("click",function () {
        var ref = $(".ui-selected");

        if(ref.length>1){
            alert("No puedes seleccionar más de un árbitro")
        }else{
            if(ref.data().referee.id===$("#referee").data().id||ref.data().referee.id===$("#assistant1").data().id){
                alert("Ya has seleccionado al "+ref.data().referee.name+" como arbitro.")
            }else{
                $("#assistant2").val(ref.data().referee.firstname+" "+ref.data().referee.name).removeData().data(ref.data().referee)
                $('#playersModal').removeClass("show");
                $('#playersModal').css("display","none");
            }
        }
    })

    $('#close').on("click",function(){
        $('#playersModal').removeClass("show");
        $('#playersModal').css("display","none");
        console.log("Cerrado el modal de jugadores.");
    })
    $('#closeReferee').on("click",function(){
        $('#playersModal').removeClass("show");
        $('#playersModal').css("display","none");
        console.log("Cerrado el modal de jugadores.");
    })
});