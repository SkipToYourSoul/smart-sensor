/**
 *  Belongs to smart-sensor
 *  Author: liye on 2017/8/18
 *  Description: when open app.html, load sensor data
 */

var charts_list = [];
var video_list = [];

function initSensorData() {
    if (sensors.length <= 0)
        return;

    // --- loading
    var $loading = $("#fakeLoader");
    $loading.fakeLoader({
        timeToHide: 5000,
        spinner:"spinner4",
        bgColor:"rgba(154, 154, 154, 0.7)"
    });

    // --- traverse sensor, init sensor content
    for (var i=0; i < sensors.length; i++){
        var row = sensors[i];
        var id = row['id'];
        // --- chart
        if (row['type'] == 1){
            var chart = echarts.init(document.getElementById('sensor-content-' + id));
            chart.showLoading();
            if (initialSensorChart(id, chart))
                chart.hideLoading();
            charts_list.push(chart);
        } else if (row['type'] == 2){

        }
    }


    window.onresize = function () {
        for (var i in charts)
            charts_list[i].resize();
    };
    $loading.fadeOut();
}

initSensorData();