package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.entity.endereco.Endereco;
import br.edu.ifto.ecommerce.model.enums.Estado;
import br.edu.ifto.ecommerce.model.record.BreadcrumbItem;
import br.edu.ifto.ecommerce.model.repository.EnderecoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;

import static br.edu.ifto.ecommerce.utils.AutenticacaoUtils.getPessoaLogada;
import static br.edu.ifto.ecommerce.utils.BreadcrumbUtils.breadcrumb;
import static br.edu.ifto.ecommerce.utils.Diretorios.*;
import static br.edu.ifto.ecommerce.utils.Rotas.*;

@Controller
@AllArgsConstructor
@RequestMapping(ENDERECOS)
public class EnderecoController {

    private final EnderecoRepository enderecoRepository;

    /**
     * @param endereco necessário devido utilizar no form.html o th:object que faz referência ao objeto esperado no controller.
     * @return html de cadastro de endereço
     */
    @GetMapping(INSERT)
    public String insert(Endereco endereco, ModelMap model, HttpServletRequest request) {
        model.addAttribute("estados", Estado.values());
        model.addAttribute("origem", origemRequisicao(request));
        model.addAttribute("breadcrumbItems", breadcrumb(
                new BreadcrumbItem("Meu Perfil", "/" + CLIENTES + PERFIL),
                new BreadcrumbItem("Cadastrar Endereço", null)
        ));
        return HTML_CLIENTE_FORM_ENDERECO;
    }

    @PostMapping(SAVE)
    public String save(@Valid Endereco endereco, BindingResult result, ModelMap model,
                        @RequestParam(value = "origem", required = false) String origem) {
        if (result.hasErrors()) {
            model.addAttribute("estados", Estado.values());
            model.addAttribute("origem", origem);
            return HTML_CLIENTE_FORM_ENDERECO;
        }

        endereco.setPessoa(getPessoaLogada());
        enderecoRepository.insert(endereco);
        return redirecionarParaOrigem(origem);
    }

    @GetMapping(EDIT_ID)
    public String edit(@PathVariable("id") Long id, ModelMap model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Endereco endereco = enderecoRepository.findById(id);

        if (endereco == null || !endereco.pertenceA(getPessoaLogada())) {
            redirectAttributes.addFlashAttribute("erro", "Endereço não encontrado.");
            return "redirect:/" + CLIENTES + PERFIL;
        }

        model.addAttribute("endereco", endereco);
        model.addAttribute("estados", Estado.values());
        model.addAttribute("origem", origemRequisicao(request));
        model.addAttribute("breadcrumbItems", breadcrumb(
                new BreadcrumbItem("Meu Perfil", "/" + CLIENTES + PERFIL),
                new BreadcrumbItem("Editar Endereço", null)
        ));
        return HTML_CLIENTE_FORM_ENDERECO;
    }

    @PostMapping(UPDATE)
    public String update(@Valid Endereco endereco, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes,
                          @RequestParam(value = "origem", required = false) String origem) {
        Endereco enderecoExistente = enderecoRepository.findById(endereco.getId());

        if (enderecoExistente == null || !enderecoExistente.pertenceA(getPessoaLogada())) {
            redirectAttributes.addFlashAttribute("erro", "Endereço não encontrado.");
            return "redirect:/" + CLIENTES + PERFIL;
        }

        if (result.hasErrors()) {
            model.addAttribute("estados", Estado.values());
            model.addAttribute("origem", origem);
            return HTML_CLIENTE_FORM_ENDERECO;
        }

        endereco.setPessoa(enderecoExistente.getPessoa());
        enderecoRepository.update(endereco);
        return redirecionarParaOrigem(origem);
    }

    @PostMapping(DELETE_ID)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Endereco endereco = enderecoRepository.findById(id);

        if (endereco == null || !endereco.pertenceA(getPessoaLogada())) {
            redirectAttributes.addFlashAttribute("erro", "Endereço não encontrado.");
            return "redirect:/" + CLIENTES + PERFIL;
        }

        boolean sucesso = enderecoRepository.delete(id);

        if (!sucesso) redirectAttributes.addFlashAttribute("erro", "Não é possível excluir! Existem pedidos associados a este endereço.");

        return "redirect:/" + CLIENTES + PERFIL;
    }

    /**
     * Extrai o caminho (path + query) da página que originou a requisição, a partir do header "Referer".
     * Usado para que o formulário de endereço retorne o usuário para a tela de onde ele veio.
     */
    private String origemRequisicao(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer == null || referer.isBlank()) return null;

        try {
            URI uri = URI.create(referer);
            String path = uri.getRawPath();
            if (path == null || path.isBlank()) return null;

            String query = uri.getRawQuery();
            return query != null ? path + "?" + query : path;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Monta o redirecionamento para a origem informada, validando que se trata de um caminho interno
     * (evitando open redirect). Caso a origem seja inválida ou ausente, retorna para "Meus Endereços".
     */
    private String redirecionarParaOrigem(String origem) {
        boolean origemValida = origem != null && origem.startsWith("/") && !origem.startsWith("//");
        return "redirect:" + (origemValida ? origem : "/" + CLIENTES + PERFIL);
    }
}
