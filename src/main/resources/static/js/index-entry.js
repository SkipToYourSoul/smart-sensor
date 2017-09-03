/**
 *  Belongs to smart-sensor
 *  Author: liye on 2017/9/3
 *  Description:
 */

var $input = $('#app-searcher');
var app_suggest = [];
var app_info = {};

for (var d in apps){
    app_suggest.push(apps[d]['id'] + "." + apps[d]['name'] + "(" + apps[d]['creator'] + ")");
    app_info[apps[d]['id']] = apps[d];

    $('#hot-app-list').append('<li>' + apps[d]['name'] + '</li>');
}
$input.typeahead({ source:app_suggest });

$input.change(function() {
    var current = $input.typeahead("getActive");
    if (current) {
        // Some item from your model is active!
        if (current == $input.val()) {
            // This means the exact match is found. Use toLowerCase() if you want case insensitive match.
            var appId = $input.val().split('.')[0];
            showSelectAppInfo(appId);
        } else {
            // message_info("WRONG FIELD: " + current, "info");
            // This means it is only a partial match, you can either add a new item
            // or take the active if you don't want new items
        }
    } else {
        // Nothing is active so it is a new value (or maybe empty value)
    }
});

function showSelectAppInfo(appId){
    $('#select-app-info').html(
        '<p>应用名：' + app_info[appId]['name'] +  '</p>' +
        '<p>应用分享人：' + app_info[appId]['creator'] +  '</p>' +
        '<p>应用描述：' + app_info[appId]['description'] +  '</p>'
    );
}