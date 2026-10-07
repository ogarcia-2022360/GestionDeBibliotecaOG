package kinal.GestionDeBibliotecaOG.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
    private Long version;

    // Constructores
    public Libro() {
    }

    public Libro(Long id, String isbn, String titulo, String autor, String categoria, Integer stockTotal, Integer stockDisponible) {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.stockTotal = stockTotal;
        this.stockDisponible = stockDisponible;
    }

    // Patrón Builder manual para compatibilidad con tu código actual
    public static LibroBuilder builder() {
        return new LibroBuilder();
    }

    public static class LibroBuilder {
        private Long id;
        private String isbn;
        private String titulo;
        private String autor;
        private String categoria;
        private Integer stockTotal;
        private Integer stockDisponible;

        public LibroBuilder id(Long id) { this.id = id; return this; }
        public LibroBuilder isbn(String isbn) { this.isbn = isbn; return this; }
        public LibroBuilder titulo(String titulo) { this.titulo = titulo; return this; }
        public LibroBuilder autor(String autor) { this.autor = autor; return this; }
        public LibroBuilder categoria(String categoria) { this.categoria = categoria; return this; }
        public LibroBuilder stockTotal(Integer stockTotal) { this.stockTotal = stockTotal; return this; }
        public LibroBuilder stockDisponible(Integer stockDisponible) { this.stockDisponible = stockDisponible; return this; }

        public Libro build() {
            return new Libro(id, isbn, titulo, autor, categoria, stockTotal, stockDisponible);
        }
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Integer getStockTotal() { return stockTotal; }
    public void setStockTotal(Integer stockTotal) { this.stockTotal = stockTotal; }

    public Integer getStockDisponible() { return stockDisponible; }
    public void setStockDisponible(Integer stockDisponible) { this.stockDisponible = stockDisponible; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
}