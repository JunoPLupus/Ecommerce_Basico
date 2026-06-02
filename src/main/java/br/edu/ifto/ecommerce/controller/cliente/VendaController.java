package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.entity.usuario.Usuario;
import br.edu.ifto.ecommerce.model.entity.venda.ItemVenda;
import br.edu.ifto.ecommerce.model.entity.venda.Venda;
import br.edu.ifto.ecommerce.model.repository.VendaRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

import static br.edu.ifto.ecommerce.utils.Diretorios.HTML_CLIENTE_DETAIL_PEDIDO;
import static br.edu.ifto.ecommerce.utils.Diretorios.HTML_CLIENTE_LISTA_PEDIDOS;
import static br.edu.ifto.ecommerce.utils.Rotas.*;

@Controller
@AllArgsConstructor
@RequestMapping(PEDIDOS)
public class VendaController {
    private final VendaRepository vendaRepository;

    @PostMapping(INSERT)
    public String finalizarCompra(HttpSession session) {
        Venda carrinho = (Venda) session.getAttribute(CARRINHO);

        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        carrinho.setCliente(usuarioLogado.getPessoa());
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
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Venda> pedidos = vendaRepository.findAllByClienteId(usuarioLogado.getPessoa().getId());
        model.addAttribute("pedidos", pedidos);
        return HTML_CLIENTE_LISTA_PEDIDOS;
    }

    @GetMapping(DETALHES_ID)
    public String detalhesPedido(@PathVariable("id") Long id, Model model) {
        Venda venda = vendaRepository.findById(id);
        model.addAttribute("venda", venda);
        return HTML_CLIENTE_DETAIL_PEDIDO;
    }
}
