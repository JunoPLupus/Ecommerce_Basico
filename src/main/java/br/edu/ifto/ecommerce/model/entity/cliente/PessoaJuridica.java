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
@DiscriminatorValue("J")
public class PessoaJuridica extends Pessoa {

    @NotNull (message = "{erro.pessoajuridica.cnpj.obrigatorio}")
    @NotBlank (message = "{erro.pessoajuridica.cnpj.obrigatorio}")
    @Pattern(regexp = "^\\d{2}\\.?\\d{3}\\.?\\d{3}\\/?\\d{4}-?\\d{2}$", message = "{erro.pessoajuridica.cnpj.invalido}")
    private String cnpj;

    @NotNull (message = "{erro.pessoajuridica.razaoSocial.obrigatorio}")
    @NotBlank (message = "{erro.pessoajuridica.razaoSocial.obrigatorio}")
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
