/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Produccion;
import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author je110
 */
public interface IProduccionDAO {
    
    boolean crear(Produccion p) throws Exception;

    Produccion Leer(int id) throws Exception;

    List<Produccion> lista() throws Exception;

    boolean actualizar(Produccion p) throws Exception;

    boolean eliminar(int id) throws Exception;

    List<Produccion> listaDeCultivosId(int cultivoId) throws Exception;

    List<Produccion> listaDeFechas(LocalDate fecha) throws Exception;

    List<Produccion> Buscar(String texto) throws Exception;
}
