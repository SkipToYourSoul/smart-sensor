/**
 *  Belongs to smart-sensor
 *  Author: liye on 2017/8/1
 *  Description:
 */

if (sensors.length > 0){
    // --- init chart
    var $loading = $("#fakeLoader");
    $loading.fakeLoader({
        timeToHide: 5000,
        spinner:"spinner4",
        bgColor:"rgba(154, 154, 154, 0.7)"
    });

    // --- init sensor content
    var charts = [];
    for (var i in sensors){
        var row = sensors[i];
        // --- chart
        if (row['type'] == 1){
            var chart = echarts.init(document.getElementById('sensor-content-' + row['id']));
            initialSensorChart(row['id'], chart);
            charts.push(chart);
        }
    }
    window.onresize = function () {
        for (var i in charts)
            charts[i].resize();
    };
    $loading.fadeOut();
}

function initialSensorChart(sensorId, chart) {
    $.ajax({
        type: "get",
        url: current_address + "/sensor/data",
        data: {sensorId: sensorId},
        dataType: "json",
        success: function (sensor) {
            var chartSeriesData = [];
            for (var i in sensor){
                var row = sensor[i];
                chartSeriesData.push({
                    value:[
                        row['timestamp'],
                        row['value']]
                });
            }
            chart.setOption(chartOption(chartSeriesData));
        },
        error: function (err_msg) {
            message_info('加载传感器数据出错，传感器编号：' + sensorId, 'error', 3);
        }
    });
}

var chartOption = function (chartSeriesData) {
    return {
        title: {
            // text: 'SENSOR DATA'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross'
            },
            formatter: function(params){
                return params[0].value[0] + "<br/>" + params[0].value[1];
            },
            backgroundColor: 'rgba(245, 245, 245, 0.8)',
            borderWidth: 1,
            borderColor: '#ccc',
            padding: 10,
            textStyle: {
                color: '#000'
            },
            /*position: function (pos, params, el, elRect, size) {
             var obj = {top: 10};
             obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 30;
             return obj;
             },*/
            extraCssText: 'width: 170px'
        },
        legend: {
            top: 10,
            left: 'center',
            data: ['模拟数据']
        },
        grid: {
            borderWidth: 0,
            top: '10%',
            bottom: '20%',
            left: '5%',
            right: '5%',
            textStyle: {
                color: "#fff"
            }
        },
        calculable: true,
        toolbox: {
            show: true,
            feature: {
                dataView: {readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore: {},
                saveAsImage: {},
                brush: {
                    type: ['lineX', 'clear']
                }
            },
            right: 20
        },
        dataZoom: [{
            show: true,
            height: 30,
            xAxisIndex: [0],
            bottom: 0,
            start: 0,
            end: 100,
            handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
            handleSize: '80%',
            handleStyle: {
                color: '#fff',
                shadowBlur: 3,
                shadowColor: 'rgba(0, 0, 0, 0.6)',
                shadowOffsetX: 2,
                shadowOffsetY: 2
            },
            textStyle:{
                color:"#fff"},
            borderColor:"#eee"
        }, {
            type: "inside",
            xAxisIndex: [0],
            start: 0,
            end: 100
        }],
        xAxis: {
            type: 'time',
            name: 'TIME',
            nameRotate: 45,
            splitNumber: 10,
            splitLine: {
                show: false
            },
            scale: true,
            boundaryGap : false,
            axisPointer: {
                z: 100
            }
        },
        yAxis: {
            type: 'value',
            name: 'VALUE',
            scale: true,
            splitArea: {
                show: true
            },
            boundaryGap: ['10%', '10%']
        },
        series: [{
            name: '模拟数据',
            type: 'line',
            symbolSize:10,
            symbol:'circle',
            hoverAnimation: false,
            data: chartSeriesData
        }]
    };
};