package br.edu.ifto.ecommerce.controller;

import br.edu.ifto.ecommerce.model.entity.produto.Produto;
import br.edu.ifto.ecommerce.model.record.BreadcrumbItem;
import br.edu.ifto.ecommerce.model.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static br.edu.ifto.ecommerce.utils.BreadcrumbUtils.*;

@Transactional
@Controller
@RequestMapping("produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping({"", "/list"})
    public String listar(ModelMap model) {
        model.addAttribute("produtos", produtoRepository.findAll());
        return "produto/list";
    }

    /**
     * @param produto necessário devido utilizar no form.html o th:object que faz referência ao objeto esperado no controller.
     * @return html de cadastro de produto
     */
    @GetMapping("/insert")
    public String insert(Produto produto, ModelMap model){
        model.addAttribute("breadcrumbItems", breadcrumb(
                new BreadcrumbItem("Produtos", "/produtos"),
                new BreadcrumbItem("Cadastrar Produto", null)
        ));
        return "produto/form";
    }

    @PostMapping("/save")
    public String save(Produto produto){
        produtoRepository.insert(produto);
        return "redirect:/produtos";
    }

    /**
     * @param id do produto a ser editado
     * @return html de edição de produto
     * @PathVariable é utilizado quando o valor da variável é passada diretamente na URL
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("produto", produtoRepository.findById(id));
        model.addAttribute("breadcrumbItems", breadcrumb(
                new BreadcrumbItem("Produtos", "/produtos"),
                new BreadcrumbItem("Editar Produto", null)
        ));
        return "produto/form";
    }

    @PostMapping("/update")
    public String update(Produto produto) {
        produtoRepository.update(produto);
        return "redirect:/produtos";
    }

    /**
     * @param id do produto a ser removido
     * @return redirecionamento para a listagem de produtos
     * @PathVariable é utilizado quando o valor da variável é passada diretamente na URL
     */
    @PostMapping("/remove/{id}")
    public String remove(@PathVariable("id") Long id){
        produtoRepository.delete(id);
        return "redirect:/produtos";
    }
}
