package br.edu.ifto.ecommerce.model.repository;

import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClienteRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Pessoa> findAll(){
        return em.createQuery("FROM Pessoa", Pessoa.class).getResultList();
    }

    public List<Pessoa> findAllByNomeOuRazaoSocial(String nomeOuRazaoSocial) {
        return em.createQuery(
                        "FROM Pessoa p WHERE lower(TREAT(p AS PessoaFisica).nome) LIKE lower(:nome) OR lower(TREAT(p AS PessoaJuridica).razaoSocial) LIKE lower(:nome)", Pessoa.class)
                .setParameter("nome", "%" + nomeOuRazaoSocial + "%")
                .getResultList();
    }

    public Pessoa findById(Long id){
        return em.find(Pessoa.class, id);
    }

    public void insert(Pessoa pessoa){
        em.persist(pessoa);
    }
}
