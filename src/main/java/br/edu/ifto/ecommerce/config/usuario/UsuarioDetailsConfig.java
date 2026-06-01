package br.edu.ifto.ecommerce.config.usuario;

import br.edu.ifto.ecommerce.model.entity.usuario.Usuario;
import br.edu.ifto.ecommerce.model.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Repository
public class UsuarioDetailsConfig implements UserDetailsService {

    UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = repository.findByLogin(login);
        if(usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
        return new User(usuario.getLogin(),
                usuario.getPassword(),
                true,
                true,
                true,
                true,
                usuario.getAuthorities());
    }
}
