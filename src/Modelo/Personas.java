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
            throw new IllegalArgumentException("Teléfono inválido. Debe tener 8 dígitos");

        this.telefono = normalizarTelefono(telefono);
    }

    public static boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }

        String telefonoLimpio = telefono.trim().replaceAll("[^0-9-]", "");

        String soloNumeros = telefonoLimpio.replaceAll("-", "");
 
        return soloNumeros.matches("^[0-9]{8}$");
    }

    public static String normalizarTelefono(String telefono) {
        if (telefono == null) return "";

        String soloNumeros = telefono.replaceAll("[^0-9]", "");
  
        if (soloNumeros.length() == 8) {
            return String.format("%s-%s-%s-%s", 
                soloNumeros.substring(0, 2),
                soloNumeros.substring(2, 4),
                soloNumeros.substring(4, 6),
                soloNumeros.substring(6, 8));
        }

        return telefono;
    }

    public void setCorreo(String correo) {
       if (!validarCorreo(correo))
            throw new IllegalArgumentException("Correo inválido.");
       this.correo = correo;
    }
    
    public static boolean validarCorreo(String correo){
        return correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public void setTelefonoSinValidar(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreoSinValidar(String correo) {
        this.correo = correo;
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
