package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.entity.cliente.PessoaFisica;
import br.edu.ifto.ecommerce.model.entity.cliente.PessoaJuridica;
import br.edu.ifto.ecommerce.service.ClienteService;
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
    public String insert(){
        return HTML_CLIENTE_FORM;
    }

    @PostMapping(SAVE_PF)
    public String saveFisica(@Valid PessoaFisica pessoa,
                             String login,
                             String password,
                             BindingResult result,
                             Model model){
        if (result.hasErrors()) {
            String mensagem = result.getAllErrors().getFirst().getDefaultMessage();
            model.addAttribute("erro", mensagem);
            return HTML_CLIENTE_FORM;
        }
        clienteService.insert(pessoa, login, password);
        return "redirect:/" + CADASTRO_CLIENTE;
    }

    @PostMapping(SAVE_PJ)
    public String saveJuridica(@Valid PessoaJuridica pessoa,
                               String login,
                               String password,
                               BindingResult result,
                               Model model){
        if (result.hasErrors()) {
            String mensagem = result.getAllErrors().getFirst().getDefaultMessage();
            model.addAttribute("erro", mensagem);
            return HTML_CLIENTE_FORM;
        }
        clienteService.insert(pessoa, login, password);
        return "redirect:/" + CADASTRO_CLIENTE;
    }
}
