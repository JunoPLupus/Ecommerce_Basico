package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.entity.venda.ItemVenda;
import br.edu.ifto.ecommerce.model.entity.venda.Venda;
import br.edu.ifto.ecommerce.model.repository.ClienteRepository;
import br.edu.ifto.ecommerce.model.repository.VendaRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

import static br.edu.ifto.ecommerce.config.Rotas.*;

@Transactional
@Controller
@AllArgsConstructor
@RequestMapping(PEDIDOS)
public class VendaController {
    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;

    @PostMapping(INSERT)
    public String finalizarCompra(@RequestParam("clienteId") Long clienteId, HttpSession session) {
        Venda carrinho = (Venda) session.getAttribute(CARRINHO);

        Pessoa cliente = clienteRepository.findById(clienteId);
        carrinho.setCliente(cliente);
        carrinho.setData(LocalDateTime.now());

        for (ItemVenda item : carrinho.getItens()) {
            item.setVenda(carrinho);
        }

        vendaRepository.insert(carrinho);
        session.removeAttribute(CARRINHO);

        return "redirect:/admin/clientes/detalhes/" + clienteId;
    }
}
