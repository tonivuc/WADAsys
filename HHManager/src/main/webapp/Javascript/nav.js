$(document).ready(function (){
    $(function(){
        $("#navbar").load("nav.html");
        $("#modaldiv").load("lagnyhusstand.html");

    });

    $("#bildenav").click(function () {
        window.location="forside.html"
    });
});
