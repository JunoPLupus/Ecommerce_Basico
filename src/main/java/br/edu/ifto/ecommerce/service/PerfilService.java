package br.edu.ifto.ecommerce.service;

import br.edu.ifto.ecommerce.model.dto.EditarPerfilDTO;
import br.edu.ifto.ecommerce.model.dto.PessoaDTO;
import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.repository.ClienteRepository;
import br.edu.ifto.ecommerce.model.repository.UsuarioRepository;
import br.edu.ifto.ecommerce.utils.mappers.PerfilMapper;
import br.edu.ifto.ecommerce.utils.mappers.PessoaMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class PerfilService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Busca os dados do perfil para exibição.
     *
     * @param pessoaId identificador da pessoa logada.
     * @return o perfil como DTO de leitura.
     */
    @Transactional(readOnly = true)
    public PessoaDTO buscarPerfil(Long pessoaId) {
        return PessoaMapper.toDTO(clienteRepository.findById(pessoaId));
    }

    /**
     * Busca os dados do perfil no formato do formulário de edição.
     *
     * @param pessoaId identificador da pessoa logada.
     * @return DTO de edição preenchido.
     */
    @Transactional(readOnly = true)
    public EditarPerfilDTO buscarParaEdicao(Long pessoaId) {
        return PerfilMapper.toDTO(clienteRepository.findById(pessoaId));
    }

    /**
     * Atualiza os dados pessoais da Pessoa e, opcionalmente, a senha do Usuário.
     * A Pessoa é carregada dentro da transação para que o JPA detecte as mudanças
     * automaticamente via dirty checking, sem necessidade de chamada explícita a merge().
     *
     * @param dto       dados do formulário (nome/razão social, telefone e senha opcional).
     * @param pessoaId  identificador da pessoa a ser atualizada.
     * @param usuarioId identificador do usuário (para troca de senha).
     */
    public void atualizar(EditarPerfilDTO dto, Long pessoaId, Long usuarioId) {
        Pessoa pessoa = clienteRepository.findById(pessoaId);
        PerfilMapper.aplicarDTO(dto, pessoa);

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            String senhaCriptografada = passwordEncoder.encode(dto.getSenha());
            usuarioRepository.updateSenha(usuarioId, senhaCriptografada);
        }
    }
}
