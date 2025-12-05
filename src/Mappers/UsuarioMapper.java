/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.UsuarioDto;
import Entidades.iMapper;
import Modelo.Usuario;

/**
 *
 * @author Reynold
 */
public class UsuarioMapper implements iMapper<Usuario, UsuarioDto>{

    @Override
    public UsuarioDto ToDto(Usuario entidad) {
          return new UsuarioDto(
                  entidad.getUser(),
                  entidad.getContraseña(),
                  entidad.getCedula(),
                  entidad.getNombre(),
                  entidad.getTelefono(),
                  entidad.getCorreo()
        );
    }

    @Override
    public Usuario ToEntidad(UsuarioDto dto) {
        return new Usuario(
                  dto.getUser(),
                  dto.getContraseña(),
                  dto.getCedula(),
                  dto.getNombre(),
                  dto.getTelefono(),
                  dto.getCorreo()
        );
    }
    
}
