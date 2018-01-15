/**
 * Created by BrageHalse on 12.01.2018.
 */
$(document).ready(function () {
    setupPage()
});

function setupPage(){
    var husholdning;
    var epost = localStorage.getItem("epost");
    var brukernavn = "";

    $.getJSON("server/hhservice/"+epost+"/husholdningData", function (data) {
        husholdning = data;
        console.log(husholdning.navn);
        var husholdningId = husholdning.husholdningId;
        var husNavn = husholdning.navn;
        var nyhetsinnlegg = husholdning.nyhetsinnlegg;
        var medlemmer = husholdning.medlemmer;
        var handlelister = husholdning.handlelister;
        localStorage.setItem("husholdningId", husholdningId);

        for(var i = 0, len = nyhetsinnlegg.length; i<len; i++){
            var fofatterId = nyhetsinnlegg[i].forfatterId;
            console.log(fofatterId);
        }
    });

    $.getJSON("server/BrukerService/"+epost+"/brukerData", function (data) {
        setTimeout(function () {
            var bruker = data;
            var brukerId = bruker.brukerId;
            brukernavn = bruker.navn;
            var gjøremål = bruker.gjøremål;
            console.log(bruker);
            console.log(brukernavn);
            localStorage.setItem("brukerId", brukerId);
            $("a#brukernavn").text(''+brukernavn);
            $("#nyhet").html(''+brukernavn);
        },100);

    });
}