package br.edu.ifto.pweb_jpa.model.entity.cliente;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "pessoa_id")
public class PessoaFisica extends Pessoa {

    private String cpf;
    private String nome;

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getNomeExibicao() {
        return getNome();
    }
}
