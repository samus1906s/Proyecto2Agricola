/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;
import DTOs.AlmacenDTO;
import Entidades.iMapper;
import Modelo.Almacen;

/**
 *
 * @author samue
 */
public class AlmacenMapper implements iMapper<Almacen, AlmacenDTO>{

    @Override
    public AlmacenDTO ToDto(Almacen entidad) {
        return new AlmacenDTO(
                entidad.getId(),
                entidad.getProduccionId(),
                entidad.getCantidadDisponible(),
                entidad.getFechaIngreso(),
                entidad.getFechaEgreso(),
                entidad.getEstado()
        );
    }

    @Override
    public Almacen ToEntidad(AlmacenDTO dto) {
      return new Almacen(
              dto.getId(),
              dto.getProduccionId(),
              dto.getCantidadDisponible(),
              dto.getFechaIngreso(),
              dto.getFechaEgreso(),
              dto.getEstado()
      );
    }
    
}
