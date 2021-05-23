const gameLinks = document.querySelectorAll("ul li a");
const askLevel = document.getElementById("askLevel");
for(let i = 0; i < gameLinks.length; i++) {
    gameLinks[i].addEventListener('click', function (e) {
        e.preventDefault();
        if(askLevel.style.display === 'block') {
            askLevel.style.display = 'none';
        } else {
            askLevel.style.display = 'block';
        }
    })
}