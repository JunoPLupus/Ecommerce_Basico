package br.edu.ifto.ecommerce.service;

import br.edu.ifto.ecommerce.model.entity.produto.Produto;
import br.edu.ifto.ecommerce.model.entity.venda.ItemVenda;
import br.edu.ifto.ecommerce.model.entity.venda.Venda;
import br.edu.ifto.ecommerce.model.repository.ProdutoRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Transactional
@Service
@AllArgsConstructor
public class CarrinhoService {

    private final ProdutoRepository produtoRepository;
    public void adicionarItemNoCarrinho(Venda carrinho, Long id) throws Exception {

        Produto produto = getProduto(id);
        for(ItemVenda item : carrinho.getItens()) {
            if (Objects.equals(item.getProduto().getId(), produto.getId())) {
                item.setQuantidade(item.getQuantidade() + 1);
                return;
            }
        }
        ItemVenda itemCarrinho = new ItemVenda();
        itemCarrinho.setProduto(produto);
        itemCarrinho.setQuantidade(1);
        carrinho.getItens().add(itemCarrinho);
    }

    public void reduzirQtdItemNoCarrinho(Venda carrinho, Long id) throws Exception {
        Produto produto = getProduto(id);
        for(ItemVenda item : carrinho.getItens()) {
            if (Objects.equals(item.getProduto().getId(), produto.getId())) {

                if(item.getQuantidade() > 1) {
                    item.setQuantidade(item.getQuantidade() - 1);
                    return;
                }
            }
        }
        removerItemNoCarrinho(carrinho, id);
    }

    public void removerItemNoCarrinho(Venda carrinho, Long id) throws Exception {
        Produto produto = getProduto(id);
        carrinho.getItens().removeIf(item -> Objects.equals(item.getProduto().getId(), produto.getId()));
    }

    private Produto getProduto(Long id) throws Exception {
        Produto produto = produtoRepository.findById(id);
        if(produto == null) throw new Exception("Produto não encontrado.");
        return produto;
    }
}
