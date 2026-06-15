package br.edu.ifto.ecommerce.model.enums;

public enum FormaPagamento {

    CREDITO("Crédito"),
    DEBITO("Débito"),
    PIX("Pix");

    private final String descricao;

    FormaPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
