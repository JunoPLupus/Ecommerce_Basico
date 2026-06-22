package br.edu.ifto.ecommerce.service;

import br.edu.ifto.ecommerce.model.dto.VendaDTO;
import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.entity.endereco.Endereco;
import br.edu.ifto.ecommerce.model.entity.venda.Venda;
import br.edu.ifto.ecommerce.model.enums.FormaPagamento;
import br.edu.ifto.ecommerce.model.repository.VendaRepository;
import br.edu.ifto.ecommerce.utils.VendaMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Transactional
@Service
@AllArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;

    /**
     * Lista vendas (DTO de leitura) aplicando filtros de nome do cliente, intervalo
     * de datas e faixa de valor total. O filtro por valor é aplicado em memória.
     *
     * @param nomeCliente trecho do nome/razão social do cliente (pode ser nulo).
     * @param dataInicial data inicial do intervalo (pode ser nula).
     * @param dataFinal   data final do intervalo (pode ser nula).
     * @param valorMinimo valor total mínimo (pode ser nulo).
     * @param valorMaximo valor total máximo (pode ser nulo).
     * @return lista de vendas que atendem aos filtros.
     */
    @Transactional(readOnly = true)
    public List<VendaDTO> listarComFiltros(String nomeCliente, LocalDate dataInicial, LocalDate dataFinal,
                                           Double valorMinimo, Double valorMaximo) {
        List<Venda> vendas = vendaRepository.findAllByDynamicFilters(nomeCliente, dataInicial, dataFinal);
        return VendaMapper.toDTOList(filtrarPorValorTotal(vendas, valorMinimo, valorMaximo));
    }

    /**
     * Lista as vendas/pedidos de um cliente (DTO de leitura).
     *
     * @param clienteId identificador do cliente.
     * @return lista de vendas do cliente.
     */
    @Transactional(readOnly = true)
    public List<VendaDTO> listarPedidosDoCliente(Long clienteId) {
        return VendaMapper.toDTOList(vendaRepository.findAllByClienteId(clienteId));
    }

    /**
     * Busca uma venda pelo id (DTO de leitura).
     *
     * @param id identificador da venda.
     * @return a venda como DTO, ou {@code null} se não existir.
     */
    @Transactional(readOnly = true)
    public VendaDTO buscarPorId(Long id) {
        return VendaMapper.toDTO(vendaRepository.findById(id));
    }

    /**
     * Retorna o pedido como DTO apenas se ele pertencer ao cliente informado; caso
     * contrário, {@code null}. Impede que um cliente acesse pedidos de outro pela URL.
     *
     * @param id        identificador da venda/pedido.
     * @param clienteId identificador do cliente que deve ser dono do pedido.
     * @return a venda como DTO, ou {@code null} se não existir ou não pertencer ao cliente.
     */
    @Transactional(readOnly = true)
    public VendaDTO buscarPedidoDoCliente(Long id, Long clienteId) {
        Venda venda = vendaRepository.findById(id);
        boolean pertenceAoCliente = venda != null && venda.getCliente() != null
                && Objects.equals(venda.getCliente().getId(), clienteId);
        return pertenceAoCliente ? VendaMapper.toDTO(venda) : null;
    }

    /**
     * Converte o carrinho em uma venda persistida, vinculando cliente, endereço,
     * forma de pagamento, data e associando os itens à venda.
     *
     * @param carrinho       carrinho (venda em aberto) com os itens.
     * @param endereco       endereço de entrega.
     * @param formaPagamento forma de pagamento escolhida.
     * @param cliente        cliente que está finalizando a compra.
     * @return a venda persistida (usada apenas para compor a URL de redirecionamento).
     */
    public Venda finalizar(Venda carrinho, Endereco endereco, FormaPagamento formaPagamento, Pessoa cliente) {
        carrinho.setCliente(cliente);
        carrinho.setEndereco(endereco);
        carrinho.setFormaPagamento(formaPagamento);
        carrinho.setData(LocalDateTime.now());
        carrinho.getItens().forEach(item -> item.setVenda(carrinho));
        return vendaRepository.insert(carrinho);
    }

    /**
     * Filtra a lista de vendas pelo valor total em memória, pois o total é calculado
     * a partir dos itens. Para volumes maiores, considerar subquery HQL.
     *
     * @param vendas      vendas a filtrar.
     * @param valorMinimo valor total mínimo (ignorado se nulo ou não positivo).
     * @param valorMaximo valor total máximo (ignorado se nulo ou não positivo).
     * @return vendas dentro da faixa de valor.
     */
    private List<Venda> filtrarPorValorTotal(List<Venda> vendas, Double valorMinimo, Double valorMaximo) {
        Stream<Venda> stream = vendas.stream();
        if (valorMinimo != null && valorMinimo > 0.0) stream = stream.filter(v -> v.total() >= valorMinimo);
        if (valorMaximo != null && valorMaximo > 0.0) stream = stream.filter(v -> v.total() <= valorMaximo);
        return stream.toList();
    }
}
