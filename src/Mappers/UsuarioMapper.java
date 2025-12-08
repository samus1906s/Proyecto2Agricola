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
        if (entidad == null) return null;
        
          return new UsuarioDto(
                  entidad.getId(),
                  entidad.getCedula(),
                  entidad.getNombre(),
                  entidad.getTelefono(),
                  entidad.getCorreo(),
                  entidad.getUser(),
                  entidad.getContraseña()
        );
    }

    @Override
    public Usuario ToEntidad(UsuarioDto dto) {
         if (dto == null) return null;
         
        return new Usuario(
                dto.getId(),
                dto.getUsuario(),
                dto.getContraseña(),
                dto.getCedula(),
                dto.getNombre(),
                dto.getTelefono(),
                dto.getCorreo()
        );
    }
    
}
