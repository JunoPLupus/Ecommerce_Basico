package br.edu.ifto.ecommerce.utils.mappers;

import br.edu.ifto.ecommerce.model.dto.ProdutoDTO;
import br.edu.ifto.ecommerce.model.entity.produto.Produto;

import java.util.List;

public final class ProdutoMapper {

    private ProdutoMapper() {}

    public static ProdutoDTO toDTO(Produto produto) {
        if (produto == null) return null;
        return new ProdutoDTO(
                produto.getId(),
                produto.getUrlImagem(),
                produto.getDescricao(),
                produto.getValor()
        );
    }

    public static List<ProdutoDTO> toDTOList(List<Produto> produtos) {
        return produtos.stream().map(ProdutoMapper::toDTO).toList();
    }
}
