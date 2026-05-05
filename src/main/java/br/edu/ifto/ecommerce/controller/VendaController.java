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
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static br.edu.ifto.ecommerce.utils.BreadcrumbUtils.*;

@Transactional
@Controller
@RequestMapping("vendas")
public class VendaController {

    @Autowired
    private VendaRepository vendaRepository;

    @GetMapping("")
    public String list(@RequestParam(required = false) String nomeCliente,
                       @RequestParam(required = false) LocalDate dataInicial,
                       @RequestParam(required = false) LocalDate dataFinal,
                       Model model) {

        List<Venda> vendas = vendaRepository.findAllByDynamicFilters(nomeCliente, dataInicial, dataFinal);

        int filtrosAplicados = 0;
        if (nomeCliente != null) {
            model.addAttribute("nomeCliente", nomeCliente);
            filtrosAplicados++;
        }
        if (dataInicial != null) {
            model.addAttribute("dataInicial", dataInicial);
            filtrosAplicados++;
        }
        if (dataFinal != null) model.addAttribute("dataFinal", dataFinal);
        if(filtrosAplicados > 0) model.addAttribute("filtrosAplicados", filtrosAplicados);
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
