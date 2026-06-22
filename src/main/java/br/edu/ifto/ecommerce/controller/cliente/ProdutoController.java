package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static br.edu.ifto.ecommerce.utils.constants.Diretorios.HTML_CLIENTE_LISTA_PRODUTOS;
import static br.edu.ifto.ecommerce.utils.constants.Rotas.*;

@Controller
@AllArgsConstructor
@RequestMapping(PRODUTOS)
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping({"", LISTA})
    public String listar(ModelMap model) {
        model.addAttribute("produtos", produtoService.listarTodos());
        return HTML_CLIENTE_LISTA_PRODUTOS;
    }
}
