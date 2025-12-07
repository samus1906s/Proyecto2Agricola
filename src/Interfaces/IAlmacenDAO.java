/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;
import Modelo.Almacen;
import java.util.List;
import java.sql.SQLException;

/**
 *
 * @author samue
 */
public interface IAlmacenDAO {
  public boolean crear(Almacen a) throws SQLException;

    public Almacen Leer(int id) throws SQLException;

    public List<Almacen> lista() throws SQLException;

    public boolean actualizar(Almacen a) throws SQLException;

    public boolean eliminar(int id) throws SQLException;

    public List<Almacen> Buscar(String texto) throws SQLException;  
}
