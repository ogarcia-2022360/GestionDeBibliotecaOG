package kinal.GestionDeBibliotecaOG.service;

import kinal.GestionDeBibliotecaOG.dto.PrestamoDTO;
import kinal.GestionDeBibliotecaOG.dto.PrestamoRequest;
import kinal.GestionDeBibliotecaOG.entity.*;
import kinal.GestionDeBibliotecaOG.exception.ApiException;
import kinal.GestionDeBibliotecaOG.repository.LibroRepository;
import kinal.GestionDeBibliotecaOG.repository.PrestamoRepository;
import kinal.GestionDeBibliotecaOG.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;

    @Transactional
    public PrestamoDTO registrarPrestamo(PrestamoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new ApiException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        Libro libro = libroRepository.findById(request.getLibroId())
                .orElseThrow(() -> new ApiException("Libro no encontrado", HttpStatus.NOT_FOUND));

        // Regla 1: Validar si el usuario tiene préstamos atrasados al intentar solicitar uno nuevo
        List<Prestamo> prestamosActivos = prestamoRepository.findByUsuarioAndEstado(usuario, EstadoPrestamo.ACTIVO);
        boolean tieneAtraso = prestamosActivos.stream()
                .anyMatch(p -> LocalDate.now().isAfter(p.getFechaDevolucionEsperada()));

        if (tieneAtraso) {
            usuario.setEstado(EstadoUsuario.SANCIONADO);
            usuarioRepository.save(usuario);
            throw new ApiException("El usuario tiene préstamos atrasados y ha sido SANCIONADO", HttpStatus.FORBIDDEN);
        }

        if (usuario.getEstado() == EstadoUsuario.SANCIONADO) {
            throw new ApiException("El usuario se encuentra SANCIONADO y no puede realizar préstamos", HttpStatus.FORBIDDEN);
        }

        // Regla 2: Un LECTOR no puede tener más de 3 préstamos activos
        if (usuario.getRol() == Rol.LECTOR && prestamosActivos.size() >= 3) {
            throw new ApiException("El usuario ya tiene el límite máximo de 3 préstamos activos", HttpStatus.BAD_REQUEST);
        }

        // Regla 3: Validar stock disponible
        if (libro.getStockDisponible() <= 0) {
            throw new ApiException("No hay ejemplares disponibles para este libro", HttpStatus.BAD_REQUEST);
        }

        // Actualizar stock
        libro.setStockDisponible(libro.getStockDisponible() - 1);
        libroRepository.save(libro);

        // Crear préstamo (Plazo fijo de 14 días)
        LocalDate hoy = LocalDate.now();
        Prestamo prestamo = Prestamo.builder()
                .usuario(usuario)
                .libro(libro)
                .fechaPrestamo(hoy)
                .fechaDevolucionEsperada(hoy.plusDays(14))
                .estado(EstadoPrestamo.ACTIVO)
                .build();

        return mapToDTO(prestamoRepository.save(prestamo));
    }

    @Transactional
    public PrestamoDTO registrarDevolucion(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new ApiException("Préstamo no encontrado", HttpStatus.NOT_FOUND));

        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new ApiException("El préstamo ya fue devuelto previamente", HttpStatus.BAD_REQUEST);
        }

        LocalDate hoy = LocalDate.now();
        prestamo.setFechaDevolucionReal(hoy);

        if (hoy.isAfter(prestamo.getFechaDevolucionEsperada())) {
            prestamo.setEstado(EstadoPrestamo.ATRASADO);
        } else {
            prestamo.setEstado(EstadoPrestamo.DEVUELTO);
        }

        // Recuperar stock
        Libro libro = prestamo.getLibro();
        libro.setStockDisponible(libro.getStockDisponible() + 1);
        libroRepository.save(libro);

        return mapToDTO(prestamoRepository.save(prestamo));
    }

    @Transactional(readOnly = true)
    public List<PrestamoDTO> obtenerMisPrestamos(String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new ApiException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        return prestamoRepository.findByUsuario(usuario).stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PrestamoDTO> obtenerPrestamosAtrasados() {
        return prestamoRepository.findByEstadoAndFechaDevolucionEsperadaBefore(EstadoPrestamo.ACTIVO, LocalDate.now())
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private PrestamoDTO mapToDTO(Prestamo prestamo) {
        return PrestamoDTO.builder()
                .id(prestamo.getId())
                .usuarioId(prestamo.getUsuario().getId())
                .nombreUsuario(prestamo.getUsuario().getNombre())
                .libroId(prestamo.getLibro().getId())
                .tituloLibro(prestamo.getLibro().getTitulo())
                .fechaPrestamo(prestamo.getFechaPrestamo())
                .fechaDevolucionEsperada(prestamo.getFechaDevolucionEsperada())
                .fechaDevolucionReal(prestamo.getFechaDevolucionReal())
                .estado(prestamo.getEstado().name())
                .build();
    }
}