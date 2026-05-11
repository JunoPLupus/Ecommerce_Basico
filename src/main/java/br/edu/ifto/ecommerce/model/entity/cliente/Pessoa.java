package br.edu.ifto.ecommerce.model.entity.cliente;

import jakarta.persistence.*;
import lombok.*;

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

    private String email;

    private String telefone;

    public abstract char getTipo();

    public abstract String getNomeExibicao();

    public abstract String getDocumento();

    public abstract String getDocumentoMascarado();
}
