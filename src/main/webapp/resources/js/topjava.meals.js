// $(document).ready(function () {

function filter() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $('#filterForm').serialize()
    }).done(function(data) {
        context.datatableApi.clear().rows.add(data).draw();
    });
}


$(function () {
    makeEditable({
            ajaxUrl: "ajax/profile/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
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
                        "desc"
                    ]
                ],
            }),
        }
    );
});

