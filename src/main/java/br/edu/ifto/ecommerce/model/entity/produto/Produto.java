package br.edu.ifto.ecommerce.model.entity.produto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Produto implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull (message = "{erro.produto.descricao.obrigatorio}")
    @NotBlank (message = "{erro.produto.descricao.obrigatorio}")
    @Size(min = 5, max = 200, message = "{erro.produto.descricao.tamanho}")
    private String descricao;

    @NotNull (message = "{erro.produto.valor.obrigatorio}")
    @DecimalMin(value="0.01", message="{erro.produto.valor.tamanho}")
    private BigDecimal valor;
}
