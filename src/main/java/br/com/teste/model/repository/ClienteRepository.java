package br.com.teste.model.repository;

import br.com.teste.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsById(Long id);

    Optional<Cliente> findById(Long id);
}
