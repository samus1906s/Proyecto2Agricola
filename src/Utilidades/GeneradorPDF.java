/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import DTOs.ProduccionDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author je110
 */
public class GeneradorPDF {
    
    private static final BaseColor COLOR_HEADER = new BaseColor(45, 95, 63);
    private static final BaseColor COLOR_ALTERNADO = new BaseColor(204, 255, 204);
    
    public static boolean generarReporteProducciones(List<ProduccionDTO> producciones) {
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Reporte PDF");

            String nombreSugerido = "Reporte_Producciones_" + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + 
                ".pdf";
            fileChooser.setSelectedFile(new java.io.File(nombreSugerido));
            
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos PDF", "pdf");
            fileChooser.setFileFilter(filter);
            
            int seleccion = fileChooser.showSaveDialog(null);
            
            if (seleccion != JFileChooser.APPROVE_OPTION) {
                return false; 
            }
            
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
            if (!rutaArchivo.toLowerCase().endsWith(".pdf")) {
                rutaArchivo += ".pdf";
            }
            
            Document documento = new Document(PageSize.A4);
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            
            documento.open();

            agregarEncabezado(documento);
            agregarInformacionGeneral(documento, producciones);
            agregarTablaProducciones(documento, producciones);
            agregarEstadisticas(documento, producciones);
            agregarPiePagina(documento);
            
            documento.close();

            try {
                java.awt.Desktop.getDesktop().open(new java.io.File(rutaArchivo));
            } catch (Exception e) {
                System.err.println("No se pudo abrir el PDF automáticamente: " + e.getMessage());
            }
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private static void agregarEncabezado(Document documento) throws DocumentException {

        Font fuenteTitulo = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, COLOR_HEADER);
        Paragraph titulo = new Paragraph("REPORTE DE PRODUCCIÓN AGRÍCOLA", fuenteTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(10);
        documento.add(titulo);

        Font fuenteSubtitulo = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
        String fechaReporte = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        Paragraph subtitulo = new Paragraph("Generado el: " + fechaReporte, fuenteSubtitulo);
        subtitulo.setAlignment(Element.ALIGN_CENTER);
        subtitulo.setSpacingAfter(20);
        documento.add(subtitulo);

        Paragraph linea = new Paragraph("_____________________________________________________");
        linea.setAlignment(Element.ALIGN_CENTER);
        linea.setSpacingAfter(20);
        documento.add(linea);
    }

    private static void agregarInformacionGeneral(Document documento, List<ProduccionDTO> producciones) throws DocumentException {
    
        Font fuenteSeccion = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        Paragraph seccion = new Paragraph("Información General", fuenteSeccion);
        seccion.setSpacingAfter(10);
        documento.add(seccion);
    
        Font fuenteNormal = FontFactory.getFont(FontFactory.HELVETICA, 11);
        Font fuenteBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
    
        Paragraph info = new Paragraph();
        info.add(new Chunk("Total de Registros: ", fuenteBold));
        info.add(new Chunk(String.valueOf(producciones.size()), fuenteNormal));
        info.add(Chunk.NEWLINE);
    
        double cantidadTotal = producciones.stream().mapToDouble(p -> p.getCantidadRecolectada().doubleValue()).sum();
    
        info.add(new Chunk("Cantidad Total Recolectada: ", fuenteBold));
        info.add(new Chunk(String.format("%.2f kg", cantidadTotal), fuenteNormal));
        info.add(Chunk.NEWLINE);
    
        info.setSpacingAfter(20);
        documento.add(info);
    }
    
    private static void agregarTablaProducciones(Document documento, List<ProduccionDTO> producciones) throws DocumentException {
        
        Font fuenteSeccion = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Paragraph seccion = new Paragraph("Detalle de Producciones", fuenteSeccion);
        seccion.setSpacingAfter(10);
        documento.add(seccion);

        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(10);
        tabla.setSpacingAfter(10);

        float[] anchos = {0.8f, 1.5f, 1.2f, 1.3f, 1.2f, 1.5f};
        tabla.setWidths(anchos);

        Font fuenteHeader = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
        String[] headers = {"ID", "Cultivo", "Fecha", "Cantidad", "Calidad", "Destino"};
        
        for (String header : headers) {
            PdfPCell celda = new PdfPCell(new Phrase(header, fuenteHeader));
            celda.setBackgroundColor(COLOR_HEADER);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding(8);
            tabla.addCell(celda);
        }

        Font fuenteDatos = new Font(Font.FontFamily.HELVETICA, 9);
        boolean alternar = false;
        
        for (ProduccionDTO p : producciones) {

            PdfPCell celdaId = new PdfPCell(new Phrase(p.getIdProduccion().toString(), fuenteDatos));
            celdaId.setHorizontalAlignment(Element.ALIGN_CENTER);
            if (alternar) celdaId.setBackgroundColor(COLOR_ALTERNADO);
            tabla.addCell(celdaId);

            PdfPCell celdaCultivo = new PdfPCell(new Phrase("Cultivo #" + p.getCultivoId(), fuenteDatos));
            if (alternar) celdaCultivo.setBackgroundColor(COLOR_ALTERNADO);
            tabla.addCell(celdaCultivo);
            
            PdfPCell celdaFecha = new PdfPCell(new Phrase(p.getFecha().toString(), fuenteDatos));
            celdaFecha.setHorizontalAlignment(Element.ALIGN_CENTER);
            if (alternar) celdaFecha.setBackgroundColor(COLOR_ALTERNADO);
            tabla.addCell(celdaFecha);

            PdfPCell celdaCantidad = new PdfPCell(new Phrase(p.getCantidadRecolectada() + " kg", fuenteDatos));
            celdaCantidad.setHorizontalAlignment(Element.ALIGN_RIGHT);
            if (alternar) celdaCantidad.setBackgroundColor(COLOR_ALTERNADO);
            tabla.addCell(celdaCantidad);

            PdfPCell celdaCalidad = new PdfPCell(new Phrase(p.getCalidadProducto(), fuenteDatos));
            celdaCalidad.setHorizontalAlignment(Element.ALIGN_CENTER);
            if (alternar) celdaCalidad.setBackgroundColor(COLOR_ALTERNADO);
            tabla.addCell(celdaCalidad);

            PdfPCell celdaDestino = new PdfPCell(new Phrase(p.getDestino(), fuenteDatos));
            if (alternar) celdaDestino.setBackgroundColor(COLOR_ALTERNADO);
            tabla.addCell(celdaDestino);
            
            alternar = !alternar;
        }
        
        documento.add(tabla);
    }

    private static void agregarEstadisticas(Document documento, List<ProduccionDTO> producciones) throws DocumentException {
    
        Font fuenteSeccion = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        Paragraph seccion = new Paragraph("Estadísticas de Calidad", fuenteSeccion);
        seccion.setSpacingBefore(20);
        seccion.setSpacingAfter(10);
        documento.add(seccion);

        long excelente = producciones.stream().filter(p -> "Excelente".equalsIgnoreCase(p.getCalidadProducto())).count();
        long buena = producciones.stream().filter(p -> "Buena".equalsIgnoreCase(p.getCalidadProducto())).count();
        long regular = producciones.stream().filter(p -> "Regular".equalsIgnoreCase(p.getCalidadProducto())).count();
        long deficiente = producciones.stream().filter(p -> "Deficiente".equalsIgnoreCase(p.getCalidadProducto())).count();
    
        Font fuenteNormal = FontFactory.getFont(FontFactory.HELVETICA, 11);
        Font fuenteBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
    
        Paragraph stats = new Paragraph();
        stats.add(new Chunk("• Excelente: ", fuenteBold));
        stats.add(new Chunk(excelente + " registros\n", fuenteNormal));
        stats.add(new Chunk("• Buena: ", fuenteBold));
        stats.add(new Chunk(buena + " registros\n", fuenteNormal));
        stats.add(new Chunk("• Regular: ", fuenteBold));
        stats.add(new Chunk(regular + " registros\n", fuenteNormal));
        stats.add(new Chunk("• Deficiente: ", fuenteBold));
        stats.add(new Chunk(deficiente + " registros\n", fuenteNormal));
    
        documento.add(stats);
    }

    private static void agregarPiePagina(Document documento) throws DocumentException {
        Font fuentePie = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.ITALIC);
        Paragraph pie = new Paragraph( "\n\n_____________________________________________________\n" + "Sistema de Gestión de Producción Agrícola\n" + "© 2025 - Documento generado automáticamente", fuentePie);
        pie.setAlignment(Element.ALIGN_CENTER);
        documento.add(pie);
    }
}
