/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DTOs.UsuarioDTO;
import Servicios.UsuarioServicio;
import java.util.List;

/**
 * 
 * @author Reynold
 */
public class ControladorUsuario {
    
    private final UsuarioServicio servicio;

    public ControladorUsuario() {
        this.servicio = new UsuarioServicio();
    }

    public boolean registrarUsuario(UsuarioDTO dto) throws Exception {
        return servicio.registrar(dto);
    }

    public boolean actualizarUsuario(UsuarioDTO dto) throws Exception {
        return servicio.actualizar(dto);
    }
    
    public boolean actualizarUsuarioConContrasena(UsuarioDTO dto, String nuevaContrasena) throws Exception {
        return servicio.actualizarConContrasena(dto, nuevaContrasena);
    }

    public boolean eliminarUsuario(int id) throws Exception {
        return servicio.eliminar(id);
    }

    public UsuarioDTO obtenerUsuario(int id) throws Exception {
        return servicio.obtenerPorId(id);
    }

    public List<UsuarioDTO> listarUsuarios() throws Exception {
        return servicio.listar();
    }

    public List<UsuarioDTO> buscar(String texto) throws Exception {
        return servicio.buscar(texto);
    }
    
    public boolean existeUsuario(String usuario) throws Exception {
        return servicio.existeUsuario(usuario);
    }
    
    public boolean existenUsuarios() throws Exception {
        return servicio.existenUsuarios();
    }
}
