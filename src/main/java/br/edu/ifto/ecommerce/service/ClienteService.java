package br.edu.ifto.ecommerce.service;

import br.edu.ifto.ecommerce.model.dto.ClienteResumoDTO;
import br.edu.ifto.ecommerce.model.dto.PessoaDTO;
import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import br.edu.ifto.ecommerce.model.entity.role.Role;
import br.edu.ifto.ecommerce.model.entity.usuario.Usuario;
import br.edu.ifto.ecommerce.model.repository.ClienteRepository;
import br.edu.ifto.ecommerce.model.repository.RoleRepository;
import br.edu.ifto.ecommerce.model.repository.UsuarioRepository;
import br.edu.ifto.ecommerce.utils.PessoaMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.edu.ifto.ecommerce.utils.Roles.ROLE_USER;

@Transactional
@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void insert(Pessoa pessoa, String login, String password) {
        Pessoa pessoaSalva = clienteRepository.insert(pessoa);

        Role roleUser = roleRepository.findByNome(ROLE_USER);
        String senhaCriptografada = passwordEncoder.encode(password);
        Usuario usuarioNovo = new Usuario(pessoaSalva, login, senhaCriptografada, roleUser);

        usuarioRepository.insert(usuarioNovo);
    }

    @Transactional(readOnly = true)
    public List<ClienteResumoDTO> listar(String nome) {
        List<Pessoa> clientes = (nome != null && !nome.isEmpty())
                ? clienteRepository.findAllByNomeOuRazaoSocial(nome)
                : clienteRepository.findAll();
        return PessoaMapper.toResumoList(clientes);
    }

    /**
     * @return o cliente como DTO de leitura, ou {@code null} se não existir.
     */
    @Transactional(readOnly = true)
    public PessoaDTO buscarDetalhe(Long id) {
        return PessoaMapper.toDTO(clienteRepository.findById(id));
    }
}
