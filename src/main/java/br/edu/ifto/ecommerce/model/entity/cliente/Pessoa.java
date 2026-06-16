package br.edu.ifto.ecommerce.model.entity.cliente;

import br.edu.ifto.ecommerce.model.entity.endereco.Endereco;
import br.edu.ifto.ecommerce.model.view.PessoaView;
import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Pessoa implements PessoaView {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotBlank (message = "{erro.pessoa.email.obrigatorio}")
    @Email (message = "{erro.pessoa.email.invalido}")
    @Column(unique = true)
    private String email;

    @NotBlank (message = "{erro.pessoa.telefone.obrigatorio}")
    private String telefone;

    @OneToMany (mappedBy = "pessoa", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos;

    /**
     * Verifica se o objeto é uma instância de PessoaFisica.
     * @return boolean - `true` se instância de PessoaFisica,
     * caso contrário, retorna `false`.
     */
    @Override
    public boolean isPF() {
        return this instanceof PessoaFisica;
    }

    @Override
    public abstract String getNomeExibicao();

    @Override
    public String getNomeCurto() {
        String[] partes = getNomeExibicao().split(" ");
        if (partes.length >= 2) return partes[0] + " " + partes[1];
        return partes[0];
    }

    @Override
    public abstract String getDocumento();

    @Override
    public abstract String getDocumentoMascarado();
}
