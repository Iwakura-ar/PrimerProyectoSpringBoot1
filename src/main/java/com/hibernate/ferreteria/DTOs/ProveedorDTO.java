package com.hibernate.ferreteria.DTOs;

import java.util.List;

public class ProveedorDTO {

    private Long id;
    private String nombre;
    private String contacto;
    private String cuit;
    private String telefono;
    private String email;
    private List<String> articulosProvistos;

    public ProveedorDTO() {

    }

    public ProveedorDTO(Long id, String nombre, String cuit,
                        String telefono, String email, List<String> articulosProvistos) {
        this.id = id;
        this.nombre = nombre;
        this.contacto = contacto;
        this.cuit = cuit;
        this.telefono = telefono;
        this.email = email;
        this.articulosProvistos = articulosProvistos;
    }

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

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
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

    public List<String> getArticulosProvistos() {
        return articulosProvistos;
    }

    public void setArticulosProvistos(List<String> articulosProvistos) {
        this.articulosProvistos = articulosProvistos;
    }
}
