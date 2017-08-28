/**
 *  Belongs to smart-sensor
 *  Author: liye on 2017/8/25
 *  Description:
 */
var recorder_videos = {};
var recorder_data = {};
var recorder_chart;
var current_recorder_time;
var recorder_start_time;
var recorder_interval = null;
var is_recorder_video_play = {};

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
    recorder_data = time_data;

    // step2: construct html
    var $recorder_chart = $('#data-recorder-chart');
    var $recorder_video = $('#data-recorder-video');
    var $recorder_control = $('#data-recorder-control');

    $recorder_chart.empty().attr('hidden', 'hidden');
    $recorder_video.empty().attr('hidden', 'hidden');
    $recorder_control.attr('hidden', 'hidden');

    if (JSON.stringify(time_data.chart) == "{}" && JSON.stringify(time_data.video) != "{}"){
        // only video
        $recorder_video.removeClass().addClass('col-sm-12').removeAttr('hidden');
        $recorder_control.removeAttr('hidden');

        recorder_videos = generateVideo(time_data);
        message_info("only video", "info");
    } else if (JSON.stringify(time_data.chart) != "{}" && JSON.stringify(time_data.video) == "{}"){
        // only chart
        $recorder_chart.removeClass().addClass('col-sm-12').removeAttr('hidden');
        $recorder_control.removeAttr('hidden');

        recorder_chart = generateChart(time_data);
        message_info("only chart", "info");
    } else if (JSON.stringify(time_data.chart) != "{}" && JSON.stringify(time_data.video) != "{}"){
        $recorder_chart.removeClass().addClass('col-sm-6').removeAttr('hidden');
        $recorder_video.removeClass().addClass('col-sm-6').removeAttr('hidden');
        $recorder_control.removeAttr('hidden');

        recorder_chart = generateChart(time_data);
        recorder_videos = generateVideo(time_data);
        message_info("video and chart", "info");
    } else {
        $recorder_chart.removeClass().empty();
        $recorder_video.removeClass().empty();
    }

    // this.reset();
    recorder_start_time = start_time;
    prepareRecorder();

}).on('err.form.fv', function (evt) {
    message_info("submit error", "info");
});

function prepareRecorder() {
    // initial clock
    current_recorder_time = new Date(Date.parse(recorder_start_time.replace(/-/g, "/")));
    $('#recorder-clock').html(recorder_start_time);

    // prepare data
    setRecorderChart(recorder_start_time);

    // initial video
    is_recorder_video_play = {};
    for (var i in recorder_videos)
        recorder_videos[i].currentTime(0);
}


function setClockTime(interval) {
    current_recorder_time = new Date(current_recorder_time.getTime() + 1000*interval);
    var format_time = current_recorder_time.Format("yyyy-MM-dd HH:mm:ss");
    $('#recorder-clock').html(format_time);

    return format_time;
}

function setRecorderChart(time){
    var chart_option = recorder_chart.getOption();
    var series = chart_option.series;

    // get series index according name
    var series_name_index = {};
    for (var index in series){
        var name = series[index]['name'];
        series_name_index[name] = index;
    }

    // if current time has data, push to options
    for (var flag in recorder_data.chart){
        for (var index in recorder_data.chart[flag]){
            var data_time = parseTime(recorder_data.chart[flag][index]['value'][0]);
            if (time == data_time){
                series[series_name_index[flag]].data.push(recorder_data.chart[flag][index]['value']);
            }
        }
    }

    chart_option.series = series;
    recorder_chart.setOption(chart_option);
}

function setRecorderVideo(time){
    for (var video_id in recorder_data.video){
        var start_time = parseTime(recorder_data.video[video_id][0]['startTime']);
        var end_time = parseTime(recorder_data.video[video_id][0]['endTime']);

        console.log(start_time + ' - ' + end_time);

        if (start_time <= time && end_time >= time && !is_recorder_video_play.hasOwnProperty(video_id)){
            is_recorder_video_play[video_id] = 1;
            recorder_videos[video_id].play();
        }
    }
}

function recorderPlay() {
    recorder_interval = setInterval(function () {
        var time = setClockTime(1);
        setRecorderChart(time);
        setRecorderVideo(time);
    }, 1000);
    $('#play-btn').attr('disabled', 'disabled');
    $('#pause-btn').removeAttr('disabled');
}

function recorderPause() {
    if (recorder_interval != null)
        clearInterval(recorder_interval);
    for (var i in recorder_videos){
        recorder_videos[i].pause();
        delete is_recorder_video_play[i];
    }

    $('#pause-btn').attr('disabled', 'disabled');
    $('#play-btn').removeAttr('disabled');
}

function recorderReset() {
    if (recorder_interval != null)
        clearInterval(recorder_interval);

    $('#pause-btn').attr('disabled', 'disabled');
    $('#play-btn').removeAttr('disabled');

    var chart_option = recorder_chart.getOption();
    var series = chart_option.series;

    for (var index in series){
        series[index].data = [];
    }

    chart_option.series = series;
    recorder_chart.setOption(chart_option);
    prepareRecorder();
}

function generateChart(time_data){
    echarts.dispose(document.getElementById("data-recorder-chart"));
    var data_recorder_chart = echarts.init(document.getElementById("data-recorder-chart"), "", opts={
        height: 400
    });
    var legends = [];
    var series = [];
    for (var flag in time_data.chart){
        legends.push({name: flag});
        var series_data = chartSeriesOption(flag, /*time_data['chart'][flag]*/[]);
        series.push(series_data);
    }
    data_recorder_chart.setOption(chartOption(legends, series));
    return data_recorder_chart;
}

function generateVideo(time_data){
    // dispose
    console.log(recorder_videos);
    for (var video in recorder_videos){
        recorder_videos[video].dispose();
    }

    var videos = {};
    var $videos = $('#data-recorder-video');

    for (var sensor_id in time_data.video){
        var video_options = time_data.video[sensor_id];
        for (var index in video_options){
            var video_id = sensor_id/* + "-" + index*/;
            $videos.append('<h4>视频片段：来自传感器(' + sensor_id + ')的第' + (Number(index)+1) + '段视频</h4>');
            $videos.append('<small>视频时间：' + parseTime(video_options[index]['startTime']) + ' - ' + parseTime(video_options[index]['endTime']) + '</small>');
            $videos.append('<video id="' + video_id + '" class="video-js vjs-fluid" preload="auto" data-setup="{}"></video>');
            videos[video_id] = video_options[index]['option'];

            // only append one video for a sensor
            break;
        }
    }

    for (var id in videos){
        var option = videos[id];
        videos[id] = videojs(id, option, function () {
            videojs.log('The video player ' + id + ' is ready');
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

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};