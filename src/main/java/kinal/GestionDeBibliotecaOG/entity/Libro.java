package kinal.GestionDeBibliotecaOG.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(
        name = "libros",
        indexes = {
                @Index(name = "idx_libro_isbn", columnList = "isbn"),
                @Index(name = "idx_libro_titulo", columnList = "titulo"),
                @Index(name = "idx_libro_categoria", columnList = "categoria")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_libro_isbn", columnNames = "isbn")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El ISBN es obligatorio")
    @Column(unique = true, nullable = false, length = 20)
    private String isbn;

    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(length = 150)
    private String autor;

    @Column(length = 100)
    private String categoria;

    @NotNull
    @Min(value = 0, message = "El stock total no puede ser negativo")
    @Column(nullable = false)
    private Integer stockTotal;

    @NotNull
    @Min(value = 0, message = "El stock disponible no puede ser negativo")
    @Column(nullable = false)
    private Integer stockDisponible;

    @Version
    private Long version; // Evita conflictos en actualizaciones concurrentes de stock
}