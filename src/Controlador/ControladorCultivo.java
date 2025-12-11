/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DTOs.DTOCultivo;
import Servicios.CultivoServicio;
import java.util.List;

/**
 *
 * @author Valdelomaar
 */
public class ControladorCultivo {
    
    private final CultivoServicio servicio;

    public ControladorCultivo() {
        this.servicio = new CultivoServicio();
    }

    public boolean registrarCultivo(DTOCultivo dto) throws Exception {
        boolean exito = servicio.registrar(dto);
        return exito;
    }

    public boolean actualizarCultivo(DTOCultivo dto) throws Exception {
        return servicio.actualizar(dto);
    }

    public boolean eliminarCultivo(int id) throws Exception {
        return servicio.eliminar(id);
    }

    public DTOCultivo obtenerCultivo(int id) throws Exception {
        return servicio.obtenerPorId(id);
    }

    public List<DTOCultivo> listarCultivos() throws Exception {
        return servicio.listar();
    }

    public List<DTOCultivo> buscar(String texto) throws Exception {
        return servicio.buscar(texto);
    }
    
}
