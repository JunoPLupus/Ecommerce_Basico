function selecionarnavLink(campoA, campoB, linkA, linkB, action) {
    campoA.hidden = false;
    campoB.hidden = true;
    campoB.querySelectorAll('input').forEach(input => input.disabled = true);
    campoA.querySelectorAll('input').forEach(input => input.disabled = false);

    linkA.classList.add("active");
    linkB.classList.remove("active");

    linkA.ariaCurrent = "page";
    linkB.ariaCurrent = null;

    document.getElementById("form-cadastro-cliente").action = action;
}

const navLinkPf = document.getElementById("nav-item-pf");
const navLinkPj = document.getElementById("nav-item-pj");

const camposPf = document.getElementById("campos-pf");
const camposPj = document.getElementById("campos-pj");

navLinkPf.addEventListener(
    "click",
    () =>
        selecionarnavLink(
            camposPf,
            camposPj,
            navLinkPf,
            navLinkPj,
            '/clientes/save/fisica'
        ));

navLinkPj.addEventListener(
    "click",
    () =>
        selecionarnavLink(
            camposPj,
            camposPf,
            navLinkPj,
            navLinkPf,
            '/clientes/save/juridica'
        ));
