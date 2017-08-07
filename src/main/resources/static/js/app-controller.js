/**
 * Belongs to
 * Author: liye on 2017/8/6
 * Description:
 */

$new_app_form = $('#new-app-form');
$new_app_form.formValidation({
    framework: 'bootstrap',
    icon: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove'
    },
    fields: {
        'new-app-name': {validators: {notEmpty: {message: '应用名不能为空'}}},
        'new-app-description': {validators: {notEmpty: {message: '应用描述不能为空'}}}
    }
}).on('success.form.fv', function (evt){
    evt.preventDefault();
    $.ajax({
        type: 'post',
        url: current_address + '/new',
        data: $(this).serialize(),
        success: function (id) {
            message_info("创建成功", 'success');
            window.location.href = current_address + "?id=" + id;
        },
        error: function (id) {
            message_info("创建失败", 'error');
        }
    });

}).on('err.form.fv', function (evt) {
    message_info("提交失败", 'error');
});

$edit_app_form = $('#edit-app-form');
$edit_app_form.formValidation({
    framework: 'bootstrap',
    icon: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove'
    },
    fields: {
        'edit-app-name': {validators: {notEmpty: {message: '应用名不能为空'}}},
        'edit-app-description': {validators: {notEmpty: {message: '应用描述不能为空'}}}
    }
}).on('success.form.fv', function (evt){
    evt.preventDefault();
    $.ajax({
        type: 'post',
        url: current_address + '/edit',
        data: $(this).serialize() + "&id=" + currentAppId,
        success: function (id) {
            location.replace(location.href);
            message_info("修改成功", 'success');
        },
        error: function (id) {
            message_info("修改失败", 'error');
        }
    });

}).on('err.form.fv', function (evt) {
    message_info("提交失败", 'error');
});


// --------------------------
// new sensor
// --------------------------
$edit_app_modal = $('#edit-app-modal');
$edit_app_modal.on('show.bs.modal', function (e) {
    $('#edit-app-name').val(apps[currentAppIndex - 1]['name']);
    $('#edit-app-description').val(apps[currentAppIndex - 1]['description']);
});

$new_sensor_form = $('#new-sensor-form');
$new_sensor_form.formValidation({
    framework: 'bootstrap',
    icon: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove'
    },
    fields: {
        'new-sensor-name': {validators: {notEmpty: {message: '传感器名称不能为空'}}},
        'new-sensor-description': {validators: {notEmpty: {message: '传感器描述不能为空'}}},
        'new-sensor-code': {validators: {notEmpty: {message: '传感器编号不能为空'}}}
    }
}).on('success.form.fv', function (evt){
    evt.preventDefault();
    $.ajax({
        type: 'post',
        url: current_address + '/new/sensor',
        data: $(this).serialize() + "&appId=" + currentAppId,
        success: function (id) {
            location.replace(location.href);
            message_info("新建传感器成功", 'success');
        },
        error: function (id) {
            message_info("新建传感器失败", 'error');
        }
    });

}).on('err.form.fv', function (evt) {
    message_info("提交失败", 'error');
});

$('#positionBtn').click(function () {
    message_info("定位中，请稍等", 'info');
    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function(r){
        if(this.getStatus() == BMAP_STATUS_SUCCESS){
            $('#new-sensor-latitude').val(r.point.lat);
            $('#new-sensor-longitude').val(r.point.lng);
            message_info("定位完成", 'info');
        }
        else {
            message_info('无法定位', 'info');
        }
    },{enableHighAccuracy: true});

    var currentCity = new BMap.LocalCity();
    currentCity.get(function (city) {
        $('#new-sensor-city').val(city.name);
    });
});