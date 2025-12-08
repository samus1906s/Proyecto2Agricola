/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import DAOs.TrabajadorDao;
import DTOs.TrabajadorDto;
import Interfaces.ITrabjadorDao;
import Mappers.TrabajadorMapper;
import Modelo.Trabajador;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Student
 */
public class ServicioTrabajador {
    private ITrabjadorDao tdao;
    private TrabajadorMapper tmapper;

    public ServicioTrabajador() {
        this.tdao = new TrabajadorDao();
        this.tmapper = new TrabajadorMapper();
    }

    public boolean registrar(TrabajadorDto dto) throws Exception{
        Trabajador trabajador = tmapper.ToEntidad(dto);
        return tdao.crear(trabajador);
    }

    public boolean actualizar(TrabajadorDto dto) throws Exception{
        Trabajador trabajador = tmapper.ToEntidad(dto);
        return tdao.actualizar(trabajador);
    }
    
    public boolean eliminar(int id) throws Exception{
        return tdao.eliminar(id);
    }

    public TrabajadorDto obtenerPorId(int id) throws Exception{
        Trabajador t = tdao.Leer(id);
        return t != null ? tmapper.ToDto(t) : null;
    }

    public List<TrabajadorDto> listar() throws Exception{
        List<Trabajador> lista = tdao.lista();
        List<TrabajadorDto> resultado = new ArrayList<>();

        for (Trabajador t : lista){
            resultado.add(tmapper.ToDto(t));
        }
        return resultado;
    }

    public List<TrabajadorDto> buscar(String texto) throws Exception{
        List<Trabajador> lista = tdao.Buscar(texto);
        List<TrabajadorDto> resultado = new ArrayList<>();
        
        for (Trabajador t : lista){
            resultado.add(tmapper.ToDto(t));
        }
        return resultado;
    }

    public TrabajadorDto buscarPorCedula(String cedula) throws Exception{
        TrabajadorDao trabajadorDao = (TrabajadorDao) tdao;
        Trabajador t = trabajadorDao.buscarPorCedula(cedula);
        return t !=null ? tmapper.ToDto(t) : null;
    }

    public boolean existeCedula(String cedula) throws Exception{
        TrabajadorDao trabajadorDao = (TrabajadorDao) tdao;
        return trabajadorDao.buscarPorCedula(cedula) !=null;
    }
}
