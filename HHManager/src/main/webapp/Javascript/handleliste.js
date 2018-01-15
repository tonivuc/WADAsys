/**
 * Created by Karol on 14.01.2018.
 */
$(document).ready(function () {

});

function leggTilNyHandleliste() {
    var handlelisteNavn;
    var epost = localStorage.getItem("epost");
    var brukerId = localStorage.getItem("brukerId");
    var husholdningId = localStorage.getItem("husholdningId");

    $.getJSON("server/hhservices/" + epost + "husholdningData", function (data) {
        husholdning = data;
        console.log(husholdning.id);
    })
}