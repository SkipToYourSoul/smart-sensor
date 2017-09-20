/**
 *  Belongs to smart-sensor
 *  Author: liye on 2017/9/19
 *  Description: load the js when enter in the app review page
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

    var data_recorder_chart1 = echarts.init(document.getElementById("experiment-chart-1-1"), "", opts={
        height: 300
    });
    data_recorder_chart1.setOption(chartOption(data1));

    var data_recorder_chart2 = echarts.init(document.getElementById("experiment-chart-1-2"), "", opts={
        height: 300
    });
    data_recorder_chart2.setOption(chartOption(data2));
}

/* ==== init ==== */
initExperimentData();