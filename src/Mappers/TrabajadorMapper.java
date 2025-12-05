/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.TrabajadorDto;
import Entidades.iMapper;
import Modelo.Trabajador;

/**
 *
 * @author Student
 */
public class TrabajadorMapper implements iMapper<Trabajador, TrabajadorDto>{

    @Override
    public TrabajadorDto ToDto(Trabajador entidad) {
        return new TrabajadorDto(
                entidad.getCedula(),
                entidad.getCorreo(),
                entidad.getNombre(),
                entidad.getTelefono(),
                entidad.getPuesto(),
                entidad.getSalario(),
                entidad.getTipo(),
                entidad.obtenerNivelCampo()
        );
    }

    @Override
    public Trabajador ToEntidad(TrabajadorDto dto) {
        return new Trabajador(
                dto.getId(),
                dto.getCedula(),
                dto.getCorreo(),
                dto.getNombre(),
                dto.getTelefono(),
                dto.getPuesto(),
                dto.getSalario()
        );
    }
    
}
