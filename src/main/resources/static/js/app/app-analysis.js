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
    data_recorder_chart1.setOption(analysisChartOption(['记录1-温度','记录1-湿度','记录2-温度','记录2-湿度']));

    var data_recorder_chart2 = echarts.init(document.getElementById("analysis-chart-1-2"), "", opts={
        height: 250
    });
    data_recorder_chart2.setOption(analysisChartOption(['记录1-光照','记录2-光照']));
}

// videos
videojs("analysis-video", sensorData['1']['3'][0]['option'], function () {
    videojs.log('The video player is ready');
});