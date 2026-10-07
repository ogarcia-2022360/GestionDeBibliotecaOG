package kinal.GestionDeBibliotecaOG.dto;

import jakarta.validation.constraints.NotNull;

public class PrestamoRequest {

    @NotNull(message = "El ID del libro es obligatorio")
    private Long libroId;

    private Long usuarioId;

    public PrestamoRequest() {
    }

    public PrestamoRequest(Long libroId, Long usuarioId) {
        this.libroId = libroId;
        this.usuarioId = usuarioId;
    }

    public Long getLibroId() {
        return libroId;
    }

    public void setLibroId(Long libroId) {
        this.libroId = libroId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}