package br.com.teste.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private Long id;

    @NotEmpty
    private String nome;

    @NotEmpty
    private String fantasia;

    @NotEmpty
    private String documento;

    @NotEmpty
    private String endereco;
}
