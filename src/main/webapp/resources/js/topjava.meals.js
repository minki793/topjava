var mealAjaxUrl = "ajax/profile/meals/";


function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/profile/meals/", updateTableByData);
}

$.ajaxSetup({
    converters: {
        "text json": function (json_string) {
            var json = $.parseJSON(json_string);
            if (json instanceof Array) {
                json.forEach(json_value =>
                    json_value.dateTime = json_value.dateTime.replace('T', ' ').substr(0, 16)
                );
            } else {
                if (json.hasOwnProperty("dateTime")) {
                    json.dateTime = json.dateTime.replace('T', ' ').substr(0, 16);
                }
            }
            return json;
        }
    }
});

$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-mealExcess", data.excess);
            }
        }),
        updateTable: updateFilteredTable
    });

    $.datetimepicker.setLocale("ru");

    $("#startDate").datetimepicker({
        timepicker:false,
        format:'Y-m-d'
    });

    $("#startTime").datetimepicker({
        datepicker:false,
        format:'H:i'
    });

    $("#endDate").datetimepicker({
        timepicker:false,
        format:'Y-m-d'
    });

    $("#endTime").datetimepicker({
        datepicker:false,
        format:'H:i'
    });

    $("#dateTime").datetimepicker({
        format:'Y-m-d H:i',
        lang:'ru'
    });
});