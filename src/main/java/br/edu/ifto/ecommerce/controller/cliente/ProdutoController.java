package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.entity.produto.Produto;
import br.edu.ifto.ecommerce.model.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import static br.edu.ifto.ecommerce.config.Diretorios.HTML_CLIENTE_LISTA_PRODUTOS;
import static br.edu.ifto.ecommerce.config.Rotas.*;

@Transactional
@Controller
@AllArgsConstructor
@RequestMapping(PRODUTOS)
public class ProdutoController {
    private ProdutoRepository produtoRepository;

    @GetMapping({"", LISTA})
    public String listar(ModelMap model) {
        List<Produto> produtos = new ArrayList<>(
                produtoRepository.findAllByDynamicFilters(null, null, null));

        model.addAttribute("produtos", produtos);
        return HTML_CLIENTE_LISTA_PRODUTOS;
    }
}
