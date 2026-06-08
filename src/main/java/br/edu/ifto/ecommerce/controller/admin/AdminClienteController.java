package br.edu.ifto.ecommerce.controller.admin;

import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.entity.venda.Venda;
import br.edu.ifto.ecommerce.model.record.BreadcrumbItem;
import br.edu.ifto.ecommerce.model.repository.ClienteRepository;
import br.edu.ifto.ecommerce.model.repository.VendaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static br.edu.ifto.ecommerce.utils.Diretorios.*;
import static br.edu.ifto.ecommerce.utils.Rotas.*;
import static br.edu.ifto.ecommerce.utils.BreadcrumbUtils.breadcrumb;

@Controller
@AllArgsConstructor
@RequestMapping(ADMIN_CLIENTES)
public class AdminClienteController {

    ClienteRepository clienteRepository;
    VendaRepository vendaRepository;

    @GetMapping("")
    public String list(@RequestParam(required = false) String nome,
                       Model model){
        List<Pessoa> clientes;
        int filtrosAplicados = 0;

        if(nome != null && !nome.isEmpty()){
            clientes = clienteRepository.findAllByNomeOuRazaoSocial(nome);
            model.addAttribute("nome", nome);
            model.addAttribute("filtrosAplicados", ++filtrosAplicados);
        } else {
            clientes = clienteRepository.findAll();
        }

        model.addAttribute("clientes", clientes);
        return HTML_ADMIN_LISTA_CLIENTES;
    }

    @GetMapping(DETALHES_ID)
    public String detail(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes){
        Pessoa cliente = clienteRepository.findById(id);

        if (cliente == null) {
            redirectAttributes.addFlashAttribute("erro", "Cliente #" + id + " não encontrado.");
            return "redirect:/" + ADMIN_CLIENTES;
        }

        List<Venda> vendas = vendaRepository.findAllByClienteId(id);

        model.addAttribute("cliente", cliente);
        model.addAttribute("vendas", vendas);
        model.addAttribute("breadcrumbItems", breadcrumb(
                new BreadcrumbItem("Clientes", "/" + ADMIN_CLIENTES),
                new BreadcrumbItem("Detalhes do cliente #" + id, null)
        ));

        return HTML_ADMIN_DETAIL_CLIENTES;
    }
}
