/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vista;

import Controlador.ControladorCultivo;
import DTOs.DTOCultivo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
/**
 *
 * @author je110
 */
public class IntFrmTablaCultivo extends javax.swing.JInternalFrame {

    private ControladorCultivo controlador;
    private TableRowSorter<DefaultTableModel> sorter;
    private DefaultTableModel modelo;

    public IntFrmTablaCultivo() {
        this.controlador = new ControladorCultivo();
        initComponents();
        configurarTabla();
        cargarCombos();
        cargarDatosTabla();
        personalizarTabla();
        personalizarTituloYBorde();
    }
    
    private void configurarTabla() {
        modelo = (DefaultTableModel) tblCultivos.getModel();
        sorter = new TableRowSorter<>(modelo);
        tblCultivos.setRowSorter(sorter);
    }
    
    private void cargarCombos() {
        try {
           
            cmbEstado.addItem("Todos");
            
          
            for (Modelo.EstadoCrecimiento estado : Modelo.EstadoCrecimiento.values()) {
                cmbEstado.addItem(estado.name());
            }

           
            cmbNombre.removeAllItems();
            cmbNombre.addItem("Todos");
        
            List<DTOCultivo> cultivos = controlador.listarCultivos();
            java.util.Set<String> nombresUnicos = new java.util.HashSet<>();
            
            if (cultivos != null) {
                for (DTOCultivo cultivo : cultivos) {
                    if (nombresUnicos.add(cultivo.getNombre())) {
                        cmbNombre.addItem(cultivo.getNombre());
                    }
                }
            }
                 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar filtros: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarDatosTabla() {
        try {
            modelo.setRowCount(0);
        
            List<DTOCultivo> cultivos = controlador.listarCultivos();
        
            if (cultivos != null && !cultivos.isEmpty()) {
                for (DTOCultivo c : cultivos) {
                    Object[] fila = new Object[7];
                    fila[0] = c.getIdCultivo();
                    fila[1] = c.getNombre();
                    fila[2] = c.getTipo();
                    fila[3] = c.getAreaSembrada() + " hectáreas";
                    fila[4] = c.getEstado();
                    fila[5] = c.getFechaSiembra();
                    fila[6] = c.getFechaCosecha();
                
                    modelo.addRow(fila);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No hay cultivos registrados", "Información", JOptionPane.INFORMATION_MESSAGE);
            }    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void filtrarTabla() {
        try {
            if (cmbNombre.getSelectedItem() == null || cmbEstado.getSelectedItem() == null) {
                return; 
            }
        
           String nombreCultivo = cmbNombre.getSelectedItem().toString(); 
           String estado = cmbEstado.getSelectedItem().toString();        
           Date fecha = dtcFecha.getDate();
           String busqueda = txtBuscar.getText().trim();
        
           List<RowFilter<DefaultTableModel, Object>> filtros = new ArrayList<>();
        
           
            if (!busqueda.isEmpty()) {
                filtros.add(RowFilter.regexFilter("(?i)" + busqueda));
            }
        
            
            if (!nombreCultivo.equals("Todos")) {
                filtros.add(RowFilter.regexFilter("^" + nombreCultivo + "$", 1)); 
            }
        
           
            if (!estado.equals("Todos")) {
               
                filtros.add(RowFilter.regexFilter(estado, 4)); 
            }
        
            
            if (fecha != null) {
                LocalDate fechaSeleccionada = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
               
                filtros.add(RowFilter.regexFilter(fechaSeleccionada.toString(), 5));
            }
        
         
            if (!filtros.isEmpty()) {
                RowFilter<DefaultTableModel, Object> filtroCompuesto = RowFilter.andFilter(filtros);
                sorter.setRowFilter(filtroCompuesto);
            } else {
                sorter.setRowFilter(null);
            }
        
        } catch (Exception e) {
            if (e.getMessage() != null && !e.getMessage().contains("null")) {
                JOptionPane.showMessageDialog(this, "Error al filtrar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void personalizarTituloYBorde() {
        try {
            BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();

            Color verdeTitulo = new Color(232, 245, 233);
            JComponent titleBar = ui.getNorthPane();
            titleBar.setBackground(verdeTitulo);
            titleBar.setOpaque(true);

            this.setBorder(createLineBorder(new Color(204, 255, 204), 4));

            PropertyChangeListener listener = evt -> {
                if ("frameType".equals(evt.getPropertyName())) {
                    this.setForeground(Color.WHITE);
                }
            };
            this.addPropertyChangeListener(listener);

            this.putClientProperty("JInternalFrame.activeTitleForeground", Color.WHITE);
            this.putClientProperty("JInternalFrame.inactiveTitleForeground", Color.WHITE);

            this.putClientProperty("JInternalFrame.activeTitleBackground", verdeTitulo);
            this.putClientProperty("JInternalFrame.inactiveTitleBackground", verdeTitulo);

        } catch (Exception e) {
            System.err.println("Error personalizando título: " + e);
        }
    }

    private void personalizarTabla() {
       
        JTableHeader header = tblCultivos.getTableHeader();
        header.setBackground(new Color(45, 95, 63));
        header.setForeground(new Color(0, 0, 0)); 
        header.setFont(new Font("Bell MT", Font.BOLD, 14));
        header.setOpaque(true);

        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
                setBackground(new Color(45, 95, 63));
                setForeground(new Color(0, 0, 0)); 
                setFont(new Font("Bell MT", Font.BOLD, 14));
                setHorizontalAlignment(SwingConstants.CENTER);
                setBorder(createLineBorder(new Color(0, 0, 0), 1));
            
                return this;
            }
        });
    
        tblCultivos.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
                if (isSelected) {
                    setBackground(new Color(144, 238, 144));
                    setForeground(new Color(0, 0, 0));
                } else {
                    setBackground(new Color(204, 255, 204));
                    setForeground(new Color(0, 0, 0));
                }
            
                setHorizontalAlignment(SwingConstants.CENTER);
            
                return this;
            }
        });
    
        tblCultivos.setGridColor(new Color(45, 95, 63));
        tblCultivos.setShowGrid(true);
        tblCultivos.setRowHeight(30);
        tblCultivos.setFillsViewportHeight(true);
    
        scpTabla.getViewport().setBackground(new Color(204, 255, 204));
    
        personalizarScrollBars();
    }

    private void personalizarScrollBars() {
        Color verdeOscuro = new Color(45, 95, 63);
        Color verdeClaro = new Color(204, 255, 204);
    
        scpTabla.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = verdeOscuro;
                this.trackColor = verdeClaro;
            }
        });
    
        scpTabla.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = verdeOscuro;
                this.trackColor = verdeClaro;
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        pnlTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        pnlBarraBusqueda = new javax.swing.JPanel();
        lblIconoBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        pnlFiltros = new javax.swing.JPanel();
        lblTituloFiltros = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        dtcFecha = new com.toedter.calendar.JDateChooser();
        lblNombre = new javax.swing.JLabel();
        cmbNombre = new javax.swing.JComboBox<>();
        lblEstado = new javax.swing.JLabel();
        cmbEstado = new javax.swing.JComboBox<>();
        pnlBotones = new javax.swing.JPanel();
        btnEditar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        scpTabla = new javax.swing.JScrollPane();
        tblCultivos = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestión de Cultivos");

        pnlPrincipal.setBackground(new java.awt.Color(45, 95, 63));

        pnlTitulo.setBackground(new java.awt.Color(45, 95, 63));

        lblTitulo.setFont(new java.awt.Font("Bell MT", 1, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Grafica.png"))); // NOI18N
        lblTitulo.setText("Gestiones de Cultivos");

        javax.swing.GroupLayout pnlTituloLayout = new javax.swing.GroupLayout(pnlTitulo);
        pnlTitulo.setLayout(pnlTituloLayout);
        pnlTituloLayout.setHorizontalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTituloLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitulo)
                .addGap(344, 344, 344))
        );
        pnlTituloLayout.setVerticalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pnlBarraBusqueda.setBackground(new java.awt.Color(45, 95, 63));
        pnlBarraBusqueda.setLayout(new java.awt.BorderLayout());

        lblIconoBuscar.setBackground(new java.awt.Color(204, 255, 204));
        lblIconoBuscar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblIconoBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Buscar.png"))); // NOI18N
        lblIconoBuscar.setOpaque(true);
        pnlBarraBusqueda.add(lblIconoBuscar, java.awt.BorderLayout.LINE_START);

        txtBuscar.setBackground(new java.awt.Color(204, 255, 204));
        txtBuscar.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        pnlBarraBusqueda.add(txtBuscar, java.awt.BorderLayout.CENTER);

        pnlFiltros.setBackground(new java.awt.Color(204, 255, 204));

        lblTituloFiltros.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblTituloFiltros.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloFiltros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/filtrar.png"))); // NOI18N
        lblTituloFiltros.setText("Filtros de Búsqueda");

        lblFecha.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFecha.setText("Fecha:");

        dtcFecha.setDateFormatString("dd/MM/yyyy");
        dtcFecha.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtcFechaPropertyChange(evt);
            }
        });

        lblNombre.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNombre.setText("Nombre:");

        cmbNombre.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        cmbNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNombreActionPerformed(evt);
            }
        });

        lblEstado.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstado.setText("Estado:");

        cmbEstado.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        cmbEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEstadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFiltrosLayout = new javax.swing.GroupLayout(pnlFiltros);
        pnlFiltros.setLayout(pnlFiltrosLayout);
        pnlFiltrosLayout.setHorizontalGroup(
            pnlFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltrosLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblTituloFiltros)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFiltrosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFiltrosLayout.createSequentialGroup()
                        .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dtcFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlFiltrosLayout.createSequentialGroup()
                        .addComponent(lblNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbNombre, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlFiltrosLayout.createSequentialGroup()
                        .addComponent(lblEstado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlFiltrosLayout.setVerticalGroup(
            pnlFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltrosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloFiltros)
                .addGap(18, 18, 18)
                .addGroup(pnlFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dtcFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(54, 54, 54)
                .addGroup(pnlFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(cmbNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(pnlFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstado)
                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        pnlBotones.setBackground(new java.awt.Color(45, 95, 63));
        pnlBotones.setLayout(new java.awt.GridLayout(1, 4));

        btnEditar.setBackground(new java.awt.Color(204, 255, 204));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Editar.png"))); // NOI18N
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        pnlBotones.add(btnEditar);

        btnActualizar.setBackground(new java.awt.Color(204, 255, 204));
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Refrescar.png"))); // NOI18N
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        pnlBotones.add(btnActualizar);

        btnEliminar.setBackground(new java.awt.Color(204, 255, 204));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Eliminar.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        pnlBotones.add(btnEliminar);

        btnCancelar.setBackground(new java.awt.Color(204, 255, 204));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/salir.png"))); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        pnlBotones.add(btnCancelar);

        tblCultivos.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        tblCultivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Tipo", "Área", "Estado", "Fecha Siembra", "Fecha Cosecha"
            }
        ));
        scpTabla.setViewportView(tblCultivos);

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlBarraBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addComponent(pnlFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scpTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 853, Short.MAX_VALUE))
                    .addComponent(pnlBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addComponent(pnlTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBarraBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scpTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(pnlFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int filaSeleccionada = tblCultivos.getSelectedRow();
    
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fila para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        try {
            int filaModelo = tblCultivos.convertRowIndexToModel(filaSeleccionada);
            int id = (int) modelo.getValueAt(filaModelo, 0);
        
            DTOCultivo dto = controlador.obtenerCultivo(id);

            JDesktopPane desktop = this.getDesktopPane();
            if (desktop != null) {
                IntFrmCultivo frmExistente = null;

                for (javax.swing.JInternalFrame frame : desktop.getAllFrames()) {
                    if (frame instanceof IntFrmCultivo) {
                        frmExistente = (IntFrmCultivo) frame;
                        break;
                    }
                }

                if (frmExistente != null) {
                    frmExistente.cargarCultivoParaEdicion(dto);
                    frmExistente.setVisible(true);
                    frmExistente.toFront();
                    try {
                        frmExistente.setSelected(true);
                    } catch (PropertyVetoException e) {
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Por favor, abra primero la ventana de Registro de Cultivos", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al editar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        cargarDatosTabla();
        
        txtBuscar.setText("");
        cmbNombre.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
        dtcFecha.setDate(null);
        sorter.setRowFilter(null);
        
        JOptionPane.showMessageDialog(this, "Tabla actualizada correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tblCultivos.getSelectedRow();
    
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fila para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        try {
            int filaModelo = tblCultivos.convertRowIndexToModel(filaSeleccionada);
            int id = (int) modelo.getValueAt(filaModelo, 0);
            String nombre = (String) modelo.getValueAt(filaModelo, 1);
        
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el cultivo: " + nombre + " (ID: " + id + ")?\n" +"Esta acción no se puede deshacer.", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean exito = controlador.eliminarCultivo(id);
            
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Cultivo eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatosTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el cultivo", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la ventana de gestión de producciones?", "Confirmar cierre", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        filtrarTabla();
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void cmbNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNombreActionPerformed
        filtrarTabla();
    }//GEN-LAST:event_cmbNombreActionPerformed

    private void cmbEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEstadoActionPerformed
        filtrarTabla();
    }//GEN-LAST:event_cmbEstadoActionPerformed

    private void dtcFechaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtcFechaPropertyChange
        if ("date".equals(evt.getPropertyName())) {
            filtrarTabla();
        }
    }//GEN-LAST:event_dtcFechaPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<String> cmbNombre;
    private com.toedter.calendar.JDateChooser dtcFecha;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblIconoBuscar;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTituloFiltros;
    private javax.swing.JPanel pnlBarraBusqueda;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlFiltros;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JScrollPane scpTabla;
    private javax.swing.JTable tblCultivos;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
