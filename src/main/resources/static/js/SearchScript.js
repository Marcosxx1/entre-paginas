$(document).ready(function () {
    $("#searchInput").on("input", function () {
        const inputText = $(this).val();
        const selectedOption = $(".selected-option").text().toLowerCase();

        if (!inputText) {
            $("#suggestions").empty();
            return;
        }

        $.ajax({
            type: "GET",
            url: "/user/list",
            data: { query: inputText, option: selectedOption },
            success: function (data) {
                $("#suggestions").empty();
                data.forEach(function (result) {
                    $("#suggestions").append("<div><a href='#'>" + result + "</a></div>");
                });
            }
        });
    });
});