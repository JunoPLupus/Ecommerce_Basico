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

    /**
     * Cadastra um novo cliente: persiste a pessoa e cria o usuário associado com o
     * papel {@code ROLE_USER} e a senha codificada.
     *
     * @param pessoa   dados da pessoa (física ou jurídica).
     * @param login    login do novo usuário.
     * @param password senha em texto puro (será codificada).
     */
    public void insert(Pessoa pessoa, String login, String password) {
        Pessoa pessoaSalva = clienteRepository.insert(pessoa);

        Role roleUser = roleRepository.findByNome(ROLE_USER);
        String senhaCriptografada = passwordEncoder.encode(password);
        Usuario usuarioNovo = new Usuario(pessoaSalva, login, senhaCriptografada, roleUser);

        usuarioRepository.insert(usuarioNovo);
    }

    /**
     * Lista clientes em formato resumido, filtrando por nome/razão social quando
     * o termo é informado.
     *
     * @param nome termo de busca (pode ser nulo ou vazio para listar todos).
     * @return lista de clientes resumidos.
     */
    @Transactional(readOnly = true)
    public List<ClienteResumoDTO> listar(String nome) {
        List<Pessoa> clientes = (nome != null && !nome.isEmpty())
                ? clienteRepository.findAllByNomeOuRazaoSocial(nome)
                : clienteRepository.findAll();
        return PessoaMapper.toResumoList(clientes);
    }

    /**
     * Busca os dados completos de um cliente para exibição.
     *
     * @param id identificador do cliente.
     * @return o cliente como DTO de leitura, ou {@code null} se não existir.
     */
    @Transactional(readOnly = true)
    public PessoaDTO buscarDetalhe(Long id) {
        return PessoaMapper.toDTO(clienteRepository.findById(id));
    }

    /**
     * Obtém o login do usuário vinculado a um cliente.
     *
     * @param pessoaId identificador da pessoa/cliente.
     * @return o login do cliente, ou {@code null} se não houver usuário vinculado.
     */
    @Transactional(readOnly = true)
    public String buscarLoginDoCliente(Long pessoaId) {
        Usuario usuario = usuarioRepository.findByPessoaId(pessoaId);
        return usuario != null ? usuario.getLogin() : null;
    }
}
