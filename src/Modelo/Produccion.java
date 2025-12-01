/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author je110
 */
public class Produccion {
    
    private int idProduccion;
    private int idCultivo;
    private LocalDate fecha;
    private BigDecimal cantidadRecolectada;
    private String calidadProducto;
    private String destino;

    public Produccion() {
    }

    public Produccion(int idProduccion, int idCultivo, LocalDate fecha, BigDecimal cantidadRecolectada, String calidadProducto, String destino) {
        this.idProduccion = idProduccion;
        this.idCultivo = idCultivo;
        this.fecha = fecha;
        this.cantidadRecolectada = cantidadRecolectada;
        this.calidadProducto = calidadProducto;
        this.destino = destino;
    }

    public int getIdProduccion() {
        return idProduccion;
    }

    public int getIdCultivo() {
        return idCultivo;
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

    public void setIdProduccion(int idProduccion) {
        this.idProduccion = idProduccion;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setCantidadRecolectada(BigDecimal cantidadRecolectada) {
        this.cantidadRecolectada = cantidadRecolectada;
    }

    public void setIdCultivo(int idCultivo) {
        this.idCultivo = idCultivo;
    }

    public void setCalidadProducto(String calidadProducto) {
        this.calidadProducto = calidadProducto;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
   
}
