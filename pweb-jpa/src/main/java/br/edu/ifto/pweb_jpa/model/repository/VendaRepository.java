package br.edu.ifto.pweb_jpa.model.repository;

import br.edu.ifto.pweb_jpa.model.entity.venda.Venda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VendaRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Venda> findAll() {
        return em.createQuery("from Venda", Venda.class).getResultList();
    }

    public Venda findById(Long id) {
        return em.find(Venda.class, id);
    }
}
