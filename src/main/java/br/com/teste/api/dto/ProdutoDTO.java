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
public class ProdutoDTO {

    private Long id;

    @NotEmpty
    private String descricao;

    @NotEmpty
    private String codigoBarras;

    @NotEmpty
    private Double valorVenda;

    @NotEmpty
    private Double pesoBruto;

    @NotEmpty
    private Double pesoLiquido;
}
