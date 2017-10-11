/**
 *  Belongs to smart-sensor
 *  Author: liye on 2017/10/11
 *  Description:
 */

// --- new, edit, delete app
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
    var appId = currentAppIndex > 0?apps[currentAppIndex - 1]['id']:-1;
    $.ajax({
        type: 'post',
        url: current_address + '/new/app',
        data: $(this).serialize() + "&id=" + appId,
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
$new_app_modal.on('shown.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var todo = button.attr('todo');
    var modal = $(this);

    if (todo == "new"){
        modal.find('.modal-title').text('新建应用');
        $('#app-confirm-btn').text("新建应用");
        $('#is-new-app').attr('value', 1);

        $('#new-app-name').val("");
        $('#new-app-description').val("");
    } else if (todo == "edit"){
        modal.find('.modal-title').text('编辑应用');
        $('#app-confirm-btn').text("确认修改");
        $('#is-new-app').attr('value', 0);

        $('#new-app-name').val(apps[currentAppIndex - 1]['name']);
        $('#new-app-description').val(apps[currentAppIndex - 1]['description']);
    }
});

function deleteApp(){
    bootbox.confirm({
        title: "删除应用?",
        message: "确认删除应用吗? 应用相关的传感器相关数据也会被删除.",
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
                    url: current_address + '/delete/app?id=' + apps[currentAppIndex - 1]['id'],
                    success: function (id) {
                        location.replace(current_address);
                    },
                    error: function (id) {
                        message_info("操作失败", 'error');
                    }
                });
            }
        }
    });
}

