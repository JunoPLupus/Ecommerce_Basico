package br.edu.ifto.ecommerce.utils;

import br.edu.ifto.ecommerce.model.dto.ClienteResumoDTO;
import br.edu.ifto.ecommerce.model.dto.PessoaDTO;
import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;

import java.util.List;

/**
 * Converte a entidade {@code Pessoa} em DTOs de leitura.
 * Centraliza a montagem dos DTOs, evitando duplicação entre serviços/controllers.
 */
public final class PessoaMapper {

    private PessoaMapper() {}

    public static PessoaDTO toDTO(Pessoa pessoa) {
        if (pessoa == null) return null;
        return new PessoaDTO(
                pessoa.getId(),
                pessoa.getEmail(),
                pessoa.getTelefone(),
                pessoa.isPF(),
                pessoa.getNomeExibicao(),
                pessoa.getNomeCurto(),
                pessoa.getDocumento(),
                pessoa.getDocumentoMascarado()
        );
    }

    public static List<PessoaDTO> toDTOList(List<Pessoa> pessoas) {
        return pessoas.stream().map(PessoaMapper::toDTO).toList();
    }

    public static ClienteResumoDTO toResumo(Pessoa pessoa) {
        if (pessoa == null) return null;
        return new ClienteResumoDTO(pessoa.getId(), pessoa.getNomeExibicao());
    }

    public static List<ClienteResumoDTO> toResumoList(List<Pessoa> pessoas) {
        return pessoas.stream().map(PessoaMapper::toResumo).toList();
    }
}
