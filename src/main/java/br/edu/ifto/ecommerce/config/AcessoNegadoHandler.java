package br.edu.ifto.ecommerce.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;

import org.springframework.security.core.context.SecurityContextHolder;
import br.edu.ifto.ecommerce.model.entity.usuario.Usuario;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static br.edu.ifto.ecommerce.utils.Roles.ROLE_ADMIN;
import static br.edu.ifto.ecommerce.utils.Rotas.ADMIN_PRODUTOS;
import static br.edu.ifto.ecommerce.utils.Rotas.PRODUTOS;

public class AcessoNegadoHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        assert usuarioLogado != null;
        if (usuarioLogado.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(ROLE_ADMIN))) {
            response.sendRedirect("/" + ADMIN_PRODUTOS);
            return;
        }
        response.sendRedirect("/" + PRODUTOS);
    }
}
