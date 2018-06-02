// TODO clean up

let chartHandler;

$(document).ready(() => {
    loadData();
    document.getElementById("time1").value = moment(moment(Date.now()).subtract(10, 'years')).format("YYYY-MM-DDTHH:MM");
    document.getElementById("time2").value = moment(Date.now()).format("YYYY-MM-DDTHH:MM");
});

function loadData() {
    $.get("user/feeder-metadata", response => {
        console.log(response);
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


    let d = moment(document.getElementById("time1").value);
    let d2 = moment(document.getElementById("time2").value);

    console.log("a: "+companyId)
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "feederMeasurements",
        data: JSON.stringify({ companyId: metadata[index].company_id, serial: metadata[index].serial, timel: moment(d).format("YYYY-MM-DD HH:mm:ss") , timeh: moment(d2).format("YYYY-MM-DD HH:mm:ss")}),
        success: response => {
            response.forEach(showData);
            data=response;
            showChart();
        }
    });
}

function showData(element, index, array) {
    let ct='<tr><td>'+element.feederSerial+'</td><td>'+moment(element.time).format("YYYY-MM-DD HH:mm:ss")+'</td><td>'+element.value+'</td></tr>';
    $("#mes").append(ct);
}

function showChart() {
    if(chartHandler!==undefined) chartHandler.clear();
    chartHandler = new Chart($("#chart"), {
        type: 'line',
        data: {
            datasets:[{
                data: data.map((elem) => elem.value),
                backgroundColor: "rgba(54, 162, 235, 0.6)"
            }],
            labels: data.map((elem) => moment(elem.time).format("YYYY-MM-DD HH:mm:ss"))
        },
        options: {
            maintainAspectRatio: false,
            legend: {
                display: false,
            },
            scales: {
                yAxes: [{
                    ticks: {
                        callback: (value, index, values) => {
                            return value;
                        },
                        beginAtZero: true
                    }
                }]
            }
        }
    });
}