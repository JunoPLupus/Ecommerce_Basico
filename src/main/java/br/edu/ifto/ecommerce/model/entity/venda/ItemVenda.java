package br.edu.ifto.ecommerce.model.entity.venda;

import br.edu.ifto.ecommerce.model.entity.produto.Produto;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class ItemVenda implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne // O dono do relacionamento, esta tabela vai ter a FK
    private Produto produto;

    @ManyToOne
    private Venda venda;

    private Double quantidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() { return produto; }

    public void setProduto(Produto produto) { this.produto = produto; }

    public Venda getVenda() { return venda; }

    public void setVenda(Venda venda) { this.venda = venda; }

    public BigDecimal total(){
        return this.produto
                .getValor()
                .multiply(BigDecimal.valueOf(this.quantidade));
    }
}
