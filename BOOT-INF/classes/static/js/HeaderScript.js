$(document).ready(function () {
    const $selectBox = $(".custom-select .select-box");
    const $options = $(".custom-select .options");
    const $optionItems = $(".custom-select .options li");
    const $selectedOption = $(".custom-select .selected-option");

    $selectBox.click(function (event) {
        $options.toggle();
        event.stopPropagation();
    });

    $(document).click(function (event) {
        if (!$(event.target).closest(".custom-select").length) {
            $options.hide();
        }
    });

    $optionItems.each(function () {
        const $item = $(this);

        if ($item.text() === $selectedOption.text()) {
            $item.hide();
        }

        $item.click(function () {
            $selectedOption.text($item.text());
            $("#searchInput").attr('placeholder', 'Pesquise por ' + $item.text().toLowerCase());
            $options.hide();

            $optionItems.show();
            $item.hide();
        });
    });
});