$(document).ready(() => {
    $(".needs-validation").on("submit", event => {
        $(event.currentTarget).addClass("was-validated");
        event.preventDefault();
        if (event.currentTarget.checkValidity()) {
            $(event.currentTarget).removeClass("was-validated");
            postAddFeeder();
        }
    });
});

function postAddFeeder() {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/user/create-feeder",
        data: JSON.stringify({
            serial: $("#serial").val(),
            description: $("#description").val(),
            secret: $("#secret").val()
        }),
        success: () => {
            showAlert(true, messages.FEEDER_SUCCESS);
        },
        error: () => {
            showAlert(false, messages.FEEDER_FAIL);
        }
    });
}