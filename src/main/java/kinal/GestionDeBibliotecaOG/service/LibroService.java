package kinal.GestionDeBibliotecaOG.service;

import kinal.GestionDeBibliotecaOG.dto.LibroDTO;
import kinal.GestionDeBibliotecaOG.entity.Libro;
import kinal.GestionDeBibliotecaOG.exception.ApiException;
import kinal.GestionDeBibliotecaOG.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LibroService {

    private final LibroRepository libroRepository;

    @Transactional(readOnly = true)
    public Page<LibroDTO> obtenerTodos(String titulo, String categoria, Pageable pageable) {
        Page<Libro> libros;
        if (titulo != null && categoria != null) {
            libros = libroRepository.findByTituloContainingIgnoreCaseAndCategoriaContainingIgnoreCase(titulo, categoria, pageable);
        } else if (titulo != null) {
            libros = libroRepository.findByTituloContainingIgnoreCase(titulo, pageable);
        } else if (categoria != null) {
            libros = libroRepository.findByCategoriaContainingIgnoreCase(categoria, pageable);
        } else {
            libros = libroRepository.findAll(pageable);
        }
        return libros.map(this::mapToDTO);
    }

    @Transactional(readOnly = true)
    public LibroDTO obtenerPorId(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new ApiException("Libro no encontrado", HttpStatus.NOT_FOUND));
        return mapToDTO(libro);
    }

    @Transactional
    public LibroDTO crearLibro(LibroDTO dto) {
        if (libroRepository.existsByIsbn(dto.getIsbn())) {
            throw new ApiException("Ya existe un libro registrado con este ISBN", HttpStatus.BAD_REQUEST);
        }

        Libro libro = Libro.builder()
                .isbn(dto.getIsbn())
                .titulo(dto.getTitulo())
                .autor(dto.getAutor())
                .categoria(dto.getCategoria())
                .stockTotal(dto.getStockTotal())
                .stockDisponible(dto.getStockTotal())
                .build();

        return mapToDTO(libroRepository.save(libro));
    }

    @Transactional
    public LibroDTO actualizarLibro(Long id, LibroDTO dto) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new ApiException("Libro no encontrado", HttpStatus.NOT_FOUND));

        libro.setTitulo(dto.getTitulo());
        libro.setAutor(dto.getAutor());
        libro.setCategoria(dto.getCategoria());

        if (dto.getStockTotal() != null) {
            int diferenciaStock = dto.getStockTotal() - libro.getStockTotal();
            int nuevoStockDisponible = libro.getStockDisponible() + diferenciaStock;

            if (nuevoStockDisponible < 0) {
                throw new ApiException("No se puede reducir el stock total por debajo de los libros prestados actualmente", HttpStatus.BAD_REQUEST);
            }

            libro.setStockTotal(dto.getStockTotal());
            libro.setStockDisponible(nuevoStockDisponible);
        }

        return mapToDTO(libroRepository.save(libro));
    }

    @Transactional
    public void eliminarLibro(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new ApiException("Libro no encontrado", HttpStatus.NOT_FOUND);
        }
        libroRepository.deleteById(id);
    }

    private LibroDTO mapToDTO(Libro libro) {
        return LibroDTO.builder()
                .id(libro.getId())
                .isbn(libro.getIsbn())
                .titulo(libro.getTitulo())
                .autor(libro.getAutor())
                .categoria(libro.getCategoria())
                .stockTotal(libro.getStockTotal())
                .stockDisponible(libro.getStockDisponible())
                .build();
    }
}