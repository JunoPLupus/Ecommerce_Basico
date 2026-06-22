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

    public static final int LOGIN_TAMANHO_MIN = 3;
    public static final int SENHA_TAMANHO_MIN = 6;

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{erro.usuario.pessoa.obrigatorio}")
    @OneToOne
    private Pessoa pessoa;

    @NotNull(message = "{erro.usuario.login.obrigatorio}")
    @NotBlank(message = "{erro.usuario.login.obrigatorio}")
    @Size(min = LOGIN_TAMANHO_MIN, message = "{erro.usuario.login.tamanho.min}")
    @Column(unique = true)
    private String login;

    @NotNull(message = "{erro.usuario.senha.obrigatorio}")
    @NotBlank(message = "{erro.usuario.senha.obrigatorio}")
    @Size(min = SENHA_TAMANHO_MIN, message = "{erro.usuario.senha.tamanho.min}")
    private String password;

    @NotNull(message = "{erro.usuario.roles.obrigatorio}")
    @Size(min = 1, max = 2, message = "{erro.usuario.roles.tamanho}")
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    public Usuario(Pessoa pessoa, String login, String password, Role role) {
        this.pessoa = pessoa;
        this.login = login;
        this.password = password;
        this.roles = Arrays.asList(role);
    }

    public String getNomeCurto() {
        return pessoa != null ? pessoa.getNomeCurto() : login;
    }

    public String getNomeCompleto() {
        return pessoa != null ? pessoa.getNomeExibicao() : login;
    }

    public String getAvatarLetter() {
        String nome = getNomeCurto();
        return nome.isEmpty() ? "?" : String.valueOf(nome.charAt(0)).toUpperCase();
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
