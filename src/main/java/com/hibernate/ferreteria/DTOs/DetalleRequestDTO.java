package com.hibernate.ferreteria.DTOs;

public class DetalleRequestDTO {

    private Integer articuloId;
    private Integer cantidad;

    public DetalleRequestDTO() {}

    public Integer getArticuloId() { return articuloId; }
    public void setArticuloId(Integer articuloId) { this.articuloId = articuloId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
