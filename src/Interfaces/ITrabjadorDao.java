/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Trabajador;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Reynold
 */
public interface ITrabjadorDao {
    public boolean crear(Trabajador t) throws SQLException;
    public Trabajador Leer(int id) throws SQLException;
    public List<Trabajador> lista() throws SQLException;
    public boolean actualizar(Trabajador c) throws SQLException;
    public boolean eliminar(int id) throws SQLException;
    public List<Trabajador> Buscar(String texto) throws SQLException;
}
