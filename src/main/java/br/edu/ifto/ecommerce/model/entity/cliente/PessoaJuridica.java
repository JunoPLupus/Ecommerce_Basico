package br.edu.ifto.ecommerce.model.entity.cliente;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("J")
public class PessoaJuridica extends Pessoa {

    private String cnpj;
    private String razaoSocial;

    @Override
    public char getTipo() {
        return 'J';
    }

    @Override
    public String getNomeExibicao() {
        return getRazaoSocial();
    }

    @Override
    public String getDocumento() {
        return getCnpj();
    }

    @Override
    public String getDocumentoMascarado() {
        return "**" + getDocumento().substring(2, 10) + "****-**";
    }
}
