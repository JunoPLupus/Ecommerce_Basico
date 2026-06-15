package br.edu.ifto.ecommerce.model.entity.endereco;

import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.enums.Estado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Endereco implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotBlank (message = "{erro.endereco.logradouro.obrigatorio}")
    @Size(min = 5, max = 200, message = "{erro.endereco.logradouro.tamanho}")
    private String logradouro;

    private String numero;

    @NotBlank (message = "{erro.endereco.bairro.obrigatorio}")
    @Size(min = 3, max = 100, message = "{erro.endereco.bairro.tamanho}")
    private String bairro;

    @NotBlank (message = "{erro.endereco.cidade.obrigatorio}")
    @Size(min = 3, max = 30, message = "{erro.endereco.cidade.tamanho}")
    private String cidade;

    @NotNull (message = "{erro.endereco.estado.obrigatorio}")
    @Enumerated(EnumType.STRING)
    @Column(length = 2)
    private Estado estado;

    @NotBlank (message = "{erro.endereco.cep.obrigatorio}")
    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "{erro.endereco.cep.invalido}")
    private String cep;

    @ManyToOne
    private Pessoa pessoa;
}
