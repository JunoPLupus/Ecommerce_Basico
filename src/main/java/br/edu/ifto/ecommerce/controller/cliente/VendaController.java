package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.entity.usuario.Usuario;
import br.edu.ifto.ecommerce.model.entity.venda.ItemVenda;
import br.edu.ifto.ecommerce.model.entity.venda.Venda;
import br.edu.ifto.ecommerce.model.repository.VendaRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

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

        vendaRepository.insert(carrinho);
        session.removeAttribute(CARRINHO);

        return "redirect:/" + PRODUTOS;
    }
}
