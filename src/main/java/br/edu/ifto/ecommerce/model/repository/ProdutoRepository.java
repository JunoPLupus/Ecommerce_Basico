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

    private String generateDynamicHQL(String descricao,
                                      Double precoMinimo,
                                      Double precoMaximo) {
        String hql = "FROM Produto p WHERE ";

        hql += descricao != null?
                "lower(descricao) like lower(:descricao)" :
                "1=1";

        if (precoMinimo != null && precoMaximo != null) hql+= " AND p.valor BETWEEN :precoMinimo AND :precoMaximo";
        else if (precoMinimo != null) hql += " AND p.valor >= :precoMinimo";
        else if (precoMaximo != null) hql += " AND p.valor <= :precoMaximo";

        return hql;
    }

    public List<Produto> findAllByDynamicFilters(String descricao,
                                               Double precoMinimo,
                                               Double precoMaximo) {
        String dynamicHql = generateDynamicHQL(descricao, precoMinimo, precoMaximo);

        var query = em.createQuery(dynamicHql, Produto.class);

        if (descricao != null) query.setParameter("descricao", "%" + descricao + "%");
        if (precoMinimo != null) query.setParameter("precoMinimo", precoMinimo);
        if (precoMaximo != null) query.setParameter("precoMaximo", precoMaximo);

        return query.getResultList();
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
