/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import Modelo.Administrador;
import Modelo.TrabajadorCampo;

/**
 *
 * @author Student
 */
public class TrabajadorDto {
    private int id;
    private String cedula;
    private String nombre;
    private String telefono;
    private String correo;
    private Administrador puesto;
    private TrabajadorCampo tipoTrabajador;
    private double salario;

    public TrabajadorDto(int id, String cedula, String nombre, String telefono, String correo, Administrador puesto, TrabajadorCampo tipoTrabajador, double salario) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.puesto = puesto;
        this.tipoTrabajador = tipoTrabajador;
        this.salario = salario;
    }
    
     public TrabajadorDto(String cedula, String nombre, String telefono, String correo, Administrador puesto, TrabajadorCampo tipoTrabajador, double salario){
        this(0, cedula, nombre, telefono, correo, puesto, tipoTrabajador, salario);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Administrador getPuesto() {
        return puesto;
    }

    public void setPuesto(Administrador puesto) {
        this.puesto = puesto;
    }

    public TrabajadorCampo getTipoTrabajador() {
        return tipoTrabajador;
    }

    public void setTipoTrabajador(TrabajadorCampo tipoTrabajador) {
        this.tipoTrabajador = tipoTrabajador;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
    
    
}
