/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;

/**
 *
 * @author je110
 */
public class Almacen {
    
    private int id;                       
    private int produccionId;             
    private double cantidadDisponible;    
    private LocalDate fechaIngreso;       
    private LocalDate fechaEgreso;        
    private EstadoAlmacen estado; 

    public Almacen() {
        
    }

    public Almacen(int id, int produccionId, double cantidadDisponible, LocalDate fechaIngreso, LocalDate fechaEgreso, EstadoAlmacen estado) {
        this.id = id;
        this.produccionId = produccionId;
        this.cantidadDisponible = cantidadDisponible;
        this.fechaIngreso = fechaIngreso;
        this.fechaEgreso = fechaEgreso;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public int getProduccionId() {
        return produccionId;
    }

    public double getCantidadDisponible() {
        return cantidadDisponible;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public LocalDate getFechaEgreso() {
        return fechaEgreso;
    }

    public EstadoAlmacen getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduccionId(int produccionId) {
        this.produccionId = produccionId;
    }

    public void setCantidadDisponible(double cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void setFechaEgreso(LocalDate fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public void setEstado(EstadoAlmacen estado) {
        this.estado = estado;
    }

}
