package br.edu.ifto.ecommerce.service;

import br.edu.ifto.ecommerce.model.dto.EditarPerfilDTO;
import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.repository.ClienteRepository;
import br.edu.ifto.ecommerce.model.repository.UsuarioRepository;
import br.edu.ifto.ecommerce.utils.PerfilMapper;
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
     * Atualiza os dados pessoais da Pessoa e, opcionalmente, a senha do Usuário.
     * A Pessoa é carregada dentro da transação para que o JPA detecte as mudanças
     * automaticamente via dirty checking, sem necessidade de chamada explícita a merge().
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
