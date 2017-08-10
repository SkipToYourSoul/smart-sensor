/**
 * Belongs to
 * Author: liye on 2017/7/24
 * Description: basic settings of baidu map
 */

// --- map initial -------------------------------------------------------
var map = new BMap.Map("allmap",{
    minZoom: 8,
    // maxZoom: 14,
    enableMapClick: false
});
map.setMapStyle({style:'grayscale'});
var centerPoint = new BMap.Point(121.499, 31.240);
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
var opts = {
    width : 200,     // 信息窗口宽度
    title : "<b>传感器信息</b>" // 信息窗口标题
};

$.ajax({
    type: "get",
    url: current_address + "/index/map/sensor",
    dataType: "json",
    success: function (sensor) {
        var markers = [];
        for (var row in sensor){
            var d = sensor[row];
            var info = "<hr/>" +
                "<p>NAME: " + d['name'] + "</p>" +
                "<p>CREATOR: " + d['creator'] + "</p>" +
                "<p>DESCRIPTION: " + d['description'] + "</p>";
            var id = d['id'];
            markers.push(createMarker(d, info, id));
    }
        var markerCluster = new BMapLib.MarkerClusterer(map, {markers: markers});
    },
    error: function (err_msg) {
        message_info('加载传感器数据出错', 'error', 3);
    }
});

createMarker = function (d, info, id) {
    var _marker = new BMap.Marker(new BMap.Point(d['longitude'], d['latitude']));
    _marker.addEventListener("mouseover", function(e){
        this.openInfoWindow(new BMap.InfoWindow(info, opts));
    });
    _marker.addEventListener("mouseout", function(e){
        this.closeInfoWindow();
    });
    _marker.addEventListener("click", function(e){
        chart_line.setOption(chart_line_option(id));
    });
    return _marker;
};

// ---------------------------------------------------------------------------

// --- map function ----------------------------------------------------------
function toMark(){
    map.centerAndZoom(centerPoint, 12);
}

$('#theme-select').change(function () {
    console.info(this.value);
    if (this.value == 1){
        map.setMapStyle({style:'grayscale'});
    } else if (this.value == 2){
        map.setMapStyle({style:'normal'});
    }
});
// ---------------------------------------------------------------------------
