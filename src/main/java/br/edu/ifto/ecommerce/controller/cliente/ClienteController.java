package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.entity.cliente.PessoaFisica;
import br.edu.ifto.ecommerce.model.entity.cliente.PessoaJuridica;
import br.edu.ifto.ecommerce.model.repository.ClienteRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static br.edu.ifto.ecommerce.config.Diretorios.HTML_CLIENTE_FORM;
import static br.edu.ifto.ecommerce.config.Rotas.*;

@Transactional
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
    public String saveFisica(PessoaFisica pessoa){
        clienteRepository.insert(pessoa);
        return "redirect:/" + CADASTRO_CLIENTE;
    }

    @PostMapping(SAVE_PJ)
    public String saveJuridica(PessoaJuridica pessoa){
        clienteRepository.insert(pessoa);
        return "redirect:/" + CADASTRO_CLIENTE;
    }
}
