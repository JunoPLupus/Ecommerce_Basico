package br.edu.ifto.ecommerce.model.view;

/**
 * Contrato de exibição de uma pessoa (física ou jurídica).
 * <p>
 * Permite que tanto a entidade JPA {@code Pessoa} quanto DTOs de leitura
 * exponham os mesmos campos para a camada de visão (Thymeleaf), sem que os
 * DTOs precisem herdar da entidade. Os templates dependem desta abstração
 * (DIP), não da implementação concreta.
 */
public interface PessoaView {

    Long getId();

    String getEmail();

    String getTelefone();

    boolean isPF();

    String getNomeExibicao();

    String getNomeCurto();

    String getDocumento();

    String getDocumentoMascarado();
}
