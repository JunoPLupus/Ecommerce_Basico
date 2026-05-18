package br.edu.ifto.ecommerce.model.entity.venda;

import br.edu.ifto.ecommerce.model.entity.produto.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ItemVenda implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne // O dono do relacionamento, esta tabela vai ter a FK
    private Produto produto;

    @ManyToOne
    private Venda venda;

    private int quantidade;

    public BigDecimal total(){
        return this.produto
                .getValor()
                .multiply(BigDecimal.valueOf(this.quantidade));
    }
}
