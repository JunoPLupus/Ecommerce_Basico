package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.entity.endereco.Endereco;
import br.edu.ifto.ecommerce.model.enums.Estado;
import br.edu.ifto.ecommerce.model.record.BreadcrumbItem;
import br.edu.ifto.ecommerce.model.repository.EnderecoRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static br.edu.ifto.ecommerce.utils.AutenticacaoUtils.getPessoaLogada;
import static br.edu.ifto.ecommerce.utils.BreadcrumbUtils.breadcrumb;
import static br.edu.ifto.ecommerce.utils.Diretorios.*;
import static br.edu.ifto.ecommerce.utils.Rotas.*;

@Controller
@AllArgsConstructor
@RequestMapping(ENDERECOS)
public class EnderecoController {

    private final EnderecoRepository enderecoRepository;

    @GetMapping({"", LISTA})
    public String listar(ModelMap model) {
        List<Endereco> enderecos = enderecoRepository.findAllByPessoaId(getPessoaLogada().getId());

        model.addAttribute("enderecos", enderecos);
        return HTML_CLIENTE_LISTA_ENDERECOS;
    }

    /**
     * @param endereco necessário devido utilizar no form.html o th:object que faz referência ao objeto esperado no controller.
     * @return html de cadastro de endereço
     */
    @GetMapping(INSERT)
    public String insert(Endereco endereco, ModelMap model) {
        model.addAttribute("estados", Estado.values());
        model.addAttribute("breadcrumbItems", breadcrumb(
                new BreadcrumbItem("Meus Endereços", "/" + ENDERECOS),
                new BreadcrumbItem("Cadastrar Endereço", null)
        ));
        return HTML_CLIENTE_FORM_ENDERECO;
    }

    @PostMapping(SAVE)
    public String save(@Valid Endereco endereco, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("estados", Estado.values());
            return HTML_CLIENTE_FORM_ENDERECO;
        }

        endereco.setPessoa(getPessoaLogada());
        enderecoRepository.insert(endereco);
        return "redirect:/" + ENDERECOS;
    }

    @GetMapping(EDIT_ID)
    public String edit(@PathVariable("id") Long id, ModelMap model, RedirectAttributes redirectAttributes) {
        Endereco endereco = enderecoRepository.findById(id);

        if (endereco == null || !endereco.pertenceA(getPessoaLogada())) {
            redirectAttributes.addFlashAttribute("erro", "Endereço não encontrado.");
            return "redirect:/" + ENDERECOS;
        }

        model.addAttribute("endereco", endereco);
        model.addAttribute("estados", Estado.values());
        model.addAttribute("breadcrumbItems", breadcrumb(
                new BreadcrumbItem("Meus Endereços", "/" + ENDERECOS),
                new BreadcrumbItem("Editar Endereço", null)
        ));
        return HTML_CLIENTE_FORM_ENDERECO;
    }

    @PostMapping(UPDATE)
    public String update(@Valid Endereco endereco, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) {
        Endereco enderecoExistente = enderecoRepository.findById(endereco.getId());

        if (enderecoExistente == null || !enderecoExistente.pertenceA(getPessoaLogada())) {
            redirectAttributes.addFlashAttribute("erro", "Endereço não encontrado.");
            return "redirect:/" + ENDERECOS;
        }

        if (result.hasErrors()) {
            model.addAttribute("estados", Estado.values());
            return HTML_CLIENTE_FORM_ENDERECO;
        }

        endereco.setPessoa(enderecoExistente.getPessoa());
        enderecoRepository.update(endereco);
        return "redirect:/" + ENDERECOS;
    }

    @PostMapping(DELETE_ID)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Endereco endereco = enderecoRepository.findById(id);

        if (endereco == null || !endereco.pertenceA(getPessoaLogada())) {
            redirectAttributes.addFlashAttribute("erro", "Endereço não encontrado.");
            return "redirect:/" + ENDERECOS;
        }

        boolean sucesso = enderecoRepository.delete(id);

        if (!sucesso) redirectAttributes.addFlashAttribute("erro", "Não é possível excluir! Existem pedidos associados a este endereço.");

        return "redirect:/" + ENDERECOS;
    }
}
