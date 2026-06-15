package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.entity.endereco.Endereco;
import br.edu.ifto.ecommerce.model.entity.venda.ItemVenda;
import br.edu.ifto.ecommerce.model.entity.venda.Venda;
import br.edu.ifto.ecommerce.model.enums.FormaPagamento;
import br.edu.ifto.ecommerce.model.repository.EnderecoRepository;
import br.edu.ifto.ecommerce.model.repository.VendaRepository;
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

import java.time.LocalDateTime;
import java.util.List;

import br.edu.ifto.ecommerce.model.record.BreadcrumbItem;
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
    private final VendaRepository vendaRepository;
    private final EnderecoRepository enderecoRepository;

    @GetMapping(FINALIZAR)
    public String revisarPedido(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Venda carrinho = (Venda) session.getAttribute(CARRINHO);

        if (carrinho == null || carrinho.getItens().isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Seu carrinho está vazio.");
            return "redirect:/" + CARRINHO;
        }

        model.addAttribute(CARRINHO, carrinho);
        model.addAttribute("enderecos", enderecoRepository.findAllByPessoaId(getPessoaLogada().getId()));
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

        if (carrinho == null || carrinho.getItens().isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Seu carrinho está vazio.");
            return "redirect:/" + CARRINHO;
        }

        if (formaPagamento == null) {
            redirectAttributes.addFlashAttribute("erro", "Selecione uma forma de pagamento.");
            return "redirect:/" + PEDIDOS + FINALIZAR;
        }

        Pessoa pessoaLogada = getPessoaLogada();
        Endereco endereco = enderecoRepository.findById(enderecoId);

        if (endereco == null || !endereco.pertenceA(pessoaLogada)) {
            redirectAttributes.addFlashAttribute("erro", "Selecione um endereço de entrega válido.");
            return "redirect:/" + PEDIDOS + FINALIZAR;
        }

        carrinho.setCliente(pessoaLogada);
        carrinho.setEndereco(endereco);
        carrinho.setFormaPagamento(formaPagamento);
        carrinho.setData(LocalDateTime.now());

        for (ItemVenda item : carrinho.getItens()) {
            item.setVenda(carrinho);
        }

        Venda vendaSalva = vendaRepository.insert(carrinho);
        session.removeAttribute(CARRINHO);

        return "redirect:/" + PEDIDOS + DETALHES + "/" + vendaSalva.getId();
    }

    @GetMapping(LISTA)
    public String meusPedidos(Model model) {
        List<Venda> pedidos = vendaRepository.findAllByClienteId(getPessoaLogada().getId());
        model.addAttribute("pedidos", pedidos);
        return HTML_CLIENTE_LISTA_PEDIDOS;
    }

    @GetMapping(DETALHES_ID)
    public String detalhesPedido(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Venda venda = vendaRepository.findById(id);

        if (venda == null) {
            redirectAttributes.addFlashAttribute("erro", "Pedido #" + id + " não encontrado.");
            return "redirect:/" + PEDIDOS + LISTA;
        }

        model.addAttribute("venda", venda);
        model.addAttribute("breadcrumbItems", breadcrumb(
                new BreadcrumbItem("Meus Pedidos", "/" + PEDIDOS + LISTA),
                new BreadcrumbItem("Pedido #" + venda.getId(), null)
        ));
        return HTML_CLIENTE_DETAIL_PEDIDO;
    }
}
