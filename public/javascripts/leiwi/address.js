$("#autoComplete_adresse").autocomplete({
    source: "/addressSearch",
    minLength: 2,
    select: function( event, ui ) {
        $( "#autoComplete_adresse" ).val(ui.item.street + ", " + ui.item.houseNr +  "; " + ui.item.district);
        addFeatureToMap(ui.item.lon, ui.item.lat);
        return false;
    }})
    .autocomplete( "instance" )._renderItem = function( ul, item ) {
    return $( "<li>" )
        .append( "<a>" + item.street + ", " + item.houseNr + "; " + item.district + "</a>" )
        .appendTo( ul );
};

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

function addFeatureToMap(lon, lat){

    if (vectorLayer != null) {
        map.removeLayer(vectorLayer);
    }

    var iconFeatures=[];

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