package br.edu.ifto.ecommerce.model.repository;

import br.edu.ifto.ecommerce.model.entity.produto.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProdutoRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Produto> findAll() {
        return em.createQuery("from Produto", Produto.class).getResultList();
    }

    public Produto findById(Long id) {
        return em.find(Produto.class, id);
    }

    public void save(Produto produto) { em.persist(produto); }

    public void update(Produto produto) { em.merge(produto); }

    public void delete(Long id) {
        Produto produto = em.find(Produto.class, id);
        em.remove(produto);
    }
}
