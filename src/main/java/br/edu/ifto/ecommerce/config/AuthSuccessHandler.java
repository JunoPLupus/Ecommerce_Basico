package br.edu.ifto.ecommerce.config;

import br.edu.ifto.ecommerce.model.entity.usuario.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

import static br.edu.ifto.ecommerce.utils.Roles.ROLE_ADMIN;
import static br.edu.ifto.ecommerce.utils.Rotas.*;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        Usuario authUser = (Usuario) authentication.getPrincipal();

        assert authUser != null;
        String defaultUrl = authUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_ADMIN)) ? ADMIN_VENDAS : PRODUTOS;

        response.sendRedirect("/" + defaultUrl);
    }
}
