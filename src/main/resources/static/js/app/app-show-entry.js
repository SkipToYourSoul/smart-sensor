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

    var data_recorder_chart2 = echarts.init(document.getElementById("experiment-chart-1-8"), "", opts={
        height: 300
    });
    data_recorder_chart2.setOption(chartOption(data2));
}
// initExperimentData();

/* ==== test code ==== */
function randomData(now) {
    var value = Math.random() * 1000;
    value = value + Math.random() * 21 - 10;

    return {
        name: now.toString(),
        value: [
            now.Format("yyyy-MM-dd HH:mm:ss"),
            Math.round(value)
        ]
    }
}

/* ==== some global variable ==== */
var chart_object = {};
var chart_height = 300;

/* ==== init ==== */
function init() {
    // --- traverse experiment to init chart
    for (var exp_id in sensors){
        // --- continue if current experiment has not value sensor
        if (!sensors[exp_id].hasOwnProperty('1'))
            continue;

        var sensor_list = sensors[exp_id]['1'];
        for (var index in sensor_list){
            var sensor = sensor_list[index];
            var sensor_id = sensor['id'];
            var dimension = sensor['sensorConfig']['dimension'];
            var legend = dimension.split(';');

            var chart_dom = "experiment-chart-" + exp_id + "-" + sensor_id;
            var chart = echarts.init(document.getElementById(chart_dom), "", opts = {
                height: chart_height
            });
            chart.setOption(experimentChartOption(legend));
            chart_object[chart_dom] = chart;
        }
    }
}

/* ==== recorder ==== */
var interval_object = {};
function recorder(button){
    var exp_id = button.getAttribute('data');
    message_info("实验" + exp_id + "开始录制", 'info');

    var now = new Date(2017, 8, 21, 18, 0, 0);

    if (!interval_object.hasOwnProperty(exp_id)) {
        interval_object[exp_id] = setInterval(function () {
            for (var chart_dom in chart_object) {
                if (chart_dom.split('-')[2] != (exp_id + ''))
                    continue;

                var chart = chart_object[chart_dom];
                var series = chart.getOption()['series'];

                for (var i = 0; i < series.length; i++) {
                    var data = series[i]['data'];
                    data.push(randomData(now));
                    series[i]['data'] = data;
                }

                chart_object[chart_dom].setOption({
                    series: series
                });
            }

            now = new Date(+now + 1000);
        }, 1000);
    } else {
        clearInterval(interval_object[exp_id]);
        delete interval_object[exp_id];
    }

    console.log(interval_object);
}

/* ==== run ==== */
init();