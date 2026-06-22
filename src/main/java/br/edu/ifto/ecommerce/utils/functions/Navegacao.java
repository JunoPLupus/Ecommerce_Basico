package br.edu.ifto.ecommerce.utils.functions;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static br.edu.ifto.ecommerce.utils.constants.Roles.ROLE_ADMIN;
import static br.edu.ifto.ecommerce.utils.constants.Rotas.ADMIN_VENDAS;
import static br.edu.ifto.ecommerce.utils.constants.Rotas.PRODUTOS;

/**
 * Resolve a página inicial conforme o papel do usuário autenticado.
 * Centraliza a regra usada tanto no AuthSuccessHandler (após o login) quanto no
 * LoginController (acesso a /login já autenticado), evitando divergência.
 */
public final class Navegacao {

    private Navegacao() {}

    public static String paginaInicial(Collection<? extends GrantedAuthority> authorities) {
        boolean isAdmin = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_ADMIN));
        return isAdmin ? ADMIN_VENDAS : PRODUTOS;
    }
}
