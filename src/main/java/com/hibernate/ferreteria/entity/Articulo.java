package com.hibernate.ferreteria.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

//Creamos una clase con la etiqueta entidad para aclarar que hablamos de una tabla de
//articulos
@Entity
@Table(name="articulo") //indica que es una tabla SQL
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Articulo {

    //Próximsa 3 líneas para definir el atributo Id de la tabla Articulos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //llama a un campo
    //de generación automático, por ser un id
    @Column(name="id")
    private int id;
    @Column(name="nombre_articulo")
    private String nombre_articulo;
    @Column(name="precio")
    private Double precio;
    @Column(name="stock")
    private int stock;

    @Override
    public String toString() {
        return "Articulo: " + nombre_articulo + " stock: " + stock + " precio: " + precio;
    }

    //Con lombok nos evitamos los getters, setters y constructores para esta clase

    @ManyToMany(mappedBy = "articulos")
    private Set<Proveedor> proveedores = new HashSet<>();
}
