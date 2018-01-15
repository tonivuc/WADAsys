/**
 * Created by Karol on 14.01.2018.
 */
$(document).ready(function () {

});

function leggTilNyHandleliste() {
    var handlelisteNavn;
    var epost = localStorage.getItem("epost");

    $.getJSON("server/hhservices/" + epost + "husholdningData", function (data) {
        husholdning = data;
        console.log(husholdning.id);
    })
}