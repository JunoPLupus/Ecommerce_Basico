package br.edu.ifto.ecommerce.model.entity.venda;

import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public int qtdItensTotal() {
        return this.itens.stream()
                .mapToInt(ItemVenda::getQuantidade)
                .sum();
    }

    public Double total() {
        return this.itens.stream()
                .map(ItemVenda::total)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Venda venda = (Venda) o;
        return getId() != null && Objects.equals(getId(), venda.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
