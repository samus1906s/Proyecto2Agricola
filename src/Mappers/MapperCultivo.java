/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.DTOCultivo;
import Entidades.iMapper; 
import Modelo.Cultivo;

/**
 *
 * @author Valdelomaar
 */
public class MapperCultivo implements iMapper<Cultivo, DTOCultivo> {
   
    @Override
    public DTOCultivo ToDto(Cultivo entidad) {
        if (entidad == null) return null;
        
        return new DTOCultivo(
            entidad.getIdCultivo(),
            entidad.getNombre(),
            entidad.getTipo(),
            entidad.getAreaSembrada(),
            entidad.getEstado(),
            entidad.getFechaSiembra(),
            entidad.getFechaCosecha()
        );
    }

    @Override
    public Cultivo ToEntidad(DTOCultivo dto) {
        if (dto == null) return null;
        
        return new Cultivo(
            dto.getIdCultivo(),
            dto.getNombre(),
            dto.getTipo(),
            dto.getAreaSembrada(),
            dto.getEstado(),
            dto.getFechaSiembra(),
            dto.getFechaCosecha()
        );
    }
}