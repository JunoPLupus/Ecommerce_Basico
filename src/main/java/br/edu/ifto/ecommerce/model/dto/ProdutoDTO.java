package br.edu.ifto.ecommerce.model.dto;

import java.math.BigDecimal;

/**
 * DTO de leitura de produto, usado nas listagens (admin e cliente).
 * O formulário de cadastro/edição continua vinculado à entidade.
 */
public record ProdutoDTO(
        Long id,
        String urlImagem,
        String descricao,
        BigDecimal valor
) {}
