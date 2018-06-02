$(document).ready(() => {
    $(".needs-validation").on("submit", event => {
        $(event.currentTarget).addClass("was-validated");
        event.preventDefault();
        if (event.currentTarget.checkValidity()) {
            $(event.currentTarget).removeClass("was-validated");
            postAddCompany();
        }
    });
});

function postAddCompany() {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/admin/create-company",
        data: JSON.stringify({
            name: $("#name").val(),
            username: $("#login").val(),
            password: $("#password").val()
        }),
        success: () => {
            showAlert(true, messages.COMPANY_SUCCESS);
        },
        error: () => {
            showAlert(false, messages.COMPANY_FAIL);
        }
    });
}