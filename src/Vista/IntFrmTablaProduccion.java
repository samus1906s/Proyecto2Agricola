/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vista;

import Controlador.ControladorProduccion;
import Controlador.ControladorCultivo;
import DTOs.ProduccionDTO;
import DTOs.DTOCultivo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.JComponent;
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
public class IntFrmTablaProduccion extends javax.swing.JInternalFrame {

    private ControladorProduccion controlador;
    private ControladorCultivo controladorCultivo;
    private TableRowSorter<DefaultTableModel> sorter;
    private DefaultTableModel modelo;

    public IntFrmTablaProduccion(ControladorProduccion controlador, ControladorCultivo controladorCultivo) {
        this.controlador = controlador;
        this.controladorCultivo = controladorCultivo;
        initComponents();
        configurarTabla();
        cargarCombos();
        cargarDatosTabla();
        personalizarTabla();
        personalizarTituloYBorde();
    }
    
    private void configurarTabla() {
        modelo = (DefaultTableModel) tblProduccion.getModel();
        sorter = new TableRowSorter<>(modelo);
        tblProduccion.setRowSorter(sorter);
    }
    
    private void cargarCombos() {
        try {

            cmbCultivo.removeAllItems();
            cmbCultivo.addItem("Todos");
            
            List<DTOCultivo> cultivos = controladorCultivo.listarCultivos();
            if (cultivos != null) {
                for (DTOCultivo cultivo : cultivos) {
                    cmbCultivo.addItem(cultivo.getNombre());
                }
            }

            cmbDestino.removeAllItems();
            cmbDestino.addItem("Todos");
            cmbDestino.addItem("Venta");
            cmbDestino.addItem("Almacenamiento");
            cmbDestino.addItem("Procesamiento");
            cmbDestino.addItem("Exportación");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar filtros: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarDatosTabla() {
        try {
            modelo.setRowCount(0);
            
            List<ProduccionDTO> producciones = controlador.listarProducciones();
            
            if (producciones != null && !producciones.isEmpty()) {
                for (ProduccionDTO p : producciones) {
                    Object[] fila = new Object[6];
                    fila[0] = p.getIdProduccion();
                    fila[1] = obtenerNombreCultivo(p.getCultivoId());
                    fila[2] = p.getFecha().toString();
                    fila[3] = p.getCantidadRecolectada() + " kg";
                    fila[4] = p.getCalidadProducto();
                    fila[5] = p.getDestino();
                    
                    modelo.addRow(fila);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No hay producciones registradas", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private String obtenerNombreCultivo(Integer cultivoId) {
        try {
            if (cultivoId == null) return "Desconocido";
            
            DTOCultivo cultivo = controladorCultivo.obtenerCultivo(cultivoId);
            return cultivo != null ? cultivo.getNombre() : "Cultivo #" + cultivoId;            
        } catch (Exception e) {
            return "Error";
        }
    }
    
    private void filtrarTabla() {
        try {
        
            if (cmbCultivo.getSelectedItem() == null || cmbDestino.getSelectedItem() == null) {
                return; 
            }
        
           String cultivo = cmbCultivo.getSelectedItem().toString();
           String destino = cmbDestino.getSelectedItem().toString();
           Date fecha = dtcFecha.getDate();
           String busqueda = txtBuscar.getText().trim();
        
           List<RowFilter<DefaultTableModel, Object>> filtros = new ArrayList<>();
        
            if (!busqueda.isEmpty()) {
                filtros.add(RowFilter.regexFilter("(?i)" + busqueda));
            }
        
            if (!cultivo.equals("Todos")) {
                filtros.add(RowFilter.regexFilter(cultivo, 1));
            }
        
            if (!destino.equals("Todos")) {
                filtros.add(RowFilter.regexFilter(destino, 5));
            }
        
            if (fecha != null) {
                LocalDate fechaSeleccionada = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                filtros.add(RowFilter.regexFilter(fechaSeleccionada.toString(), 2));
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
       
        JTableHeader header = tblProduccion.getTableHeader();
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
                setFont(new java.awt.Font("Bell MT", Font.BOLD, 14));
                setHorizontalAlignment(SwingConstants.CENTER);
                setBorder(createLineBorder(new Color(0, 0, 0), 1));
            
                return this;
            }
        });
    
        tblProduccion.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
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
    
        tblProduccion.setGridColor(new Color(45, 95, 63));
        tblProduccion.setShowGrid(true);
        tblProduccion.setRowHeight(30);
        tblProduccion.setFillsViewportHeight(true);
    
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
        lblCultivo = new javax.swing.JLabel();
        cmbCultivo = new javax.swing.JComboBox<>();
        lblDestino = new javax.swing.JLabel();
        cmbDestino = new javax.swing.JComboBox<>();
        pnlBotones = new javax.swing.JPanel();
        btnEditar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        scpTabla = new javax.swing.JScrollPane();
        tblProduccion = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestión de Producciones");

        pnlPrincipal.setBackground(new java.awt.Color(45, 95, 63));

        pnlTitulo.setBackground(new java.awt.Color(45, 95, 63));

        lblTitulo.setFont(new java.awt.Font("Bell MT", 1, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Grafica.png"))); // NOI18N
        lblTitulo.setText("Gestiones de Producciones");

        javax.swing.GroupLayout pnlTituloLayout = new javax.swing.GroupLayout(pnlTitulo);
        pnlTitulo.setLayout(pnlTituloLayout);
        pnlTituloLayout.setHorizontalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTituloLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitulo)
                .addGap(259, 259, 259))
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

        lblCultivo.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblCultivo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCultivo.setText("Cultivo:");

        cmbCultivo.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        cmbCultivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCultivoActionPerformed(evt);
            }
        });

        lblDestino.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblDestino.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDestino.setText("Destino:");

        cmbDestino.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        cmbDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDestinoActionPerformed(evt);
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFiltrosLayout.createSequentialGroup()
                        .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dtcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFiltrosLayout.createSequentialGroup()
                        .addComponent(lblCultivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCultivo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFiltrosLayout.createSequentialGroup()
                        .addComponent(lblDestino)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
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
                    .addComponent(lblCultivo)
                    .addComponent(cmbCultivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(pnlFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDestino)
                    .addComponent(cmbDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        tblProduccion.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        tblProduccion.setForeground(new java.awt.Color(0, 0, 0));
        tblProduccion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Cultivo", "Fecha", "Cantidad", "Calidad", "Destino"
            }
        ));
        scpTabla.setViewportView(tblProduccion);

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
        int filaSeleccionada = tblProduccion.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fila para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int filaModelo = tblProduccion.convertRowIndexToModel(filaSeleccionada);
            int id = (int) modelo.getValueAt(filaModelo, 0);
            
            ProduccionDTO dto = controlador.obtenerProduccion(id);
            
            IntFrmProduccion frmEdicion = new IntFrmProduccion(controlador);
            frmEdicion.cargarDatos(dto);
            
            javax.swing.JDesktopPane desktop = this.getDesktopPane();
            if (desktop != null) {
                desktop.add(frmEdicion);
                frmEdicion.setVisible(true);
                frmEdicion.toFront();
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al editar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        cargarDatosTabla();
        
        txtBuscar.setText("");
        cmbCultivo.setSelectedIndex(0);
        cmbDestino.setSelectedIndex(0);
        dtcFecha.setDate(null);
        sorter.setRowFilter(null);
        
        JOptionPane.showMessageDialog(this, "Tabla actualizada correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tblProduccion.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fila para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int filaModelo = tblProduccion.convertRowIndexToModel(filaSeleccionada);
            int id = (int) modelo.getValueAt(filaModelo, 0);
            
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar la producción con ID: " + id + "?\n" +"Esta acción no se puede deshacer.", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean exito = controlador.eliminarProduccion(id);
                
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Registro eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatosTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el registro", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void cmbCultivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCultivoActionPerformed
        filtrarTabla();
    }//GEN-LAST:event_cmbCultivoActionPerformed

    private void cmbDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDestinoActionPerformed
        filtrarTabla();
    }//GEN-LAST:event_cmbDestinoActionPerformed

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
    private javax.swing.JComboBox<String> cmbCultivo;
    private javax.swing.JComboBox<String> cmbDestino;
    private com.toedter.calendar.JDateChooser dtcFecha;
    private javax.swing.JLabel lblCultivo;
    private javax.swing.JLabel lblDestino;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblIconoBuscar;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTituloFiltros;
    private javax.swing.JPanel pnlBarraBusqueda;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlFiltros;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JScrollPane scpTabla;
    private javax.swing.JTable tblProduccion;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
