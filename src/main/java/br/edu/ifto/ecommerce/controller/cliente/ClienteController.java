package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.entity.cliente.PessoaFisica;
import br.edu.ifto.ecommerce.model.entity.cliente.PessoaJuridica;
import br.edu.ifto.ecommerce.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static br.edu.ifto.ecommerce.config.Rotas.*;

@Transactional
@Controller
@RequestMapping(CLIENTES)
public class ClienteController {
    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping(CADASTRO)
    public String insert(){
        return "cliente/form";
    }

    @PostMapping(SAVE_PF)
    public String saveFisica(PessoaFisica pessoa){
        clienteRepository.insert(pessoa);
        return "redirect:/clientes/cadastro";
    }

    @PostMapping(SAVE_PJ)
    public String saveJuridica(PessoaJuridica pessoa){
        clienteRepository.insert(pessoa);
        return "redirect:/clientes/cadastro";
    }
}
