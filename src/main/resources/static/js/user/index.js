let chartHandler;

$(document).ready(() => {
    loadData();
});


function loadData() {
    $.get("feederMetadata", response => {
        response.forEach(createCompanyButton);
    });
}

function createCompanyButton(element, index, array){
    if(element.x==="1") {
        if(index!==0)
            $("#feederButtons").append('&nbsp;');
        let ct='<div><h1 style="font-size: 25px;">Company: ' + element.company_id+'</h1></div>&nbsp;';
        $("#feederButtons").append(ct);
    }
    if(element.x==="0") {
        var button = document.createElement('button');
        button.classList.add("btn", "btn-block", "btn-primary", "text-left");
        button.id=element.serial;
        button.textContent=element.serial + ': '+element.desc;
        document.getElementById("feederButtons").appendChild(button);
        document.getElementById(element.serial).addEventListener("click", function() {
            console.log(this.id);
            measurementData(this.id)
        }, false);
    }
}


function measurementData(serial) {
    document.getElementById("measurementContent").innerHTML = "";
    let ct='<thead> <tr> <th>Serial</th> <th>Timestamp</th> <th>Value</th> </tr> </thead><tbody id = "mes"></tbody>';
    $("#measurementContent").append(ct);

    var d = new Date(2000, 1, 1, 0, 0, 0, 0);
    var d2 = new Date(2020, 3, 25, 0, 0, 0, 0);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "feederMeasurements",
        data: JSON.stringify({ serial: serial, timel: d , timeh: d2}),
        success: response => {
            response.forEach(showData);
        }
    });
}

function showData(element, index, array) {
    let ct='<tr><td>'+element.feederSerial+'</td><td>'+moment(element.time).format("YYYY-MM-DD HH:mm:ss")+'</td><td>'+element.value+'</td></tr>';
    $("#mes").append(ct);
}