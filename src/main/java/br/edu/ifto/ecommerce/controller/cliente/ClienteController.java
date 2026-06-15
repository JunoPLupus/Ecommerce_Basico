package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.entity.cliente.PessoaFisica;
import br.edu.ifto.ecommerce.model.entity.cliente.PessoaJuridica;
import br.edu.ifto.ecommerce.model.repository.ClienteRepository;
import br.edu.ifto.ecommerce.service.ClienteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import static br.edu.ifto.ecommerce.utils.AutenticacaoUtils.getPessoaLogada;
import static br.edu.ifto.ecommerce.utils.Diretorios.HTML_CLIENTE_FORM;
import static br.edu.ifto.ecommerce.utils.Diretorios.HTML_CLIENTE_PERFIL;
import static br.edu.ifto.ecommerce.utils.Rotas.*;

@Controller
@AllArgsConstructor
@RequestMapping(CLIENTES)
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteRepository clienteRepository;

    @GetMapping(PERFIL)
    public String perfil(Model model) {
        Pessoa pessoa = clienteRepository.findById(getPessoaLogada().getId());
        model.addAttribute("pessoa", pessoa);
        return HTML_CLIENTE_PERFIL;
    }

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
        if (result.hasErrors()) {
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
        if (result.hasErrors()) {
            model.addAttribute("pessoaFisica", new PessoaFisica());
            model.addAttribute("activeTab", "pj");
            return HTML_CLIENTE_FORM;
        }
        clienteService.insert(pessoaJuridica, login, password);
        request.login(login, password);
        return "redirect:/" + PRODUTOS;
    }
}
