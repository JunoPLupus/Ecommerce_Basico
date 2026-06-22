package br.edu.ifto.ecommerce.controller.cliente;

import br.edu.ifto.ecommerce.model.entity.venda.Venda;
import br.edu.ifto.ecommerce.service.CarrinhoService;
import br.edu.ifto.ecommerce.utils.mappers.VendaMapper;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

import static br.edu.ifto.ecommerce.utils.constants.Diretorios.HTML_CARRINHO;
import static br.edu.ifto.ecommerce.utils.constants.Rotas.*;

@Controller
@AllArgsConstructor
@RequestMapping(CARRINHO)
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @GetMapping("")
    public String verCarrinho(HttpSession session, Model model) {
        model.addAttribute(CARRINHO, VendaMapper.toDTO(getCarrinho(session)));
        return HTML_CARRINHO;
    }

    @PostMapping(INSERT_ID)
    public String adicionarItemCarrinho(@PathVariable("id") Long id, HttpSession session) {
        carrinhoService.adicionarItemNoCarrinho(getCarrinho(session), id);
        return "redirect:/" + PRODUTOS;
    }

    @PostMapping(ADD_ID)
    public String aumentarQtdItemCarrinho(@PathVariable("id") Long id, HttpSession session) {
        carrinhoService.adicionarItemNoCarrinho(getCarrinho(session), id);
        return "redirect:/" + CARRINHO;
    }

    @PostMapping(REDUCE_ID)
    public String reduzirQtdOuRemoverItemCarrinho(@PathVariable("id") Long id, HttpSession session) {
        carrinhoService.reduzirQtdItemNoCarrinho(getCarrinho(session), id);
        return "redirect:/" + CARRINHO;
    }

    @PostMapping(DELETE_ID)
    public String removerItemCarrinho(@PathVariable("id") Long id, HttpSession session) {
        carrinhoService.removerItemNoCarrinho(getCarrinho(session), id);
        return "redirect:/" + CARRINHO;
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
