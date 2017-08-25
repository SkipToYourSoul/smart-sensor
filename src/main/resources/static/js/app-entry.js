/**
 *  Belongs to smart-sensor
 *  Author: liye on 2017/8/18
 *  Description: when open app.html, load sensor data
 */

var charts_list = [];
var video_players = {};
var timelines = {};
var timelineVideoIndex = {};

/*
* { name, startTime, endTime }
* */
var data_duration_info = [];

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
            var chart = echarts.init(document.getElementById('sensor-chart-' + id));
            chart.showLoading();
            var series = [];
            var legend_data = [];
            for (var data_index in sensorData[id]){
                var time_series = sensorData[id][data_index]['timeSeries'];
                var flag = sensorData[id][data_index]['flag'];
                var series_data = chartSeriesOption(flag + "", time_series);
                series.push(series_data);
                legend_data.push({name: flag + ""});

                data_duration_info.push({
                    name: flag,
                    startTime: sensorData[id][data_index]['startTime'],
                    endTime: sensorData[id][data_index]['endTime']
                });
            }

            chart.setOption(chartOption(legend_data, series));
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
            boundEvent(id, 0);

            // --- select
            var $video_select = $('#sensor-video-select-' + id);
            for (var k=0; k<sensorData[id].length; k++)
                $video_select.html($video_select.html() + '<option value="' + k + '">视频片段' + (k+1) + '</option>');

            $video_select.change(function () {
                var id = this.id.split('-')[3];
                video_players[id].poster(sensorData[id][this.value]['option']['poster']);
                video_players[id].src(sensorData[id][this.value]['option']['sources']);

                timelines[id] = new TL.Timeline('sensor-photo-' + id, sensorData[id][this.value]['timeline'], timelineOptions);
                boundEvent(id, this.value);
            });
        }
    }

    // init data-duration-info
    var $duration_info = $('#data-duration-info');
    for (var i in data_duration_info){
        $duration_info.html($duration_info.html() +
            '<p>' + data_duration_info[i]['name'] + ": " + data_duration_info[i]['startTime'] + " - " + data_duration_info[i]['endTime']);
    }

    window.onresize = function () {
        for (var i in charts_list)
            charts_list[i].resize();
    };
    $loading.fadeOut();
}

initSensorData();

function boundEvent(id, index){
    timelineVideoIndex = {};
    for (var i in sensorData[id][index]['timeline']['events']){
        var time_in_video = sensorData[id][index]['timeline']['events'][i]['media']['timeInVideo'];
        timelineVideoIndex[time_in_video] = i;
    }

    video_players[id].on('timeupdate', function (evt) {
        var current_video = video_players[id];
        var seconds = Math.floor(current_video.currentTime());
        if (timelineVideoIndex.hasOwnProperty( seconds )) {
            timelines[id].goTo( Number(timelineVideoIndex[seconds]) + 1);
        }
        console.log("current time: " + current_video.currentTime());
    });
}

function videoPlay(evt) {
    var current_sensor_id = evt.id.split('-')[3];
    var current_video_index = $('#sensor-video-select-' + current_sensor_id).val();

    video_players[current_sensor_id].play();
}

function videoPause(evt) {
    var current_sensor_id = evt.id.split('-')[3];
    var current_video_index = $('#sensor-video-select-' + current_sensor_id).val();

    video_players[current_sensor_id].pause();
}

$('#start-picker').datetimepicker({
    autoclose: true,
    todayBtn: true,
    todayHighlight: true,
    format: "yyyy-mm-dd hh:ii:ss"
});

$('#end-picker').datetimepicker({
    autoclose: true,
    todayBtn: true,
    todayHighlight: true,
    format: "yyyy-mm-dd hh:ii:ss"
});