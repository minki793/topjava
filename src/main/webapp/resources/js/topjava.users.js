
function changeActive(checkbox) {
    $.ajax({
        url: context.ajaxUrl + chkbox.closest("tr").attr("id"),
        type: "POST",
        data: "enabled=" + checkbox.checked,
    }).done(function () {

    //    successNoty(enabled ? "common.enabled" : "common.disabled");
    }).fail(function () {
   //     $(chkbox).prop("checked", !enabled);
    });
}


// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            meal: false,
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            })
        }
    );
});