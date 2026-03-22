package br.edu.ifto.pweb_jpa.model.entity.cliente;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
public class PessoaJuridica extends Pessoa {

    private String cnpj;
    private String razaoSocial;

    public String getCnpj() {
        return this.cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return this.razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @Override
    public String getNomeExibicao() {
        return getRazaoSocial();
    }
}
