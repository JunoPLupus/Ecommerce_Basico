package br.edu.ifto.ecommerce.utils.mappers;

import br.edu.ifto.ecommerce.model.dto.EditarPerfilDTO;
import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.entity.cliente.PessoaFisica;
import br.edu.ifto.ecommerce.model.entity.cliente.PessoaJuridica;

public final class PerfilMapper {

    private PerfilMapper() {}

    public static EditarPerfilDTO toDTO(Pessoa pessoa) {
        EditarPerfilDTO dto = new EditarPerfilDTO();
        dto.setNomeOuRazaoSocial(pessoa.getNomeExibicao());
        dto.setTelefone(pessoa.getTelefone());
        return dto;
    }

    public static void aplicarDTO(EditarPerfilDTO dto, Pessoa pessoa) {
        pessoa.setTelefone(dto.getTelefone());

        if (pessoa instanceof PessoaFisica pf) {
            pf.setNome(dto.getNomeOuRazaoSocial());
        } else if (pessoa instanceof PessoaJuridica pj) {
            pj.setRazaoSocial(dto.getNomeOuRazaoSocial());
        }
    }
}
