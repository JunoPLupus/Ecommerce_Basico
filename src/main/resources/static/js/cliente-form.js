function selecionarTab(mostrar, esconder, linkAtivo, linkInativo) {
    mostrar.removeAttribute('hidden');
    esconder.setAttribute('hidden', '');

    linkAtivo.classList.add('active');
    linkInativo.classList.remove('active');

    linkAtivo.ariaCurrent = 'page';
    linkInativo.ariaCurrent = null;
}

const navLinkPf = document.getElementById('nav-item-pf');
const navLinkPj = document.getElementById('nav-item-pj');

const containerPf = document.getElementById('container-pf');
const containerPj = document.getElementById('container-pj');

navLinkPf.addEventListener('click', () => selecionarTab(containerPf, containerPj, navLinkPf, navLinkPj));
navLinkPj.addEventListener('click', () => selecionarTab(containerPj, containerPf, navLinkPj, navLinkPf));
