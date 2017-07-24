/**
 * Belongs to
 * Author: liye on 2017/7/24
 * Description: basic settings of baidu map
 */

// --- new map
var map = new BMap.Map("allmap",{
    minZoom: 8,
    maxZoom: 14,
    enableMapClick: false
});
map.setMapStyle({style:'grayscale'});
var centerPoint = new BMap.Point(121.499, 31.240);

// --- set center point according browser
map.centerAndZoom(centerPoint, 12);
var geolocation = new BMap.Geolocation();
geolocation.getCurrentPosition(function(r){
    if(this.getStatus() == BMAP_STATUS_SUCCESS){
        var mk = new BMap.Marker(r.point);
        map.panTo(r.point);
    }
    else {
        alert('failed'+this.getStatus());
    }
},{enableHighAccuracy: true})

// --- operation settings
map.enableScrollWheelZoom();
map.enableInertialDragging();

// --- map control
map.addControl(new BMap.CityListControl({
    anchor: BMAP_ANCHOR_TOP_LEFT,
    offset: new BMap.Size(10, 20)
}));

// --- marker
var marker = new BMap.Marker(centerPoint, {
    icon: new BMap.Symbol(BMap_Symbol_SHAPE_POINT, {
        scale: 2,
        fillColor: "orange",
        fillOpacity: 0.9
    })
});
map.addOverlay(marker);
function hideMark() {
    marker.hide();
}

var markers = [];
for (var i=0; i<10; i++){
    var sub_marker = new BMap.Marker(new BMap.Point(121.4 + 0.01*i, 31.2 + 0.01*i));
    sub_marker.addEventListener('click', attribute);
    markers.push(sub_marker);
}
var markerCluster = new BMapLib.MarkerClusterer(map, {markers: markers});

function attribute(e){
    var p = e.target;
    alert("marker的位置是" + p.getPosition().lng + "," + p.getPosition().lat);
}

// --- marker info
var opts = {
    width : 200,     // 信息窗口宽度
    height: 100,     // 信息窗口高度
    title : "SENSOR 1" // 信息窗口标题
};
var infoWindow = new BMap.InfoWindow("<b>Author: liye</b>", opts);  // 创建信息窗口对象
marker.addEventListener("click", function(){
    var p = marker.getPosition();
    map.openInfoWindow(infoWindow, new BMap.Point(p.lng, p.lat+0.01)); //开启信息窗口
});