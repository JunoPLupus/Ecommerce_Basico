package br.edu.ifto.ecommerce.model.dto;

/**
 * DTO enxuto para a listagem de clientes, que exibe apenas id e nome de exibição.
 * Evita expor mais dados do que a tela precisa (ISP).
 */
public record ClienteResumoDTO(
        Long id,
        String nomeExibicao
) {}
