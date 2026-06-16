package br.edu.ifto.ecommerce.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @Size(min = 6, message = "{erro.usuario.senha.tamanho.min}")
    private String senha;
}
