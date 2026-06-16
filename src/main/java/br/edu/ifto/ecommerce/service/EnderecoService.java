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

    @Transactional(readOnly = true)
    public List<Endereco> listarDoDono(Long donoId) {
        return enderecoRepository.findAllByPessoaId(donoId);
    }

    @Transactional(readOnly = true)
    public List<EnderecoResumoDTO> listarResumoDoDono(Long donoId) {
        return EnderecoMapper.toResumoList(enderecoRepository.findAllByPessoaId(donoId));
    }

    /**
     * Busca o endereço garantindo que ele pertence ao dono informado.
     * Centraliza a regra de posse, antes repetida em vários pontos do controller.
     */
    @Transactional(readOnly = true)
    public Optional<Endereco> buscarDoDono(Long id, Pessoa dono) {
        Endereco endereco = enderecoRepository.findById(id);
        if (endereco != null && endereco.pertenceA(dono)) {
            return Optional.of(endereco);
        }
        return Optional.empty();
    }

    public void cadastrar(Endereco endereco, Pessoa dono) {
        endereco.setPessoa(dono);
        enderecoRepository.insert(endereco);
    }

    public void atualizar(Endereco enderecoEditado, Endereco enderecoExistente) {
        enderecoEditado.setPessoa(enderecoExistente.getPessoa());
        enderecoRepository.update(enderecoEditado);
    }

    public boolean excluir(Long id) {
        return enderecoRepository.delete(id);
    }
}
