package com.hibernate.ferreteria.DTOs;

import com.hibernate.ferreteria.entity.TipoMovimiento;

import java.time.LocalDateTime;

public class MovimientoStockDTO {
    private Long id;
    private Integer articuloId;
    private String nombreArticulo;
    private TipoMovimiento tipo;
    private Integer cantidad;
    private String motivo;
    private LocalDateTime fecha;
    private Integer StockResultante;

    public MovimientoStockDTO(Long id, Integer articuloId, String nombreArticulo, TipoMovimiento tipo,
                              Integer cantidad, String motivo, LocalDateTime fecha,
                              Integer stockResultante) {
        this.id = id;
        this.articuloId = articuloId;
        this.nombreArticulo = nombreArticulo;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.motivo = motivo;
        this.fecha = fecha;
        StockResultante = stockResultante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Integer articuloId) {
        this.articuloId = articuloId;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Integer getStockResultante() {
        return StockResultante;
    }

    public void setStockResultante(Integer stockResultante) {
        StockResultante = stockResultante;
    }
}
