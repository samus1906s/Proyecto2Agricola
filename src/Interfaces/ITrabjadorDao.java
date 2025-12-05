/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Trabajador;
import java.util.List;

/**
 *
 * @author Reynold
 */
public interface ITrabjadorDao {
    public boolean crear(Trabajador t) throws Exception;
    public Trabajador Leer(int id) throws Exception;
    public List<Trabajador> lista() throws Exception;
    public boolean actualizar(Trabajador c) throws Exception;
    public boolean eliminar(int id) throws Exception;
    public List<Trabajador> Buscar(String texto) throws Exception;
}
