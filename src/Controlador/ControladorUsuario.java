/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DTOs.UsuarioDto;
import Servicios.ServicioUsuario;
import java.util.List;

/**
 *
 * @author Student
 */
public class ControladorUsuario {
     private final ServicioUsuario servicio;

    public ControladorUsuario(ServicioUsuario servicio) {
        this.servicio = servicio;
    }
    
    public boolean registrarUsuario(UsuarioDto dto) throws Exception{
        if (dto.getCedula() ==null || dto.getCedula().trim().isEmpty()){
            throw new Exception("La cédula es requerida");
        }
        if (dto.getNombre() ==null || dto.getNombre().trim().isEmpty()){
            throw new Exception("El nombre es requerido");
        }
        if (dto.getUsuario() ==null || dto.getUsuario().trim().isEmpty()){
            throw new Exception("El nombre de usuario es requerido");
        }
        if (dto.getContraseña() == null || dto.getContraseña().trim().isEmpty()){
            throw new Exception("La contraseña es requerida");
        }
        if (dto.getContraseña().length() < 6){
            throw new Exception("La contraseña debe tener al menos 6 caracteres");
        }
        return servicio.registrar(dto);
    }

    public boolean actualizarUsuario(UsuarioDto dto) throws Exception{
        if (dto.getId() <= 0){
            throw new Exception("ID de usuario inválido");
        }
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()){
            throw new Exception("El nombre es requerido");
        }
        if (dto.getUsuario() == null || dto.getUsuario().trim().isEmpty()){
            throw new Exception("El nombre de usuario es requerido");
        }
        return servicio.actualizar(dto);
    }

    public boolean eliminarUsuario(int id) throws Exception{
        if (id <= 0){
            throw new Exception("ID de usuario inválido");
        }
        return servicio.eliminar(id);
    }

    public UsuarioDto obtenerUsuario(int id) throws Exception{
        if (id <= 0){
            throw new Exception("ID de usuario inválido");
        }
        
        return servicio.obtenerPorId(id);
    }

    public List<UsuarioDto> listarUsuarios() throws Exception{
        return servicio.listar();
    }

    public List<UsuarioDto> buscar(String texto) throws Exception{
        if (texto ==null || texto.trim().isEmpty()){
            return listarUsuarios();
        }
        return servicio.buscar(texto);
    }

    public UsuarioDto autenticar(String usuario, String contraseña) throws Exception{
        if (usuario ==null || usuario.trim().isEmpty()){
            throw new Exception("El nombre de usuario es requerido");
        }
        if (contraseña ==null || contraseña.trim().isEmpty()){
            throw new Exception("La contraseña es requerida");
        }
        UsuarioDto usuarioDto = servicio.autenticar(usuario, contraseña);
      
        if (usuarioDto ==null){
            throw new Exception("Usuario o contraseña incorrectos");
        }
        return usuarioDto;
    }

    public UsuarioDto buscarPorUsuario(String usuario) throws Exception{
        if (usuario == null || usuario.trim().isEmpty()){
            throw new Exception("El nombre de usuario es requerido");
        }
        return servicio.buscarPorUsuario(usuario);
    }

    public UsuarioDto buscarPorCedula(String cedula) throws Exception{
        if (cedula ==null || cedula.trim().isEmpty()){
            throw new Exception("La cédula es requerida");
        }
        return servicio.buscarPorCedula(cedula);
    }

    public boolean existeUsuario(String usuario) throws Exception{
        return servicio.existeUsuario(usuario);
    }

    public boolean cambiarContraseña(int id, String contraseñaActual, String contraseñaNueva) throws Exception{
        if (id <= 0){
            throw new Exception("ID de usuario inválido");
        }
        if (contraseñaActual ==null || contraseñaActual.trim().isEmpty()){
            throw new Exception("La contraseña actual es requerida");
        }
        if (contraseñaNueva ==null || contraseñaNueva.trim().isEmpty()){
            throw new Exception("La nueva contraseña es requerida");
        }
        if (contraseñaNueva.length() <6){
            throw new Exception("La nueva contraseña debe tener al menos 6 caracteres");
        }
        
        if (contraseñaActual.equals(contraseñaNueva)){
            throw new Exception("La nueva contraseña debe ser diferente a la actual");
        }
        return servicio.cambiarContraseña(id, contraseñaActual, contraseñaNueva);
    }
}
