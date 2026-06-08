package br.edu.ifto.ecommerce.model.entity.cliente;

import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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

    @NotBlank (message = "{erro.pessoa.email.obrigatorio}")
    @Email (message = "{erro.pessoa.email.invalido}")
    @Column(unique = true)
    private String email;

    @NotBlank (message = "{erro.pessoa.telefone.obrigatorio}")
    private String telefone;

    public abstract char getTipo();

    public abstract String getNomeExibicao();

    public String getNomeCurto() {
        String[] partes = getNomeExibicao().split(" ");
        if (partes.length >= 2) return partes[0] + " " + partes[1];
        return partes[0];
    }

    public abstract String getDocumento();

    public abstract String getDocumentoMascarado();
}
