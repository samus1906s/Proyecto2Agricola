/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vista;

import Controlador.ControladorProduccion;

/**
 *
 * @author je110
 */
public class IntFrmTablaProduccion extends javax.swing.JInternalFrame {

    private ControladorProduccion controlador;
    /**
     * Creates new form IntFrmTablaProduccion
     */
    public IntFrmTablaProduccion(ControladorProduccion controlador) {
        this.controlador = controlador;
        initComponents();
        personalizarTabla();
        personalizarTituloYBorde();
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

    private void personalizarTabla() {
       
        javax.swing.table.JTableHeader header = tblProduccion.getTableHeader();
        header.setBackground(new java.awt.Color(45, 95, 63));
        header.setForeground(new java.awt.Color(0, 0, 0)); 
        header.setFont(new java.awt.Font("Bell MT", java.awt.Font.BOLD, 14));
        header.setOpaque(true);

        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
                setBackground(new java.awt.Color(45, 95, 63));
                setForeground(new java.awt.Color(0, 0, 0)); 
                setFont(new java.awt.Font("Bell MT", java.awt.Font.BOLD, 14));
                setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                setBorder(javax.swing.BorderFactory.createLineBorder(
                new java.awt.Color(0, 0, 0), 1));
            
                return this;
            }
        });
    
        tblProduccion.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent( javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
                if (isSelected) {
                    setBackground(new java.awt.Color(144, 238, 144));
                    setForeground(new java.awt.Color(0, 0, 0));
                } else {
                    setBackground(new java.awt.Color(204, 255, 204));
                    setForeground(new java.awt.Color(0, 0, 0));
                }
            
                setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            
                return this;
            }
        });
    
        tblProduccion.setGridColor(new java.awt.Color(45, 95, 63));
        tblProduccion.setShowGrid(true);
        tblProduccion.setRowHeight(30);
        tblProduccion.setFillsViewportHeight(true);
    
        scpTabla.getViewport().setBackground(new java.awt.Color(204, 255, 204));
    
        personalizarScrollBars();
    }

    private void personalizarScrollBars() {
        java.awt.Color verdeOscuro = new java.awt.Color(45, 95, 63);
        java.awt.Color verdeClaro = new java.awt.Color(204, 255, 204);
    
        scpTabla.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = verdeOscuro;
                this.trackColor = verdeClaro;
            }
        });
    
        scpTabla.getHorizontalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
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
                .addGap(187, 187, 187))
        );
        pnlTituloLayout.setVerticalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo)
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

        lblCultivo.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblCultivo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCultivo.setText("Cultivo:");

        cmbCultivo.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        cmbCultivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblDestino.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblDestino.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDestino.setText("Destino:");

        cmbDestino.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        cmbDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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

        jButton1.setBackground(new java.awt.Color(204, 255, 204));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Editar.png"))); // NOI18N
        pnlBotones.add(jButton1);

        jButton2.setBackground(new java.awt.Color(204, 255, 204));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Refrescar.png"))); // NOI18N
        pnlBotones.add(jButton2);

        jButton3.setBackground(new java.awt.Color(204, 255, 204));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Eliminar.png"))); // NOI18N
        pnlBotones.add(jButton3);

        jButton4.setBackground(new java.awt.Color(204, 255, 204));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Cancelar.png"))); // NOI18N
        pnlBotones.add(jButton4);

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
                        .addComponent(scpTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE))
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbCultivo;
    private javax.swing.JComboBox<String> cmbDestino;
    private com.toedter.calendar.JDateChooser dtcFecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
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
