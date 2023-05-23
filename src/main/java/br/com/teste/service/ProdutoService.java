package br.com.teste.service;

import br.com.teste.model.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProdutoService {

    Produto save(Produto any);

    Optional<Produto> getById(Long id);

    void delete(Produto produto);

    Produto update(Produto produto);

    Page<Produto> find(Produto filter, Pageable pageRequest );

    Optional<Produto> getProdutoById(Long id);

}
