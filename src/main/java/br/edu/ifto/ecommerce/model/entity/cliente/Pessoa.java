package br.edu.ifto.ecommerce.model.entity.cliente;

import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Pessoa {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull (message = "{erro.pessoa.email.obrigatorio}")
    @NotBlank (message = "{erro.pessoa.email.obrigatorio}")
    @Email (message = "{erro.pessoa.email.invalido}")
    private String email;

    @NotNull (message = "{erro.pessoa.telefone.obrigatorio}")
    @NotBlank (message = "{erro.pessoa.telefone.obrigatorio}")
    private String telefone;

    public abstract char getTipo();

    public abstract String getNomeExibicao();

    public abstract String getDocumento();

    public abstract String getDocumentoMascarado();
}
