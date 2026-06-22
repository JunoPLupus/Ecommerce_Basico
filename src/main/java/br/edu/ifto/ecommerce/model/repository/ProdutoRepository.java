package br.edu.ifto.ecommerce.model.repository;

import br.edu.ifto.ecommerce.model.entity.produto.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class ProdutoRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * Monta dinamicamente o HQL de filtragem de produtos conforme os parâmetros
     * informados (descrição e faixa de preço). Parâmetros nulos são ignorados.
     *
     * @param descricao   trecho da descrição (pode ser nulo).
     * @param precoMinimo preço mínimo (pode ser nulo).
     * @param precoMaximo preço máximo (pode ser nulo).
     * @return a consulta HQL montada.
     */
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

    /**
     * Lista produtos aplicando os filtros de descrição e faixa de preço informados.
     *
     * @param descricao   trecho da descrição (pode ser nulo).
     * @param precoMinimo preço mínimo (pode ser nulo).
     * @param precoMaximo preço máximo (pode ser nulo).
     * @return lista de produtos que atendem aos filtros.
     */
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

    /**
     * Busca um produto pelo seu identificador.
     *
     * @param id identificador do produto.
     * @return o produto encontrado, ou {@code null} se não existir.
     */
    public Produto findById(Long id) {
        return em.find(Produto.class, id);
    }

    /**
     * Verifica se existe algum item de venda vinculado ao produto informado.
     *
     * @param id identificador do produto.
     * @return {@code true} se houver item de venda associado; caso contrário, {@code false}.
     */
    public boolean existsItemVendaByProdutoId(Long id) {
        String jpql = "SELECT COUNT(item_venda) FROM ItemVenda item_venda WHERE item_venda.produto.id = :id";

        Long count = em.createQuery(jpql, Long.class)
                .setParameter("id", id)
                .getSingleResult();

        return count > 0;
    }

    /**
     * Persiste um novo produto.
     *
     * @param produto produto a ser inserido.
     */
    public void insert(Produto produto) { em.persist(produto); }

    /**
     * Atualiza um produto existente.
     *
     * @param produto produto com os dados atualizados.
     */
    public void update(Produto produto) { em.merge(produto); }

    /**
     * Exclui um produto, desde que não haja itens de venda associados a ele.
     *
     * @param id identificador do produto.
     * @return {@code true} se excluído; {@code false} se houver itens de venda associados.
     */
    public boolean delete(Long id) {
        Produto produto = em.find(Produto.class, id);

        if (!existsItemVendaByProdutoId(id)) {
            em.remove(produto);
            return true;
        }

        return false;
    }
}
