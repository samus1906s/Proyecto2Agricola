/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import DAOs.CultivoDAO;
import DTOs.DTOCultivo;
import Interfaces.ICultivoDAO;
import Mappers.MapperCultivo;
import Modelo.Cultivo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valdelomaar
 */
public class CultivoServicio {
    
   private ICultivoDAO dao;
    private MapperCultivo mapper;

    public CultivoServicio() {
        this.dao = new CultivoDAO();
        this.mapper = new MapperCultivo();
    }

    public boolean registrar(DTOCultivo dto) throws Exception {
        Cultivo cultivo = mapper.ToEntidad(dto);
        boolean exito = dao.crear(cultivo);
        if (exito) {
            dto.setIdCultivo(cultivo.getIdCultivo()); 
        }
        return exito;
    }

    public boolean actualizar(DTOCultivo dto) throws Exception {
        Cultivo cultivo = mapper.ToEntidad(dto);
        return dao.actualizar(cultivo);
    }

    public boolean eliminar(int id) throws Exception {
        return dao.eliminar(id);
    }

    public DTOCultivo obtenerPorId(int id) throws Exception {
        Cultivo c = dao.Leer(id);
        return mapper.ToDto(c);
    }

    public List<DTOCultivo> listar() throws Exception {
        List<Cultivo> lista = dao.lista();
        List<DTOCultivo> resultado = new ArrayList<>();

        for (Cultivo c : lista) {
            resultado.add(mapper.ToDto(c));
        }
        return resultado;
    }

    public List<DTOCultivo> buscar(String texto) throws Exception {
        List<Cultivo> lista = dao.Buscar(texto);
        List<DTOCultivo> resultado = new ArrayList<>();
        
        for (Cultivo c : lista) {
            resultado.add(mapper.ToDto(c));
        }
        return resultado;
    }

}
