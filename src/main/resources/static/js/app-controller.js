/**
 * Belongs to
 * Author: liye on 2017/8/6
 * Description:
 */

// --------------------------
// new and edit app
// --------------------------
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
        url: current_address + '/new/app?id=' + currentAppId,
        data: $(this).serialize(),
        success: function (id) {
            window.location.href = current_address + "?id=" + id;
        },
        error: function (id) {
            message_info("操作失败", 'error');
        }
    });

}).on('err.form.fv', function (evt) {
    message_info("提交失败", 'error');
});

$new_app_modal = $('#new-app-modal');
$new_app_modal.on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var todo = button.attr('todo');
    var modal = $(this);
    
    if (todo == "new"){
        modal.find('.modal-title').text('新建应用');
        $('#app-confirm-btn').text("新建应用");
        $('#is-new-app').attr('value', 1);
    } else if (todo == "edit"){
        modal.find('.modal-title').text('编辑应用');
        $('#app-confirm-btn').text("确认修改");
        $('#is-new-app').attr('value', 0);

        $('#new-app-name').val(apps[currentAppIndex - 1]['name']);
        $('#new-app-description').val(apps[currentAppIndex - 1]['description']);
    }
});

// --------------------------
// new, edit and delete sensor
// --------------------------
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
        data: $(this).serialize() + "&appId=" + currentAppId + "&sensorId=" + $('#is-new-sensor').attr('sensor-id'),
        success: function (id) {
            location.replace(location.href);
        },
        error: function (id) {
            message_info("操作传感器失败", 'error');
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

$new_sensor_modal = $('#new-sensor-modal');
$new_sensor_modal.on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var index = button.attr('data');
    var modal = $(this);

    if (index == null){
        // new sensor
        modal.find('.modal-title').text('新建传感器');
        $('#sensor-confirm-btn').text("新建传感器");
        $('#is-new-sensor').attr('value', 1);
    } else {
        // edit sensor
        modal.find('.modal-title').text('修改传感器信息');
        $('#sensor-confirm-btn').text("确认修改");
        $('#is-new-sensor').attr('value', 0).attr('sensor-id', sensors[index]['id']);

        $('#new-sensor-name').val(sensors[index]['name']);
        $('#new-sensor-code').val(sensors[index]['code']);
        $('#new-sensor-city').val(sensors[index]['city']);
        $('#new-sensor-latitude').val(sensors[index]['latitude']);
        $('#new-sensor-longitude').val(sensors[index]['longitude']);
        $('#new-sensor-description').val(sensors[index]['description']);
    }
});

function deleteSensor(evt) {
    var sensorIndex = evt.getAttribute('data');
    var sensorId = sensors[sensorIndex]['id'];

    bootbox.confirm({
        title: "删除传感器?",
        message: "确认删除传感器吗? 传感器相关数据也会被删除.",
        buttons: {
            cancel: {
                label: '<i class="fa fa-times"></i> 取消'
            },
            confirm: {
                label: '<i class="fa fa-check"></i> 确认删除'
            }
        },
        callback: function (result) {
            if (result){
                $.ajax({
                    type: 'get',
                    url: current_address + '/delete/sensor?id=' + sensorId,
                    success: function (id) {
                        location.replace(location.href);
                    },
                    error: function (id) {
                        message_info("操作失败", 'error');
                    }
                });
            }
        }
    });
}

function monitorSensorChart(evt) {
    var $button = $(evt);
    var sensorIndex = evt.getAttribute('data');
    var sensorId = sensors[sensorIndex]['id'];
    var $sensor_state = $('#sensor-state-' + sensorId);
    $sensor_state.removeClass('label-danger');
    $sensor_state.addClass('label-warning');
    $sensor_state.html('链接中');

    $.ajax({
        type: 'get',
        url: current_address + '/monitor/sensor',
        data: {sensorId: sensorId, appId: currentAppId},
        success: function (id) {
            $sensor_state.removeClass('label-warning');
            $sensor_state.addClass('label-success');
            $sensor_state.html('已链接');
            $button.html('断开连接');
            $button.removeClass('btn-success');
            $button.addClass('btn-danger');
        },
        error: function (id) {
            $sensor_state.removeClass('label-warning');
            $sensor_state.addClass('label-danger');
            $sensor_state.html('链接失败');
            $button.html('重新连接');
        }
    });
}