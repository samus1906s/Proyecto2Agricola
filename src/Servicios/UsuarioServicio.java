/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import DAOs.UsuarioDAO;
import DTOs.UsuarioDTO;
import Modelo.Usuario;
import Mappers.UsuarioMapper;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Reynold
 */
public class UsuarioServicio {
    
    private UsuarioDAO dao;
    private UsuarioMapper mapper;
    
    public UsuarioServicio() {
        this.dao = new UsuarioDAO();
        this.mapper = new UsuarioMapper();
    }
    
    public boolean registrar(UsuarioDTO dto) throws Exception {
        
        boolean existeUsuario = dao.existeUsuario(dto.getUsuario());
        
        if (existeUsuario) {
            JOptionPane.showMessageDialog(null,"Ya existe un usuario con ese nombre de usuario.\n" +"Por favor elija otro nombre de usuario.", "Usuario duplicado",JOptionPane.WARNING_MESSAGE);
            return false;
        }
 
        if (dto.getContrasena() == null || dto.getContrasena().length() < 6) {
            JOptionPane.showMessageDialog(null,"La contraseña debe tener al menos 6 caracteres.","Contraseña inválida",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        Usuario usuario = mapper.ToEntidad(dto);
        return dao.crear(usuario);
    }
    
    public boolean actualizar(UsuarioDTO dto) throws Exception {
        Usuario usuario = mapper.ToEntidad(dto);
        return dao.actualizar(usuario);
    }
    
    public boolean actualizarConContrasena(UsuarioDTO dto, String nuevaContrasena) throws Exception {
        
        if (nuevaContrasena == null || nuevaContrasena.length() < 6) {
            JOptionPane.showMessageDialog( null,"La contraseña debe tener al menos 6 caracteres.","Contraseña inválida",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        Usuario usuario = mapper.ToEntidad(dto);
        return dao.actualizarConContrasena(usuario, nuevaContrasena);
    }
    
    public boolean eliminar(int id) throws Exception {

        List<Usuario> usuarios = dao.lista();
        long admins = usuarios.stream().filter(u -> u.getRol().name().equals("ADMINISTRADOR")).count();
        
        Usuario usuarioAEliminar = dao.Leer(id);
        
        if (usuarioAEliminar != null && 
            usuarioAEliminar.getRol().name().equals("ADMINISTRADOR") && 
            admins <= 1) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar el único administrador del sistema.", "Error al eliminar", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return dao.eliminar(id);
    }
    
    public List<UsuarioDTO> listar() throws Exception {
        List<Usuario> lista = dao.lista();
        List<UsuarioDTO> resultado = new ArrayList<>();
        for (Usuario u : lista) {
            resultado.add(mapper.ToDto(u));
        }
        return resultado;
    }
    
    public UsuarioDTO obtenerPorId(int id) throws Exception {
        Usuario u = dao.Leer(id);
        return mapper.ToDto(u);
    }
    
    public List<UsuarioDTO> buscar(String texto) throws Exception {
        List<Usuario> lista = dao.Buscar(texto);
        List<UsuarioDTO> resultado = new ArrayList<>();
        for (Usuario u : lista) {
            resultado.add(mapper.ToDto(u));
        }
        return resultado;
    }
    
    public UsuarioDTO autenticar(String usuario, String contrasena) throws Exception {
        Usuario u = dao.autenticar(usuario, contrasena);
        if (u != null) {

            dao.registrarAcceso(u.getId());
        }
        return mapper.ToDto(u);
    }
    
    public boolean existeUsuario(String usuario) throws Exception {
        return dao.existeUsuario(usuario);
    }
    
    public boolean existenUsuarios() throws Exception {
        return dao.existenUsuarios();
    } 
}
