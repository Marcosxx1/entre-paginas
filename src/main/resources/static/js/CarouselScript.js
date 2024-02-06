$(document).ready(function () {
    const totalElements = $('.container-comunidades .comunidade').length;

    $('.container-comunidades').slick({
        infinite: true,
        slidesToShow: Math.min(3.6, totalElements),
        slidesToScroll: 1,
        draggable: true,
        centerMode: true,
        responsive: [
            {
                breakpoint: 1024,
                settings: {
                    slidesToShow: Math.min(4, totalElements),
                    slidesToScroll: 1
                }
            },
            {
                breakpoint: 600,
                settings: {
                    slidesToShow: Math.min(3, totalElements),
                    slidesToScroll: 1
                }
            },
            {
                breakpoint: 480,
                settings: {
                    slidesToShow: Math.min(2, totalElements),
                    slidesToScroll: 1
                }
            }
        ]
    });

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

$(document).ready(function () {
    const totalElements = $('.container-livros .book').length;

    $('.container-livros').slick({
        infinite: true,
        slidesToShow: Math.min(3, totalElements),
        slidesToScroll: 1,
        draggable: true,
        centerMode: true,
        responsive: [
            {
                breakpoint: 1024,
                settings: {
                    slidesToShow: Math.min(4, totalElements),
                    slidesToScroll: 1
                }
            },
            {
                breakpoint: 600,
                settings: {
                    slidesToShow: Math.min(3, totalElements),
                    slidesToScroll: 1
                }
            },
            {
                breakpoint: 480,
                settings: {
                    slidesToShow: Math.min(2, totalElements),
                    slidesToScroll: 1
                }
            }
        ]
    });
});