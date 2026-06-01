package br.edu.ifto.ecommerce.model.repository;

import br.edu.ifto.ecommerce.model.entity.usuario.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager em;

    public Usuario findByLogin(String login) {
        return em.createQuery(
                        "FROM Usuario u WHERE u.login = :login", Usuario.class)
                .setParameter("login", login)
                .getSingleResultOrNull();
    }
}
