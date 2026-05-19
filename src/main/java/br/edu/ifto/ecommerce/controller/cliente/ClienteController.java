package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.entity.cliente.PessoaFisica;
import br.edu.ifto.ecommerce.model.entity.cliente.PessoaJuridica;
import br.edu.ifto.ecommerce.model.repository.ClienteRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import static br.edu.ifto.ecommerce.config.Diretorios.HTML_CLIENTE_FORM;
import static br.edu.ifto.ecommerce.config.Rotas.*;

@Controller
@AllArgsConstructor
@RequestMapping(CLIENTES)
public class ClienteController {

    ClienteRepository clienteRepository;

    @GetMapping(CADASTRO)
    public String insert(){
        return HTML_CLIENTE_FORM;
    }

    @PostMapping(SAVE_PF)
    public String saveFisica(@Valid PessoaFisica pessoa, BindingResult result, Model model){
        if (result.hasErrors()) {
            String mensagem = result.getAllErrors().getFirst().getDefaultMessage();
            model.addAttribute("erro", mensagem);
            return HTML_CLIENTE_FORM;
        }
        clienteRepository.insert(pessoa);
        return "redirect:/" + CADASTRO_CLIENTE;
    }

    @PostMapping(SAVE_PJ)
    public String saveJuridica(@Valid PessoaJuridica pessoa, BindingResult result, Model model){
        if (result.hasErrors()) {
            String mensagem = result.getAllErrors().getFirst().getDefaultMessage();
            model.addAttribute("erro", mensagem);
            return HTML_CLIENTE_FORM;
        }
        clienteRepository.insert(pessoa);
        return "redirect:/" + CADASTRO_CLIENTE;
    }
}
