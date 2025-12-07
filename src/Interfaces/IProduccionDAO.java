/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Produccion;
import java.time.LocalDate;
import java.util.List;
import java.sql.SQLException;
/**
 *
 * @author je110
 */
public interface IProduccionDAO {
    
    boolean crear(Produccion p) throws SQLException;

    Produccion Leer(int id) throws SQLException;

    List<Produccion> lista() throws SQLException;

    boolean actualizar(Produccion p) throws SQLException;

    boolean eliminar(int id) throws SQLException;

    List<Produccion> listaDeCultivosId(int cultivoId) throws SQLException;

    List<Produccion> listaDeFechas(LocalDate fecha) throws SQLException;

    List<Produccion> Buscar(String texto) throws SQLException;
}
