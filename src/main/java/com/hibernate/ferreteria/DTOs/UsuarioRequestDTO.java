package com.hibernate.ferreteria.DTOs;

public class UsuarioRequestDTO {
    private String usuario;
    private String password;
    private String rol;

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
