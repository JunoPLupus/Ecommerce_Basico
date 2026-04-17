package br.edu.ifto.ecommerce.model.entity.venda;

import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Venda implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToMany (mappedBy = "venda", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ItemVenda> itens;

    @ManyToOne
    private Pessoa cliente;

    private LocalDateTime data;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public List<ItemVenda> getItens() { return itens; }

    public void setItens(List<ItemVenda> itens) { this.itens = itens; }

    public LocalDateTime getData() { return data; }

    public void setData(LocalDateTime data) { this.data = data; }

    public Pessoa getCliente() {
        return cliente;
    }

    public void setCliente(Pessoa cliente) {
        this.cliente = cliente;
    }

    public Double total() {
        double total = 0.0;

        for (ItemVenda item : itens) {
            total += item.total().doubleValue();
        }

        return total;
    }
}
