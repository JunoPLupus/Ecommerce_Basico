package br.edu.ifto.ecommerce.service;

import br.edu.ifto.ecommerce.model.dto.ProdutoDTO;
import br.edu.ifto.ecommerce.model.entity.produto.Produto;
import br.edu.ifto.ecommerce.model.repository.ProdutoRepository;
import br.edu.ifto.ecommerce.utils.mappers.ProdutoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class ProdutoService {

    private static final String IMAGEM_PADRAO = "https://placehold.co/600x400";

    private final ProdutoRepository produtoRepository;

    /**
     * Lista produtos (DTO de leitura) aplicando filtros de descrição e faixa de preço.
     *
     * @param descricao   trecho da descrição (pode ser nulo).
     * @param precoMinimo preço mínimo (pode ser nulo).
     * @param precoMaximo preço máximo (pode ser nulo).
     * @return lista de produtos que atendem aos filtros.
     */
    @Transactional(readOnly = true)
    public List<ProdutoDTO> listarComFiltros(String descricao, Double precoMinimo, Double precoMaximo) {
        return ProdutoMapper.toDTOList(
                produtoRepository.findAllByDynamicFilters(descricao, precoMinimo, precoMaximo));
    }

    /**
     * Lista todos os produtos (DTO de leitura), sem filtros.
     *
     * @return lista de todos os produtos.
     */
    @Transactional(readOnly = true)
    public List<ProdutoDTO> listarTodos() {
        return ProdutoMapper.toDTOList(
                produtoRepository.findAllByDynamicFilters(null, null, null));
    }

    /**
     * Busca um produto pelo id, retornando a entidade usada pelo formulário de
     * edição (binding via {@code th:field}).
     *
     * @param id identificador do produto.
     * @return a entidade do produto, ou {@code null} se não existir.
     */
    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    /**
     * Persiste um novo produto, aplicando a imagem padrão quando a URL não é informada.
     *
     * @param produto produto a ser salvo.
     */
    public void salvar(Produto produto) {
        aplicarImagemPadrao(produto);
        produtoRepository.insert(produto);
    }

    /**
     * Atualiza um produto, aplicando a imagem padrão quando a URL não é informada.
     *
     * @param produto produto com os dados atualizados.
     */
    public void atualizar(Produto produto) {
        aplicarImagemPadrao(produto);
        produtoRepository.update(produto);
    }

    /**
     * Exclui um produto, desde que não haja itens de venda associados a ele.
     *
     * @param id identificador do produto.
     * @return {@code true} se excluído; {@code false} se houver vendas associadas.
     */
    public boolean excluir(Long id) {
        return produtoRepository.delete(id);
    }

    /**
     * Define a imagem padrão quando o produto não possui uma URL de imagem.
     *
     * @param produto produto a ser ajustado.
     */
    private void aplicarImagemPadrao(Produto produto) {
        if (produto.getUrlImagem() == null || produto.getUrlImagem().trim().isEmpty()) {
            produto.setUrlImagem(IMAGEM_PADRAO);
        }
    }
}
