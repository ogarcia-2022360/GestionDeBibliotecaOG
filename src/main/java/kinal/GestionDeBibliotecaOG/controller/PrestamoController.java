package kinal.GestionDeBibliotecaOG.controller;

import kinal.GestionDeBibliotecaOG.dto.PrestamoDTO;
import kinal.GestionDeBibliotecaOG.dto.PrestamoRequest;
import kinal.GestionDeBibliotecaOG.service.PrestamoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prestamos")
@RequiredArgsConstructor
public class PrestamoController {

    private final PrestamoService prestamoService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<PrestamoDTO> registrarPrestamo(@Valid @RequestBody PrestamoRequest request) {
        return new ResponseEntity<>(prestamoService.registrarPrestamo(request), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/devolucion")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<PrestamoDTO> registrarDevolucion(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.registrarDevolucion(id));
    }

    @GetMapping("/mis-prestamos")
    @PreAuthorize("hasAuthority('LECTOR')")
    public ResponseEntity<List<PrestamoDTO>> obtenerMisPrestamos(Authentication authentication) {
        return ResponseEntity.ok(prestamoService.obtenerMisPrestamos(authentication.getName()));
    }

    @GetMapping("/atrasados")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<List<PrestamoDTO>> obtenerPrestamosAtrasados() {
        return ResponseEntity.ok(prestamoService.obtenerPrestamosAtrasados());
    }
}