/**
 * Created by BrageHalse on 11.01.2018.
 */
$(document).ready(function () {
    $("#loggInnBtn").click(function () {
        var brukerEpost = $("#email").val();
        var passord = $("#password").val();
        if (brukerEpost != "" || passord != ""){
            $.ajax({
                url: "/BrukerService/login",
                type: 'POST',
                data: JSON.stringify({epost: brukerEpost, hashPass: passord}),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function () {
                    window.location="forside.html";
                },
                error: function () {
                    alert("feil epost eller passord!");
                    window.location="forside.html"; // FJERN DET HER, KUN FOR TEST
                }
            })
        }
    })
    $("#regBruker").click(function () {
        window.location="lagbruker.html"
    });
});