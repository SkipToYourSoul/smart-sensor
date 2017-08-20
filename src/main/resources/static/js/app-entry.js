/**
 *  Belongs to smart-sensor
 *  Author: liye on 2017/8/18
 *  Description: when open app.html, load sensor data
 */

var charts_list = [];
var video_players = {};
var timelines = {};

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

        if (type == 1){
            var chart = echarts.init(document.getElementById('sensor-content-' + id));
            chart.showLoading();
            var chartSeriesData = [];
            for (var j in sensorData[id]){
                chartSeriesData.push({
                    value:[
                        sensorData[id][j]['dataTime'],
                        sensorData[id][j]['value']
                    ]
                });
            }
            chart.setOption(chartOption(chartSeriesData));
            chart.hideLoading();
            charts_list.push(chart);
        } else if (type == 2){
            if (sensorData[id] == null || sensorData[id].length == 0) {
                console.info("empty data");
                continue;
            }

            video_players[id] = videojs('sensor-camera-' + id, sensorData[id][0]['option'], function () {
                console.log('the video player is ready');
            });
            timelines[id] = new TL.Timeline('sensor-photo-' + id, sensorData[id][0]['timeline'], timelineOptions);

            // --- select
            var $video_select = $('#sensor-video-select-' + id);
            for (var k=0; k<sensorData[id].length; k++)
                $video_select.html($video_select.html() + '<option value="' + k + '">视频片段' + (k+1) + '</option>');

            $video_select.change(function () {
                var id = this.id.split('-')[3];
                video_players[id].poster(sensorData[id][this.value]['option']['poster']);
                video_players[id].src(sensorData[id][this.value]['option']['sources']);

                timelines[id] = new TL.Timeline('sensor-photo-' + id, sensorData[id][this.value]['timeline'], timelineOptions);
            });
        }
    }

    $loading.fadeOut();
}

initSensorData();

window.onresize = function () {
    for (var i in charts_list)
        charts_list[i].resize();
};

function videoPlay(evt) {
    var current_sensor_id = evt.id.split('-')[3];
    var current_video_index = $('#sensor-video-select-' + current_sensor_id).val();

    video_players[current_sensor_id].play();
    timelines[current_sensor_id].goToNext();
}

function videoPause(evt) {
    var current_sensor_id = evt.id.split('-')[3];
    var current_video_index = $('#sensor-video-select-' + current_sensor_id).val();

    video_players[current_sensor_id].pause();
    timelines[current_sensor_id].goToStart();
}