package br.edu.ifto.ecommerce.model.dto;

import br.edu.ifto.ecommerce.model.view.PessoaView;
import lombok.Getter;

/**
 * DTO de leitura de uma pessoa. Implementa {@link PessoaView} para servir como
 * fonte de dados dos templates sem expor a entidade JPA (evita lazy loading
 * acidental e acoplamento da view ao modelo de persistência).
 */
@Getter
public class PessoaDTO implements PessoaView {

    private final Long id;
    private final String email;
    private final String telefone;
    private final boolean pf;
    private final String nomeExibicao;
    private final String nomeCurto;
    private final String documento;
    private final String documentoMascarado;

    public PessoaDTO(Long id, String email, String telefone, boolean pf,
                     String nomeExibicao, String nomeCurto,
                     String documento, String documentoMascarado) {
        this.id = id;
        this.email = email;
        this.telefone = telefone;
        this.pf = pf;
        this.nomeExibicao = nomeExibicao;
        this.nomeCurto = nomeCurto;
        this.documento = documento;
        this.documentoMascarado = documentoMascarado;
    }

    @Override
    public boolean isPF() {
        return pf;
    }
}
