var view = new ol.View({
    center: ol.proj.transform([16.3800599, 48.2206849], 'EPSG:4326', 'EPSG:3857'),
    zoom: 11
});

var map = new ol.Map({
    target: 'map',
    layers: [
        new ol.layer.Tile({
            source: new ol.source.OSM()
        })],
    view: view
});


var iconStyle = new ol.style.Style({
    image: new ol.style.Icon(/** type {olx.style.IconOptions} */ ({
        anchor: [0.5, 46],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        opacity: 0.75,
        src: 'http://ol3js.org/en/master/examples/data/icon.png'
    }))
});

var vectorLayer = null;

function addFeatureToMap(lon, lat) {

    removeMarker();

    var iconFeatures = [];

    var iconFeature = new ol.Feature({
        geometry: new ol.geom.Point(ol.proj.transform([lon, lat], 'EPSG:4326',
            'EPSG:3857')),
        name: 'Ihre gewählte Adresse',
        population: 4000,
        rainfall: 500
    });

    iconFeatures.push(iconFeature);

    var vectorSource = new ol.source.Vector({
        features: iconFeatures //add an array of features
    });


    vectorLayer = new ol.layer.Vector({
        source: vectorSource,
        style: iconStyle
    });


    map.addLayer(vectorLayer);
}

function removeMarker() {
    if (vectorLayer != null) {
        map.removeLayer(vectorLayer);
    }
}

map.on('singleclick', function (evt) {
    var coord = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');

    var jsonData = {};
    jsonData.lon = coord.shift();
    jsonData.lat = coord.shift();

    storeCoordinateInHiddenFields(jsonData.lon, jsonData.lat);

    addFeatureToMap(jsonData.lon, jsonData.lat);

    $.ajax({
        data: JSON.stringify(jsonData),
        dataType: 'json',
        type: 'POST',
        url: "coord",
        contentType: 'application/json; charset=utf-8',
        success: function (item) {
            $("#autoComplete_adresse").val(item.street + ", " + item.houseNr + "; " + item.district)
        }
    });
});


var circleLayer = null;

var circleStyle = new ol.style.Style({
    image: new ol.style.Circle({
        radius: 120,
        fill: new ol.style.Fill({
            color: 'rgba(122, 122, 122, 0.6)'
        })
    })
});

function removeCircle() {
    if (circleLayer != null) {
        map.removeLayer(circleLayer);
    }
}
function addCircleToMap(radius) {
    removeCircle();
    var coordinates = $("#hidden_cord");

    var circleFeatures = [];

    var circle = new ol.Feature({
        geometry: new ol.geom.Point(ol.proj.transform([coordinates.prop("lon"), coordinates.prop("lat")], 'EPSG:4326',
            'EPSG:3857')),
        name: 'Berechner Umkreis',
        population: 4000,
        rainfall: 500
    });

    circleFeatures.push(circle);

    var circleSource = new ol.source.Vector({
        features: circleFeatures //add an array of features
    });


    circleLayer = new ol.layer.Vector({
        source: circleSource,
        style: circleStyle
    });


    map.addLayer(circleLayer);
}