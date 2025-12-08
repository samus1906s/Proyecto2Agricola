/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Reynold
 */
public class Usuario extends Personas{
    private int id;
    private String user;
    private String contraseña;

    public Usuario(int id, String user, String contraseña, String cedula, String nombre, String telefono, String correo) {
        super(cedula, nombre, telefono, correo);
        this.id = id;
        this.user = user;
        this.contraseña = contraseña;
    }

    public int getId() {
        return id;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    } 
}
