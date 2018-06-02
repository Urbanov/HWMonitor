$(document).ready(() => {
    $(".needs-validation").on("submit", event => {
        $(event.currentTarget).addClass("was-validated");
        event.preventDefault();
        if (event.currentTarget.checkValidity()) {
            $(event.currentTarget).removeClass("was-validated");
            postChangePassword();
        }
    });
});

function postChangePassword() {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/common/update-password",
        data: JSON.stringify({
            oldPassword: $("#old").val(),
            newPassword: $("#new").val()
        }),
        success: () => {
            showAlert(true, messages.PASSWORD_SUCCESS);
        },
        error: () => {
            showAlert(false, messages.PASSWORD_FAIL);
        }
    });
}