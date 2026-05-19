package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.entity.venda.Venda;
import br.edu.ifto.ecommerce.model.repository.ClienteRepository;
import br.edu.ifto.ecommerce.service.CarrinhoService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import static br.edu.ifto.ecommerce.config.Diretorios.HTML_CARRINHO;
import static br.edu.ifto.ecommerce.config.Rotas.*;

@Controller
@AllArgsConstructor
@RequestMapping(CARRINHO)
public class CarrinhoController {

    private CarrinhoService carrinhoService;
    private ClienteRepository clienteRepository;

    @GetMapping("")
    public String verCarrinho(HttpSession session, Model model) {
        Venda carrinho = getCarrinho(session);
        model.addAttribute(CARRINHO, carrinho);
        List<Pessoa> clientes = clienteRepository.findAll();
        model.addAttribute("clientes", clientes);
        return HTML_CARRINHO;
    }

    @PostMapping(INSERT_ID)
    public String adicionarItemCarrinho(@PathVariable("id") Long id, HttpSession session) {
        auxAddItemNoCarrinho(id, session);
        return "redirect:/" + PRODUTOS;
    }

    @PostMapping(ADD_ID)
    public String aumentarQtdItemCarrinho(@PathVariable("id") Long id, HttpSession session) {
        auxAddItemNoCarrinho(id, session);
        return "redirect:/" + CARRINHO;
    }

    @PostMapping(REDUCE_ID)
    public String reduzirQtdOuRemoverItemCarrinho(@PathVariable("id") Long id, HttpSession session) throws Exception {
        Venda carrinho = getCarrinho(session);
        carrinhoService.reduzirQtdItemNoCarrinho(carrinho, id);
        return "redirect:/" + CARRINHO;
    }

    @PostMapping(DELETE_ID)
    public String removerItemCarrinho(@PathVariable("id") Long id, HttpSession session) throws Exception {
        Venda carrinho = getCarrinho(session);
        carrinhoService.removerItemNoCarrinho(carrinho,id);
        return "redirect:/" + CARRINHO;
    }

    private void auxAddItemNoCarrinho(Long id, HttpSession session) {
        try {
            Venda carrinho = getCarrinho(session);
            carrinhoService.adicionarItemNoCarrinho(carrinho, id);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Venda getCarrinho(HttpSession session) {
        if (session.getAttribute(CARRINHO) == null) {
            Venda carrinho = new Venda();
            carrinho.setItens(new ArrayList<>());
            session.setAttribute(CARRINHO, carrinho);
        }
        return (Venda) session.getAttribute(CARRINHO);
    }
}
