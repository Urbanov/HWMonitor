const messages = {
    LOGIN_FAILED: "Incorrect username or password",
    COMPANY_SUCCESS: "Company registered successfully",
    COMPANY_FAIL: "Could not register company",
    FEEDER_SUCCESS: "Feeder registered successfully",
    FEEDER_FAIL: "Could not register feeder",
    PASSWORD_SUCCESS: "Password changed successfully",
    PASSWORD_FAIL: "Could not change password"
};

function showAlert(success, text) {
    $(document).scrollTop(0);
    let alertElement = success ? $(".alert-success") : $(".alert-danger");
    alertElement.find(".alertText").text(text);
    alertElement.fadeIn(500).delay(1000).fadeOut(500);
}