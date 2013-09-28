(function () {
    setInterval(function () {
        $.get("/", function (data,status) {
            $("#message").append($("<div></div>").html(data))
        });
    }, 5000);

    $("#butt").click(function () {
        var qStr = $("#MainForm").serializeArray();
        $.post("/", qStr, function (data,status) {

        });
    });
})();