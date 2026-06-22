package br.edu.ifto.ecommerce.controller.admin;

import br.edu.ifto.ecommerce.model.dto.ClienteResumoDTO;
import br.edu.ifto.ecommerce.model.dto.PessoaDTO;
import br.edu.ifto.ecommerce.model.record.BreadcrumbItem;
import br.edu.ifto.ecommerce.service.ClienteService;
import br.edu.ifto.ecommerce.service.VendaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static br.edu.ifto.ecommerce.utils.constants.Diretorios.*;
import static br.edu.ifto.ecommerce.utils.constants.Rotas.*;
import static br.edu.ifto.ecommerce.utils.functions.BreadcrumbUtils.breadcrumb;

@Controller
@AllArgsConstructor
@RequestMapping(ADMIN_CLIENTES)
public class AdminClienteController {

    private final ClienteService clienteService;
    private final VendaService vendaService;

    @GetMapping("")
    public String list(@RequestParam(required = false) String nome, Model model) {
        List<ClienteResumoDTO> clientes = clienteService.listar(nome);

        if (nome != null && !nome.isEmpty()) {
            model.addAttribute("nome", nome);
            model.addAttribute("filtrosAplicados", 1);
        }

        model.addAttribute("clientes", clientes);
        return HTML_ADMIN_LISTA_CLIENTES;
    }

    @GetMapping(DETALHES_ID)
    public String detail(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        PessoaDTO cliente = clienteService.buscarDetalhe(id);

        if (cliente == null) {
            redirectAttributes.addFlashAttribute("erro", "Cliente #" + id + " não encontrado.");
            return "redirect:/" + ADMIN_CLIENTES;
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("login", clienteService.buscarLoginDoCliente(id));
        model.addAttribute("vendas", vendaService.listarPedidosDoCliente(id));
        model.addAttribute("breadcrumbItems", breadcrumb(
                new BreadcrumbItem("Clientes", "/" + ADMIN_CLIENTES),
                new BreadcrumbItem("Detalhes do cliente #" + id, null)
        ));

        return HTML_ADMIN_DETAIL_CLIENTES;
    }
}
