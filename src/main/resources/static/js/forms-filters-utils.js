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