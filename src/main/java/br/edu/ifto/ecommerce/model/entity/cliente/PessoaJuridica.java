package br.edu.ifto.ecommerce.model.entity.cliente;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("J")
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
    public char getTipo() {
        return 'J';
    }

    @Override
    public String getNomeExibicao() {
        return getRazaoSocial();
    }

    @Override
    public String getDocumento() {
        return getCnpj();
    }

    @Override
    public String getDocumentoMascarado() {
        return "**" + getDocumento().substring(2, 10) + "****-**";
    }
}
