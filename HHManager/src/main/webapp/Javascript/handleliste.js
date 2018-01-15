/**
 * Created by Karol on 14.01.2018.
 */
$(document).ready(function () {

});

function leggTilNyHandleliste() {
    var handlelisteNavn = $("#handlelisteNavn").val();
    var epost = localStorage.getItem("epost");
    var brukerId = localStorage.getItem("brukerId");
    var husholdningId = localStorage.getItem("husholdningId");

    if (handlelisteNavn == "") {
        alert("skriv inn noke pls! ");
        return;

    }

    $.ajax({
        url: "server/HandlelisteService/login",
        type: 'POST',
        data: JSON.stringify(bruker),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (result) {
            var data = JSON.parse(result);
            if (data){
                localStorage.setItem("epost", brukerEpost);
                window.location = "forside.html";
            }else{
                alert("feil epost eller passord!");
            }

        },
        error: function () {
            alert("serverfeil :/")
        }
    })




    $.getJSON("server/hhservices/" + epost + "husholdningData", function (data) {
        husholdning = data;
        console.log(husholdning.id);
    })
}