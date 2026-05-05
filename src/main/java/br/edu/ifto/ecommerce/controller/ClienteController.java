package br.edu.ifto.ecommerce.controller;

import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.entity.cliente.PessoaFisica;
import br.edu.ifto.ecommerce.model.entity.cliente.PessoaJuridica;
import br.edu.ifto.ecommerce.model.entity.venda.Venda;
import br.edu.ifto.ecommerce.model.record.BreadcrumbItem;
import br.edu.ifto.ecommerce.model.repository.ClienteRepository;
import br.edu.ifto.ecommerce.model.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static br.edu.ifto.ecommerce.utils.BreadcrumbUtils.breadcrumb;

@Transactional
@Controller
@RequestMapping("clientes")
public class ClienteController {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    VendaRepository vendaRepository;

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

    @GetMapping("")
    public String list(@RequestParam(required = false) String nome,
                       Model model){
        List<Pessoa> clientes;
        int filtrosAplicados = 0;

        if(nome != null && !nome.isEmpty()){
            clientes = clienteRepository.findAllByNomeOuRazaoSocial(nome);
            model.addAttribute("nome", nome);
            model.addAttribute("filtrosAplicados", ++filtrosAplicados);
        } else {
            clientes = clienteRepository.findAll();
        }

        model.addAttribute("clientes", clientes);
        return "cliente/list";
    }

    @GetMapping("/detalhes/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        Pessoa cliente = clienteRepository.findById(id);
        List<Venda> vendas = vendaRepository.findAllByClienteId(id);

        model.addAttribute("cliente", cliente);
        model.addAttribute("vendas", vendas);
        model.addAttribute("breadcrumbItems", breadcrumb(
                new BreadcrumbItem("Clientes", "/clientes"),
                new BreadcrumbItem("Detalhes do cliente #" + id, null)
        ));

        return "cliente/detail";
    }
}
