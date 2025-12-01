/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.ProduccionDTO;
import Modelo.Produccion;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author je110
 */
public class ProduccionMapper {
    
    public static Produccion dtoAModelo(ProduccionDTO dto) {
        if (dto == null) return null;

        Produccion p = new Produccion(); // 

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
    
     public static ProduccionDTO modeloADto(Produccion p) {
        if (p == null) return null;

        return new ProduccionDTO(p.getIdProduccion(), p.getIdCultivo(), p.getFecha(), p.getCantidadRecolectada(), p.getCalidadProducto(), p.getDestino());
    }
     
    public static Produccion resultadoSetDelModelo(ResultSet rs) throws SQLException {

        Produccion p = new Produccion();

        p.setIdProduccion(rs.getInt("idProduccion"));
        p.setIdCultivo(rs.getInt("idCultivo"));
        p.setFecha(rs.getDate("fecha").toLocalDate());
        p.setCantidadRecolectada(rs.getBigDecimal("cantidad_recolectada"));
        p.setCalidadProducto(rs.getString("calidad_producto"));
        p.setDestino(rs.getString("destino"));

        return p;
    }
}
