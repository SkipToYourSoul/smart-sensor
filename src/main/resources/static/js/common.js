/**
 *  Belongs to smart-sensor
 *  Author: liye on 2017/7/25
 *  Description: some common function
 */
var current_address = window.location.href;
if (current_address.indexOf('?') >= 0){
    current_address = current_address.substring(0, window.location.href.indexOf('?'));
}

function loadSource(){
    var link = document.createElement("link");
    link.rel = "stylesheet";
    link.href = "source/messenger/messenger.css";
    document.body.appendChild(link);

    link = document.createElement("link");
    link.rel = "stylesheet";
    link.href = "source/messenger/messenger-theme-air.css";
    document.body.appendChild(link);

    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = "source/messenger/messenger-theme-future.js";
    document.body.appendChild(script);
}
loadSource();

$._messengerDefaults = {
    extraClasses: 'messenger-fixed messenger-on-top',
    theme: 'air'
};

function message_info(text, type) {
    var hide = arguments[2]?arguments[2]:10;
    Messenger().post({
        message: text,
        type: type,
        hideAfter: hide,
        hideOnNavigate: true,
        showCloseButton: true
    });
}

// parse 2017-08-17T18:16:31.000+08:00 to 2017-08-17 18:16:31
function parseTime(time) {
    return time.split('.')[0].replace('T', ' ');
}

// --- rewrite format function, new Date(xx,xx,xx).Format("yyyy-MM-dd HH:mm:ss")
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