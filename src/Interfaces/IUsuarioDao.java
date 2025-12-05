/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Usuario;
import java.util.List;

/**
 *
 * @author Reynold
 */
public interface IUsuarioDao {
    public boolean crear(Usuario u) throws Exception;
    public Usuario Leer(int id) throws Exception;
    public List<Usuario> lista() throws Exception;
    public boolean actualizar(Usuario c) throws Exception;
    public boolean eliminar(int id) throws Exception;
    public List<Usuario> Buscar(String texto) throws Exception;
}
