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

    public List<Produto> findAllByDescricao(String descricao) {
        return em.createQuery(
                        "from Produto where lower(descricao) like lower(:descricao)", Produto.class)
                .setParameter("descricao", "%" + descricao + "%")
                .getResultList();
    }

    public Produto findById(Long id) {
        return em.find(Produto.class, id);
    }

    public boolean existsItemVendaByProdutoId(Long id) {
        String jpql = "SELECT COUNT(item_venda) FROM ItemVenda item_venda WHERE item_venda.produto.id = :id";

        Long count = em.createQuery(jpql, Long.class)
                .setParameter("id", id)
                .getSingleResult();

        return count > 0;
    }

    public void insert(Produto produto) { em.persist(produto); }

    public void update(Produto produto) { em.merge(produto); }

    public boolean delete(Long id) {
        Produto produto = em.find(Produto.class, id);

        if (!existsItemVendaByProdutoId(id)) {
            em.remove(produto);
            return true;
        }

        return false;
    }
}
