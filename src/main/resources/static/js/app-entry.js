/**
 *  Belongs to smart-sensor
 *  Author: liye on 2017/8/18
 *  Description: when open app.html, load sensor data
 */

var charts_list = [];
var video_list = [];
var timeline_list = [];

var additionalOptions = {
    start_at_slide: 0,
    slide_padding_lr: 20,
    scale_factor: 0.5,
    timenav_height: 200
};

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
        var type = row['type'];

        var data = sensorData[id];
        if (type == 1){
            var chart = echarts.init(document.getElementById('sensor-content-' + id));
            chart.showLoading();
            var chartSeriesData = [];
            for (var j in data){
                chartSeriesData.push({
                    value:[
                        data[j]['dataTime'],
                        data[j]['value']
                    ]
                });
            }
            chart.setOption(chartOption(chartSeriesData));
            chart.hideLoading();
            charts_list.push(chart);
        } else if (type == 2){
            var video_player = videojs('sensor-camera-' + id, data[0]['option'], function () {
                console.log('the video player is ready');
            });
            video_list.push(video_player);

            var timeline = new TL.Timeline('sensor-photo-' + id, data[0]['timeline'], additionalOptions);
        }
    }

    $loading.fadeOut();
}

initSensorData();
window.onresize = function () {
    for (var i in charts_list)
        charts_list[i].resize();
};