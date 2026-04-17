package br.edu.ifto.ecommerce.controller;

import br.edu.ifto.ecommerce.model.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("venda", repository.findById(id));
        return "venda/detail";
    }
}
