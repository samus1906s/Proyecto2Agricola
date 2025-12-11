/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Reynold
 */

public class Trabajador extends Personas {
    private int idTrabajador;
    private TipoPuesto puesto;
    private TrabajadorCampo tipoTrabajador;
    private double salario;
    private String horario;

    public Trabajador() {
        super("0000000000", "Temp", "00000000", "temp@temp.com");
    }

    public Trabajador(int idTrabajador, String cedula, String nombre, String telefono, String correo, TipoPuesto puesto, TrabajadorCampo tipoTrabajador, double salario, String horario) {
        super(cedula, nombre, telefono, correo);
        this.idTrabajador = idTrabajador;
        this.puesto = puesto;
        this.tipoTrabajador = tipoTrabajador;
        this.salario = salario;
        this.horario = horario;
    }

    public Trabajador(String cedula, String nombre, String telefono, String correo, TipoPuesto puesto, TrabajadorCampo tipoTrabajador, double salario, String horario) {
        super(cedula, nombre, telefono, correo);
        this.puesto = puesto;
        this.tipoTrabajador = tipoTrabajador;
        this.salario = salario;
        this.horario = horario;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public TipoPuesto getPuesto() {
        return puesto;
    }

    public TrabajadorCampo getTipoTrabajador() {
        return tipoTrabajador;
    }

    public double getSalario() {
        return salario;
    }

    public String getHorario() {
        return horario;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public void setPuesto(TipoPuesto puesto) {
        this.puesto = puesto;
    }

    public void setTipoTrabajador(TrabajadorCampo tipoTrabajador) {
        this.tipoTrabajador = tipoTrabajador;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }


    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    @Override
    public String toString() {
        return nombre;
    }
}