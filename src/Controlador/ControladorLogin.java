/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DTOs.UsuarioDTO;
import Servicios.UsuarioServicio;
import Vista.JdlLogin;

/**
 * 
 * @author Reynold
 */
public class ControladorLogin {
    
    private final JdlLogin vista;
    private final UsuarioServicio servicio;
    private int intentosFallidos;
    private static final int MAX_INTENTOS = 3;
    
    public ControladorLogin(JdlLogin vista) {
        this.vista = vista;
        this.servicio = new UsuarioServicio();
        this.intentosFallidos = 0;
    }
    
    public void autenticar(String usuario, String contrasena) {
        try {

            if (usuario == null || usuario.trim().isEmpty()) {
                vista.mostrarError("El usuario no puede estar vacío");
                return;
            }
            
            if (contrasena == null || contrasena.isEmpty()) {
                vista.mostrarError("La contraseña no puede estar vacía");
                return;
            }
            
            if (intentosFallidos >= MAX_INTENTOS) {
                vista.mostrarError("Demasiados intentos fallidos. Reinicie la aplicación.");
                return;
            }         
            
            UsuarioDTO usuarioDTO = servicio.autenticar(usuario, contrasena);
            
            if (usuarioDTO != null) {

                vista.mostrarExito("Bienvenido " + usuarioDTO.getNombreCompleto());

                SesionUsuario.getInstance().iniciarSesion(usuarioDTO);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                vista.loginExitoso();
                
            } else {

                intentosFallidos++;
                int intentosRestantes = MAX_INTENTOS - intentosFallidos;
                
                if (intentosRestantes > 0) {
                    vista.mostrarError("Usuario o contraseña incorrectos. Intentos restantes: " + intentosRestantes);
                } else {
                    vista.mostrarError("Demasiados intentos fallidos. Reinicie la aplicación.");
                }
            }
            
        } catch (Exception e) {
            vista.mostrarError("Error al autenticar: " + e.getMessage());
            System.err.println("Error en autenticar(): " + e);
            e.printStackTrace();
        }
    }
    
    public void reiniciarIntentos() {
        this.intentosFallidos = 0;
    }
}
