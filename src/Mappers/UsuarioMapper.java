/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.UsuarioDTO;
import Entidades.iMapper;
import Modelo.Usuario;
import Modelo.RolUsuario;
import Modelo.EstadoUsuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * 
 * @author Reynold
 */
public class UsuarioMapper implements iMapper<Usuario, UsuarioDTO> {
    
    @Override
    public UsuarioDTO ToDto(Usuario entidad) {
        if (entidad == null) return null;
        
        return new UsuarioDTO(
            entidad.getId(),
            entidad.getNombreCompleto(),
            entidad.getUsuario(),
            entidad.getEmail(),
            entidad.getRol(),
            entidad.getContrasena(),
            entidad.getEstado(),
            entidad.getFechaCreacion(),
            entidad.getUltimoAcceso()
        );
    }
    
    @Override
    public Usuario ToEntidad(UsuarioDTO dto) {
        if (dto == null) return null;
        
        Usuario u = new Usuario();
        
        if (dto.getId() != null) {
            u.setId(dto.getId());
        }
        u.setNombreCompleto(dto.getNombreCompleto());
        u.setUsuario(dto.getUsuario());
        u.setEmail(dto.getEmail());
        u.setRol(dto.getRol());
        u.setContrasena(dto.getContrasena());
        u.setEstado(dto.getEstado());
        u.setFechaCreacion(dto.getFechaCreacion());
        u.setUltimoAcceso(dto.getUltimoAcceso());
        
        return u;
    }
    
    public static Usuario resultadoSetDelModelo(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNombreCompleto(rs.getString("nombre_completo"));
        u.setUsuario(rs.getString("usuario"));
        u.setEmail(rs.getString("email"));
        u.setRol(RolUsuario.valueOf(rs.getString("rol")));
        u.setContrasena(rs.getString("contrasena"));
        u.setEstado(EstadoUsuario.valueOf(rs.getString("estado")));
        
        Timestamp fechaCreacion = rs.getTimestamp("fecha_creacion");
        u.setFechaCreacion(fechaCreacion != null ? fechaCreacion.toLocalDateTime() : null);
        
        Timestamp ultimoAcceso = rs.getTimestamp("ultimo_acceso");
        u.setUltimoAcceso(ultimoAcceso != null ? ultimoAcceso.toLocalDateTime() : null);
        
        return u;
    }
}
