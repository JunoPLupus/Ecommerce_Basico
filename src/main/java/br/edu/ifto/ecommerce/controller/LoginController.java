package br.edu.ifto.ecommerce.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static br.edu.ifto.ecommerce.utils.Diretorios.HTML_LOGIN;
import static br.edu.ifto.ecommerce.utils.Roles.ROLE_ADMIN;
import static br.edu.ifto.ecommerce.utils.Rotas.ADMIN_PRODUTOS;
import static br.edu.ifto.ecommerce.utils.Rotas.PRODUTOS;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals(ROLE_ADMIN));
            return "redirect:/" + (isAdmin ? ADMIN_PRODUTOS : PRODUTOS);
        }
        return HTML_LOGIN;
    }
}
