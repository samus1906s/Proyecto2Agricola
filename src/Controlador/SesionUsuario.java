/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DTOs.UsuarioDTO;
import Modelo.RolUsuario;

/**
 * 
 * @author Reynold
 */
public class SesionUsuario {
    
    private static SesionUsuario instancia;
    private UsuarioDTO usuarioActual;
    
    private SesionUsuario() {
        
    }
    
    public static SesionUsuario getInstance() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }
    
    public void iniciarSesion(UsuarioDTO usuario) {
        this.usuarioActual = usuario;
        System.out.println("Sesión iniciada para: " + usuario.getNombreCompleto());
    }
    
    public void cerrarSesion() {
        if (usuarioActual != null) {
            System.out.println("Sesión cerrada para: " + usuarioActual.getNombreCompleto());
        }
        this.usuarioActual = null;
    }
    
    public UsuarioDTO getUsuarioActual() {
        return usuarioActual;
    }
    
    public boolean haySesionActiva() {
        return usuarioActual != null;
    }
    
    public boolean isAdministrador() {
        return usuarioActual != null && usuarioActual.getRol() == RolUsuario.ADMINISTRADOR;
    }
    
    public boolean isTrabajador() {
        return usuarioActual != null && usuarioActual.getRol() == RolUsuario.TRABAJADOR;
    }
    
    public String getNombreUsuarioActual() {
        return usuarioActual != null ? usuarioActual.getNombreCompleto() : "Sin sesión";
    }
    
    public String getRolUsuarioActual() {
        return usuarioActual != null ? usuarioActual.getRol().name() : "Sin rol";
    }
    
    public int getIdUsuarioActual() {
        return usuarioActual != null ? usuarioActual.getId() : -1;
    }
}
