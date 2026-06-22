package br.edu.ifto.ecommerce.service;

import br.edu.ifto.ecommerce.exception.RecursoNaoEncontradoException;
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

    /**
     * Adiciona um produto ao carrinho. Se o produto já estiver presente, apenas
     * incrementa a quantidade do item correspondente.
     *
     * @param carrinho carrinho (venda em aberto) a ser modificado.
     * @param id       identificador do produto.
     * @throws RecursoNaoEncontradoException se o produto não existir.
     */
    public void adicionarItemNoCarrinho(Venda carrinho, Long id) {
        Produto produto = getProduto(id);
        for (ItemVenda item : carrinho.getItens()) {
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

    /**
     * Reduz em uma unidade a quantidade de um produto no carrinho. Quando a
     * quantidade chegaria a zero, o item é removido.
     *
     * @param carrinho carrinho (venda em aberto) a ser modificado.
     * @param id       identificador do produto.
     * @throws RecursoNaoEncontradoException se o produto não existir.
     */
    public void reduzirQtdItemNoCarrinho(Venda carrinho, Long id) {
        Produto produto = getProduto(id);
        for (ItemVenda item : carrinho.getItens()) {
            if (Objects.equals(item.getProduto().getId(), produto.getId())) {
                if (item.getQuantidade() > 1) {
                    item.setQuantidade(item.getQuantidade() - 1);
                    return;
                }
            }
        }
        removerItemNoCarrinho(carrinho, id);
    }

    /**
     * Remove completamente um produto do carrinho.
     *
     * @param carrinho carrinho (venda em aberto) a ser modificado.
     * @param id       identificador do produto.
     * @throws RecursoNaoEncontradoException se o produto não existir.
     */
    public void removerItemNoCarrinho(Venda carrinho, Long id) {
        Produto produto = getProduto(id);
        carrinho.getItens().removeIf(item -> Objects.equals(item.getProduto().getId(), produto.getId()));
    }

    /**
     * Busca um produto pelo id, lançando exceção quando não encontrado.
     *
     * @param id identificador do produto.
     * @return o produto encontrado.
     * @throws RecursoNaoEncontradoException se o produto não existir.
     */
    private Produto getProduto(Long id) {
        Produto produto = produtoRepository.findById(id);
        if (produto == null) throw new RecursoNaoEncontradoException("Produto não encontrado.");
        return produto;
    }
}
