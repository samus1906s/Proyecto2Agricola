/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import DTOs.ProduccionDTO;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 *
 * @author je110
 */
public class GeneradorXML {
    
    public static boolean generarReporteProducciones(List<ProduccionDTO> producciones) {
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Reporte XML");

            String nombreSugerido = "Reporte_Producciones_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xml";
            fileChooser.setSelectedFile(new File(nombreSugerido));
            
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos XML", "xml");
            fileChooser.setFileFilter(filter);
            
            int seleccion = fileChooser.showSaveDialog(null);
            
            if (seleccion != JFileChooser.APPROVE_OPTION) {
                return false; 
            }
            
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
            if (!rutaArchivo.toLowerCase().endsWith(".xml")) {
                rutaArchivo += ".xml";
            }

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element raiz = doc.createElement("ReporteProduccionAgricola");
            doc.appendChild(raiz);

            Element infoReporte = doc.createElement("InformacionReporte");
            raiz.appendChild(infoReporte);
            
            Element fechaGeneracion = doc.createElement("FechaGeneracion");
            fechaGeneracion.setTextContent(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            infoReporte.appendChild(fechaGeneracion);
            
            Element totalRegistros = doc.createElement("TotalRegistros");
            totalRegistros.setTextContent(String.valueOf(producciones.size()));
            infoReporte.appendChild(totalRegistros);

            double cantidadTotal = producciones.stream().mapToDouble(p -> p.getCantidadRecolectada().doubleValue()).sum();
            
            Element cantidadTotalElem = doc.createElement("CantidadTotalRecolectada");
            cantidadTotalElem.setAttribute("unidad", "kg");
            cantidadTotalElem.setTextContent(String.format("%.2f", cantidadTotal));
            infoReporte.appendChild(cantidadTotalElem);

            Element produccionesElem = doc.createElement("Producciones");
            raiz.appendChild(produccionesElem);

            for (ProduccionDTO p : producciones) {
                Element produccion = doc.createElement("Produccion");
                produccionesElem.appendChild(produccion);

                produccion.setAttribute("id", p.getIdProduccion().toString());

                Element cultivo = doc.createElement("Cultivo");
                cultivo.setAttribute("id", p.getCultivoId().toString());
                cultivo.setTextContent("Cultivo #" + p.getCultivoId());
                produccion.appendChild(cultivo);

                Element fecha = doc.createElement("Fecha");
                fecha.setTextContent(p.getFecha().toString());
                produccion.appendChild(fecha);

                Element cantidad = doc.createElement("CantidadRecolectada");
                cantidad.setAttribute("unidad", "kg");
                cantidad.setTextContent(p.getCantidadRecolectada().toString());
                produccion.appendChild(cantidad);

                Element calidad = doc.createElement("CalidadProducto");
                calidad.setTextContent(p.getCalidadProducto());
                produccion.appendChild(calidad);

                Element destino = doc.createElement("Destino");
                destino.setTextContent(p.getDestino());
                produccion.appendChild(destino);
            }

            Element estadisticas = doc.createElement("Estadisticas");
            raiz.appendChild(estadisticas);
            
            Element porCalidad = doc.createElement("PorCalidad");
            estadisticas.appendChild(porCalidad);

            agregarEstadisticaCalidad(doc, porCalidad, "Excelente", producciones);
            agregarEstadisticaCalidad(doc, porCalidad, "Buena", producciones);
            agregarEstadisticaCalidad(doc, porCalidad, "Regular", producciones);
            agregarEstadisticaCalidad(doc, porCalidad, "Deficiente", producciones);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileWriter(rutaArchivo));
            
            transformer.transform(source, result);

            try {
                java.awt.Desktop.getDesktop().open(new File(rutaArchivo));
            } catch (Exception e) {
                System.err.println("No se pudo abrir el XML automáticamente: " + e.getMessage());
            }
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private static void agregarEstadisticaCalidad(Document doc, Element padre, String calidad, List<ProduccionDTO> producciones) {
        long count = producciones.stream().filter(p -> calidad.equalsIgnoreCase(p.getCalidadProducto())).count();
        
        Element elem = doc.createElement(calidad.replace(" ", ""));
        elem.setAttribute("cantidad", String.valueOf(count));
        
        double porcentaje = producciones.isEmpty() ? 0 : (count * 100.0) / producciones.size();
        elem.setAttribute("porcentaje", String.format("%.2f%%", porcentaje));
        
        padre.appendChild(elem);
    }

    public static boolean generarReporteProduccionIndividual(ProduccionDTO produccion) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Producción XML");
            
            String nombreSugerido = "Produccion_" + produccion.getIdProduccion() + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".xml";
            fileChooser.setSelectedFile(new File(nombreSugerido));
            
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos XML", "xml");
            fileChooser.setFileFilter(filter);
            
            int seleccion = fileChooser.showSaveDialog(null);
            
            if (seleccion != JFileChooser.APPROVE_OPTION) {
                return false;
            }
            
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
            if (!rutaArchivo.toLowerCase().endsWith(".xml")) {
                rutaArchivo += ".xml";
            }
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            Element raiz = doc.createElement("ProduccionAgricola");
            doc.appendChild(raiz);
            
            raiz.setAttribute("id", produccion.getIdProduccion().toString());
            raiz.setAttribute("fechaExportacion",LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            
            Element cultivo = doc.createElement("Cultivo");
            cultivo.setAttribute("id", produccion.getCultivoId().toString());
            raiz.appendChild(cultivo);
            
            Element fecha = doc.createElement("Fecha");
            fecha.setTextContent(produccion.getFecha().toString());
            raiz.appendChild(fecha);
            
            Element cantidad = doc.createElement("CantidadRecolectada");
            cantidad.setAttribute("unidad", "kg");
            cantidad.setTextContent(produccion.getCantidadRecolectada().toString());
            raiz.appendChild(cantidad);
            
            Element calidad = doc.createElement("CalidadProducto");
            calidad.setTextContent(produccion.getCalidadProducto());
            raiz.appendChild(calidad);
            
            Element destino = doc.createElement("Destino");
            destino.setTextContent(produccion.getDestino());
            raiz.appendChild(destino);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileWriter(rutaArchivo));
            transformer.transform(source, result);
            
            try {
                java.awt.Desktop.getDesktop().open(new File(rutaArchivo));
            } catch (Exception e) {
                System.err.println("No se pudo abrir el XML: " + e.getMessage());
            }
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
