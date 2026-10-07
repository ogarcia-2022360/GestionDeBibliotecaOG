package kinal.GestionDeBibliotecaOG.dto;

import kinal.GestionDeBibliotecaOG.entity.EstadoPrestamo;
import java.time.LocalDate;

public class PrestamoDTO {

    private Long id;
    private Long usuarioId;
    private String usuarioNombre;
    private Long libroId;
    private String libroTitulo;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;
    private EstadoPrestamo estado;

    // Constructor vacío
    public PrestamoDTO() {
    }

    // Constructor completo
    public PrestamoDTO(Long id, Long usuarioId, String usuarioNombre, Long libroId,
                       String libroTitulo, LocalDate fechaPrestamo,
                       LocalDate fechaDevolucionEsperada, LocalDate fechaDevolucionReal,
                       EstadoPrestamo estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.libroId = libroId;
        this.libroTitulo = libroTitulo;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.estado = estado;
    }

    // Builder manual para resolver el error en PrestamoService
    public static PrestamoDTOBuilder builder() {
        return new PrestamoDTOBuilder();
    }

    public static class PrestamoDTOBuilder {
        private Long id;
        private Long usuarioId;
        private String usuarioNombre;
        private Long libroId;
        private String libroTitulo;
        private LocalDate fechaPrestamo;
        private LocalDate fechaDevolucionEsperada;
        private LocalDate fechaDevolucionReal;
        private EstadoPrestamo estado;

        public PrestamoDTOBuilder id(Long id) { this.id = id; return this; }
        public PrestamoDTOBuilder usuarioId(Long usuarioId) { this.usuarioId = usuarioId; return this; }
        public PrestamoDTOBuilder usuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; return this; }
        public PrestamoDTOBuilder libroId(Long libroId) { this.libroId = libroId; return this; }
        public PrestamoDTOBuilder libroTitulo(String libroTitulo) { this.libroTitulo = libroTitulo; return this; }
        public PrestamoDTOBuilder fechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; return this; }
        public PrestamoDTOBuilder fechaDevolucionEsperada(LocalDate fechaDevolucionEsperada) { this.fechaDevolucionEsperada = fechaDevolucionEsperada; return this; }
        public PrestamoDTOBuilder fechaDevolucionReal(LocalDate fechaDevolucionReal) { this.fechaDevolucionReal = fechaDevolucionReal; return this; }
        public PrestamoDTOBuilder estado(EstadoPrestamo estado) { this.estado = estado; return this; }

        public PrestamoDTO build() {
            return new PrestamoDTO(id, usuarioId, usuarioNombre, libroId, libroTitulo, fechaPrestamo, fechaDevolucionEsperada, fechaDevolucionReal, estado);
        }
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }

    public Long getLibroId() { return libroId; }
    public void setLibroId(Long libroId) { this.libroId = libroId; }

    public String getLibroTitulo() { return libroTitulo; }
    public void setLibroTitulo(String libroTitulo) { this.libroTitulo = libroTitulo; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaDevolucionEsperada() { return fechaDevolucionEsperada; }
    public void setFechaDevolucionEsperada(LocalDate fechaDevolucionEsperada) { this.fechaDevolucionEsperada = fechaDevolucionEsperada; }

    public LocalDate getFechaDevolucionReal() { return fechaDevolucionReal; }
    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) { this.fechaDevolucionReal = fechaDevolucionReal; }

    public EstadoPrestamo getEstado() { return estado; }
    public void setEstado(EstadoPrestamo estado) { this.estado = estado; }
}