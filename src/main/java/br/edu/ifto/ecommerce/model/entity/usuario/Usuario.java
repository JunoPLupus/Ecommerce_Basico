package br.edu.ifto.ecommerce.model.entity.usuario;

import br.edu.ifto.ecommerce.model.entity.cliente.Pessoa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import br.edu.ifto.ecommerce.model.entity.role.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario implements Serializable, UserDetails {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{erro.usuario.pessoa.obrigatorio}")
    @OneToOne
    private Pessoa pessoa;

    @NotNull(message = "{erro.usuario.login.obrigatorio}")
    @NotBlank(message = "{erro.usuario.login.obrigatorio}")
    @Size(min = 3, message = "{erro.usuario.login.tamanho.min}")
    @Column(unique = true)
    private String login;

    @NotNull(message = "{erro.usuario.senha.obrigatorio}")
    @NotBlank(message = "{erro.usuario.senha.obrigatorio}")
    @Size(min = 6, message = "{erro.usuario.senha.tamanho.min}")
    private String password;

    @NotNull(message = "{erro.usuario.roles.obrigatorio}")
    @Size(min = 1, max = 2, message = "{erro.usuario.roles.tamanho}")
    @ManyToMany
    private List<Role> roles;

    public Usuario(Pessoa pessoa, String login, String password, Role role) {
        this.pessoa = pessoa;
        this.login = login;
        this.password = password;
        this.roles = Arrays.asList(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }
}
