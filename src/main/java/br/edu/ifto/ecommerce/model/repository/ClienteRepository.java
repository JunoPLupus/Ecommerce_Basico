package br.edu.ifto.ecommerce.model.repository;

import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteRepository {

    @PersistenceContext
    private EntityManager em;

    public void insert(Pessoa pessoa){
        em.persist(pessoa);
    }
}
