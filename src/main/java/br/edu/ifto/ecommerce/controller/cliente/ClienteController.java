package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.entity.cliente.PessoaFisica;
import br.edu.ifto.ecommerce.model.entity.cliente.PessoaJuridica;
import br.edu.ifto.ecommerce.model.entity.usuario.Usuario;
import br.edu.ifto.ecommerce.service.ClienteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import static br.edu.ifto.ecommerce.utils.Diretorios.HTML_CLIENTE_FORM;
import static br.edu.ifto.ecommerce.utils.Rotas.*;

@Controller
@AllArgsConstructor
@RequestMapping(CLIENTES)
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping(CADASTRO)
    public String insert(Model model) {
        model.addAttribute("pessoaFisica", new PessoaFisica());
        model.addAttribute("pessoaJuridica", new PessoaJuridica());
        return HTML_CLIENTE_FORM;
    }

    @PostMapping(SAVE_PF)
    public String saveFisica(@Valid PessoaFisica pessoaFisica,
                             BindingResult result,
                             String login,
                             String password,
                             Model model,
                             HttpServletRequest request) throws ServletException {
        boolean credenciaisInvalidas = validarCredenciais(login, password, model);
        if (result.hasErrors() || credenciaisInvalidas) {
            model.addAttribute("pessoaJuridica", new PessoaJuridica());
            model.addAttribute("activeTab", "pf");
            return HTML_CLIENTE_FORM;
        }
        clienteService.insert(pessoaFisica, login, password);
        request.login(login, password);
        return "redirect:/" + PRODUTOS;
    }

    @PostMapping(SAVE_PJ)
    public String saveJuridica(@Valid PessoaJuridica pessoaJuridica,
                               BindingResult result,
                               String login,
                               String password,
                               Model model,
                               HttpServletRequest request) throws ServletException {
        boolean credenciaisInvalidas = validarCredenciais(login, password, model);
        if (result.hasErrors() || credenciaisInvalidas) {
            model.addAttribute("pessoaFisica", new PessoaFisica());
            model.addAttribute("activeTab", "pj");
            return HTML_CLIENTE_FORM;
        }
        clienteService.insert(pessoaJuridica, login, password);
        request.login(login, password);
        return "redirect:/" + PRODUTOS;
    }

    /**
     * Valida login e senha do cadastro — campos não vinculados ao th:object, por isso
     * validados aqui. As mensagens são expostas via model para exibição inline no formulário.
     * @return true se houver alguma credencial inválida.
     */
    private boolean validarCredenciais(String login, String password, Model model) {
        boolean invalido = false;
        if (login == null || login.isBlank() || login.length() < Usuario.LOGIN_TAMANHO_MIN) {
            model.addAttribute("erroLogin",
                    "O login deve conter no mínimo " + Usuario.LOGIN_TAMANHO_MIN + " caracteres.");
            invalido = true;
        }
        if (password == null || password.isBlank() || password.length() < Usuario.SENHA_TAMANHO_MIN) {
            model.addAttribute("erroSenha",
                    "A senha deve conter no mínimo " + Usuario.SENHA_TAMANHO_MIN + " caracteres.");
            invalido = true;
        }
        return invalido;
    }
}
