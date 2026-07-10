package com.hibernate.ferreteria.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true)
    private String cuit;

    private String telefono;

    private String email;

    // Lado "propietario" de la relacion muchos a muchos.
    // La tabla intermedia articulo_proveedor guarda los pares (articulo_id, proveedor_id).
    @ManyToMany
    @JoinTable(
            name = "articulo_proveedor",
            joinColumns = @JoinColumn(name = "proveedor_id"),
            inverseJoinColumns = @JoinColumn(name = "articulo_id")
    )
    private Set<Articulo> articulos = new HashSet<>();

    public Proveedor() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(Set<Articulo> articulos) {
        this.articulos = articulos;
    }
}
