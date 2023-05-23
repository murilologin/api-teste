package br.com.teste.model.repository;

import br.com.teste.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsById(Long id);

    Optional<Produto> findById(Long id);
}
