package br.edu.ifto.ecommerce.model.dto;

import br.edu.ifto.ecommerce.model.enums.Estado;

/**
 * DTO de leitura de endereço, usado na listagem do perfil do cliente.
 * Mantém a view desacoplada da entidade JPA {@code Endereco}.
 */
public record EnderecoResumoDTO(
        Long id,
        String logradouro,
        String numero,
        String bairro,
        String cidade,
        Estado estado,
        String cep
) {}
