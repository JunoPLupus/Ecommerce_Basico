package br.edu.ifto.ecommerce.controller.admin;

import br.edu.ifto.ecommerce.model.dto.ProdutoDTO;
import br.edu.ifto.ecommerce.model.entity.produto.Produto;
import br.edu.ifto.ecommerce.model.record.BreadcrumbItem;
import br.edu.ifto.ecommerce.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static br.edu.ifto.ecommerce.utils.Diretorios.*;
import static br.edu.ifto.ecommerce.utils.Rotas.*;
import static br.edu.ifto.ecommerce.utils.BreadcrumbUtils.*;

@Controller
@AllArgsConstructor
@RequestMapping(ADMIN_PRODUTOS)
public class AdminProdutoController {

    private final ProdutoService produtoService;

    @GetMapping({"", LISTA})
    public String listar(@RequestParam(required = false) String descricao,
                         @RequestParam(required = false) Double precoMinimo,
                         @RequestParam(required = false) Double precoMaximo,
                         ModelMap model) {

        // TODO: ajustar filtro de pesquisa para aceitar descrição ou id como input
        List<ProdutoDTO> produtos = produtoService.listarComFiltros(descricao, precoMinimo, precoMaximo);
        int filtrosAplicados = 0;

        if (descricao != null) {
            model.addAttribute("descricao", descricao);
            filtrosAplicados++;
        }
        if (precoMinimo != null || precoMaximo != null) filtrosAplicados++;
        if (precoMinimo != null) model.addAttribute("precoMinimo", precoMinimo);
        if (precoMaximo != null) model.addAttribute("precoMaximo", precoMaximo);

        if (filtrosAplicados > 0) model.addAttribute("filtrosAplicados", filtrosAplicados);
        model.addAttribute("produtos", produtos);
        return HTML_ADMIN_LISTA_PRODUTOS;
    }

    /**
     * @param produto necessário devido utilizar no form.html o th:object que faz referência ao objeto esperado no controller.
     * @return html de cadastro de produto
     */
    @GetMapping(INSERT)
    public String insert(Produto produto, ModelMap model) {
        model.addAttribute("breadcrumbItems", breadcrumb(
                new BreadcrumbItem("Produtos", "/" + ADMIN_PRODUTOS),
                new BreadcrumbItem("Cadastrar Produto", null)
        ));
        return HTML_ADMIN_FORM_PRODUTOS;
    }

    @PostMapping(SAVE)
    public String save(@Valid Produto produto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return HTML_ADMIN_FORM_PRODUTOS;
        }
        produtoService.salvar(produto);
        return "redirect:/" + ADMIN_PRODUTOS;
    }

    /**
     * @param id do produto a ser editado
     * @return html de edição de produto
     */
    @GetMapping(EDIT_ID)
    public String edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("produto", produtoService.buscarPorId(id));
        model.addAttribute("breadcrumbItems", breadcrumb(
                new BreadcrumbItem("Produtos", "/" + ADMIN_PRODUTOS),
                new BreadcrumbItem("Editar Produto", null)
        ));
        return HTML_ADMIN_FORM_PRODUTOS;
    }

    @PostMapping(UPDATE)
    public String update(@Valid Produto produto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return HTML_ADMIN_FORM_PRODUTOS;
        }
        produtoService.atualizar(produto);
        return "redirect:/" + ADMIN_PRODUTOS;
    }

    /**
     * @param id do produto a ser removido
     * @return redirecionamento para a listagem de produtos
     */
    @PostMapping(DELETE_ID)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean sucesso = produtoService.excluir(id);

        if (!sucesso) redirectAttributes.addFlashAttribute("erro", "Não é possível excluir! Existem vendas associadas a este produto.");

        return "redirect:/" + ADMIN_PRODUTOS;
    }
}
