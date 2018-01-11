/**
 * Created by BrageHalse on 10.01.2018.
 */
$(document).ready(function () {
    $("#endreEpost").click(function () {
        var nyEpost = $("#email").val();
        alert("hey!");
        if (nyEpost != "") {
            $.ajax({
                url: "/BrukerService/endreEpost",
                type: 'PUT',
                data: JSON.stringify(nyEpost),
                contentType: 'text/plain',
                dataType: 'json',
                success: function () {
                    alert("Epost endret til " + nyEpost + ".");
                },
                error: function () {
                    alert("Noe gikk galt :(")
                }
            })
        }

    });
});