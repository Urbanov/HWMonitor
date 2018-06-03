let chartHandler;

$(document).ready(() => {
    updateFeederList();

    $("#chartButton").click(() => {
        $("#listView").fadeOut(150, () => {
            $("#chartView").fadeIn(150);
        });
        $("#listButton").toggleClass("active");
        $("#chartButton").toggleClass("active");
    });

    $("#listButton").click(() => {
        $("#chartView").fadeOut(150, () => {
            $("#listView").fadeIn(150);
        });
        $("#listButton").toggleClass("active");
        $("#chartButton").toggleClass("active");
    });

    $.fn.datetimepicker.Constructor.Default = $.extend($.fn.datetimepicker.Constructor.Default, {
        icons : {
            time : 'fas fa-clock',
            date : 'fas fa-calendar',
            up : 'fas fa-arrow-up',
            down : 'fas fa-arrow-down',
            previous : 'fas fa-chevron-left',
            next : 'fas fa-chevron-right',
            today : 'fas fa-calendar-check-o',
            clear : 'fas fa-trash',
            close : 'fas fa-times'
        }
    });

    $('#datetimepicker').datetimepicker({
        format: "YYYY-MM-DD HH:mm:ss",
        inline: true,
        date: moment(moment(Date.now()).subtract(1, 'days')),
        sideBySide: false,
        locale: moment.locale('de')
    });

    chartHandler = new Chart($("#chart"), {
        type: 'line',
        data: {
            datasets:[{
                data: [],
                backgroundColor: "rgba(54, 162, 235, 0.6)"
            }],
            labels: [],
        },
        options: {
            animation: false,
            maintainAspectRatio: false,
            legend: {
                display: false,
            },
            scales: {
                xAxes: [{
                    display: false
                }],
                yAxes: [{
                    ticks: {
                        callback: (value, index, values) => {
                            return value + "\u2103";
                        },
                        min: 0,
                        max: 100
                    }
                }]
            }
        }
    });
});

function updateFeederList() {
    $.get("/user/feeder-metadata", response => {
        response.forEach(feeder => {
            $("#feederList").append($("<button/>", {
                text: "#" + feeder.serial + ": " + feeder.description,
                class: "btn btn-block btn-outline-dark text-left",
                click: () => getMeasurements(feeder.serial)
            }));
        });
    });
}

function getMeasurements(serial) {
    $("#feederList").find(".active").removeClass("active");
    $(event.currentTarget).addClass("active");

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/user/feeder-measurements",
        data: JSON.stringify({
            serial: serial,
            begin: moment($("#datetimepicker").datetimepicker("viewDate")).format("YYYY-MM-DD HH:mm:ss"),
            end: moment(Date.now()).format("YYYY-MM-DD HH:mm:ss")
        }),
        success: response => {
            if (response.length > 0) {
                $("#placeholder").fadeOut(150, () => {
                    $("#measurements").fadeIn(150);
                    showMeasurementsAsChart(response);
                    showMeasurementsAsList(response);
                });
            }
            else {
                $("#measurements").fadeOut(150, () => {
                    $("#placeholder").fadeIn(150);
                });
            }
        }
    });
}

function showMeasurementsAsChart(data) {
    chartHandler.data.datasets[0].data = data.map(elem => elem.value);
    chartHandler.data.labels = data.map(elem => elem.timestamp);
    chartHandler.update();
}

function showMeasurementsAsList(data) {
    $("#table").bootstrapTable("load", data);
}
