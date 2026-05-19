package br.edu.ifto.ecommerce.model.entity.cliente;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("F")
public class PessoaFisica extends Pessoa {

    @NotNull (message = "{erro.pessoafisica.cpf.obrigatorio}")
    @NotBlank (message = "{erro.pessoafisica.cpf.obrigatorio}")
    @Pattern(regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$", message = "{erro.pessoafisica.cpf.invalido}")
    private String cpf;

    @NotNull (message = "{erro.pessoafisica.nome.obrigatorio}")
    @NotBlank (message = "{erro.pessoafisica.nome.obrigatorio}")
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
