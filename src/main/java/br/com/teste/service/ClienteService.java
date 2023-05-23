package br.com.teste.service;

import br.com.teste.model.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClienteService {

    Cliente save(Cliente cliente);

    Optional<Cliente> getById(Long id);

    void delete(Cliente cliente);

    Cliente update(Cliente cliente);

    Page<Cliente> find(Cliente filter, Pageable pageRequest );

    Optional<Cliente> getClienteById(Long id);
}
