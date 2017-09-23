/**
 * Belongs to
 * Author: liye on 2017/9/23
 * Description:
 */
/* ==== load sensor data ====
 *    chart: experiment-chart-id
 *    video: experiment-video-id
 * */
function initExperimentData() {
    for (var exp_id in sensorData){
        var sensor_data_map = sensorData[exp_id];
        for (var sensor_id in sensor_data_map){
            // var sensor_type = sensors[sensor_id]['type'];
        }
    }

    var data1 = sensorData['1']['1'][0]['timeSeries'];
    var data2 = sensorData['1']['1'][1]['timeSeries'];

    var data_recorder_chart1 = echarts.init(document.getElementById("analysis-chart-1-1"), "", opts={
        height: 250
    });
    data_recorder_chart1.setOption(analysisChartOption(data1));

    var data_recorder_chart2 = echarts.init(document.getElementById("analysis-chart-1-8"), "", opts={
        height: 250
    });
    data_recorder_chart2.setOption(analysisChartOption(data2));
}

// videos
videojs("analysis-video", sensorData['1']['7'][0]['option'], function () {
    videojs.log('The video player is ready');
});