/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Cultivo;
import java.util.List;

/**
 *
 * @author Valdelomaar
 */
public interface ICultivoDAO {
    public boolean crear(Cultivo c) throws Exception;
    public Cultivo Leer(int id) throws Exception;
    public List<Cultivo> lista() throws Exception;
    public boolean actualizar(Cultivo c) throws Exception;
    public boolean eliminar(int id) throws Exception;
    public List<Cultivo> Buscar(String texto) throws Exception;
}
