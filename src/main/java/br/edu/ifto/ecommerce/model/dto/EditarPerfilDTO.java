package br.edu.ifto.ecommerce.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditarPerfilDTO {

    @NotBlank(message = "{erro.pessoafisica.nome.obrigatorio}")
    private String nomeOuRazaoSocial;

    private String telefone;

    /**
     * Senha opcional: quando em branco, mantém a atual. O tamanho mínimo é validado
     * condicionalmente no controller (apenas quando informada), por isso não há
     * {@code @Size} aqui — caso contrário um campo vazio seria rejeitado.
     */
    private String senha;
}
