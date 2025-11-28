/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Modelo;

/**
 *
 * @author Valdelomaar
 */
public enum EstadoCrecimiento {
    
    GERMINACION("Germinación"),
    ROMPIMIENTO("Rompimiento del suelo"),
    CRECIMIENTO_HOJAS("Crecimiento de hojas"),
    CRECIMIENTO_TALLO("Crecimiento de tallo"),
    CRECIMIENTO_RAICES("Crecimiento de raíces"),

    FLORACION("Floración"),
    FRUCTIFICACION("Fructificación"),
    MADUREZ("Madurez del cultivo");

    private final String descripcion;

    private EstadoCrecimiento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean esEtapaVegetativa() {
        return this == GERMINACION || this == ROMPIMIENTO ||
               this == CRECIMIENTO_HOJAS || this == CRECIMIENTO_TALLO ||
               this == CRECIMIENTO_RAICES;
    }

    public boolean esEtapaReproductiva() {
        return this == FLORACION || this == FRUCTIFICACION ||
               this == MADUREZ;
    }
}

    

