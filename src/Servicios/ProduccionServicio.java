/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import DAOs.ProduccionDAO;
import DTOs.ProduccionDTO;
import Modelo.Produccion;
import Mappers.ProduccionMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author je110
 */
public class ProduccionServicio {
    
    private ProduccionDAO dao; 
    private ProduccionMapper mapper;
    
    public ProduccionServicio() {
        this.dao = new ProduccionDAO();
        this.mapper = new ProduccionMapper();
    }
    
   public boolean registrar(ProduccionDTO dto) throws Exception {

        boolean existeDuplicado = dao.existeProduccionDuplicada(
            dto.getCultivoId(), 
            dto.getFecha(), 
            dto.getCantidadRecolectada()
        );
        
        if (existeDuplicado) {

            int respuesta = JOptionPane.showConfirmDialog(
                null,
                "Ya existe un registro con el mismo cultivo, fecha y cantidad.\n" +
                "¿Desea registrarlo de todas formas?",
                "Posible duplicado",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (respuesta != JOptionPane.YES_OPTION) {
                return false;
            }
        }

        Produccion produccion = mapper.ToEntidad(dto);
        return dao.crear(produccion);
    }
 
    public int registrarYRetornarId(ProduccionDTO dto) throws Exception {

        boolean existeDuplicado = dao.existeProduccionDuplicada(
            dto.getCultivoId(), 
            dto.getFecha(), 
            dto.getCantidadRecolectada()
        );
        
        if (existeDuplicado) {
            int respuesta = JOptionPane.showConfirmDialog(
                null,
                "Ya existe un registro con el mismo cultivo, fecha y cantidad.\n" +
                "¿Desea registrarlo de todas formas?",
                "Posible duplicado",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (respuesta != JOptionPane.YES_OPTION) {
                return -1; 
            }
        }

        Produccion produccion = mapper.ToEntidad(dto);
        int idGenerado = dao.crearYRetornarId(produccion);

        if (idGenerado > 0) {
            dto.setIdProduccion(idGenerado);
        }
        
        return idGenerado;
    }

    public ProduccionDTO obtenerUltima() throws Exception {
        Produccion p = dao.obtenerUltima();
        if (p != null) {
            return mapper.ToDto(p);
        }
        return null;
    }

    public boolean actualizar(ProduccionDTO dto) throws Exception {
        Produccion produccion = mapper.ToEntidad(dto);
        return dao.actualizar(produccion);
    }
    
    public boolean eliminar(int id) throws Exception {
        return dao.eliminar(id);
    }
    
    public List<ProduccionDTO> listar() throws Exception {
        List<Produccion> lista = dao.lista();
        List<ProduccionDTO> resultado = new ArrayList<>();
        for (Produccion p : lista) {
            resultado.add(mapper.ToDto(p));
        }
        return resultado;
    }
    
    public ProduccionDTO obtenerPorId(int id) throws Exception {
        Produccion p = dao.Leer(id);
        return mapper.ToDto(p);
    }
    
    public List<ProduccionDTO> buscar(String texto) throws Exception {
        List<Produccion> lista = dao.Buscar(texto);
        List<ProduccionDTO> resultado = new ArrayList<>();
        for (Produccion p : lista) {
            resultado.add(mapper.ToDto(p));
        }
        return resultado;
    }
    
    public List<ProduccionDTO> listarPorCultivo(int cultivoId) throws Exception {
        List<Produccion> lista = dao.listaDeCultivosId(cultivoId);
        List<ProduccionDTO> salida = new ArrayList<>();
        for (Produccion p : lista) {
            salida.add(mapper.ToDto(p));
        }
        return salida;
    }
    
    public List<ProduccionDTO> listarPorFecha(String fecha) throws Exception {
        LocalDate f = LocalDate.parse(fecha);
        List<Produccion> lista = dao.listaDeFechas(f);
        List<ProduccionDTO> salida = new ArrayList<>();
        for (Produccion p : lista) {
            salida.add(mapper.ToDto(p));
        }
        return salida;
    }
}
