/**
 *  Belongs to smart-sensor
 *  Author: liye on 2017/9/19
 *  Description: load the js when enter in the app review page
 */
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

/* ==== init ====
*   init chart
*   id rule: experiment-chart-(exp_id)-(sensor_id)
* */
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

    // --- auto chart size adjust
    window.onresize = function () {
        for (var chart in chart_object)
            chart_object[chart].resize();
    };
}

/* ==== monitor ====
*   monitor sensor
*   set interval for chart, then update chart data
* */
var is_monitor_object = {};
function monitor(button) {
    var exp_id = button.getAttribute('data');
    var $exp_state = $('#experiment-state-' + exp_id);

    // --- current experiment is in monitor state?
    if (!is_monitor_object.hasOwnProperty(exp_id)){
        message_info("开始监控实验" + exp_id, "info");
        $exp_state.html("监控中");

        is_monitor_object[exp_id] = setInterval(function () {
            var now = new Date();

            // --- traverse chart of this experiment
            for (var chart_dom in chart_object) {
                if (chart_dom.split('-')[2] != (exp_id + ''))
                    continue;

                var chart = chart_object[chart_dom];
                var series = chart.getOption()['series'];

                for (var i = 0; i < series.length; i++) {
                    // -- update series data
                    var data = series[i]['data'];
                    data.push(randomData(now));
                    series[i]['data'] = data;
                }

                // -- update series markArea
                if (is_recoder_object.hasOwnProperty(exp_id)){
                    var mark_list = series[0]['markArea']['data'];
                    var recorder_length = is_recoder_object[exp_id].length;
                    if (recorder_length % 2 == 1){
                        // - recorder ing
                        var mark_index = Math.floor(recorder_length/2);
                        mark_list[mark_index] = [{
                            xAxis: is_recoder_object[exp_id][recorder_length - 1]
                        }, {
                            xAxis: now.Format("yyyy-MM-dd HH:mm:ss")
                        }];
                        series[0]['markArea']['data'] = mark_list;
                    }
                }

                // -- set new option
                chart_object[chart_dom].setOption({
                    series: series
                });
            }
            now = new Date(+now + 1000);
        }, 1000);
    } else {
        message_info("停止监控实验" + exp_id, "info");
        $exp_state.html("");

        clearInterval(is_monitor_object[exp_id]);
        delete is_monitor_object[exp_id];
    }
}

/* ==== recorder ====
*   recorder sensor data
*   1. add markArea in chart
*   2. save data
* */
var is_recoder_object = {};
var recorder_data_object = {};
function recorder(button){
    var exp_id = button.getAttribute('data');
    var $exp_state = $('#experiment-recorder-' + exp_id);

    // -- if not in monitor state, break
    if (!is_monitor_object.hasOwnProperty(exp_id)){
        message_info("实验" + exp_id + "未开始监控，无法记录！");
        return;
    }

    if (!is_recoder_object.hasOwnProperty(exp_id)){
        is_recoder_object[exp_id] = [];
    }

    if (is_recoder_object[exp_id].length % 2 == 0){
        message_info("实验" + exp_id + ": 开始记录");
        $exp_state.html("记录中");

        is_recoder_object[exp_id].push(new Date().Format("yyyy-MM-dd HH:mm:ss"));
    } else {
        message_info("实验" + exp_id + ": 停止记录");
        $exp_state.html("");

        is_recoder_object[exp_id].push(new Date().Format("yyyy-MM-dd HH:mm:ss"));
        // -- save data here

    }
}

/* ==== run ==== */
init();