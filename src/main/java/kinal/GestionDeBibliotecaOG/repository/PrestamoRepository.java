package kinal.GestionDeBibliotecaOG.repository;

import kinal.GestionDeBibliotecaOG.entity.EstadoPrestamo;
import kinal.GestionDeBibliotecaOG.entity.Prestamo;
import kinal.GestionDeBibliotecaOG.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    List<Prestamo> findByUsuario(Usuario usuario);

    List<Prestamo> findByUsuarioAndEstado(Usuario usuario, EstadoPrestamo estado);

    List<Prestamo> findByEstadoAndFechaDevolucionEsperadaBefore(EstadoPrestamo estado, LocalDate fecha);
}