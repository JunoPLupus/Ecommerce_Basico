package br.edu.ifto.ecommerce.controller;

import br.edu.ifto.ecommerce.utils.Navegacao;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static br.edu.ifto.ecommerce.utils.Diretorios.HTML_LOGIN;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/" + Navegacao.paginaInicial(auth.getAuthorities());
        }
        return HTML_LOGIN;
    }
}
