package br.edu.ifto.ecommerce.controller;

import br.edu.ifto.ecommerce.model.entity.venda.Venda;
import br.edu.ifto.ecommerce.model.record.BreadcrumbItem;
import br.edu.ifto.ecommerce.model.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static br.edu.ifto.ecommerce.utils.BreadcrumbUtils.*;

@Transactional
@Controller
@RequestMapping("vendas")
public class VendaController {

    @Autowired
    private VendaRepository repository;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("vendas", repository.findAll());
        return "venda/list";
    }

    @GetMapping("/detalhes/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Venda venda = repository.findById(id);

        model.addAttribute("venda", venda);
        model.addAttribute("breadcrumbItems", breadcrumb(
                new BreadcrumbItem("Vendas", "/vendas"),
                new BreadcrumbItem("Detalhes da venda #" + venda.getId(), null)
        ));

        return "venda/detail";
    }
}
