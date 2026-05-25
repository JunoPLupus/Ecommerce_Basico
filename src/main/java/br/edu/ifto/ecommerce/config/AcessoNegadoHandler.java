package br.edu.ifto.ecommerce.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static br.edu.ifto.ecommerce.config.Roles.ROLE_ADMIN;
import static br.edu.ifto.ecommerce.config.Rotas.ADMIN_PRODUTOS;
import static br.edu.ifto.ecommerce.config.Rotas.PRODUTOS;

public class AcessoNegadoHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        User usuarioLogado = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        assert usuarioLogado != null;
        if (usuarioLogado.getAuthorities().contains(new SimpleGrantedAuthority(ROLE_ADMIN))) {
            response.sendRedirect("/" + ADMIN_PRODUTOS);
            return;
        }
        response.sendRedirect("/" + PRODUTOS);
    }
}
