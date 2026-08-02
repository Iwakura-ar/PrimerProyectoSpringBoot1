package com.hibernate.ferreteria.DTOs;

public class UsuarioDTO {
    private Long id;
    private String usuario;
    private String rol;
    private boolean activo;

    public UsuarioDTO(Long id, String usuario, String rol, boolean activo) {
        this.id = id;
        this.usuario = usuario;
        this.rol = rol;
        this.activo = activo;
    }

    public Long getId() { return id; }
    public String getUsuario() { return usuario; }
    public String getRol() { return rol; }
    public boolean isActivo() { return activo; }
}