package com.hibernate.ferreteria.DTOs;

import java.util.List;

public class VentaRequestDTO {

    private Long usuarioId;
    private List<DetalleRequestDTO> items;

    public VentaRequestDTO() {}

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public List<DetalleRequestDTO> getItems() { return items; }
    public void setItems(List<DetalleRequestDTO> items) { this.items = items; }
}