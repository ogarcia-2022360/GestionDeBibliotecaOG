package kinal.GestionDeBibliotecaOG.repository;

import kinal.GestionDeBibliotecaOG.entity.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    Page<Libro> findByTituloContainingIgnoreCase(
            String titulo,
            Pageable pageable
    );

    Page<Libro> findByCategoriaContainingIgnoreCase(
            String categoria,
            Pageable pageable
    );

    Page<Libro> findByTituloContainingIgnoreCaseAndCategoriaContainingIgnoreCase(
            String titulo,
            String categoria,
            Pageable pageable
    );
}
