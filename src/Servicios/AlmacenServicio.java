/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import DAOs.AlmacenDAO;
import DTOs.AlmacenDTO;
import Mappers.AlmacenMapper;
import Modelo.Almacen;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author samue
 */
public class AlmacenServicio {
    private AlmacenDAO dao;
    private AlmacenMapper mapper;

    public AlmacenServicio() {
        this.dao = new AlmacenDAO();
        this.mapper = new AlmacenMapper();
    }
    
     public boolean registrar(AlmacenDTO dto) throws Exception {
        Almacen almacen = mapper.ToEntidad(dto);
        return dao.crear(almacen);
    }

    public boolean actualizar(AlmacenDTO dto) throws Exception {
        Almacen almacen = mapper.ToEntidad(dto);
        return dao.actualizar(almacen);
    }

    public boolean eliminar(int id) throws Exception {
        return dao.eliminar(id);
    }

    public List<AlmacenDTO> listar() throws Exception {
        List<Almacen> lista = dao.lista();
        List<AlmacenDTO> resultado = new ArrayList<>();
        for (Almacen a : lista) {
            resultado.add(mapper.ToDto(a));
        }
        return resultado;
    }

    public AlmacenDTO obtenerPorId(int id) throws Exception {
        Almacen a = dao.Leer(id);
        return mapper.ToDto(a);
    }

    public List<AlmacenDTO> buscar(String texto) throws Exception {
        List<Almacen> lista = dao.Buscar(texto);
        List<AlmacenDTO> resultado = new ArrayList<>();
        for (Almacen a : lista) {
            resultado.add(mapper.ToDto(a));
        }
        return resultado;
    }

    public List<AlmacenDTO> buscarPorFechaIngreso(String fecha) throws Exception {
        LocalDate f = LocalDate.parse(fecha);
        List<Almacen> lista = dao.listaPorFechaIngreso(f);
        List<AlmacenDTO> salida = new ArrayList<>();
        for (Almacen a : lista) {
            salida.add(mapper.ToDto(a));
        }
        return salida;
    }
}
