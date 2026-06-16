package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.dto.VendaDTO;
import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.entity.endereco.Endereco;
import br.edu.ifto.ecommerce.model.entity.venda.Venda;
import br.edu.ifto.ecommerce.model.enums.FormaPagamento;
import br.edu.ifto.ecommerce.model.record.BreadcrumbItem;
import br.edu.ifto.ecommerce.service.EnderecoService;
import br.edu.ifto.ecommerce.service.VendaService;
import br.edu.ifto.ecommerce.utils.VendaMapper;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static br.edu.ifto.ecommerce.utils.AutenticacaoUtils.getPessoaLogada;
import static br.edu.ifto.ecommerce.utils.BreadcrumbUtils.breadcrumb;
import static br.edu.ifto.ecommerce.utils.Diretorios.HTML_CLIENTE_DETAIL_PEDIDO;
import static br.edu.ifto.ecommerce.utils.Diretorios.HTML_CLIENTE_FINALIZAR_PEDIDO;
import static br.edu.ifto.ecommerce.utils.Diretorios.HTML_CLIENTE_LISTA_PEDIDOS;
import static br.edu.ifto.ecommerce.utils.Rotas.*;

@Controller
@AllArgsConstructor
@RequestMapping(PEDIDOS)
public class VendaController {

    private final VendaService vendaService;
    private final EnderecoService enderecoService;

    @GetMapping(FINALIZAR)
    public String revisarPedido(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Venda carrinho = (Venda) session.getAttribute(CARRINHO);

        if (carrinhoVazio(carrinho)) {
            redirectAttributes.addFlashAttribute("erro", "Seu carrinho está vazio.");
            return "redirect:/" + CARRINHO;
        }

        model.addAttribute(CARRINHO, VendaMapper.toDTO(carrinho));
        model.addAttribute("enderecos", enderecoService.listarResumoDoDono(getPessoaLogada().getId()));
        model.addAttribute("formasPagamento", FormaPagamento.values());
        model.addAttribute("breadcrumbItems", breadcrumb(
                new BreadcrumbItem("Carrinho", "/" + CARRINHO),
                new BreadcrumbItem("Finalizar Compra", null)
        ));
        return HTML_CLIENTE_FINALIZAR_PEDIDO;
    }

    @PostMapping(FINALIZAR)
    public String finalizarCompra(@RequestParam Long enderecoId,
                                  @RequestParam(required = false) FormaPagamento formaPagamento,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        Venda carrinho = (Venda) session.getAttribute(CARRINHO);

        if (carrinhoVazio(carrinho)) {
            redirectAttributes.addFlashAttribute("erro", "Seu carrinho está vazio.");
            return "redirect:/" + CARRINHO;
        }

        if (formaPagamento == null) {
            redirectAttributes.addFlashAttribute("erro", "Selecione uma forma de pagamento.");
            return "redirect:/" + PEDIDOS + FINALIZAR;
        }

        Pessoa pessoaLogada = getPessoaLogada();
        Optional<Endereco> endereco = enderecoService.buscarDoDono(enderecoId, pessoaLogada);

        if (endereco.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Selecione um endereço de entrega válido.");
            return "redirect:/" + PEDIDOS + FINALIZAR;
        }

        Venda vendaSalva = vendaService.finalizar(carrinho, endereco.get(), formaPagamento, pessoaLogada);
        session.removeAttribute(CARRINHO);

        return "redirect:/" + PEDIDOS + DETALHES + "/" + vendaSalva.getId();
    }

    @GetMapping(LISTA)
    public String meusPedidos(Model model) {
        model.addAttribute("pedidos", vendaService.listarPedidosDoCliente(getPessoaLogada().getId()));
        return HTML_CLIENTE_LISTA_PEDIDOS;
    }

    @GetMapping(DETALHES_ID)
    public String detalhesPedido(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        VendaDTO venda = vendaService.buscarPedidoDoCliente(id, getPessoaLogada().getId());

        if (venda == null) {
            redirectAttributes.addFlashAttribute("erro", "Pedido #" + id + " não encontrado.");
            return "redirect:/" + PEDIDOS + LISTA;
        }

        model.addAttribute("venda", venda);
        model.addAttribute("breadcrumbItems", breadcrumb(
                new BreadcrumbItem("Meus Pedidos", "/" + PEDIDOS + LISTA),
                new BreadcrumbItem("Pedido #" + venda.id(), null)
        ));
        return HTML_CLIENTE_DETAIL_PEDIDO;
    }

    private boolean carrinhoVazio(Venda carrinho) {
        return carrinho == null || carrinho.getItens().isEmpty();
    }
}
