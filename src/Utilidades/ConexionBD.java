/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static ConexionBD instancia;
    private Connection conexion;

    private static final String URL = "jdbc:mysql://localhost:3307/produccion_agricola?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "admin";
    private static final String CONTRASENA = "admin123";

    private ConexionBD() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        System.out.println("Conexión establecida.");
    }

    public static synchronized Connection getConnection() throws SQLException {
        try {
            if (instancia == null || instancia.conexion == null || instancia.conexion.isClosed()) {
                instancia = new ConexionBD();
            }
            return instancia.conexion;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error: Driver no encontrado", e);
        }
    }

    public static void cerrar() throws SQLException {
        if (instancia != null && instancia.conexion != null && !instancia.conexion.isClosed()) {
            instancia.conexion.close();
            System.out.println("Conexión cerrada.");
        }
    }
}


