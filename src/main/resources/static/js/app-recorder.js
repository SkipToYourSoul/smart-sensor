/**
 *  Belongs to smart-sensor
 *  Author: liye on 2017/8/25
 *  Description:
 */

$('#data-recorder-form').formValidation({
    framework: 'bootstrap',
    fields: {
        'start-picker': { validators: { notEmpty: { message: '建议选择数据起始时间'} } },
        'end-picker': { validators: { notEmpty: { message: '建议选择数据结束时间'} } }
    }
}).on('success.form.fv', function(evt) {
    evt.preventDefault();

    var $select = $("#data-recorder-sensor-select").find("input");
    for (var index in $select){
        var isChecked = $select[index].checked;
        var value = $select[index].value;
    }

}).on('err.form.fv', function (evt) {
    message_info("submit error", "info");
});