/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Modelo;

import Controlador.ControladorProduccion;
import DTOs.ProduccionDTO;
import Utilidades.ConexionBD;
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;

/**
 *
 * @author je110
 */
public class MainPrueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      try {
            ControladorProduccion controlador = new ControladorProduccion();

            // ✅ AQUÍ SÍ EXISTE "p"
            ProduccionDTO p = new ProduccionDTO();
            p.setCultivoId(1); // Este ID debe existir en la tabla cultivo
            p.setFecha(LocalDate.now());
            p.setCantidadRecolectada(new BigDecimal("120.50"));
            p.setCalidadProducto("Buena");
            p.setDestino("Venta");

            boolean resultado = controlador.registrarProduccion(p);

            if (resultado) {
                System.out.println("✅ Producción registrada correctamente");
            } else {
                System.out.println("❌ No se pudo registrar la producción");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
