function test() {
    $.ajax({
        url:"rest/brukere",
        type: "GET",
        contentType: 'application/json; charset=utf-8'
    })
}

test();