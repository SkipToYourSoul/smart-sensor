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