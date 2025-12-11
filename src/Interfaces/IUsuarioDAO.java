/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Usuario;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 * @author Reynold
 */
public interface IUsuarioDAO {
    
    boolean crear(Usuario u) throws SQLException;
    
    Usuario Leer(int id) throws SQLException;
    
    List<Usuario> lista() throws SQLException;
    
    boolean actualizar(Usuario u) throws SQLException;
    
    boolean eliminar(int id) throws SQLException;
    
    Usuario autenticar(String usuario, String contrasena) throws SQLException;
    
    boolean existeUsuario(String usuario) throws SQLException;
    
    boolean registrarAcceso(int usuarioId) throws SQLException;
    
    List<Usuario> Buscar(String texto) throws SQLException;
    
    boolean existenUsuarios() throws SQLException;
}
