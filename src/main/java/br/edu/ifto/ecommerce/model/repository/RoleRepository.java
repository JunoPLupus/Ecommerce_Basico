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

    /**
     * Lista todos os papéis (roles) cadastrados.
     *
     * @return lista de papéis.
     */
    public List<Role> findAll() {
        return em.createQuery("FROM Role", Role.class).getResultList();
    }

    /**
     * Busca um papel pelo seu identificador.
     *
     * @param id identificador do papel.
     * @return o papel encontrado, ou {@code null} se não existir.
     */
    public Role findById(int id) {
        return em.find(Role.class, id);
    }

    /**
     * Busca um papel pelo seu nome (ex.: {@code ROLE_USER}, {@code ROLE_ADMIN}).
     *
     * @param nome nome do papel.
     * @return o papel correspondente ao nome informado.
     */
    public Role findByNome(String nome) {
        return em.createQuery("FROM Role r WHERE r.nome = :nome", Role.class)
                .setParameter("nome", nome).getSingleResult();
    }
}
