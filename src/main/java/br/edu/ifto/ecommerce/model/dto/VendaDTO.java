package br.edu.ifto.ecommerce.model.dto;

import br.edu.ifto.ecommerce.model.enums.FormaPagamento;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de leitura de venda/pedido, usado em todas as telas de exibição
 * (listas, detalhes, carrinho e finalização). Os totais já vêm calculados.
 * cliente, endereco e formaPagamento podem ser nulos no contexto de carrinho.
 */
public record VendaDTO(
        Long id,
        LocalDateTime data,
        ClienteResumoDTO cliente,
        EnderecoResumoDTO endereco,
        FormaPagamento formaPagamento,
        List<ItemVendaDTO> itens,
        int qtdItensTotal,
        Double total
) {}
