/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;
import Modelo.Almacen;
import java.util.List;

/**
 *
 * @author samue
 */
public interface IAlmacenDAO {
  public boolean crear(Almacen a) throws Exception;

    public Almacen Leer(int id) throws Exception;

    public List<Almacen> lista() throws Exception;

    public boolean actualizar(Almacen a) throws Exception;

    public boolean eliminar(int id) throws Exception;

    public List<Almacen> Buscar(String texto) throws Exception;  
}
