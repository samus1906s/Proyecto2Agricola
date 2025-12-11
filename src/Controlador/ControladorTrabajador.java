/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Controlador;

import DTOs.TrabajadorDTO;
import Servicios.TrabajadorServicio;
import java.util.List;

/**
 * * @author Reynold
 */
public class ControladorTrabajador {
    private final TrabajadorServicio servicio;

    public ControladorTrabajador() {
        this.servicio = new TrabajadorServicio();
    }

    public boolean registrarTrabajador(TrabajadorDTO dto) throws Exception {
        
        if (dto.getCedula() == null || dto.getCedula().trim().isEmpty()) {
            throw new Exception("La cédula es requerida");
        }
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre es requerido");
        }
        if (dto.getSalario() <= 0) {
            throw new Exception("El salario debe ser mayor a cero");
        }
       
        if (dto.getHorario() == null || dto.getHorario().trim().isEmpty()) {
            throw new Exception("El horario es requerido");
        }

       
        if (servicio.existeCedula(dto.getCedula())) {
            throw new Exception("Ya existe un trabajador registrado con esta cédula");
        }
        
        
        return servicio.registrar(dto);
    }

    public boolean actualizarTrabajador(TrabajadorDTO dto) throws Exception {
        if (dto.getIdTrabajador() <= 0) {
            throw new Exception("ID de trabajador inválido");
        }
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre es requerido");
        }
        if (dto.getSalario() <= 0) {
            throw new Exception("El salario debe ser mayor a cero");
        }
        if (dto.getHorario() == null || dto.getHorario().trim().isEmpty()) {
            throw new Exception("El horario es requerido");
        }
        
        return servicio.actualizar(dto);
    }

    public boolean eliminarTrabajador(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID de trabajador inválido");
        }
        return servicio.eliminar(id);
    }

    public TrabajadorDTO obtenerTrabajador(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID de trabajador inválido");
        }
        return servicio.obtenerPorId(id);
    }

    public List<TrabajadorDTO> listarTrabajadores() throws Exception {
        return servicio.listar();
    }

    public List<TrabajadorDTO> buscar(String texto) throws Exception {
        if (texto == null || texto.trim().isEmpty()) {
            return listarTrabajadores();
        }
        return servicio.buscar(texto);
    }

    public TrabajadorDTO buscarPorCedula(String cedula) throws Exception {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new Exception("La cédula es requerida");
        }
        return servicio.buscarPorCedula(cedula);
    }

    public boolean existeCedula(String cedula) throws Exception {
        return servicio.existeCedula(cedula);
    }
}