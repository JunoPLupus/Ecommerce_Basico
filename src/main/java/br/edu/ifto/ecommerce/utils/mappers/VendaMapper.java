package br.edu.ifto.ecommerce.utils.mappers;

import br.edu.ifto.ecommerce.model.dto.ItemVendaDTO;
import br.edu.ifto.ecommerce.model.dto.VendaDTO;
import br.edu.ifto.ecommerce.model.entity.venda.ItemVenda;
import br.edu.ifto.ecommerce.model.entity.venda.Venda;

import java.util.List;

/**
 * Converte a entidade {@code Venda} (e seus itens) em DTOs de leitura.
 * Reaproveita PessoaMapper, EnderecoMapper e ProdutoMapper para os campos aninhados.
 */
public final class VendaMapper {

    private VendaMapper() {}

    public static VendaDTO toDTO(Venda venda) {
        if (venda == null) return null;
        return new VendaDTO(
                venda.getId(),
                venda.getData(),
                PessoaMapper.toResumo(venda.getCliente()),
                EnderecoMapper.toResumo(venda.getEndereco()),
                venda.getFormaPagamento(),
                toItensDTO(venda.getItens()),
                venda.qtdItensTotal(),
                venda.total()
        );
    }

    public static List<VendaDTO> toDTOList(List<Venda> vendas) {
        return vendas.stream().map(VendaMapper::toDTO).toList();
    }

    private static List<ItemVendaDTO> toItensDTO(List<ItemVenda> itens) {
        if (itens == null) return List.of();
        return itens.stream().map(VendaMapper::toItemDTO).toList();
    }

    private static ItemVendaDTO toItemDTO(ItemVenda item) {
        return new ItemVendaDTO(
                ProdutoMapper.toDTO(item.getProduto()),
                item.getQuantidade(),
                item.total()
        );
    }
}
