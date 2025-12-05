/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Reynold
 */
public class Trabajador extends Personas{
    private Administrador puesto;
    private TrabajadorCampo tipo;
    private double salario;

    public Trabajador(int id, String cedula, String nombre, String telefono, String correo, Administrador puesto, double salario) {
        super(cedula, nombre, telefono, correo);
        this.puesto = puesto;
        this.tipo = tipo;
        this.salario = salario;
    }

    public Administrador getPuesto() {
        return puesto;
    }

    public void setPuesto(Administrador puesto){
        this.puesto = puesto;
    }

    public TrabajadorCampo getTipo() {
        return tipo;
    }

    public void setTipo(TrabajadorCampo tipo) {
        this.tipo = tipo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int obtenerNivelCampo(){
        if(tipo!= null){
            return tipo.getNivel();
        }
        return 0;
    }
}
