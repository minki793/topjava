// $(document).ready(function () {

function filter() {
    $.ajax({
        type: "GET",
        url: context.ajaxUrl + "filter",
        data: $('#filterForm').serialize()
    }).done(function(data) {
        drawTable(data);
    });
}

function clearfilter() {
    $('#filterForm').trigger('reset');
    $.get(context.ajaxUrl, function (data) {
        drawTable(data);
    });
}


$(function () {
    makeEditable({
            ajaxUrl: "ajax/profile/meals/",
            meal: true,
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

