package br.edu.ifto.ecommerce.model.repository;

import br.edu.ifto.ecommerce.model.entity.venda.Venda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Repository
public class VendaRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * Monta dinamicamente o HQL de filtragem de vendas conforme o nome do cliente
     * e o intervalo de datas informados. Parâmetros nulos são ignorados.
     *
     * @param nomeCliente trecho do nome/razão social do cliente (pode ser nulo).
     * @param dataInicial data inicial do intervalo (pode ser nula).
     * @param dataFinal   data final do intervalo (pode ser nula).
     * @return a consulta HQL montada.
     */
    private String generateDynamicHQL(String nomeCliente, LocalDate dataInicial, LocalDate dataFinal) {
        String hql = "FROM Venda v WHERE ";

        hql += nomeCliente != null?
                "(lower(v.cliente.nome) like lower(:nomeCliente) OR lower(v.cliente.razaoSocial) like lower(:nomeCliente))" :
                "1=1";

        if (dataInicial != null && dataFinal != null) hql += " AND CAST(v.data AS DATE) BETWEEN :dataInicial AND :dataFinal";
        else if (dataInicial != null) hql += " AND CAST(v.data AS DATE) = :dataInicial";

        return hql;
    }

    /**
     * Lista vendas aplicando os filtros de nome do cliente e intervalo de datas.
     *
     * @param nomeCliente trecho do nome/razão social do cliente (pode ser nulo).
     * @param dataInicial data inicial do intervalo (pode ser nula).
     * @param dataFinal   data final do intervalo (pode ser nula).
     * @return lista de vendas que atendem aos filtros.
     */
    public List<Venda> findAllByDynamicFilters(String nomeCliente, LocalDate dataInicial, LocalDate dataFinal) {
        String dynamicHql = generateDynamicHQL(nomeCliente,  dataInicial, dataFinal);

        var query = em.createQuery(dynamicHql, Venda.class);

        if (nomeCliente != null) query.setParameter("nomeCliente", "%" + nomeCliente + "%");
        if (dataInicial != null) query.setParameter("dataInicial", dataInicial);
        if (dataFinal != null) query.setParameter("dataFinal", dataFinal);

        return query.getResultList();
    }

    /**
     * Lista as vendas realizadas por um cliente.
     *
     * @param idCliente identificador do cliente.
     * @return lista de vendas do cliente.
     */
    public List<Venda> findAllByClienteId(Long idCliente) {
        String hql = "FROM Venda v WHERE v.cliente.id = :idCliente";

        return em.createQuery(hql, Venda.class)
                .setParameter("idCliente", idCliente)
                .getResultList();
    }

    /**
     * Busca uma venda pelo seu identificador.
     *
     * @param id identificador da venda.
     * @return a venda encontrada, ou {@code null} se não existir.
     */
    public Venda findById(Long id) {
        return em.find(Venda.class, id);
    }

    /**
     * Persiste uma nova venda.
     *
     * @param venda venda a ser inserida.
     * @return a venda persistida (com id gerado).
     */
    public Venda insert(Venda venda) {
        em.persist(venda);
        return venda;
    }

    /**
     * Atualiza uma venda existente.
     *
     * @param venda venda com os dados atualizados.
     */
    public void update(Venda venda) { em.merge(venda); }
}
