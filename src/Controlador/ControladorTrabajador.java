/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DTOs.TrabajadorDto;
import Servicios.ServicioTrabajador;
import java.util.List;

/**
 *
 * @author Student
 */
public class ControladorTrabajador {
    private final ServicioTrabajador servicio;

    public ControladorTrabajador() {
        this.servicio = new ServicioTrabajador();
    }

    public boolean registrarTrabajador(TrabajadorDto dto) throws Exception{
        if (dto.getCedula() ==null || dto.getCedula().trim().isEmpty()){
            throw new Exception("La cédula es requerida");
        }
        if (dto.getNombre() ==null || dto.getNombre().trim().isEmpty()){
            throw new Exception("El nombre es requerido");
        }
        if (dto.getSalario() <= 0){
            throw new Exception("El salario debe ser mayor a cero");
        }
        if (servicio.existeCedula(dto.getCedula())){
            throw new Exception("Ya existe un trabajador con esta cédula");
        }
        return servicio.registrar(dto);
    }

    public boolean actualizarTrabajador(TrabajadorDto dto) throws Exception{
        if (dto.getId() <= 0){
            throw new Exception("ID de trabajador inválido");
        }
        if (dto.getNombre() ==null || dto.getNombre().trim().isEmpty()){
            throw new Exception("El nombre es requerido");
        }
        if (dto.getSalario() <= 0){
            throw new Exception("El salario debe ser mayor a cero");
        }
        return servicio.actualizar(dto);
    }

    public boolean eliminarTrabajador(int id) throws Exception{
        if (id <= 0){
            throw new Exception("ID de trabajador inválido");
        }
        return servicio.eliminar(id);
    }

    public TrabajadorDto obtenerTrabajador(int id) throws Exception{
        if (id <= 0){
            throw new Exception("ID de trabajador inválido");
        }
        return servicio.obtenerPorId(id);
    }

    public List<TrabajadorDto> listarTrabajadores() throws Exception{
        return servicio.listar();
    }

    public List<TrabajadorDto> buscar(String texto) throws Exception{
        if (texto == null || texto.trim().isEmpty()) {
            return listarTrabajadores();
        }
        return servicio.buscar(texto);
    }

    public TrabajadorDto buscarPorCedula(String cedula) throws Exception{
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new Exception("La cédula es requerida");
        }
        return servicio.buscarPorCedula(cedula);
    }

    public boolean existeCedula(String cedula) throws Exception{
        return servicio.existeCedula(cedula);
    }
}
