$(document).ready(() => {
    $(".needs-validation").on("submit", event => {
        if (event.currentTarget.checkValidity() === false) {
            event.preventDefault();
        }
        $(event.currentTarget).addClass("was-validated");
    });
});