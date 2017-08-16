/**
 *  Belongs to smart-sensor
 *  Author: liye on 2017/8/15
 *  Description:
 */

var video_player = videojs('my-video', { }, function () {
    console.log('the video player is ready');
});

function videoPlay() {
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
});


var storyjs_jsonp_data = {
    "title": {
        "media": {
            "url": "/img/oceans.png",
            "caption": "Whitney Houston performing on her My Love is Your Love Tour in Hamburg.",
            "credit": "flickr/<a href='http://www.flickr.com/photos/tm_10001/'>tm_10001</a>"
        },
        "text": {
            "headline": "视频片段一<br/> 时长46秒",
            "text": "<p>视频片段描述</p>"
        }
    },
    "events": [
    {
        "media": {
            "url": "/img/1.jpg",
            "caption": "视频截图1：0.00",
            "credit": "Cissy Houston photo:<a href='http://www.flickr.com/photos/11447043@N00/418180903/'>Tom Marcello</a><br/><a href='http://commons.wikimedia.org/wiki/File%3ADionne_Warwick_television_special_1969.JPG'>Dionne Warwick: CBS Television via Wikimedia Commons</a>"
        },
        "start_date": {
            "year": 2017,
            "month": 8,
            "day": 16,
            "hour": 20,
            "minute": 19
        },
        "unique_id": 1
    },
    {
        "media": {
            "url": "/img/2.jpg",
            "caption": "视频截图2：0.10",
            "credit": "<a href=\"http://unidiscmusic.com\">Unidisc Music</a>"
        },
        "start_date": {
            "year": 2017,
            "month": 8,
            "day": 16,
            "hour": 20,
            "minute": 19
        },
        "unique_id": 2
    },
    {
        "media": {
            "url": "/img/3.jpg",
            "caption": "视频截图3：0.10",
            "credit": "<a href=\"http://unidiscmusic.com\">Unidisc Music</a>"
        },
        "start_date": {
            "year": 2017,
            "month": 8,
            "day": 16,
            "hour": 20,
            "minute": 20
        },
        "unique_id": 2
    }
]
};

var additionalOptions = {
    start_at_slide: 0,
    slide_padding_lr: 20,
    scale_factor: 0.5
};

var timeline = new TL.Timeline('my-timeline', storyjs_jsonp_data, additionalOptions);