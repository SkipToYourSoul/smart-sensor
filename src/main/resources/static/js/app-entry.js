/**
 *  Belongs to smart-sensor
 *  Author: liye on 2017/8/18
 *  Description: when open app.html, load sensor data
 */

var charts_list = [];
var current_video_player;
var current_timeline;

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
            if (data == null || data.length == 0) {
                console.info("empty data");
                continue;
            }

            current_video_player = videojs('sensor-camera-' + id, data[0]['option'], function () {
                console.log('the video player is ready');
            });
            current_timeline = new TL.Timeline('sensor-photo-' + id, data[0]['timeline'], timelineOptions);

            // --- select
            var $video_select = $('#sensor-video-select-' + id);
            for (var k=0; k<data.length; k++)
                $video_select.html($video_select.html() + '<option value="' + k + '">视频片段' + (k+1) + '</option>');

            $video_select.change(function () {
                console.info(this.value);
                current_video_player.poster(data[this.value]['option']['poster']);
                current_video_player.src(data[this.value]['option']['sources']);

                current_timeline = new TL.Timeline('sensor-photo-' + id, data[this.value]['timeline'], timelineOptions);
            });
        }
    }

    $loading.fadeOut();
}

initSensorData();

var selectChange = function (id, data) {

};



window.onresize = function () {
    for (var i in charts_list)
        charts_list[i].resize();
};