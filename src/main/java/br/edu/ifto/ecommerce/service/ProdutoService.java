package br.edu.ifto.ecommerce.service;

import br.edu.ifto.ecommerce.model.dto.ProdutoDTO;
import br.edu.ifto.ecommerce.model.entity.produto.Produto;
import br.edu.ifto.ecommerce.model.repository.ProdutoRepository;
import br.edu.ifto.ecommerce.utils.ProdutoMapper;
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

    @Transactional(readOnly = true)
    public List<ProdutoDTO> listarComFiltros(String descricao, Double precoMinimo, Double precoMaximo) {
        return ProdutoMapper.toDTOList(
                produtoRepository.findAllByDynamicFilters(descricao, precoMinimo, precoMaximo));
    }

    @Transactional(readOnly = true)
    public List<ProdutoDTO> listarTodos() {
        return ProdutoMapper.toDTOList(
                produtoRepository.findAllByDynamicFilters(null, null, null));
    }

    /** Retorna a entidade, usada pelo formulário de edição (binding via th:field). */
    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public void salvar(Produto produto) {
        aplicarImagemPadrao(produto);
        produtoRepository.insert(produto);
    }

    public void atualizar(Produto produto) {
        aplicarImagemPadrao(produto);
        produtoRepository.update(produto);
    }

    public boolean excluir(Long id) {
        return produtoRepository.delete(id);
    }

    private void aplicarImagemPadrao(Produto produto) {
        if (produto.getUrlImagem() == null || produto.getUrlImagem().trim().isEmpty()) {
            produto.setUrlImagem(IMAGEM_PADRAO);
        }
    }
}
