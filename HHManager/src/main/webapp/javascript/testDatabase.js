function test() {
    $.ajax({
        url:"rest/brukere",
        type: "GET",
        contentType: 'application/json; charset=utf-8'
    }).done(function (result) {
        console.log("gettet");
        console.log(result);
    })
}

$(document).ready(function () {
    $("#testknapp").click(function () {
        console.log("trykket");
        test();
    })
});