/**
 * Created by Karol on 14.01.2018.
 */
$(document).ready(function () {
    $("#leggTilKnapp").on("click", function () {
        leggTilNyHandleliste();
    });
});

function leggTilNyHandleliste() {
    var handlelisteNavn = $("#handlelisteNavn").val();
    var epost = localStorage.getItem("epost");
    var brukerId = localStorage.getItem("brukerId");
    var husholdningId = localStorage.getItem("husholdningId")
    var offentlig = 0;
    var offentligKnapp = $("#offentligKnapp input").val();
    if(offentligKnapp == 0){
        alert();
    }

    var handlelisteObjekt = {
        tittel: handlelisteNavn,
        skaperId: brukerId,
        husholdningId: husholdningId,
        offentlig: offentlig
    };

    if (handlelisteNavn == "") {
        alert("Skriv et navn til handlelisten!");
        return;
    }



    $.ajax({
        url: "server/handleliste",
        type: 'POST',
        data: JSON.stringify(handlelisteObjekt),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (result) {
            alert("sdfbfj")
            var data = JSON.parse(result);
            if (data){
                alert("Det gikk bra!");
            }else{
                alert("feil!");
            }

        },
        error: function () {
            alert("serverfeil :/")
        }
    })

}