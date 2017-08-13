/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/28
 * Description:
 */

var index_big_chart = echarts.init(document.getElementById('chart-line'));

var xData = function() {
    var data = [];
    for (var i = 1; i < 13; i++) {
        data.push(i);
    }
    return data;
}();

var chart_line_option = function (title) {
    return {
        title: {
            text: '数据上报量变化图',
            subtext: '传感器编号：' + title,
            textStyle: {
                color: 'black',
                fontSize: '18'
            },
            subtextStyle: {
                color: '#90979c',
                fontSize: '14'
            }
        },
        tooltip: {
            trigger: "axis",
            axisPointer: {
                type: "shadow",
                textStyle: {
                    color: "#fff"
                }
            }
        },
        grid: {
            borderWidth: 0,
            top: 70,
            bottom: 95,
            textStyle: {
                color: "#fff"
            }
        },
        calculable: true,
        xAxis: [{
            type: "category",
            axisLine: {
                lineStyle: {
                    color: '#90979c'
                }
            },
            splitLine: {
                show: false
            },
            axisTick: {
                show: false
            },
            splitArea: {
                show: false
            },
            axisLabel: {
                interval: 0
            },
            data: xData
        }],
        yAxis: [{
            type: "value",
            splitLine: {
                "show": false
            },
            axisLine: {
                lineStyle: {
                    color: '#90979c'
                }
            },
            axisTick: {
                "show": false
            },
            axisLabel: {
                "interval": 0
            },
            splitArea: {
                "show": false
            }
        }],
        dataZoom: [{
            show: true,
            height: 30,
            xAxisIndex: [0],
            bottom: 30,
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
        series: [{
            "name": "总数",
            "type": "line",
            symbolSize:10,
            symbol:'circle',
            "itemStyle": {
                "normal": {
                    "color": "#ccc",
                    "barBorderRadius": 0,
                    "label": {
                        "show": true,
                        "position": "top",
                        formatter: function(p) {
                            return p.value > 0 ? (p.value) : '';
                        }
                    }
                }
            },
            "data": [
                1036,
                3693,
                2962,
                3810,
                2519,
                1915,
                1748,
                4675,
                6209,
                4323,
                2865,
                4298
            ]
        }
        ]
    };
};
index_big_chart.setOption(chart_line_option(1));

// --- pie chart
var pie_chart1 = echarts.init(document.getElementById('chart-pie-1'), 'infographic');
var pie_chart2 = echarts.init(document.getElementById('chart-pie-2'));
var pie_chart_option = function(title, legend, data) {
    return {
        title : {
            text: title
        },
        tooltip: {
            trigger: 'item',
            formatter: "{b}: {c} ({d}%)"
        },
        legend: {
            data: legend,
            left:'center',
            top:'bottom'
        },
        series: [
            {
                name:'data',
                type:'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '20',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: data
            }
        ]
    };
};

function init_pie_chart() {
    // --- init data
    var city_data = [], city_legend = [], city_mid_data = {};
    var type_data = [], type_legend = [], type_mid_data = {};
    var type_dict = {
        1: "数值型传感器",
        2: "图像传感器"
    };

    for (var row in sensors){
        var sensor = sensors[row];
        var city = sensor['city'];
        var type = type_dict[sensor['type']];
        if (type_legend.indexOf(type) == -1){
            type_legend.push(type);
            type_mid_data[type] = 1;
        } else {
            type_mid_data[type] += 1;
        }

        if (city == null)
            continue;
        if (city_legend.indexOf(city) == -1){
            city_legend.push(city);
            city_mid_data[city] = 1;
        } else {
            city_mid_data[city] += 1;
        }
    }

    for (var city in city_mid_data){
        city_data.push({
            name: city,
            value: city_mid_data[city]
        });
    }

    for (var type in type_mid_data){
        type_data.push({
            name: type,
            value: type_mid_data[type]
        });
    }

    pie_chart1.setOption(pie_chart_option('传感器城市分布', city_legend, city_data));
    pie_chart2.setOption(pie_chart_option('传感器型号分布', type_legend, type_data));
}

init_pie_chart();

window.onresize = function () {
    pie_chart1.resize();
    pie_chart2.resize();
    index_big_chart.resize();
};