package kinal.GestionDeBibliotecaOG.controller;

import kinal.GestionDeBibliotecaOG.dto.LibroDTO;
import kinal.GestionDeBibliotecaOG.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/libros")
public class LibroController {

    private final LibroService libroService;

    // Constructor explícito para inyección de dependencias
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public ResponseEntity<Page<LibroDTO>> listarLibros(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String categoria,
            Pageable pageable) {
        return ResponseEntity.ok(libroService.obtenerTodos(titulo, categoria, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.obtenerPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<LibroDTO> crearLibro(@Valid @RequestBody LibroDTO libroDTO) {
        return new ResponseEntity<>(libroService.crearLibro(libroDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<LibroDTO> actualizarLibro(@PathVariable Long id, @Valid @RequestBody LibroDTO libroDTO) {
        return ResponseEntity.ok(libroService.actualizarLibro(id, libroDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        libroService.eliminarLibro(id);
        return ResponseEntity.noContent().build();
    }
}