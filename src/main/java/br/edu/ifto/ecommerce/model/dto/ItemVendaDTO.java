package br.edu.ifto.ecommerce.model.dto;

import java.math.BigDecimal;

/**
 * DTO de leitura de um item de venda. Reaproveita ProdutoDTO e já traz o subtotal
 * (total) calculado, tirando esse cálculo da view.
 */
public record ItemVendaDTO(
        ProdutoDTO produto,
        int quantidade,
        BigDecimal total
) {}
