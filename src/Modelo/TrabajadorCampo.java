/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Modelo;

/**
 *
 * @author Student
 */
public enum TrabajadorCampo {
    RECOLECTOR("Recolector", 1),
    RIEGO("Riego", 2),
    SIEMBRO("Siembro", 3);

    private final String descripcion;
    private final int nivel;

    TrabajadorCampo(String descripcion, int nivel) {
        this.descripcion = descripcion;
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getNivel() {
        return nivel;
    }
}
