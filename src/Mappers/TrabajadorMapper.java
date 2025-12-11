/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package Mappers;

import DTOs.TrabajadorDTO;
import Entidades.iMapper;
import Modelo.Trabajador;

/**
 * * @author Reynold
 */
public class TrabajadorMapper implements iMapper<Trabajador, TrabajadorDTO> {

    @Override
    public TrabajadorDTO ToDto(Trabajador entidad) {
        if (entidad == null) {
            return null;
        }
          
        return new TrabajadorDTO(  
            entidad.getIdTrabajador(),
            entidad.getCedula(),
            entidad.getNombre(),
            entidad.getTelefono(),
            entidad.getCorreo(),
            entidad.getPuesto(),
            entidad.getTipoTrabajador(),
            entidad.getSalario(),
            entidad.getHorario()
        );
    }

    @Override
    public Trabajador ToEntidad(TrabajadorDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Trabajador(
            dto.getIdTrabajador(),
            dto.getCedula(),
            dto.getNombre(),
            dto.getTelefono(),
            dto.getCorreo(),
            dto.getPuesto(),
            dto.getTipoTrabajador(),
            dto.getSalario(),
            dto.getHorario()
        );
    }
}
