package br.edu.ifto.ecommerce.model.entity.role;

import br.edu.ifto.ecommerce.model.entity.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Role implements Serializable, GrantedAuthority {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{erro.role.nome.obrigatorio}}}")
    @NotBlank(message = "{erro.role.nome.obrigatorio}}")
    @Pattern(regexp = "ROLE_ADMIN|ROLE_USER", message = "{erro.role.nome.pattern}")
    @Column(unique = true)
    private String nome;

    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios;

    public Role(){ this.usuarios = new ArrayList<>(); }

    @Override
    public String getAuthority() {
        return nome;
    }
}
