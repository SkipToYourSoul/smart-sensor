/**
 *  Belongs to smart-sensor
 *  Author: liye on 2017/8/25
 *  Description:
 */

$('#data-recorder-form').formValidation({
    framework: 'bootstrap',
    fields: {
        'start-picker': { validators: { notEmpty: { message: '建议选择数据起始时间'} } },
        'end-picker': { validators: { notEmpty: { message: '建议选择数据结束时间'} } }
    }
}).on('success.form.fv', function(evt) {
    evt.preventDefault();

    // step1: get time data
    var $select = $("#data-recorder-sensor-select").find("input");
    var select_ids = [];
    for (var index in $select){
        var isChecked = $select[index].checked;
        var value = $select[index].value;
        if (isChecked == true)
            select_ids.push(Number(value));
    }

    var start_time = $('#start-picker').val();
    var end_time = $('#end-picker').val();

    var time_data = constructTimeData(start_time, end_time, select_ids);

    // step2: construct html
    var $recorder_chart = $('#data-recorder-chart');
    var $recorder_video = $('#data-recorder-video');
    var $recorder_control = $('#data-recorder-control');

    if (JSON.stringify(time_data.chart) == "{}" && JSON.stringify(time_data.video) != "{}"){
        // only video
        $recorder_video.removeClass().addClass('col-sm-12').removeAttr('hidden');
        $recorder_chart.attr('hidden', 'hidden');
        $recorder_control.removeAttr('hidden');

        generateVideo(time_data);
    } else if (JSON.stringify(time_data.chart) != "{}" && JSON.stringify(time_data.video) == "{}"){
        // only chart
        $recorder_chart.removeClass().addClass('col-sm-12').removeAttr('hidden');
        $recorder_video.attr('hidden', 'hidden');
        $recorder_control.removeAttr('hidden');

        generateChart(time_data);
    } else if (JSON.stringify(time_data.chart) != "{}" && JSON.stringify(time_data.video) != "{}"){
        $recorder_chart.removeClass().addClass('col-sm-6').removeAttr('hidden');
        $recorder_video.removeClass().addClass('col-sm-6').removeAttr('hidden');
        $recorder_control.removeAttr('hidden');

        generateChart(time_data);
        generateVideo(time_data);
    } else {
        $recorder_chart.removeClass().empty().attr('hidden', 'hidden');
        $recorder_video.removeClass().empty().attr('hidden', 'hidden');
        $recorder_control.attr('hidden', 'hidden');
    }

    this.reset();

}).on('err.form.fv', function (evt) {
    message_info("submit error", "info");
});

function generateChart(time_data){
    var data_recorder_chart = echarts.init(document.getElementById("data-recorder-chart"));
    var legends = [];
    var series = [];
    for (var flag in time_data.chart){
        legends.push({name: flag});
        var series_data = chartSeriesOption(flag, /*time_data['chart'][flag]*/[]);
        series.push(series_data);
    }
    data_recorder_chart.setOption(chartOption(legends, series));
}

function generateVideo(time_data){
    var videos = {};
    var $videos = $('#data-recorder-video');

    for (var sensor_id in time_data.video){
        var video_options = time_data.video[sensor_id];
        for (var index in video_options){
            var video_id = sensor_id + "-" + index;
            $videos.append('<video id="' + video_id + '" class="video-js vjs-fluid" controls="controls" preload="auto" data-setup="{}"></video>');
            videos[video_id] = video_options[index]['option'];
        }
    }

    for (var id in videos){
        var option = videos[id];
        videos[id] = videojs(id, option, function () {
            console.log('The video player ' + id + ' is ready');
        });
    }

    return videos;
}

function constructTimeData(start_time, end_time, select_ids){
    var value_time_data = {};
    var video_time_data = {};

    for (var index = 0; index < sensors.length; index++){
        var row = sensors[index];
        var id = row['id'];
        var type = row['type'];

        if (select_ids.indexOf(id) == -1)
            continue;

        if (type == 1){
            // value
            for (var i in sensorData[id]){
                var legend = id + " - " + sensorData[id][i]['flag'];
                var data = [];
                for (var j in sensorData[id][i]['timeSeries']){
                    var time = parseTime(sensorData[id][i]['timeSeries'][j]['value'][0]);
                    if (time >= start_time && time <= end_time)
                        data.push(sensorData[id][i]['timeSeries'][j]);
                }
                if (data.length > 0)
                    value_time_data[legend] = data;
            }
        } else if (type == 2){
            // video
            var data = [];
            for (var i in sensorData[id]){
                var video_start_time = parseTime(sensorData[id][i]['startTime']);
                var video_end_time = parseTime(sensorData[id][i]['endTime']);
                if ((video_start_time >= start_time && video_end_time <= end_time)){
                    data.push(sensorData[id][i]);
                }
            }
            if (data.length > 0)
                video_time_data[id] = data;
        }
    }

    return {
        "chart": value_time_data,
        "video": video_time_data
    };
}

// parse 2017-08-17T18:16:31.000+08:00 to 2017-08-17 18:16:31
function parseTime(time) {
    return time.split('.')[0].replace('T', ' ');
}