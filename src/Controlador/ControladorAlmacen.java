/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DTOs.AlmacenDTO;
import Servicios.AlmacenServicio;
import java.util.List;

/**
 *
 * @author samue
 */
public class ControladorAlmacen {
   private final AlmacenServicio servicio;

    public ControladorAlmacen() {
 
        this.servicio = new AlmacenServicio();
    }

    public boolean registrarAlmacen(AlmacenDTO dto) throws Exception {
        return servicio.registrar(dto);
    }

    public boolean actualizarAlmacen(AlmacenDTO dto) throws Exception {
        return servicio.actualizar(dto);
    }

    public boolean eliminarAlmacen(int id) throws Exception {
        return servicio.eliminar(id);
    }

    public AlmacenDTO obtenerAlmacen(int id) throws Exception {
        return servicio.obtenerPorId(id);
    }

    public List<AlmacenDTO> listarAlmacenes() throws Exception {
        return servicio.listar();
    }

    public List<AlmacenDTO> buscar(String texto) throws Exception {
        return servicio.buscar(texto);
    }

    public List<AlmacenDTO> buscarPorFechaIngreso(String fecha) throws Exception {
        return servicio.buscarPorFechaIngreso(fecha);
    }
}
