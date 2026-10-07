package kinal.GestionDeBibliotecaOG.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestamoRequest {

    @NotNull(message = "El ID del libro es obligatorio")
    private Long libroId;

    private Long usuarioId; // Opcional: si un administrador/bibliotecario gestiona el préstamo
}