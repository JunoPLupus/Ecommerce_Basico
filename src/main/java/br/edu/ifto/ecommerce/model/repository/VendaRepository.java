package br.edu.ifto.ecommerce.model.repository;

import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.entity.produto.Produto;
import br.edu.ifto.ecommerce.model.entity.venda.Venda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class VendaRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Venda> findAll() {
        return em.createQuery("from Venda", Venda.class).getResultList();
    }

    public List<Venda> findAllByCliente(Pessoa cliente) {
        return em.createQuery(
                        "FROM Venda v WHERE v.cliente.id = :cliente_id", Venda.class)
                .setParameter("cliente_id", cliente.getId())
                .getResultList();
    }

    public List<Venda> findAllByData(LocalDateTime data) {
        /* TODO: Implementar filtro por data sem usar horário na seleção */
        return em.createQuery(
                        "FROM Venda v WHERE date(v.data) = date(:data)", Venda.class)
                .setParameter("data", data)
                .getResultList();
    }

    public Venda findById(Long id) {
        return em.find(Venda.class, id);
    }
}
