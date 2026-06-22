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

    /**
     * Lista os endereços pertencentes a uma pessoa.
     *
     * @param idPessoa identificador da pessoa dona dos endereços.
     * @return lista de endereços da pessoa.
     */
    public List<Endereco> findAllByPessoaId(Long idPessoa) {
        String hql = "FROM Endereco e WHERE e.pessoa.id = :idPessoa";

        return em.createQuery(hql, Endereco.class)
                .setParameter("idPessoa", idPessoa)
                .getResultList();
    }

    /**
     * Busca um endereço pelo seu identificador.
     *
     * @param id identificador do endereço.
     * @return o endereço encontrado, ou {@code null} se não existir.
     */
    public Endereco findById(Long id) {
        return em.find(Endereco.class, id);
    }

    /**
     * Verifica se existe alguma venda vinculada ao endereço informado.
     *
     * @param id identificador do endereço.
     * @return {@code true} se houver venda associada; caso contrário, {@code false}.
     */
    public boolean existsVendaByEnderecoId(Long id) {
        String jpql = "SELECT COUNT(v) FROM Venda v WHERE v.endereco.id = :id";

        Long count = em.createQuery(jpql, Long.class)
                .setParameter("id", id)
                .getSingleResult();

        return count > 0;
    }

    /**
     * Persiste um novo endereço.
     *
     * @param endereco endereço a ser inserido.
     * @return o endereço persistido (com id gerado).
     */
    public Endereco insert(Endereco endereco) {
        em.persist(endereco);
        return endereco;
    }

    /**
     * Atualiza um endereço existente.
     *
     * @param endereco endereço com os dados atualizados.
     */
    public void update(Endereco endereco) {
        em.merge(endereco);
    }

    /**
     * Exclui um endereço, desde que não haja vendas associadas a ele.
     *
     * @param id identificador do endereço.
     * @return {@code true} se excluído; {@code false} se houver vendas associadas.
     */
    public boolean delete(Long id) {
        if (existsVendaByEnderecoId(id)) return false;

        em.createQuery("DELETE FROM Endereco e WHERE e.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        return true;
    }
}
