/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import DAOs.TrabajadorDAO;
import DTOs.TrabajadorDTO;
import Interfaces.ITrabajadorDAO;
import Mappers.TrabajadorMapper;
import Modelo.Trabajador;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Reynold
 */
public class TrabajadorServicio {
    private ITrabajadorDAO dao;
    private TrabajadorMapper mapper;

    public TrabajadorServicio() {
        this.dao = new TrabajadorDAO();
        this.mapper = new TrabajadorMapper();
    }

    public boolean registrar(TrabajadorDTO dto) throws Exception {
        Trabajador trabajador = mapper.ToEntidad(dto);
        boolean exito = dao.crear(trabajador);
        if (exito) {
            dto.setIdTrabajador(trabajador.getIdTrabajador());
        }
        return exito;
    }

    public boolean actualizar(TrabajadorDTO dto) throws Exception {
        Trabajador trabajador = mapper.ToEntidad(dto);
        return dao.actualizar(trabajador);
    }
    
    public boolean eliminar(int id) throws Exception {
        return dao.eliminar(id);
    }

    public TrabajadorDTO obtenerPorId(int id) throws Exception {
        Trabajador t = dao.Leer(id);
        return t != null ? mapper.ToDto(t) : null;
    }

    public List<TrabajadorDTO> listar() throws Exception {
        List<Trabajador> lista = dao.lista();
        List<TrabajadorDTO> resultado = new ArrayList<>();

        for (Trabajador t : lista) {
            resultado.add(mapper.ToDto(t));
        }
        return resultado;
    }

    public List<TrabajadorDTO> buscar(String texto) throws Exception {
        List<Trabajador> lista = dao.Buscar(texto);
        List<TrabajadorDTO> resultado = new ArrayList<>();
        
        for (Trabajador t : lista) {
            resultado.add(mapper.ToDto(t));
        }
        return resultado;
    }

    public TrabajadorDTO buscarPorCedula(String cedula) throws Exception {
        TrabajadorDAO trabajadorDao = (TrabajadorDAO) dao;
        Trabajador t = trabajadorDao.buscarPorCedula(cedula);
        return t != null ? mapper.ToDto(t) : null;
    }

    public boolean existeCedula(String cedula) throws Exception {
        TrabajadorDAO trabajadorDao = (TrabajadorDAO) dao;
        return trabajadorDao.buscarPorCedula(cedula) != null;
    }
}
