/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author samue
 */
public abstract class Personas {
    protected String cedula;
    protected String nombre;
    protected String telefono;
    protected String correo;

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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        if (!validarTelefono(telefono))
            throw new IllegalArgumentException("Teléfono inválido. Formato: 00-00-00-00");
        this.telefono = telefono;
    }
    
    public static boolean validarTelefono(String telefono){
        return telefono.matches("^[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{2}$");
    }

    public void setCorreo(String correo) {
       if (!validarCorreo(correo))
            throw new IllegalArgumentException("Correo inválido.");
       this.correo = correo;
    }
    
    public static boolean validarCorreo(String correo){
        return correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public Personas(String cedula, String nombre, String telefono, String correo) {
        if (!validarTelefono(telefono))
            throw new IllegalArgumentException("Teléfono inválido. Formato: 00-00-00-00");
        
        if (!validarCorreo(correo))
            throw new IllegalArgumentException("Correo inválido.");
        
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Personas{" + "cedula=" + cedula + ", nombre=" + nombre + ", telefono=" + telefono + ", correo=" + correo + '}';
    }
    
    
}
