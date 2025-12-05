/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vista;

import Controlador.ControladorProduccion;
import DTOs.ProduccionDTO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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

    /**
     * Creates new form IntFrmProduccion
     */
    public IntFrmProduccion() {
        this(new ControladorProduccion());
    }
    
    public IntFrmProduccion(ControladorProduccion controlador) {
        this.controlador = controlador;
        initComponents();
        cargarTabla();
        generarGrafica();
        personalizarTituloYBorde();
    }
    
    //private void cargarCombosSegunDisponibilidad() {
        //try {
            //var lista = controlador.listarCultivos(); 
            //cmbCultivoRelacionado.removeAllItems();
            //for (var c : lista) {
                
                //if (c instanceof String) {
                    //cmbCultivoRelacionado.addItem((String) c);
                //} else {
                   
                    //try {
                        //String nombre = (String) c.getClass().getMethod("getNombre").invoke(c);
                        //cmbCultivoRelacionado.addItem(nombre);
                    //} catch (Exception ex) {
                        
                    //}
                //}
            //}
        //} catch (Exception e) {
            //System.err.println("No se pudieron cargar los cultivos: " + e.getMessage());
        //}
    //}

    private void cargarTabla() {
        // TODO: Llamar a controlador.listarProducciones() y llenar tabla
        // Este método existe para que el constructor compile y para que lo rellenes.
    }
    
    private void generarGrafica() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(75, "Productividad", "Actual");

        JFreeChart chart = ChartFactory.createBarChart(
            "Porcentaje de Productividad",
            "Indicador",
            "%",
            dataset,
            PlotOrientation.VERTICAL,
            false,
            true,
            false
        );

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

        String idText = txtIdCosecha.getText();
        if (idText != null && !idText.trim().isEmpty()) {
            try {
                dto.setIdProduccion(Integer.parseInt(idText.trim()));
            } catch (NumberFormatException ex) {
                throw new Exception("ID inválido.");
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

        Object selectedCultivo = cmbCultivoRelacionado.getSelectedItem();

        try {
            dto.getClass().getMethod("setCultivoNombre", String.class).invoke(dto, selectedCultivo.toString());
        } catch (NoSuchMethodException ns) {

            try {
                dto.getClass().getMethod("setCultivoId", Integer.class).invoke(dto, cmbCultivoRelacionado.getSelectedIndex());
            } catch (NoSuchMethodException ex) {

            }
        }

        String cantidadTxt = txtCantidadRecolectada.getText();
        if (cantidadTxt == null || cantidadTxt.trim().isEmpty()) {
            throw new Exception("Debe ingresar la cantidad recolectada.");
        }
        BigDecimal cantidad;
        try {
            cantidad = new BigDecimal(cantidadTxt.trim());
        } catch (NumberFormatException ex) {
            throw new Exception("Cantidad inválida. Use solo números.");
        }

        try {
            dto.getClass().getMethod("setCantidadRecolectada", BigDecimal.class).invoke(dto, cantidad);
        } catch (NoSuchMethodException ns) {

            dto.getClass().getMethod("setCantidadRecolectada", Double.class).invoke(dto, cantidad.doubleValue());
        }

        if (cmbCalidadProducto.getSelectedItem() != null) {
            dto.setCalidadProducto(cmbCalidadProducto.getSelectedItem().toString());
        }
        if (cmbDestino.getSelectedItem() != null) {
            dto.setDestino(cmbDestino.getSelectedItem().toString());
        }

        return dto;
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

        lblIdcosecha.setText("ID Cosecha:");

        lblCantidadRecolectada.setText("Cantidad Recolectada:");

        lblFecha.setText("Fecha:");

        lblCalidadProducto.setText("Calidad de Producto:");

        lblDestino.setText("Destino:");

        lblCultivoRelacionado.setText("Cultivo Relacionado:");

        cmbCalidadProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbCultivoRelacionado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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

        lblProductividad.setText("Productividad");

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
        btnPDF.setText(" Generar PDF");
        btnPDF.setFocusPainted(false);

        btnXML.setBackground(new java.awt.Color(45, 95, 63));
        btnXML.setText("Generar XML");
        btnXML.setFocusPainted(false);

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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
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
            boolean ok = controlador.registrarProduccion(dto); 
            if (ok) {
                JOptionPane.showMessageDialog(this, "Producción registrada correctamente.");
                limpiarFormulario();
                cargarTabla(); 
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnMostrarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarTablaActionPerformed
        IntFrmTablaProduccion tabla = new IntFrmTablaProduccion(controlador);

        JDesktopPane desktop = this.getDesktopPane();
        if (desktop != null) {
            desktop.add(tabla);
            tabla.setVisible(true);
            tabla.toFront();
        }
    }//GEN-LAST:event_btnMostrarTablaActionPerformed

    private void limpiarFormulario() {
        txtIdCosecha.setText("");
        dtcFecha.setDate(null);
        txtCantidadRecolectada.setText("");
        cmbCultivoRelacionado.setSelectedIndex(0);
        cmbCalidadProducto.setSelectedIndex(0);
        cmbDestino.setSelectedIndex(0);
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
