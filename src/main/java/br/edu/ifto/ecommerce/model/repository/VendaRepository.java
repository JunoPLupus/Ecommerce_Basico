package br.edu.ifto.ecommerce.model.repository;

import br.edu.ifto.ecommerce.model.entity.venda.Venda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class VendaRepository {

    @PersistenceContext
    private EntityManager em;

    private String generateDynamicHQL(String nomeCliente, LocalDate dataInicial, LocalDate dataFinal) {
        String hql = "FROM Venda v WHERE ";

        hql += nomeCliente != null?
                "(lower(v.cliente.nome) like lower(:nomeCliente) OR lower(v.cliente.razaoSocial) like lower(:nomeCliente))" :
                "1=1";

        if (dataInicial != null && dataFinal != null) hql += " AND CAST(v.data AS DATE) BETWEEN :dataInicial AND :dataFinal";
        else if (dataInicial != null) hql += " AND CAST(v.data AS DATE) = :dataInicial";

        return hql;
    }

    public List<Venda> findAllByDynamicFilters(String nomeCliente, LocalDate dataInicial, LocalDate dataFinal) {
        String dynamicHql = generateDynamicHQL(nomeCliente,  dataInicial, dataFinal);

        var query = em.createQuery(dynamicHql, Venda.class);

        if (nomeCliente != null) query.setParameter("nomeCliente", "%" + nomeCliente + "%");
        if (dataInicial != null) query.setParameter("dataInicial", dataInicial);
        if (dataFinal != null) query.setParameter("dataFinal", dataFinal);

        return query.getResultList();
    }

    public List<Venda> findAllByClienteId(Long idCliente) {
        String hql = "FROM Venda v WHERE v.cliente.id = :idCliente";

        return em.createQuery(hql, Venda.class)
                .setParameter("idCliente", idCliente)
                .getResultList();
    }

    public Venda findById(Long id) {
        return em.find(Venda.class, id);
    }
}
