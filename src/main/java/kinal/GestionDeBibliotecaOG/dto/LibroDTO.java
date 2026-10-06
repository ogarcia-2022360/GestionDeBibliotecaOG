package kinal.GestionDeBibliotecaOG.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibroDTO {

    private Long id;

    @NotBlank(message = "El ISBN es obligatorio")
    private String isbn;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    private String autor;
    private String categoria;

    @NotNull(message = "El stock total es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stockTotal;

    @NotNull(message = "El stock disponible es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stockDisponible;
}