package com.hibernate.ferreteria.DTOs;

public class ArticulosDTO {
    private long id;
    private String nombre_articulo;
    private double precio;
    private int stock;

    public ArticulosDTO(long id, String nombre_articulo, Double precio, int stock) {
        this.id = id;
        this.nombre_articulo = nombre_articulo;
        this.precio = precio;
        this.stock = stock;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre_articulo() {
        return nombre_articulo;
    }

    public void setNombre_articulo(String nombre_articulo) {
        this.nombre_articulo = nombre_articulo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ArticulosDTO{" +
                "id=" + id +
                ", nombre_articulo='" + nombre_articulo + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}
