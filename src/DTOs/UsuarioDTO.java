/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import Modelo.RolUsuario;
import Modelo.EstadoUsuario;
import java.time.LocalDateTime;

/**
 *
 * @author Reynold
 */
public class UsuarioDTO {
    
    private Integer id;
    private String nombreCompleto;
    private String usuario;
    private String email;
    private RolUsuario rol;
    private String contrasena;
    private EstadoUsuario estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimoAcceso;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Integer id, String nombreCompleto, String usuario, String email, RolUsuario rol, String contrasena, EstadoUsuario estado, LocalDateTime fechaCreacion, LocalDateTime ultimoAcceso) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.usuario = usuario;
        this.email = email;
        this.rol = rol;
        this.contrasena = contrasena;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.ultimoAcceso = ultimoAcceso;
    }

    public Integer getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getEmail() {
        return email;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public String getContrasena() {
        return contrasena;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }
}
