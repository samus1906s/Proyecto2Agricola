/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vista;

import Controlador.ControladorProduccion;
import Controlador.ControladorCultivo;
import DTOs.ProduccionDTO;
import DTOs.DTOCultivo;
import Utilidades.GeneradorPDF;
import Utilidades.GeneradorXML;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author je110
 */
public class IntFrmProduccion extends javax.swing.JInternalFrame {

    private ControladorProduccion controlador;
    private ControladorCultivo controladorCultivo;
    private List<DTOCultivo> listaCultivos = new java.util.ArrayList<>();

    /**
     * Creates new form IntFrmProduccion
     */
    public IntFrmProduccion() {
        this(new ControladorProduccion());
    }
    
    public IntFrmProduccion(ControladorProduccion controlador) {
        this.controlador = controlador;
        this.controladorCultivo = new ControladorCultivo();
        initComponents();
        cargarCombos();
        cargarTabla();
        generarGrafica();
        personalizarTituloYBorde();
    }
    
    private void cargarCombos() {

        cmbCalidadProducto.removeAllItems();
        cmbCalidadProducto.addItem("Excelente");
        cmbCalidadProducto.addItem("Buena");
        cmbCalidadProducto.addItem("Regular");
        cmbCalidadProducto.addItem("Deficiente");
        
        cmbDestino.removeAllItems();
        cmbDestino.addItem("Venta");
        cmbDestino.addItem("Almacenamiento");
        cmbDestino.addItem("Procesamiento");
        cmbDestino.addItem("Exportación");
        
        cargarCultivosCmb();
    }
    
    private void cargarCultivosCmb() {
    try {
        cmbCultivoRelacionado.removeAllItems();
        listaCultivos.clear();

        List<DTOCultivo> cultivos = controladorCultivo.listarCultivos();

        if (cultivos != null && !cultivos.isEmpty()) {
            for (DTOCultivo cultivo : cultivos) {
                listaCultivos.add(cultivo); 
                cmbCultivoRelacionado.addItem(cultivo.getNombre()); 
            }
        } else {
            JOptionPane.showMessageDialog(this,"No hay cultivos registrados. Por favor registre cultivos primero.","Advertencia",JOptionPane.WARNING_MESSAGE);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,"Error al cargar cultivos: " + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

    private void cargarTabla() {
        try {
            List<ProduccionDTO> producciones = controlador.listarProducciones();
            
            if (producciones != null && !producciones.isEmpty()) {
                System.out.println("Producciones cargadas: " + producciones.size());
            }
        } catch (Exception e) {
            System.err.println("Error al cargar producciones: " + e.getMessage());
        }
    }

    private void generarGrafica() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        double productividadReal = calcularProductividad();

        dataset.addValue(productividadReal, "Productividad", "Actual");

        JFreeChart chart = ChartFactory.createBarChart("Porcentaje de Productividad","Indicador","%",dataset,PlotOrientation.VERTICAL,false,true,false);
    
        org.jfree.chart.plot.CategoryPlot plot = chart.getCategoryPlot();
        org.jfree.chart.renderer.category.BarRenderer renderer = (org.jfree.chart.renderer.category.BarRenderer) plot.getRenderer();

        renderer.setBaseToolTipGenerator(new org.jfree.chart.labels.StandardCategoryToolTipGenerator());
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBaseItemLabelGenerator(new org.jfree.chart.labels.StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
        renderer.setBaseItemLabelPaint(java.awt.Color.BLACK);

        if (productividadReal >= 80) {
            renderer.setSeriesPaint(0, new java.awt.Color(76, 175, 80)); 
        } else if (productividadReal >= 60) {
            renderer.setSeriesPaint(0, new java.awt.Color(255, 193, 7)); 
        } else if (productividadReal >= 40) {
            renderer.setSeriesPaint(0, new java.awt.Color(255, 152, 0)); 
        } else {
            renderer.setSeriesPaint(0, new java.awt.Color(244, 67, 54)); 
        } 

        org.jfree.chart.axis.ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setRange(0, 100); 
        rangeAxis.setStandardTickUnits(org.jfree.chart.axis.NumberAxis.createIntegerTickUnits());

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(363, 273));
        panel.setSize(new Dimension(363, 273));
        panel.setOpaque(false);

        pnlGrafica.setLayout(new BorderLayout());
        pnlGrafica.removeAll();
        pnlGrafica.add(panel, BorderLayout.CENTER);
        pnlGrafica.revalidate();
        pnlGrafica.repaint();
    }
    
    private double calcularProductividad() {
        try {
            List<ProduccionDTO> producciones = controlador.listarProducciones();

            if (producciones == null || producciones.isEmpty()) {
                System.out.println("No hay producciones registradas. Productividad: 0%");
                return 0.0;
            }
        
            int totalExcelente = 0;
            int totalBuena = 0;
            int totalRegular = 0;
            int totalDeficiente = 0;
            int total = producciones.size();

            for (ProduccionDTO p : producciones) {
                String calidad = p.getCalidadProducto();
                if (calidad == null) continue;
            
                switch (calidad.toLowerCase()) {
                    case "excelente":
                        totalExcelente++;
                        break;
                    case "buena":
                        totalBuena++;
                        break;
                    case "regular":
                        totalRegular++;
                        break;
                    case "deficiente":
                        totalDeficiente++;
                        break;
                }
            }

            double porcentaje = ((totalExcelente * 100.0) + (totalBuena * 75.0) + (totalRegular * 50.0) + (totalDeficiente * 25.0)) / total;

            porcentaje = Math.round(porcentaje * 100.0) / 100.0;
        
            return porcentaje;
        
        } catch (Exception e) {
            System.err.println("Error calculando productividad: " + e.getMessage());
            e.printStackTrace();
            return 0.0;
        }
    }

    private void personalizarTituloYBorde() {
        try {
            javax.swing.plaf.basic.BasicInternalFrameUI ui = (javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI();

            java.awt.Color verdeTitulo = new java.awt.Color(232, 245, 233);
            javax.swing.JComponent titleBar = ui.getNorthPane();
            titleBar.setBackground(verdeTitulo);
            titleBar.setOpaque(true);

            this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 204), 4));

            java.beans.PropertyChangeListener listener = evt -> {
                if ("frameType".equals(evt.getPropertyName())) {
                    this.setForeground(java.awt.Color.WHITE);
                }
            };
            this.addPropertyChangeListener(listener);

            this.putClientProperty("JInternalFrame.activeTitleForeground", java.awt.Color.WHITE);
            this.putClientProperty("JInternalFrame.inactiveTitleForeground", java.awt.Color.WHITE);

            this.putClientProperty("JInternalFrame.activeTitleBackground", verdeTitulo);
            this.putClientProperty("JInternalFrame.inactiveTitleBackground", verdeTitulo);

        } catch (Exception e) {
            System.err.println("Error personalizando título: " + e);
        }
    }

    private ProduccionDTO obtenerDTODesdeFormulario() throws Exception {
        ProduccionDTO dto = new ProduccionDTO();
    
        String idText = txtIdCosecha.getText().trim();
        if (!idText.isEmpty() && !idText.equals("0")) {
            try {
                dto.setIdProduccion(Integer.parseInt(idText));
            } catch (NumberFormatException ex) {
                throw new Exception("ID inválido. Debe ser un número.");
            }
        }

        if (dtcFecha.getDate() == null) {
            throw new Exception("Debe seleccionar una fecha.");
        }
        LocalDate fecha = dtcFecha.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dto.setFecha(fecha);
    
        if (cmbCultivoRelacionado.getSelectedItem() == null) {
            throw new Exception("Debe seleccionar un cultivo.");
        }
    
        int index = cmbCultivoRelacionado.getSelectedIndex();
        if (index < 0) {
            throw new Exception("Debe seleccionar un cultivo.");
        }
        DTOCultivo cultivoSeleccionado = listaCultivos.get(index);
        dto.setCultivoId(cultivoSeleccionado.getIdCultivo());
    
        String cantidadTxt = txtCantidadRecolectada.getText().trim();
        if (cantidadTxt.isEmpty()) {
            throw new Exception("Debe ingresar la cantidad recolectada.");
        }
    
        try {
            BigDecimal cantidad = new BigDecimal(cantidadTxt);
            if (cantidad.compareTo(BigDecimal.ZERO) <= 0) {
                throw new Exception("La cantidad debe ser mayor a cero.");
            }
            dto.setCantidadRecolectada(cantidad);
        } catch (NumberFormatException ex) {
            throw new Exception("Cantidad inválida. Use solo números (ej: 1500.50)");
        }
    
        if (cmbCalidadProducto.getSelectedItem() == null) {
            throw new Exception("Debe seleccionar la calidad del producto.");
        }
        dto.setCalidadProducto(cmbCalidadProducto.getSelectedItem().toString());
    
        if (cmbDestino.getSelectedItem() == null) {
            throw new Exception("Debe seleccionar el destino.");
        }
        dto.setDestino(cmbDestino.getSelectedItem().toString());
    
        return dto;
    }
    
    public void cargarDatos(ProduccionDTO dto) {
        try {
            if (dto == null) return;
        
            txtIdCosecha.setText(dto.getIdProduccion().toString());
        
        if (dto.getFecha() != null) {
            java.util.Date fecha = java.util.Date.from(
                dto.getFecha().atStartOfDay(ZoneId.systemDefault()).toInstant()
            );
            dtcFecha.setDate(fecha);
        }
        
        txtCantidadRecolectada.setText(dto.getCantidadRecolectada().toString());

        for (int i = 0; i < listaCultivos.size(); i++) {
            DTOCultivo cultivo = listaCultivos.get(i);
            if (cultivo.getIdCultivo() == dto.getCultivoId()) {
                cmbCultivoRelacionado.setSelectedIndex(i);
                break;
            }
        }
        
        String calidad = dto.getCalidadProducto();
        if (calidad != null) {
            for (int i = 0; i < cmbCalidadProducto.getItemCount(); i++) {
                if (cmbCalidadProducto.getItemAt(i).equals(calidad)) {
                    cmbCalidadProducto.setSelectedIndex(i);
                    break;
                }
            }
        }

        String destino = dto.getDestino();
        if (destino != null) {
            for (int i = 0; i < cmbDestino.getItemCount(); i++) {
                if (cmbDestino.getItemAt(i).equals(destino)) {
                    cmbDestino.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pnlPrincipal = new javax.swing.JPanel();
        pnlTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        pnlDatos = new javax.swing.JPanel();
        lblIdcosecha = new javax.swing.JLabel();
        lblCantidadRecolectada = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblCalidadProducto = new javax.swing.JLabel();
        lblDestino = new javax.swing.JLabel();
        txtIdCosecha = new javax.swing.JTextField();
        dtcFecha = new com.toedter.calendar.JDateChooser();
        txtCantidadRecolectada = new javax.swing.JTextField();
        lblCultivoRelacionado = new javax.swing.JLabel();
        cmbCalidadProducto = new javax.swing.JComboBox<>();
        cmbDestino = new javax.swing.JComboBox<>();
        cmbCultivoRelacionado = new javax.swing.JComboBox<>();
        pnlInformacion = new javax.swing.JPanel();
        lblHoja = new javax.swing.JLabel();
        lblProductividad = new javax.swing.JLabel();
        lblReportes = new javax.swing.JLabel();
        scpGrafica = new javax.swing.JScrollPane();
        pnlGrafica = new javax.swing.JPanel();
        btnPDF = new javax.swing.JButton();
        btnXML = new javax.swing.JButton();
        pnlBotones = new javax.swing.JPanel();
        btnLimpiar = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        btnMostrarTabla = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Producción");

        pnlPrincipal.setBackground(new java.awt.Color(45, 95, 63));

        pnlTitulo.setBackground(new java.awt.Color(45, 95, 63));

        lblTitulo.setFont(new java.awt.Font("Bell MT", 1, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/registro.png"))); // NOI18N
        lblTitulo.setText("Registros de Producción");

        javax.swing.GroupLayout pnlTituloLayout = new javax.swing.GroupLayout(pnlTitulo);
        pnlTitulo.setLayout(pnlTituloLayout);
        pnlTituloLayout.setHorizontalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTituloLayout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addComponent(lblTitulo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTituloLayout.setVerticalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlDatos.setBackground(new java.awt.Color(204, 255, 204));

        lblIdcosecha.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblIdcosecha.setText("ID Cosecha:");

        lblCantidadRecolectada.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblCantidadRecolectada.setText("Cantidad Recolectada:");

        lblFecha.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblFecha.setText("Fecha:");

        lblCalidadProducto.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblCalidadProducto.setText("Calidad de Producto:");

        lblDestino.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblDestino.setText("Destino:");

        txtIdCosecha.setEditable(false);
        txtIdCosecha.setBackground(new java.awt.Color(204, 204, 204));
        txtIdCosecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        dtcFecha.setDateFormatString("dd/MM/yyyy");

        lblCultivoRelacionado.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblCultivoRelacionado.setText("Cultivo Relacionado:");

        javax.swing.GroupLayout pnlDatosLayout = new javax.swing.GroupLayout(pnlDatos);
        pnlDatos.setLayout(pnlDatosLayout);
        pnlDatosLayout.setHorizontalGroup(
            pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addComponent(lblFecha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dtcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addComponent(lblDestino)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addComponent(lblCalidadProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCalidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addComponent(lblIdcosecha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdCosecha, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addComponent(lblCultivoRelacionado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCultivoRelacionado, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addComponent(lblCantidadRecolectada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidadRecolectada, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        pnlDatosLayout.setVerticalGroup(
            pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdCosecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIdcosecha))
                .addGap(51, 51, 51)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCultivoRelacionado)
                    .addComponent(cmbCultivoRelacionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFecha)
                    .addComponent(dtcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCantidadRecolectada)
                    .addComponent(txtCantidadRecolectada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCalidadProducto)
                    .addComponent(cmbCalidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDestino)
                    .addComponent(cmbDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        pnlInformacion.setBackground(new java.awt.Color(204, 255, 204));

        lblHoja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHoja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Hoja.png"))); // NOI18N

        lblProductividad.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblProductividad.setText("Productividad");

        lblReportes.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblReportes.setText("Generación de Reportes");

        pnlGrafica.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pnlGraficaLayout = new javax.swing.GroupLayout(pnlGrafica);
        pnlGrafica.setLayout(pnlGraficaLayout);
        pnlGraficaLayout.setHorizontalGroup(
            pnlGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 361, Short.MAX_VALUE)
        );
        pnlGraficaLayout.setVerticalGroup(
            pnlGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 271, Short.MAX_VALUE)
        );

        scpGrafica.setViewportView(pnlGrafica);

        btnPDF.setBackground(new java.awt.Color(45, 95, 63));
        btnPDF.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        btnPDF.setText(" Generar PDF");
        btnPDF.setFocusPainted(false);
        btnPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDFActionPerformed(evt);
            }
        });

        btnXML.setBackground(new java.awt.Color(45, 95, 63));
        btnXML.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        btnXML.setText("Generar XML");
        btnXML.setFocusPainted(false);
        btnXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXMLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlInformacionLayout = new javax.swing.GroupLayout(pnlInformacion);
        pnlInformacion.setLayout(pnlInformacionLayout);
        pnlInformacionLayout.setHorizontalGroup(
            pnlInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInformacionLayout.createSequentialGroup()
                .addGroup(pnlInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInformacionLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(lblProductividad)
                        .addGap(65, 65, 65)
                        .addComponent(lblHoja))
                    .addGroup(pnlInformacionLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(pnlInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblReportes)
                            .addGroup(pnlInformacionLayout.createSequentialGroup()
                                .addComponent(btnPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                                .addComponent(btnXML, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(33, 33, 33))
            .addGroup(pnlInformacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scpGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlInformacionLayout.setVerticalGroup(
            pnlInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInformacionLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProductividad)
                    .addComponent(lblHoja))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scpGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblReportes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXML, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        pnlBotones.setBackground(new java.awt.Color(45, 95, 63));
        pnlBotones.setLayout(new java.awt.GridLayout(1, 6));

        btnLimpiar.setBackground(new java.awt.Color(204, 255, 204));
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Limpiar.png"))); // NOI18N
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        pnlBotones.add(btnLimpiar);

        btnRegistrar.setBackground(new java.awt.Color(204, 255, 204));
        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Guardar.png"))); // NOI18N
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        pnlBotones.add(btnRegistrar);

        btnMostrarTabla.setBackground(new java.awt.Color(204, 255, 204));
        btnMostrarTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Tabla.png"))); // NOI18N
        btnMostrarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarTablaActionPerformed(evt);
            }
        });
        pnlBotones.add(btnMostrarTabla);

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addComponent(pnlDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlInformacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addComponent(pnlTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlInformacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 658, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        
        try {
        ProduccionDTO dto = obtenerDTODesdeFormulario();

        if (dto.getIdProduccion() != null && dto.getIdProduccion() > 0) {
            boolean exito = controlador.actualizarProduccion(dto);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, "Producción actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                limpiarFormulario();
                cargarTabla();
                generarGrafica();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar la producción.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {

            int idGenerado = controlador.registrarProduccion(dto);

            if (idGenerado == -1) {
                JOptionPane.showMessageDialog(this, "Registro cancelado o no se pudo completar.", "Cancelado", JOptionPane.WARNING_MESSAGE);
                return; 
            }

            if (idGenerado > 0) {

                txtIdCosecha.setText(String.valueOf(idGenerado));
                
                JOptionPane.showMessageDialog(this, "Producción registrada correctamente.\n" +"ID asignado: " + idGenerado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                cargarTabla();
                generarGrafica();

            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar la producción.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnMostrarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarTablaActionPerformed
        try {
            IntFrmTablaProduccion tabla = new IntFrmTablaProduccion(controlador, controladorCultivo);
            
            JDesktopPane desktop = this.getDesktopPane();
            if (desktop != null) {
                desktop.add(tabla);
                tabla.setVisible(true);
                tabla.toFront();
                
                try {
                    tabla.setMaximum(true);
                } catch (java.beans.PropertyVetoException e) {
                    System.err.println("Error maximizando ventana: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se puede abrir la ventana de tabla", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al abrir tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnMostrarTablaActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarFormulario();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed
        try {
        List<ProduccionDTO> producciones = controlador.listarProducciones();
        
        if (producciones == null || producciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay producciones registradas para generar el reporte.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        boolean exito = GeneradorPDF.generarReporteProducciones(producciones);
        
        if (exito) {
            JOptionPane.showMessageDialog(this, "Reporte PDF generado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo generar el reporte PDF.\n" + "Verifique que la librería iText esté instalada.", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar PDF: " + e.getMessage() + "\n\n" + "Asegúrese de tener la librería itextpdf-5.5.13.3.jar en la carpeta lib/", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnPDFActionPerformed

    private void btnXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXMLActionPerformed
        try {
        List<ProduccionDTO> producciones = controlador.listarProducciones();
        
        if (producciones == null || producciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay producciones registradas para generar el reporte.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        boolean exito = GeneradorXML.generarReporteProducciones(producciones);
        
        if (exito) {
            JOptionPane.showMessageDialog(this, "Reporte XML generado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo generar el reporte XML.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar XML: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnXMLActionPerformed

    private void limpiarFormulario() {
        txtIdCosecha.setText("");  
        dtcFecha.setDate(null);
        txtCantidadRecolectada.setText("");
 
        if (cmbCultivoRelacionado.getItemCount() > 0) {
            cmbCultivoRelacionado.setSelectedIndex(0);
        }
        if (cmbCalidadProducto.getItemCount() > 0) {
            cmbCalidadProducto.setSelectedIndex(0);
        }
        if (cmbDestino.getItemCount() > 0) {
            cmbDestino.setSelectedIndex(0);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnMostrarTabla;
    private javax.swing.JButton btnPDF;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnXML;
    private javax.swing.JComboBox<String> cmbCalidadProducto;
    private javax.swing.JComboBox<String> cmbCultivoRelacionado;
    private javax.swing.JComboBox<String> cmbDestino;
    private com.toedter.calendar.JDateChooser dtcFecha;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCalidadProducto;
    private javax.swing.JLabel lblCantidadRecolectada;
    private javax.swing.JLabel lblCultivoRelacionado;
    private javax.swing.JLabel lblDestino;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblHoja;
    private javax.swing.JLabel lblIdcosecha;
    private javax.swing.JLabel lblProductividad;
    private javax.swing.JLabel lblReportes;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlDatos;
    private javax.swing.JPanel pnlGrafica;
    private javax.swing.JPanel pnlInformacion;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JScrollPane scpGrafica;
    private javax.swing.JTextField txtCantidadRecolectada;
    private javax.swing.JTextField txtIdCosecha;
    // End of variables declaration//GEN-END:variables
}
