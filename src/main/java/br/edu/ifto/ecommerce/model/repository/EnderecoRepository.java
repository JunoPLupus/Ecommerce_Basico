package br.edu.ifto.ecommerce.model.repository;

import br.edu.ifto.ecommerce.model.entity.endereco.Endereco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class EnderecoRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Endereco> findAllByPessoaId(Long idPessoa) {
        String hql = "FROM Endereco e WHERE e.pessoa.id = :idPessoa";

        return em.createQuery(hql, Endereco.class)
                .setParameter("idPessoa", idPessoa)
                .getResultList();
    }

    public Endereco findById(Long id) {
        return em.find(Endereco.class, id);
    }

    public boolean existsVendaByEnderecoId(Long id) {
        String jpql = "SELECT COUNT(v) FROM Venda v WHERE v.endereco.id = :id";

        Long count = em.createQuery(jpql, Long.class)
                .setParameter("id", id)
                .getSingleResult();

        return count > 0;
    }

    public Endereco insert(Endereco endereco) {
        em.persist(endereco);
        return endereco;
    }

    public void update(Endereco endereco) {
        em.merge(endereco);
    }

    public boolean delete(Long id) {
        if (existsVendaByEnderecoId(id)) return false;

        em.createQuery("DELETE FROM Endereco e WHERE e.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        return true;
    }
}
