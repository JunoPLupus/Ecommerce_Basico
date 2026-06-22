package br.edu.ifto.ecommerce.model.entity.cliente;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("F")
public class PessoaFisica extends Pessoa {

    @NotBlank (message = "{erro.pessoafisica.cpf.obrigatorio}")
    @Pattern(regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$", message = "{erro.pessoafisica.cpf.invalido}")
    @Column(unique = true)
    private String cpf;

    @NotBlank (message = "{erro.pessoafisica.nome.obrigatorio}")
    private String nome;

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
