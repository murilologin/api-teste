package br.com.teste.api.resource;

import br.com.teste.api.dto.UserDTO;
import br.com.teste.model.entity.Usuario;
import br.com.teste.service.UserService;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Api("Teste API")
@Slf4j
public class UserController {

    private final UserService service;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Criar usuário")
    public UserDTO create(@RequestBody @Valid UserDTO dto) {
        log.info("criando usuário: {}", dto.getUsername());
        Usuario entity = modelMapper.map(dto, Usuario.class);
        entity = service.save(entity);
        return modelMapper.map(entity, UserDTO.class);
    }
    @PutMapping("{username}")
    @ApiOperation("Alterar um usuário pelo id")
    public UserDTO update(@PathVariable String username, @RequestBody UserDTO dto) {
        log.info("Alterando usuário: {}", username);
        return service.getUserByUsername(username).map(usr -> {
            usr.setName(dto.getName());
            usr.setPassword(dto.getPassword());
            usr.setEmail(dto.getEmail());
            service.update(usr);
            return modelMapper.map(usr, UserDTO.class);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("{username}")
    @ApiOperation("Busca usuário pelo username")
    public UserDTO get(@PathVariable String username) {
        log.info("Buscando usuario: {}", username);
        try {
            return service
                    .getUserByUsername(username)
                    .map(book -> modelMapper.map(book, UserDTO.class))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
    }

    @DeleteMapping("{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Excluir usuário pelo username")
    public void delete(@PathVariable String username) {
        try {
            log.info("excluindo usuario: {}", username);
            Usuario usuario = service.getUserByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            service.delete(usuario);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
    }

    @GetMapping
    @ApiOperation("Busca paginada de usuários")
    public Page<UserDTO> find(UserDTO dto, Pageable pageRequest) {
        Usuario filter = modelMapper.map(dto, Usuario.class);
        Page<Usuario> result = service.findAll(filter, pageRequest);
        List<UserDTO> list = result.getContent()
                .stream()
                .map( entity -> modelMapper.map(entity, UserDTO.class))
                .collect( Collectors.toList());
        return new PageImpl<UserDTO>( list, pageRequest, result.getTotalElements());
    }

}
