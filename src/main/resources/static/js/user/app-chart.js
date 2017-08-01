/**
 *  Belongs to smart-sensor
 *  Author: liye on 2017/8/1
 *  Description:
 */

var chart = echarts.init(document.getElementById('sensor-content-1'));

function randomData() {
    now = new Date(+now + oneDay);
    value = value + Math.random() * 21 - 10;
    return {
        name: now.toString(),
        value: [
            [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
            Math.round(value)
        ]
    }
}

var data = [];
var now = +new Date(2017, 7, 1);
var oneDay = 24 * 3600 * 1000;
var value = Math.random() * 1000;
for (var i = 0; i < 10; i++) {
    data.push(randomData());
}

var chart_option = {
    title: {
        // text: 'SENSOR DATA'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross'
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
        data: data
    }]
};

chart.setOption(chart_option);
window.onresize = chart.resize;