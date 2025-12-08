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
 * @author Reynold
 */
public class TrabajadorMapper implements iMapper<Trabajador, TrabajadorDto>{

    @Override
    public TrabajadorDto ToDto(Trabajador entidad) {
        if (entidad == null) return null;
         
        return new TrabajadorDto(  
                entidad.getId(),
                entidad.getCedula(),
                entidad.getNombre(),
                entidad.getTelefono(),
                entidad.getCorreo(),
                entidad.getPuesto(),
                entidad.getTipo(),
                entidad.getSalario()
        );
    }

    @Override
    public Trabajador ToEntidad(TrabajadorDto dto) {
        if (dto == null) return null;

        return new Trabajador(
                dto.getId(),
                dto.getPuesto(),
                dto.getTipoTrabajador(),
                dto.getSalario(),
                dto.getCedula(),
                dto.getNombre(),
                dto.getTelefono(),
                dto.getCorreo()
        );
    }
    
}
