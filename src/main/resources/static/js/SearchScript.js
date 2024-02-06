$(document).ready(function () {
    $("#searchInput").on("input", function () {
        const inputText = $(this).val();
        const selectedOption = $(".selected-option").text().toLowerCase();

        if (!inputText) {
            $("#suggestions").empty().hide();
            return;
        }

        if (selectedOption === 'usuário') {
            $.ajax({
                type: "GET",
                url: "/user/list",
                data: { query: inputText },
                success: function (data) {
                    $("#suggestions").empty().show();
                    if (data.length === 0) {
                        $("#suggestions").append("<div><a href='#'> Nada foi encontrado! </a></div>");
                    } else {
                        data.forEach(function (result) {
                            var perfilUrl = '/perfilVisitante/' + result.id;
                            $("#suggestions").append("<div><a href='" + perfilUrl + "'>" + result.nome + "</a></div>");
                        });
                    }
                }
            });
        } else if (selectedOption === 'comunidade') {
            console.log(selectedOption)

            $.ajax({
                type: "GET",
                url: "/community/list",
                data: { query: inputText },
                success: function (data) {
                    $("#suggestions").empty().show();
                    if (data.length === 0) {
                        $("#suggestions").append("<div><a href='#'> Nada foi encontrado! </a></div>");
                    } else {
                        data.forEach(function (result) {
                            $("#suggestions").append("<div><a href='#'>" + result + "</a></div>");
                        });
                    }
                }
            });
        }
    });
});
$(document).mouseup(function (e) {
    var $container = $("#searchInput, #suggestions");
    if (!$container.is(e.target) && $container.has(e.target).length === 0) {
        $("#suggestions").empty().hide();
    }
});