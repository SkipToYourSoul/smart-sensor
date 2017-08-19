/**
 *  Belongs to smart-sensor
 *  Author: liye on 2017/8/1
 *  Description:
 */

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

var timelineOptions = {
    start_at_slide: 0,
    slide_padding_lr: 20,
    scale_factor: 0.5,
    timenav_height: 200,
    // marker_height_min: 80
};

var video_option = {
    poster : '/img/oceans.png',
    sources:[
        {
            src: '//vjs.zencdn.net/v/oceans.mp4',
            type: 'video/mp4'
        }
    ],
    techOrder: ["html5", "flash"]
};

var storyjs_jsonp_data = {
    "title": {
        "text": {
            "headline": "视频片段一<br/> 时长46秒",
            "text": "<p>视频片段描述</p>"
        }
    },
    "eras": {
        "start_date": {
            "year": 2017,
            "month": 8,
            "day": 16,
            "hour": 20,
            "minute": 18,
            "second": 0
        },
        "end_date": {
            "year": 2017,
            "month": 8,
            "day": 16,
            "hour": 20,
            "minute": 21,
            "second": 0
        }
    },
    "events": [
        {
            "media": {
                "url": "/img/1.jpg",
                "caption": "视频截图1：0.00",
                "thumbnail": "/img/1.jpg"
            },
            "start_date": {
                "year": 2017,
                "month": 8,
                "day": 16,
                "hour": 20,
                "minute": 19,
                "second": 30
            },
            "text": {
                "text": "<p>视频截图1：0.00</p>"
            },
            "unique_id": 1
        },
        {
            "media": {
                "url": "/img/3.jpg",
                "caption": "视频截图3：0.10",
                "thumbnail": "/img/3.jpg"
            },
            "start_date": {
                "year": 2017,
                "month": 8,
                "day": 16,
                "hour": 20,
                "minute": 20,
                "second": 0
            },
            "unique_id": 2
        }
    ]
};

/*function videoPlay() {
 video_player.play();
 timeline.goToNext();

 var interval = setInterval(function () {
 timeline.goToNext();
 }, 3000);
 }

 function videoPause() {
 video_player.pause();
 console.log("current time: " + video_player.currentTime());
 console.log("duration time: " + video_player.duration());
 console.log("buffered time: " + video_player.buffered());
 }

 video_player.on("pause", function(){
 console.log("pause");
 });*/