package br.edu.ifto.ecommerce.model.repository;

import br.edu.ifto.ecommerce.model.entity.role.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class RoleRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Role> findAll() {
        return em.createQuery("FROM Role", Role.class).getResultList();
    }

    public Role findById(int id) {
        return em.find(Role.class, id);
    }

    public Role findByNome(String nome) {
        return em.createQuery("FROM Role r WHERE r.nome = :nome", Role.class)
                .setParameter("nome", nome).getSingleResult();
    }
}
