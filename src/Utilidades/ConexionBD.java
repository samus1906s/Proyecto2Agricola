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
    private static final String URL = "jdbc:mariadb://localhost:3307/produccion_agricola";
    private static final String USUARIO = "admin";
    private static final String CONTRASENA = "admin123";
    private static Connection conexion = null;

    public static Connection getConnection() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            return conexion;
        }

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("✔ Conexión a MariaDB establecida correctamente.");
            return conexion;
        } catch (ClassNotFoundException e) {
            throw new SQLException("❌ Error: No se encontró el driver de MariaDB.", e);
        } catch (SQLException e) {
            throw new SQLException("❌ Error al conectar a la base de datos: " + e.getMessage(), e);
        }
    }
    
     public static void cerrar() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
            System.out.println("✔ Conexión cerrada.");
        }
    }
}
