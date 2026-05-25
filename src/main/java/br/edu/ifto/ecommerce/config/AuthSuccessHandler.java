package br.edu.ifto.ecommerce.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

import static br.edu.ifto.ecommerce.config.Roles.ROLE_ADMIN;
import static br.edu.ifto.ecommerce.config.Rotas.*;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        User authUser = (User) authentication.getPrincipal();

        assert authUser != null;
        String defaultUrl = authUser.getAuthorities().contains(new SimpleGrantedAuthority(ROLE_ADMIN))? ADMIN_VENDAS : PRODUTOS;

        response.sendRedirect(defaultUrl);
    }
}
