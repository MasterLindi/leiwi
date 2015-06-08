$("#autoComplete_adresse").autocomplete({
    source: "/addressSearch",
    minLength: 2,
    select: function( event, ui ) {
        $( "#autoComplete_adresse" ).val(ui.item.street + ", " + ui.item.houseNr +  "; " + ui.item.district);
        addFeatureToMap(ui.item.lon, ui.item.lat);
        storeCoordinateInHiddenFields(ui.item.lon, ui.item.lat);
        return false;
    }})
    .autocomplete( "instance" )._renderItem = function( ul, item ) {
    return $( "<li>" )
        .append( "<a>" + item.street + ", " + item.houseNr + "; " + item.district + "</a>" )
        .appendTo( ul );
};

function storeCoordinateInHiddenFields(lon, lat) {
    var coordStorage = $("#hidden_cord");
    coordStorage.prop("lon",lon);
    coordStorage.prop("lat",lat);
}

$("#button_reset").click(function (event) {
    $("#autoComplete_adresse").val("");
    $("#checkBox_allgemein").prop("checked",false);
    $("#checkBox_mobilitaet").prop("checked",false);
    $("#checkBox_familie").prop("checked",false);
    $("#checkBox_student").prop("checked",false);
    $("#checkBox_pensionist").prop("checked",false);
    storeCoordinateInHiddenFields(0, 0);
    removeMarker();
    removeCircle();
    $("#details").addClass("invisible");
});

$("#button_berechnen").click(function (event) {
    var coordStorage = $("#hidden_cord");
    var jsonData = {};
    jsonData.lon = coordStorage.prop("lon");
    jsonData.lat = coordStorage.prop("lat");
    jsonData.common =  $("#checkBox_allgemein").prop("checked");
    jsonData.mobility =  $("#checkBox_mobilitaet").prop("checked");
    jsonData.family =  $("#checkBox_familie").prop("checked");
    jsonData.students = $("#checkBox_student").prop("checked");
    jsonData.retired = $("#checkBox_pensionist").prop("checked");

    $.ajax({
        data: JSON.stringify(jsonData),
        dataType: 'json',
        type: 'POST',
        url: "calculateIndex",
        contentType: 'application/json; charset=utf-8',
        success:  function(item){
            calculatedValues = item;
            addCircleToMap(item.index);
            $.each(item.details, function() {
                $("#detailtable").append("<tr>" +
                    "<td>"+ this.catalogName + "</td>" +
                    "<td>"+ this.calculatedValue + "</td>" +
                    "<td>"+ this.distance + "</td>" +
                    "</tr>");
            });
            $("#details").removeClass("invisible");
        }
    });
});
