package br.com.teste.api.resource;

import br.com.teste.api.dto.ClienteDTO;
import br.com.teste.model.entity.Cliente;
import br.com.teste.service.ClienteService;
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
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
@Api("Teste API")
@Slf4j
public class ClienteController {

    private final ClienteService service;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Adicionar cliente")
    public ClienteDTO create(@RequestBody @Valid ClienteDTO dto) {
        dto.setId(0L);
        log.info("criando cliente id: {}", dto.getId());
        Cliente entity = modelMapper.map(dto, Cliente.class);
        entity = service.save(entity);
        return modelMapper.map(entity, ClienteDTO.class);
    }
    @PutMapping("{id}")
    @ApiOperation("Alterar cliente pelo id")
    public ClienteDTO update(@PathVariable Long id, @RequestBody ClienteDTO dto) {
        dto.setId(id);
        log.info("alterando cliente id: {}", id);
        return service.getById(id).map(cliente -> {
            cliente.setNome(dto.getNome());
            cliente.setFantasia(dto.getFantasia());
            cliente.setDocumento(dto.getDocumento());
            cliente.setEndereco(dto.getEndereco());
            service.update(cliente);
            return modelMapper.map(cliente, ClienteDTO.class);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("{id}")
    @ApiOperation("Busca cliente pelo id")
    public ClienteDTO get(@PathVariable Long id) {
        log.info("buscando cliente pelo id: {}", id);
        return service
                .getById(id)
                .map( cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Exclui cliente pelo id")
    public void delete(@PathVariable Long id) {
        log.info("excluindo cliente id: {}", id);
        Cliente cliente = service.getById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        service.delete(cliente);
    }

    @GetMapping
    @ApiOperation("Lista paginada")
    public Page<ClienteDTO> find(ClienteDTO dto, Pageable pageRequest) {
        Cliente filter = modelMapper.map(dto, Cliente.class);
        Page<Cliente> result = service.find(filter, pageRequest);
        List<ClienteDTO> list = result.getContent()
                .stream()
                .map( entity -> modelMapper.map(entity, ClienteDTO.class))
                .collect( Collectors.toList());
        return new PageImpl<ClienteDTO>( list, pageRequest, result.getTotalElements());
    }


}
