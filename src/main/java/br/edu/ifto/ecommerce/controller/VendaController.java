package br.edu.ifto.ecommerce.controller;

import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.entity.venda.Venda;
import br.edu.ifto.ecommerce.model.record.BreadcrumbItem;
import br.edu.ifto.ecommerce.model.repository.ClienteRepository;
import br.edu.ifto.ecommerce.model.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.edu.ifto.ecommerce.utils.BreadcrumbUtils.*;

@Transactional
@Controller
@RequestMapping("vendas")
public class VendaController {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("")
    public String list(@RequestParam(required = false) String nomeCliente,
                       @RequestParam(required = false) LocalDateTime dataInicio,
                       @RequestParam(required = false) LocalDateTime dataFinal,
                       Model model) {
        List<Venda> vendas = new ArrayList<>();

        if(nomeCliente != null && !nomeCliente.isBlank()){
            // TODO: Tem que encontrar cliente por nome
            List<Pessoa> clientes = clienteRepository.findAllByNome(nomeCliente);

            for(Pessoa cliente : clientes){
                vendas.addAll(vendaRepository.findAllByCliente(cliente));
            }
        }

        // TODO: Fazer a lógica de buscar vendas por data isolada ou intervalo

        if ((nomeCliente == null || nomeCliente.isBlank()) && dataInicio == null && dataFinal == null) { vendas = vendaRepository.findAll(); }

        model.addAttribute("vendas", vendas);
        return "venda/list";
    }

    @GetMapping("/detalhes/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Venda venda = vendaRepository.findById(id);

        model.addAttribute("venda", venda);
        model.addAttribute("breadcrumbItems", breadcrumb(
                new BreadcrumbItem("Vendas", "/vendas"),
                new BreadcrumbItem("Detalhes da venda #" + venda.getId(), null)
        ));

        return "venda/detail";
    }
}
