$(document).ready(function () {
    const totalElements = $('.container-comunidades .comunidade').length;

    $('.comunidade').hover(
        function () {
            $(this).next('.comunidade-verso').removeClass('hide');
            $(this).addClass('hide');
        },
        function () {
            $(this).next('.comunidade-verso').addClass('hide');
            $(this).removeClass('hide');
        }
    );
});