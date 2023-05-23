package br.com.teste.service.impl;

import br.com.teste.exception.BusinessException;
import br.com.teste.model.entity.Cliente;
import br.com.teste.model.repository.ClienteRepository;
import br.com.teste.service.ClienteService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository repository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.repository = clienteRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        if (repository.existsById(cliente.getId())) {
            throw new BusinessException("Cliente já cadastrado!");
        }
        return repository.save(cliente);
    }

    @Override
    public Optional<Cliente> getById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(Cliente cliente) {
        if (cliente == null || cliente.getId() == null) {
            throw new IllegalArgumentException("Cliente não poder ser nulo");
        }
        this.repository.delete(cliente);
    }

    @Override
    public Cliente update(Cliente cliente) {
        //faz essa verificação pq só pode dar update se o cliente já existir
        if (cliente == null || cliente.getId() == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
        //o save do jpa tanto atualiza quanto inclui
        return this.repository.save(cliente);
    }

    @Override
    public Page<Cliente> find(Cliente filter, Pageable pageRequest) {
        Example<Cliente> example = Example.of(filter,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING)

        );
        return repository.findAll(example, pageRequest);
    }

    @Override
    public Optional<Cliente> getClienteById(Long id) {
        return repository.findById(id);
    }
}
