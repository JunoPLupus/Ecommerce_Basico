package br.edu.ifto.ecommerce.model.entity.cliente;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("F")
public class PessoaFisica extends Pessoa {

    private String cpf;
    private String nome;

    @Override
    public char getTipo() {
        return 'F';
    }

    @Override
    public String getNomeExibicao() {
        return getNome();
    }

    @Override
    public String getDocumento() {
        return getCpf();
    }

    @Override
    public String getDocumentoMascarado() {
        return "***" + getDocumento().substring(3, 9) + "**-**";
    }
}
