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
            message_info("修改成功", 'success');
            window.location.href = current_address + "?id=" + currentAppId;
        },
        error: function (id) {
            message_info("修改失败", 'error');
        }
    });

}).on('err.form.fv', function (evt) {
    message_info("提交失败", 'error');
});

$edit_app_modal = $('#edit-app-modal');
$edit_app_modal.on('show.bs.modal', function (e) {
    $('#edit-app-name').val(apps[currentAppIndex - 1]['name']);
    $('#edit-app-description').val(apps[currentAppIndex - 1]['description']);
});