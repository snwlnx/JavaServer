(function () {
    $("#butt").click(function () {
        $.get("/",
            {showChats: "yes"},
            function (data,status) {
                var jsonArr = JSON.parse(data),
                    ppp = $("#ppp");
                for(var i = 0; i < jsonArr.length; ++i) {
                    ppp.append(
                        $("<input/>")
                            .text("Кнопка")
                            .attr({type: "submit", name: "selectedChat", value: jsonArr[i]}));
                }
            }
        );
    });
})();