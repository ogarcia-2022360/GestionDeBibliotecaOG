package kinal.GestionDeBibliotecaOG.repository;

import kinal.GestionDeBibliotecaOG.entity.Usuario;
import kinal.GestionDeBibliotecaOG.entity.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByEmail(String email);

    boolean existByEmail(Spring email);
}
