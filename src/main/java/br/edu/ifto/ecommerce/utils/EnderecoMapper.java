package br.edu.ifto.ecommerce.utils;

import br.edu.ifto.ecommerce.model.dto.EnderecoResumoDTO;
import br.edu.ifto.ecommerce.model.entity.endereco.Endereco;

import java.util.List;

/**
 * Converte a entidade {@code Endereco} em DTOs de leitura ({@link EnderecoResumoDTO}).
 */
public final class EnderecoMapper {

    private EnderecoMapper() {}

    public static EnderecoResumoDTO toResumo(Endereco endereco) {
        if (endereco == null) return null;
        return new EnderecoResumoDTO(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep()
        );
    }

    public static List<EnderecoResumoDTO> toResumoList(List<Endereco> enderecos) {
        return enderecos.stream().map(EnderecoMapper::toResumo).toList();
    }
}
