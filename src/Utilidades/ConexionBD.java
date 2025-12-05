/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author je110
 */
public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3307/produccion_agricola?useSSL=false&serverTimezone=UTC";
    
    private static final String USUARIO = "admin";
    private static final String CONTRASENA = "admin123";
    private static Connection conexion = null;

    public static Connection getConnection() throws SQLException {

        if (conexion != null && !conexion.isClosed()) {
            return conexion;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

            System.out.println("Conexion a la BD establecida correctamente.");
            return conexion;

        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver MySQL.", e);
        } catch (SQLException e) {
            throw new SQLException("Error al conectar: " + e.getMessage(), e);
        }
    }

    public static void cerrar() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
            System.out.println("Conexion cerrada.");
        }
    }
}

