let chartHandler;

$(document).ready(() => {
    loadData();
});


function loadData() {
    $.get("feederMetadata", response => {
        response.forEach(createCompanyButton);
        metadata=response;
    });
}

function createCompanyButton(element, index, array){
    if(element.x==="1") {
        if(index!==0)
            $("#feederButtons").append('&nbsp;');
        let ct='<div><h1 style="font-size: 25px;">Company: ' + element.company_id+'</h1></div>&nbsp;';
        $("#feederButtons").append(ct);
        companyId=element.serial;
        console.log("c: "+companyId)
    }
    if(element.x==="0") {
        var button = document.createElement('button');
        button.classList.add("btn", "btn-block", "btn-primary", "text-left");
        button.id=index;
        button.textContent=element.serial + ': '+element.desc;
        document.getElementById("feederButtons").appendChild(button);
        document.getElementById(index).addEventListener("click", function() {
            measurementData(this.id);
        }, false);
    }
}

function measurementData(index) {
    document.getElementById("measurementContent").innerHTML = "";
    let ct='<thead> <tr> <th>Serial</th> <th>Timestamp</th> <th>Value</th> </tr> </thead><tbody id = "mes"></tbody>';
    $("#measurementContent").append(ct);

    var d = new Date(2000, 1, 1, 0, 0, 0, 0);
    var d2 = new Date(2020, 3, 25, 0, 0, 0, 0);
    console.log("a: "+companyId)
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "feederMeasurements",
        data: JSON.stringify({ companyId: metadata[index].company_id, serial: metadata[index].serial, timel: moment(d).format("YYYY-MM-DD HH:mm:ss") , timeh: moment(d2).format("YYYY-MM-DD HH:mm:ss")}),
        success: response => {
            response.forEach(showData);
        }
    });
}

function showData(element, index, array) {
    let ct='<tr><td>'+element.feederSerial+'</td><td>'+moment(element.time).format("YYYY-MM-DD HH:mm:ss")+'</td><td>'+element.value+'</td></tr>';
    $("#mes").append(ct);
}