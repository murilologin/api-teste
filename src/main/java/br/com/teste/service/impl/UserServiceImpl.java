package br.com.teste.service.impl;

import br.com.teste.exception.BusinessException;
import br.com.teste.service.UserService;
import br.com.teste.model.entity.Usuario;
import br.com.teste.model.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    public UserServiceImpl(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public Usuario save(Usuario usuario) {
        if (repository.existsByUsername(usuario.getUsername())) {
            throw new BusinessException("Usuário já cadastrado");
        }
        return repository.save(usuario);
    }

    @Override
    public Optional<Usuario> getUserByUsername(String username) {
        return Optional.of(repository.findByUsername(username));
    }

    @Override
    public void delete(Usuario usuario) {
        repository.delete(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public Page<Usuario> find(Usuario filter, Pageable pageRequest) {
        return null;
    }

    @Override
    public Page<Usuario> findAll(Usuario filter, Pageable pageRequest) {
        Example<Usuario> example = Example.of(filter,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING)

        );
        return repository.findAll(example, pageRequest);
    }
}
