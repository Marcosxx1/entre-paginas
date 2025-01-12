$(document).ready(function () {
    const modal = document.getElementById("myModal");
    const btns = document.querySelectorAll(".novo-comentario");
    const span = document.querySelector(".close");

    const postFooter = document.querySelector(".post-footer");
    const isAuthenticated = postFooter.getAttribute('data-authenticated') === 'true'; // Convert to boolean

    btns.forEach(function (btn) {
        btn.onclick = function () {
            if (!isAuthenticated) {
                alert("You must be logged in to comment.");
                return;
            }

            const postElement = this.closest('.post');
            let idPostElements = document.querySelectorAll('#idPost');
            let idPost = Array.from(idPostElements).find(element => element.textContent !== '').textContent;
            const modalPostContainer = document.querySelector('.modalIndex .post');
            modalPostContainer.innerHTML = postElement.innerHTML;
            modalPostContainer.setAttribute('data-id', idPost);
            document.querySelector('.modalIndex form').action = `/comments/create/save/${idPost}`;
            modal.style.display = "block";
        }
    });

    span.onclick = function () {
        modal.style.display = "none";
    }

    $(document).keydown(function (event) {
        if (event.key === "Escape") {
            modal.style.display = "none";
        }
    });

    modal.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
});