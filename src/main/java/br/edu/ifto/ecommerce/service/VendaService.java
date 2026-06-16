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

    @Transactional(readOnly = true)
    public List<VendaDTO> listarComFiltros(String nomeCliente, LocalDate dataInicial, LocalDate dataFinal,
                                           Double valorMinimo, Double valorMaximo) {
        List<Venda> vendas = vendaRepository.findAllByDynamicFilters(nomeCliente, dataInicial, dataFinal);
        return VendaMapper.toDTOList(filtrarPorValorTotal(vendas, valorMinimo, valorMaximo));
    }

    @Transactional(readOnly = true)
    public List<VendaDTO> listarPedidosDoCliente(Long clienteId) {
        return VendaMapper.toDTOList(vendaRepository.findAllByClienteId(clienteId));
    }

    @Transactional(readOnly = true)
    public VendaDTO buscarPorId(Long id) {
        return VendaMapper.toDTO(vendaRepository.findById(id));
    }

    /**
     * Retorna o pedido como DTO apenas se ele pertencer ao cliente informado; caso
     * contrário, {@code null}. Impede que um cliente acesse pedidos de outro pela URL.
     */
    @Transactional(readOnly = true)
    public VendaDTO buscarPedidoDoCliente(Long id, Long clienteId) {
        Venda venda = vendaRepository.findById(id);
        boolean pertenceAoCliente = venda != null && venda.getCliente() != null
                && Objects.equals(venda.getCliente().getId(), clienteId);
        return pertenceAoCliente ? VendaMapper.toDTO(venda) : null;
    }

    /**
     * Converte o carrinho em uma venda persistida. Retorna a entidade salva (usada
     * apenas para compor a URL de redirecionamento, não é renderizada).
     */
    public Venda finalizar(Venda carrinho, Endereco endereco, FormaPagamento formaPagamento, Pessoa cliente) {
        carrinho.setCliente(cliente);
        carrinho.setEndereco(endereco);
        carrinho.setFormaPagamento(formaPagamento);
        carrinho.setData(LocalDateTime.now());
        carrinho.getItens().forEach(item -> item.setVenda(carrinho));
        return vendaRepository.insert(carrinho);
    }

    /*
     * Filtro de valor aplicado em memória, pois o total é calculado a partir dos itens.
     * Para volumes maiores, considerar subquery HQL.
     */
    private List<Venda> filtrarPorValorTotal(List<Venda> vendas, Double valorMinimo, Double valorMaximo) {
        Stream<Venda> stream = vendas.stream();
        if (valorMinimo != null && valorMinimo > 0.0) stream = stream.filter(v -> v.total() >= valorMinimo);
        if (valorMaximo != null && valorMaximo > 0.0) stream = stream.filter(v -> v.total() <= valorMaximo);
        return stream.toList();
    }
}
