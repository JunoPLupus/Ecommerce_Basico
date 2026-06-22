package br.edu.ifto.ecommerce.model.repository;

import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class ClienteRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * Lista todas as pessoas (físicas e jurídicas) cadastradas.
     *
     * @return lista de todas as pessoas.
     */
    public List<Pessoa> findAll(){
        return em.createQuery("FROM Pessoa", Pessoa.class).getResultList();
    }

    /**
     * Busca pessoas cujo nome (pessoa física) ou razão social (pessoa jurídica)
     * contenha o termo informado, de forma case-insensitive.
     *
     * @param nomeOuRazaoSocial termo de busca.
     * @return lista de pessoas que correspondem ao termo.
     */
    public List<Pessoa> findAllByNomeOuRazaoSocial(String nomeOuRazaoSocial) {
        return em.createQuery(
                        "FROM Pessoa p WHERE lower(TREAT(p AS PessoaFisica).nome) LIKE lower(:nome) OR lower(TREAT(p AS PessoaJuridica).razaoSocial) LIKE lower(:nome)", Pessoa.class)
                .setParameter("nome", "%" + nomeOuRazaoSocial + "%")
                .getResultList();
    }

    /**
     * Busca uma pessoa pelo seu identificador.
     *
     * @param id identificador da pessoa.
     * @return a pessoa encontrada, ou {@code null} se não existir.
     */
    public Pessoa findById(Long id){
        return em.find(Pessoa.class, id);
    }

    /**
     * Persiste uma nova pessoa.
     *
     * @param pessoa pessoa a ser inserida.
     * @return a pessoa persistida (com id gerado).
     */
    public Pessoa insert(Pessoa pessoa){
        em.persist(pessoa);
        return pessoa;
    }
}
