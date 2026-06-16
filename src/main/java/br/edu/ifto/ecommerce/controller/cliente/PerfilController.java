package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.dto.EditarPerfilDTO;
import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.record.BreadcrumbItem;
import br.edu.ifto.ecommerce.model.repository.ClienteRepository;
import br.edu.ifto.ecommerce.service.PerfilService;
import br.edu.ifto.ecommerce.utils.BreadcrumbUtils;
import br.edu.ifto.ecommerce.utils.PerfilMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static br.edu.ifto.ecommerce.utils.AutenticacaoUtils.getPessoaLogada;
import static br.edu.ifto.ecommerce.utils.AutenticacaoUtils.getUsuarioLogado;
import static br.edu.ifto.ecommerce.utils.Diretorios.*;
import static br.edu.ifto.ecommerce.utils.Rotas.*;

@Controller
@AllArgsConstructor
@RequestMapping(CLIENTES)
public class PerfilController {

    private final ClienteRepository clienteRepository;
    private final PerfilService perfilService;

    @GetMapping(PERFIL)
    public String perfil(Model model) {
        Pessoa pessoa = clienteRepository.findById(getPessoaLogada().getId());
        model.addAttribute("pessoa", pessoa);
        return HTML_CLIENTE_PERFIL;
    }

    @GetMapping(EDITAR)
    public String editar(Model model) {
        Pessoa pessoa = clienteRepository.findById(getPessoaLogada().getId());
        model.addAttribute("editarPerfilDTO", PerfilMapper.toDTO(pessoa));
        model.addAttribute("breadcrumbEditar", BreadcrumbUtils.breadcrumb(
                new BreadcrumbItem("Meu Perfil", "/" + CLIENTES + PERFIL),
                new BreadcrumbItem("Editar Dados Pessoais", null)
        ));
        return HTML_CLIENTE_EDITAR_PERFIL;
    }

    @PostMapping(EDITAR + SAVE)
    public String salvarEdicao(@Valid EditarPerfilDTO editarPerfilDTO, BindingResult result, Model model) {
        if (editarPerfilDTO.getSenha() != null
                && !editarPerfilDTO.getSenha().isBlank()
                && editarPerfilDTO.getSenha().length() < 8) {
            result.rejectValue("senha", "size", "Senha deve ter no mínimo 8 caracteres");
        }

        if (result.hasErrors()) {
            model.addAttribute("breadcrumbEditar", BreadcrumbUtils.breadcrumb(
                    new BreadcrumbItem("Meu Perfil", "/" + CLIENTES + PERFIL),
                    new BreadcrumbItem("Editar Dados Pessoais", null)
            ));
            return HTML_CLIENTE_EDITAR_PERFIL;
        }

        perfilService.atualizar(editarPerfilDTO, getPessoaLogada().getId(), getUsuarioLogado().getId());
        return "redirect:/" + CLIENTES + PERFIL;
    }
}
