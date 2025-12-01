/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.math.BigDecimal;
import java.time.LocalDate;
/**
 *
 * @author je110
 */
public class ProduccionDTO {
    
    private Integer idProduccion;                   
    private Integer cultivoId;
    private LocalDate fecha;
    private BigDecimal cantidadRecolectada;
    private String calidadProducto;
    private String destino;

    public ProduccionDTO() {
    }

    public ProduccionDTO(Integer idProduccion, Integer cultivoId, LocalDate fecha, BigDecimal cantidadRecolectada, String calidadProducto, String destino) {
        this.idProduccion = idProduccion;
        this.cultivoId = cultivoId;
        this.fecha = fecha;
        this.cantidadRecolectada = cantidadRecolectada;
        this.calidadProducto = calidadProducto;
        this.destino = destino;
    }

    public Integer getIdProduccion() {
        return idProduccion;
    }

    public Integer getCultivoId() {
        return cultivoId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public BigDecimal getCantidadRecolectada() {
        return cantidadRecolectada;
    }

    public String getCalidadProducto() {
        return calidadProducto;
    }

    public String getDestino() {
        return destino;
    }

    public void setIdProduccion(Integer idProduccion) {
        this.idProduccion = idProduccion;
    }

    public void setCultivoId(Integer cultivoId) {
        this.cultivoId = cultivoId;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setCantidadRecolectada(BigDecimal cantidadRecolectada) {
        this.cantidadRecolectada = cantidadRecolectada;
    }

    public void setCalidadProducto(String calidadProducto) {
        this.calidadProducto = calidadProducto;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
   
}
