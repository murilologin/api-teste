package br.com.teste.service.impl;

import br.com.teste.exception.BusinessException;
import br.com.teste.model.entity.Produto;
import br.com.teste.model.repository.ProdutoRepository;
import br.com.teste.service.ProdutoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private ProdutoRepository repository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.repository = produtoRepository;
    }

    @Override
    public Produto save(Produto produto) {
        if (repository.existsById(produto.getId())) {
            throw new BusinessException("Produto já cadastrado!");
        }
        return repository.save(produto);
    }

    @Override
    public Optional<Produto> getById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(Produto produto) {
        if (produto == null || produto.getId() == null) {
            throw new IllegalArgumentException("Produto não poder ser nulo");
        }
        this.repository.delete(produto);
    }

    @Override
    public Produto update(Produto produto) {
        //faz essa verificação pq só pode dar update se o produto já existir
        if (produto == null || produto.getId() == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        //o save do jpa tanto atualiza quanto inclui
        return this.repository.save(produto);
    }

    @Override
    public Page<Produto> find(Produto filter, Pageable pageRequest) {
        Example<Produto> example = Example.of(filter,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING)

        );
        return repository.findAll(example, pageRequest);
    }

    @Override
    public Optional<Produto> getProdutoById(Long id) {
        return repository.findById(id);
    }
}
