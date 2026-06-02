package br.edu.ifto.ecommerce.config.usuario;

import br.edu.ifto.ecommerce.model.entity.usuario.Usuario;
import br.edu.ifto.ecommerce.model.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Service
public class UsuarioDetailsConfig implements UserDetailsService {

    UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = repository.findByLogin(login);
        if(usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
        return usuario;
    }
}
