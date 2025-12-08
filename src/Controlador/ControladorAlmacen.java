/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DTOs.AlmacenDTO;
import Servicios.AlmacenServicio;
import java.time.LocalDate;
import java.util.ArrayList;
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
    
    public List<AlmacenDTO> listarAlmacenesConAlertas(int diasLimite) throws Exception {
    // 1. Obtenemos toda la lista
    List<AlmacenDTO> lista = servicio.listar(); 
    List<AlmacenDTO> alertas = new ArrayList<>();

    LocalDate hoy = LocalDate.now();

    for (AlmacenDTO a : lista) {
        if (a.getFechaIngreso() != null) {
            long diasAlmacen = java.time.temporal.ChronoUnit.DAYS.between(a.getFechaIngreso(), hoy);
            
            // --- CORRECCIÓN CRÍTICA ---
            // Si la fecha del sistema está atrasada, 'diasAlmacen' da negativo.
            // Lo forzamos a 0 para evitar errores lógicos.
            if (diasAlmacen < 0) {
                diasAlmacen = 0; 
            }
            // --------------------------

            // Solo agregamos a la lista si supera el límite establecido (ej. 15 días)
            if (diasAlmacen >= diasLimite) {
                alertas.add(a);
            }
        }
    }
    return alertas;
}
}
