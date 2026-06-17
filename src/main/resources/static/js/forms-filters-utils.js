document.querySelectorAll('form').forEach(form => {
    form.addEventListener('submit', function() {
        let elementos_form = form.querySelectorAll('input');
        elementos_form.forEach(elemento => {
            if (elemento.value === '' || elemento.value === '0' || elemento.value === '0.0') {
                elemento.disabled = true;
            }
        });
    });
});
document.querySelectorAll('.pg-senha-field__toggle').forEach(botao => {
    botao.addEventListener('click', () => {
        const campo = botao.closest('.pg-senha-field');
        if (!campo) return;

        const input = campo.querySelector('input');
        const icone = botao.querySelector('i');
        const escondido = input.type === 'password';

        input.type = escondido ? 'text' : 'password';

        if (icone) {
            icone.classList.toggle('icon-eye', !escondido);
            icone.classList.toggle('icon-eye-slash', escondido);
        }
    });
});

const caminhoNav = window.location.pathname;
document.querySelectorAll('.pg-nav-collapse > ul li a').forEach(link => {
    const href = link.getAttribute('href');
    if (href && href.length > 1 && caminhoNav.startsWith(href)) {
        link.classList.add('active');
    }
});
