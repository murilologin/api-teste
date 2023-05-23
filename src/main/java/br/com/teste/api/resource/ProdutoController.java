package br.com.teste.api.resource;


import br.com.teste.api.dto.ProdutoDTO;
import br.com.teste.model.entity.Produto;
import br.com.teste.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produto")
@RequiredArgsConstructor
@Api("Teste API")
@Slf4j
public class ProdutoController {

    private final ProdutoService service;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Adicionar produto")
    public ProdutoDTO create(@RequestBody @Valid ProdutoDTO dto) {
        dto.setId(0L);
        log.info("criando produto id: {}", dto.getId());
        Produto entity = modelMapper.map(dto, Produto.class);
        entity = service.save(entity);
        return modelMapper.map(entity, ProdutoDTO.class);
    }
    @PutMapping("{id}")
    @ApiOperation("Alterar produto pelo id")
    public ProdutoDTO update(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        dto.setId(id);
        log.info("alterando produto id: {}", id);
        return service.getById(id).map(produto -> {
            produto.setCodigoBarras(dto.getCodigoBarras());
            produto.setDescricao(dto.getDescricao());
            produto.setPesoBruto(dto.getPesoBruto());
            produto.setPesoLiquido(dto.getPesoLiquido());
            produto.setValorVenda(dto.getValorVenda());
            service.update(produto);
            return modelMapper.map(produto, ProdutoDTO.class);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("{id}")
    @ApiOperation("Busca produto pelo id")
    public ProdutoDTO get(@PathVariable Long id) {
        log.info("buscando produto pelo id: {}", id);
        return service
                .getById(id)
                .map( produto -> modelMapper.map(produto, ProdutoDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Exclui produto pelo id")
    public void delete(@PathVariable Long id) {
        log.info("excluindo produto id: {}", id);
        Produto produto = service.getById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        service.delete(produto);
    }

    @GetMapping
    @ApiOperation("Lista paginada")
    public Page<ProdutoDTO> find(ProdutoDTO dto, Pageable pageRequest) {
        Produto filter = modelMapper.map(dto, Produto.class);
        Page<Produto> result = service.find(filter, pageRequest);
        List<ProdutoDTO> list = result.getContent()
                .stream()
                .map( entity -> modelMapper.map(entity, ProdutoDTO.class))
                .collect( Collectors.toList());
        return new PageImpl<ProdutoDTO>( list, pageRequest, result.getTotalElements());
    }


}
