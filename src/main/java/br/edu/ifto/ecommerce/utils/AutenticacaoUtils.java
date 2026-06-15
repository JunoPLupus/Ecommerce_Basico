package br.edu.ifto.ecommerce.utils;

import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.entity.usuario.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AutenticacaoUtils {

    private AutenticacaoUtils() {}

    public static Usuario getUsuarioLogado() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Pessoa getPessoaLogada() {
        return getUsuarioLogado().getPessoa();
    }
}
