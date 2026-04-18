package br.edu.ifto.ecommerce.controller;

import br.edu.ifto.ecommerce.model.entity.cliente.PessoaFisica;
import br.edu.ifto.ecommerce.model.entity.cliente.PessoaJuridica;
import br.edu.ifto.ecommerce.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Transactional
@Controller
@RequestMapping("clientes")
public class ClienteController {
    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping("/cadastro")
    public String insert(){
        return "cliente/form";
    }

    @PostMapping("/save/fisica")
    public String saveFisica(PessoaFisica pessoa){
        clienteRepository.insert(pessoa);
        return "redirect:/clientes/cadastro";
    }

    @PostMapping("/save/juridica")
    public String saveJuridica(PessoaJuridica pessoa){
        clienteRepository.insert(pessoa);
        return "redirect:/clientes/cadastro";
    }
}
