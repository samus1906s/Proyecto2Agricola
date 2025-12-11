/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import DTOs.ProduccionDTO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/**
 *
 * @author je110
 */
public class ExportadorCSV {
    
    public static boolean exportarProducciones(List<ProduccionDTO> producciones) {
        if (producciones == null || producciones.isEmpty()) {
            return false;
        }
        
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar reporte CSV");
            
            String timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
            );
            fileChooser.setSelectedFile(
                new File("producciones_" + timestamp + ".csv")
            );
            
            int userSelection = fileChooser.showSaveDialog(null);
            
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File archivoCSV = fileChooser.getSelectedFile();
                
                try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(archivoCSV))) {

                    writer.write("ID,Cultivo ID,Fecha,Cantidad (kg),Calidad,Destino");
                    writer.newLine();
 
                    for (ProduccionDTO p : producciones) {
                        writer.write(String.format("%d,%d,%s,%.2f,%s,%s",
                            p.getIdProduccion(),
                            p.getCultivoId(),
                            p.getFecha(),
                            p.getCantidadRecolectada(),
                            p.getCalidadProducto(),
                            p.getDestino()
                        ));
                        writer.newLine();
                    }
                    
                    writer.flush();
                }
                
                return true;
            }
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"❌ Error al generar CSV: " + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        return false;
    }
    
}
