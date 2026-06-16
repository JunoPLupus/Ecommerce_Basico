package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.dto.EditarPerfilDTO;
import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.entity.usuario.Usuario;
import br.edu.ifto.ecommerce.model.record.BreadcrumbItem;
import br.edu.ifto.ecommerce.service.EnderecoService;
import br.edu.ifto.ecommerce.service.PerfilService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static br.edu.ifto.ecommerce.utils.AutenticacaoUtils.getPessoaLogada;
import static br.edu.ifto.ecommerce.utils.AutenticacaoUtils.getUsuarioLogado;
import static br.edu.ifto.ecommerce.utils.BreadcrumbUtils.breadcrumb;
import static br.edu.ifto.ecommerce.utils.Diretorios.*;
import static br.edu.ifto.ecommerce.utils.Rotas.*;

@Controller
@AllArgsConstructor
@RequestMapping(CLIENTES)
public class PerfilController {

    private static final int SENHA_MIN = Usuario.SENHA_TAMANHO_MIN;

    private final PerfilService perfilService;
    private final EnderecoService enderecoService;

    @GetMapping(PERFIL)
    public String perfil(Model model) {
        Long pessoaId = getPessoaLogada().getId();
        model.addAttribute("pessoa", perfilService.buscarPerfil(pessoaId));
        model.addAttribute("enderecos", enderecoService.listarResumoDoDono(pessoaId));
        return HTML_CLIENTE_PERFIL;
    }

    @GetMapping(EDITAR)
    public String editar(Model model) {
        model.addAttribute("editarPerfilDTO", perfilService.buscarParaEdicao(getPessoaLogada().getId()));
        model.addAttribute("ehPessoaFisica", getPessoaLogada().isPF());
        model.addAttribute("breadcrumbEditar", breadcrumbEditar());
        return HTML_CLIENTE_EDITAR_PERFIL;
    }

    @PostMapping(EDITAR + SAVE)
    public String salvarEdicao(@Valid EditarPerfilDTO editarPerfilDTO, BindingResult result, Model model) {
        validarSenha(editarPerfilDTO, result);
        validarTelefone(editarPerfilDTO, result);

        if (result.hasErrors()) {
            model.addAttribute("ehPessoaFisica", getPessoaLogada().isPF());
            model.addAttribute("breadcrumbEditar", breadcrumbEditar());
            return HTML_CLIENTE_EDITAR_PERFIL;
        }

        perfilService.atualizar(editarPerfilDTO, getPessoaLogada().getId(), getUsuarioLogado().getId());
        return "redirect:/" + CLIENTES + PERFIL;
    }

    private void validarSenha(EditarPerfilDTO dto, BindingResult result) {
        String senha = dto.getSenha();
        if (senha != null && !senha.isBlank() && senha.length() < SENHA_MIN) {
            result.rejectValue("senha", "erro.usuario.senha.tamanho.min",
                    "A senha deve conter no mínimo " + SENHA_MIN + " caracteres.");
        }
    }

    /**
     * Telefone é opcional; quando informado, deve casar com {@link Pessoa#TELEFONE_REGEX}
     * (DDD de 2 ou 3 dígitos + número de 8 ou 9 dígitos, com separadores opcionais).
     */
    private void validarTelefone(EditarPerfilDTO dto, BindingResult result) {
        String telefone = dto.getTelefone();
        if (telefone == null || telefone.isBlank()) return;

        if (!telefone.trim().matches(Pessoa.TELEFONE_REGEX)) {
            result.rejectValue("telefone", "erro.pessoa.telefone.invalido",
                    "Telefone inválido. Use DDD e número, ex.: (63) 99999-8888.");
        }
    }

    private List<BreadcrumbItem> breadcrumbEditar() {
        return breadcrumb(
                new BreadcrumbItem("Meu Perfil", "/" + CLIENTES + PERFIL),
                new BreadcrumbItem("Editar Dados Pessoais", null)
        );
    }
}
