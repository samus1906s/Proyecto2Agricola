/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Modelo;

/**
 *
 * @author samue
 */
public enum TiposCultivo {
    CERALES("Cereales"),
    LEGUMINOSAS("Leguminosas"),
    HORTALIZAS("Hortalizas"),
    FRUTALES("Frutales"),
    TUBERCULOS("Tuberculos"),
    ORNAMENTALES("Ornamentales");
    
    private final String TiposCultivo;

    private TiposCultivo(String TiposCultivo) {
        this.TiposCultivo = TiposCultivo;
    }

    public String getTiposCultivo() {
        return TiposCultivo;
    }
    
}
