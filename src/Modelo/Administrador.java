/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Modelo;

/**
 *
 * @author Reynold
 */
public enum Administrador {
    JEFE("Jefe de área", 1),
    SUBJEFE("Subjefe", 2),
    CAPATAZ("Capataz", 3);

    private final String descripcion;
    private final int nivel;

    Administrador(String descripcion, int nivel) {
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
