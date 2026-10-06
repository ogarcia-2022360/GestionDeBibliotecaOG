package kinal.GestionDeBibliotecaOG.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "prestamos",
        indexes = {
                @Index(name = "idx_prestamo_usuario", columnList = "usuario_id"),
                @Index(name = "idx_prestamo_libro", columnList = "libro_id"),
                @Index(name = "idx_prestamo_estado", columnList = "estado"),
                @Index(name = "idx_prestamo_usuario_estado", columnList = "usuario_id, estado")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    @NotNull
    @Column(nullable = false)
    private LocalDate fechaPrestamo;

    @NotNull
    @Column(nullable = false)
    private LocalDate fechaDevolucionEsperada;

    private LocalDate fechaDevolucionReal;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPrestamo estado;

    @Version
    private Long version;
}