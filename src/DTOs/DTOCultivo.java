/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import Modelo.EstadoCrecimiento;
import Modelo.TiposCultivo;
import java.time.LocalDate;

/**
 *
 * @author Valdelomaar
 */
public class DTOCultivo {
    
    private int idCultivo;
    private String nombre;
    private TiposCultivo tipo;
    private double areaSembrada;
    private EstadoCrecimiento estado;
    private LocalDate fechaSiembra;
    private LocalDate fechaCosecha;

   
    public DTOCultivo() {
    }

  
    public DTOCultivo(int idCultivo, String nombre, TiposCultivo tipo, double areaSembrada, EstadoCrecimiento estado, LocalDate fechaSiembra, LocalDate fechaCosecha) {
        this.idCultivo = idCultivo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.areaSembrada = areaSembrada;
        this.estado = estado;
        this.fechaSiembra = fechaSiembra;
        this.fechaCosecha = fechaCosecha;
    }

  
    public int getIdCultivo() { return idCultivo; 
    }
    
    public void setIdCultivo(int idCultivo) { this.idCultivo = idCultivo; 
    }

    public String getNombre() { return nombre; 
    
    }
    
    public void setNombre(String nombre) { this.nombre = nombre; 
    }

    public TiposCultivo getTipo() { return tipo; 
    }
    
    public void setTipo(TiposCultivo tipo) { this.tipo = tipo; 
    }

    public double getAreaSembrada() { return areaSembrada; 
    }
    
    public void setAreaSembrada(double areaSembrada) { this.areaSembrada = areaSembrada; 
    }

    public EstadoCrecimiento getEstado() { return estado; 
    }
    
    public void setEstado(EstadoCrecimiento estado) { this.estado = estado; 
    }

    public LocalDate getFechaSiembra() { return fechaSiembra; 
    }
    
    public void setFechaSiembra(LocalDate fechaSiembra) {
        this.fechaSiembra = fechaSiembra; 
    }

    public LocalDate getFechaCosecha() {
        return fechaCosecha; 
    }
    public void setFechaCosecha(LocalDate fechaCosecha) {
        this.fechaCosecha = fechaCosecha; 
    }
    
    @Override
    public String toString() {
        return nombre;
    }
    
}
