/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.ProduccionDTO;
import Entidades.iMapper;
import Modelo.Produccion;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author je110
 */
public class ProduccionMapper implements iMapper<Produccion, ProduccionDTO> {
    
    @Override
    public ProduccionDTO ToDto(Produccion entidad) {
        if (entidad == null) return null;
        
        return new ProduccionDTO(entidad.getIdProduccion(),
                entidad.getIdCultivo(),
                entidad.getFecha(),
                entidad.getCantidadRecolectada(),
                entidad.getCalidadProducto(),
                entidad.getDestino()
        );
    }
    
    @Override
    public Produccion ToEntidad(ProduccionDTO dto) {
        if (dto == null) return null;
        
        Produccion p = new Produccion();

        if (dto.getIdProduccion() != null) {
            p.setIdProduccion(dto.getIdProduccion());
        }
        p.setIdCultivo(dto.getCultivoId());
        p.setFecha(dto.getFecha());
        p.setCantidadRecolectada(dto.getCantidadRecolectada());
        p.setCalidadProducto(dto.getCalidadProducto());
        p.setDestino(dto.getDestino());
        
        return p;
    }
    
    public static Produccion resultadoSetDelModelo(ResultSet rs) throws SQLException {
        Produccion p = new Produccion();
        p.setIdProduccion(rs.getInt("id"));
        p.setIdCultivo(rs.getInt("cultivo_id"));
        p.setFecha(rs.getDate("fecha").toLocalDate());
        p.setCantidadRecolectada(rs.getBigDecimal("cantidad_recolectada"));
        p.setCalidadProducto(rs.getString("calidad_producto"));
        p.setDestino(rs.getString("destino"));
        return p;
    }
}
