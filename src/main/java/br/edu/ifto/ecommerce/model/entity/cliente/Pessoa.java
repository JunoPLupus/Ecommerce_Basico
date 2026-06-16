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

    /**
     * Telefone BR com separadores opcionais (espaço, "-", "()"):
     * DDD de 2 ou 3 dígitos + número de 8 ou 9 dígitos (o 9 extra do celular).
     * Aceita, ex.: 6332165400, (63) 3216-5400, 63 99216 5400, (63)99216-5400.
     */
    public static final String TELEFONE_REGEX = "\\(?\\d{2,3}\\)?[\\s-]?\\d{4,5}[\\s-]?\\d{4}";

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotBlank (message = "{erro.pessoa.email.obrigatorio}")
    @Email (message = "{erro.pessoa.email.invalido}")
    @Column(unique = true)
    private String email;

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

    /**
     * Primeiro nome (ou primeira palavra da razão social), usado na saudação do menu.
     */
    @Override
    public String getNomeCurto() {
        return getNomeExibicao().split(" ")[0];
    }

    @Override
    public abstract String getDocumento();

    @Override
    public abstract String getDocumentoMascarado();
}
