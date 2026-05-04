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

    public List<Pessoa> findAllByNome(String nome) {
        return em.createQuery( // TODO: Terminar a implementação sem erros
                        "FROM Pessoa p WHERE lower(p.nome) like lower(:nome) OR lower(p.razaoSocial) like lower(:nome) ", Pessoa.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    public void insert(Pessoa pessoa){
        em.persist(pessoa);
    }
}
