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

    /**
     * Busca um usuário pelo seu login.
     *
     * @param login login do usuário.
     * @return o usuário encontrado, ou {@code null} se não existir.
     */
    public Usuario findByLogin(String login) {
        return em.createQuery(
                        "FROM Usuario u WHERE u.login = :login", Usuario.class)
                .setParameter("login", login)
                .getSingleResultOrNull();
    }

    /**
     * Busca o usuário vinculado a uma pessoa.
     *
     * @param pessoaId identificador da pessoa.
     * @return o usuário vinculado, ou {@code null} se não existir.
     */
    public Usuario findByPessoaId(Long pessoaId) {
        return em.createQuery(
                        "FROM Usuario u WHERE u.pessoa.id = :pessoaId", Usuario.class)
                .setParameter("pessoaId", pessoaId)
                .getSingleResultOrNull();
    }

    /**
     * Persiste um novo usuário.
     *
     * @param usuario usuário a ser inserido.
     */
    public void insert(Usuario usuario) { em.persist(usuario); }

    /**
     * Atualiza a senha (já codificada) de um usuário.
     *
     * @param id        identificador do usuário.
     * @param novaSenha nova senha já codificada.
     */
    public void updateSenha(Long id, String novaSenha) {
        em.createQuery("UPDATE Usuario u SET u.password = :senha WHERE u.id = :id")
                .setParameter("senha", novaSenha)
                .setParameter("id", id)
                .executeUpdate();
    }
}
