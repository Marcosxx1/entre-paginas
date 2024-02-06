$(document).ready(function () {
    const modal = document.getElementById("myModal");
    const btns = document.querySelectorAll(".novo-comentario");
    const span = document.querySelector(".close");
    btns.forEach(function (btn) {
        btn.onclick = function () {
            const postElement = this.closest('.post');
            let idPostElements = document.querySelectorAll('#idPost');
            let idPost = Array.from(idPostElements).find(element => element.textContent !== '').textContent; const userLogin = document.querySelector('#userLogin').textContent;
            const modalPostContainer = document.querySelector('.modalIndex .post');
            modalPostContainer.innerHTML = postElement.innerHTML;
            modalPostContainer.setAttribute('data-id', idPost);
            document.querySelector('.modalIndex form').action = `/comments/create/save/${idPost}/${userLogin}`;
            modal.style.display = "block";
        }
    });

    span.onclick = function () {
        modal.style.display = "none";
    }

    // window.onclick = function (event) {
    //     if (event.target == modal) {
    //         modal.style.display = "none";
    //     }
    // }
});