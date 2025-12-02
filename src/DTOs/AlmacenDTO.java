/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import Modelo.EstadoAlmacen;
import java.time.LocalDate;

/**
 *
 * @author samue
 */
public class AlmacenDTO {
    private final int id;
    private final int produccionId;
    private final double cantidadDisponible;
    private final LocalDate fechaIngreso;
    private final LocalDate fechaEgreso;
    private final EstadoAlmacen estado;

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
    
    

    public AlmacenDTO(int id, int produccionId, double cantidadDisponible, LocalDate fechaIngreso, LocalDate fechaEgreso, EstadoAlmacen estado) {
        this.id = id;
        this.produccionId = produccionId;
        this.cantidadDisponible = cantidadDisponible;
        this.fechaIngreso = fechaIngreso;
        this.fechaEgreso = fechaEgreso;
        this.estado = estado;
    }
    
    
}
