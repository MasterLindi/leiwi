var view = new ol.View ( {
    center: ol.proj.transform([16.3800599, 48.2206849], 'EPSG:4326', 'EPSG:3857'),
    zoom : 11
} );

var map = new ol.Map ( {
    target : 'map',
    layers :[
        new ol.layer.Tile ( {
            source : new ol.source.OSM ()
        } )],
    view : view
} );

map.on('singleclick', function(evt) {
    var coord = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');

    var jsonData = {};
    jsonData.lon = coord.shift();
    jsonData.lat = coord.shift();

    addFeatureToMap(jsonData.lon, jsonData.lat);

    $.ajax({
        data: JSON.stringify(jsonData),
        dataType: 'json',
        type: 'POST',
        url: "coord",
        contentType: 'application/json; charset=utf-8',
        success:  function(item){
            $("#autoComplete_adresse").val(item.street + ", " + item.houseNr + "; " + item.district)
        }
    });
});