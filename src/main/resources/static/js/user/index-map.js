/**
 * Belongs to
 * Author: liye on 2017/7/24
 * Description: basic settings of baidu map
 */

// --- map initial -------------------------------------------------------
var map = new BMap.Map("allmap",{
    minZoom: 8,
    maxZoom: 14,
    enableMapClick: false
});
map.setMapStyle({style:'grayscale'});
var centerPoint = new BMap.Point(125.499, 31.240);
map.centerAndZoom(centerPoint, 12);

// --- set center point and city according ip
var currentCity = new BMap.LocalCity();
currentCity.get(function (city) {
    map.setCenter(city.name);
    message_info('定位到当前城市：' + city.name, 'info', 3);
});

// --- operation settings
map.enableScrollWheelZoom();
map.enableInertialDragging();

// --- map control
map.addControl(new BMap.CityListControl({
    anchor: BMAP_ANCHOR_TOP_RIGHT,
    offset: new BMap.Size(10, 20)
}));

map.addControl(new BMap.NavigationControl({
    anchor: BMAP_ANCHOR_TOP_LEFT,
    type: BMAP_NAVIGATION_CONTROL_LARGE
}));
// ---------------------------------------------------------------------------

// --- add map marker --------------------------------------------------------
// --- marker info
var opts = {
    width : 200,     // 信息窗口宽度
    height: 200,     // 信息窗口高度
    title : "<b>传感器信息</b>" // 信息窗口标题
};

$.ajax({
    type: "get",
    url: current_address + "/map/sensor",
    dataType: "json",
    success: function (sensor) {
        var markers = [];
        for (var row in sensor){
            var d = sensor[row];
            var sub_marker = new BMap.Marker(new BMap.Point(d['longitude'], d['latitude']), {
                icon: new BMap.Symbol(BMap_Symbol_SHAPE_POINT, {
                    scale: 1,
                    fillColor: "orange",
                    fillOpacity: 0.9
                })
            });
            sub_marker.addEventListener("click", function(){
                var p = sub_marker.getPosition();
                var infoWindow = new BMap.InfoWindow("<hr/><p>CREATOR: " + d['creator'] + "</p><p>DESCRIPTION: " + d['description'] + "</p>", opts);  // 创建信息窗口对象
                map.openInfoWindow(infoWindow, new BMap.Point(p.lng, p.lat+0.008)); //开启信息窗口
            });
            markers.push(sub_marker);
        }
        var markerCluster = new BMapLib.MarkerClusterer(map, {markers: markers});
    },
    error: function (err_msg) {
        message_info('加载传感器数据出错', 'error', 3);
    }
});
// ---------------------------------------------------------------------------

// --- map function ----------------------------------------------------------
function toMark(){
    map.centerAndZoom(centerPoint, 12);
}

function hideMark() {

}
// ---------------------------------------------------------------------------
