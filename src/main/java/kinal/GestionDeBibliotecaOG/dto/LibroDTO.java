package kinal.GestionDeBibliotecaOG.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    public LibroDTO() {
    }

    public LibroDTO(Long id, String isbn, String titulo, String autor, String categoria, Integer stockTotal, Integer stockDisponible) {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.stockTotal = stockTotal;
        this.stockDisponible = stockDisponible;
    }

    // Builder manual
    public static LibroDTOBuilder builder() {
        return new LibroDTOBuilder();
    }

    public static class LibroDTOBuilder {
        private Long id;
        private String isbn;
        private String titulo;
        private String autor;
        private String categoria;
        private Integer stockTotal;
        private Integer stockDisponible;

        public LibroDTOBuilder id(Long id) { this.id = id; return this; }
        public LibroDTOBuilder isbn(String isbn) { this.isbn = isbn; return this; }
        public LibroDTOBuilder titulo(String titulo) { this.titulo = titulo; return this; }
        public LibroDTOBuilder autor(String autor) { this.autor = autor; return this; }
        public LibroDTOBuilder categoria(String categoria) { this.categoria = categoria; return this; }
        public LibroDTOBuilder stockTotal(Integer stockTotal) { this.stockTotal = stockTotal; return this; }
        public LibroDTOBuilder stockDisponible(Integer stockDisponible) { this.stockDisponible = stockDisponible; return this; }

        public LibroDTO build() {
            return new LibroDTO(id, isbn, titulo, autor, categoria, stockTotal, stockDisponible);
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
}