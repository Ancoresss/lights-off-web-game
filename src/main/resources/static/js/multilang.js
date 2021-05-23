const langBtn = document.getElementById('lang-btn');
const rules = document.querySelectorAll('.rule');
const rules_ua = [
    'Гра Lights Off має наступні правила',
    'мета гри - вимкнути всі лампочки на полі',
    'у вас є лише 100 ходів, щоб виграти',
    'клік на лампочку змінює її стан (увімкнеться, якщо була вимкнена, і вимкнеться, якщо була увімкнена)',
    'клік на лампочку також змінює стан її сусідів зверху, знизу, зправа та зліва',
    'всі рівні генеруються випадковим чином',
    'чим вищий рівень ви оберете, тим більшим буде ігрове поле'
]
const rules_en = [
    'The Lights Off game has the following rules:',
    'the purpose of the game is to turn off all the lights on the board',
    'you have only 100 moves to win',
    'clicking on a square change its state (on, if it was off, and off, if it was on)',
    'clicking on a square also changes the state of its North, South, East and West neighbors',
    'all the levels are randomly generated',
    'the higher the level you choose, the bigger the playing field will be'
]
localStorage.setItem('lang', 'en'); // default value
langBtn.addEventListener('click', function () {
    const current_lang = localStorage.getItem('lang');
    current_lang === 'en' ? localStorage.setItem('lang', 'ua') : localStorage.setItem('lang', 'en');

    if(localStorage.getItem('lang') === 'ua') {
        langBtn.innerText = 'Читати англійською';
        for(let i = 0; i < rules.length; i++) {
            rules[i].innerText = rules_ua[i];
            rules[i].style.fontFamily = 'Source Sans Pro';
        }
    } else {
        langBtn.innerText = 'Read in ukrainian';
        for(let i = 0; i < rules.length; i++) {
            rules[i].innerText = rules_en[i];
            rules[i].style.fontFamily = 'Signika';
        }
    }
});