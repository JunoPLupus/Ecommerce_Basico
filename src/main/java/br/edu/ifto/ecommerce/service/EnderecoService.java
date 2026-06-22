package br.edu.ifto.ecommerce.service;

import br.edu.ifto.ecommerce.model.dto.EnderecoResumoDTO;
import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.entity.endereco.Endereco;
import br.edu.ifto.ecommerce.model.repository.EnderecoRepository;
import br.edu.ifto.ecommerce.utils.EnderecoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@AllArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    /**
     * Lista os endereços (entidades) de um dono, usados onde a entidade é necessária
     * (ex.: vínculo na finalização da compra).
     *
     * @param donoId identificador da pessoa dona dos endereços.
     * @return lista de endereços.
     */
    @Transactional(readOnly = true)
    public List<Endereco> listarDoDono(Long donoId) {
        return enderecoRepository.findAllByPessoaId(donoId);
    }

    /**
     * Lista os endereços de um dono em formato resumido (DTO de leitura).
     *
     * @param donoId identificador da pessoa dona dos endereços.
     * @return lista de endereços resumidos.
     */
    @Transactional(readOnly = true)
    public List<EnderecoResumoDTO> listarResumoDoDono(Long donoId) {
        return EnderecoMapper.toResumoList(enderecoRepository.findAllByPessoaId(donoId));
    }

    /**
     * Busca o endereço garantindo que ele pertence ao dono informado. Centraliza a
     * regra de posse, evitando que um usuário manipule endereço de outro.
     *
     * @param id   identificador do endereço.
     * @param dono pessoa que deve ser dona do endereço.
     * @return o endereço, se existir e pertencer ao dono; caso contrário, {@link Optional#empty()}.
     */
    @Transactional(readOnly = true)
    public Optional<Endereco> buscarDoDono(Long id, Pessoa dono) {
        Endereco endereco = enderecoRepository.findById(id);
        if (endereco != null && endereco.pertenceA(dono)) {
            return Optional.of(endereco);
        }
        return Optional.empty();
    }

    /**
     * Cadastra um novo endereço vinculado ao dono informado.
     *
     * @param endereco endereço a ser cadastrado.
     * @param dono     pessoa dona do endereço.
     */
    public void cadastrar(Endereco endereco, Pessoa dono) {
        endereco.setPessoa(dono);
        enderecoRepository.insert(endereco);
    }

    /**
     * Atualiza um endereço, preservando o dono do endereço já existente.
     *
     * @param enderecoEditado   endereço com os dados do formulário.
     * @param enderecoExistente endereço atualmente persistido (fonte do dono).
     */
    public void atualizar(Endereco enderecoEditado, Endereco enderecoExistente) {
        enderecoEditado.setPessoa(enderecoExistente.getPessoa());
        enderecoRepository.update(enderecoEditado);
    }

    /**
     * Exclui um endereço, desde que não haja vendas associadas a ele.
     *
     * @param id identificador do endereço.
     * @return {@code true} se excluído; {@code false} se houver vendas associadas.
     */
    public boolean excluir(Long id) {
        return enderecoRepository.delete(id);
    }
}
