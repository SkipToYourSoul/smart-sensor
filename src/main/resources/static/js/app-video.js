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
    console.log("current time: " + video_player.currentTime());
    console.log("duration time: " + video_player.duration());
    console.log("buffered time: " + video_player.buffered());
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

video_player.on("timeupdate", function () {
    console.log("update");
});