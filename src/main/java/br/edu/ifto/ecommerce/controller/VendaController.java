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
                       @RequestParam(required = false) Double valorMinimo,
                       @RequestParam(required = false) Double valorMaximo,
                       Model model) {

        List<Venda> vendas_banco = vendaRepository.findAllByDynamicFilters(nomeCliente, dataInicial, dataFinal);
        List<Venda> vendas = _filtrarPorValorTotal(vendas_banco, valorMinimo, valorMaximo);

        boolean hasValorMinimo = valorMinimo != null && valorMinimo > 0.0;
        boolean hasValorMaximo = valorMaximo != null && valorMaximo > 0.0;

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
        if (hasValorMinimo || hasValorMaximo) filtrosAplicados++;
        if (hasValorMinimo) model.addAttribute("valorMinimo", valorMinimo);
        if (hasValorMaximo) model.addAttribute("valorMaximo", valorMaximo);

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

    /*
    * Filtro de valor aplicado em memória pois o total é calculado
    * a partir dos itens. Para volumes maiores, considerar subquery HQL.
     */
    private List<Venda> _filtrarPorValorTotal(List<Venda> vendas, Double valorMinimo, Double valorMaximo) {
        Stream<Venda> stream = vendas.stream();
        if (valorMinimo != null && valorMinimo > 0.0) stream = _filtrarPorValorMinimo(stream, valorMinimo);
        if (valorMaximo != null && valorMaximo > 0.0) stream = _filtrarPorValorMaximo(stream, valorMaximo);
        return stream.toList();
    }

    private Stream<Venda> _filtrarPorValorMinimo(Stream<Venda> vendas, Double valorMinimo) {
        return vendas.filter(v -> v.total() >= valorMinimo);
    }

    private Stream<Venda> _filtrarPorValorMaximo(Stream<Venda> vendas, Double valorMaximo) {
        return vendas.filter(v -> v.total() <= valorMaximo);
    }
}
