package com.hibernate.ferreteria.DTOs;

public class ArticuloMasVendidoDTO {

    private Integer articuloId;
    private String nombreArticulo;
    private Long cantidadVendida;

    public ArticuloMasVendidoDTO(Integer articuloId, String nombreArticulo, Long cantidadVendida) {
        this.articuloId = articuloId;
        this.nombreArticulo = nombreArticulo;
        this.cantidadVendida = cantidadVendida;
    }

    public Integer getArticuloId() {
        return articuloId;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public Long getCantidadVendida() {
        return cantidadVendida;
    }


}
