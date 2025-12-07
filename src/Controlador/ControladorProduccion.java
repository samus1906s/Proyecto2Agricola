/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DTOs.ProduccionDTO;
import Servicios.ProduccionServicio;
import java.util.List;


/**
 *
 * @author je110
 */
public class ControladorProduccion {
    
    private final ProduccionServicio servicio;

    public ControladorProduccion() {
        this.servicio = new ProduccionServicio();
    }

    public int registrarProduccion(ProduccionDTO dto) throws Exception {
        return servicio.registrarYRetornarId(dto);
    }
    
    public ProduccionDTO obtenerUltimaProduccion() throws Exception {
        return servicio.obtenerUltima();
    }

    public boolean actualizarProduccion(ProduccionDTO dto) throws Exception {
        return servicio.actualizar(dto);
    }

    public boolean eliminarProduccion(int id) throws Exception {
        return servicio.eliminar(id);
    }

    public ProduccionDTO obtenerProduccion(int id) throws Exception {
        return servicio.obtenerPorId(id);
    }

    public List<ProduccionDTO> listarProducciones() throws Exception {
        return servicio.listar();
    }

    public List<ProduccionDTO> buscar(String texto) throws Exception {
        return servicio.buscar(texto);
    }

    public List<ProduccionDTO> listarPorCultivo(int cultivoId) throws Exception {
        return servicio.listarPorCultivo(cultivoId);
    }

    public List<ProduccionDTO> listarPorFecha(String fecha) throws Exception {
        return servicio.listarPorFecha(fecha);
    }
    
}
