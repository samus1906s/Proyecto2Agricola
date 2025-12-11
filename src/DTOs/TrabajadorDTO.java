/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import Modelo.TipoPuesto;
import Modelo.TrabajadorCampo;

/**
 * 
 * @author Reynold
 */
public class TrabajadorDTO {
    private int idTrabajador;
    private String cedula;
    private String nombre;
    private String telefono;
    private String correo;
    private TipoPuesto puesto;
    private TrabajadorCampo tipoTrabajador;
    private double salario;

    public TrabajadorDTO() {
    }

    public TrabajadorDTO(int idTrabajador, String cedula, String nombre, String telefono, String correo, TipoPuesto puesto, TrabajadorCampo tipoTrabajador, double salario) {
        this.idTrabajador = idTrabajador;
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.puesto = puesto;
        this.tipoTrabajador = tipoTrabajador;
        this.salario = salario;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
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

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
   
    @Override
    public String toString() {
        return nombre;
    }
}