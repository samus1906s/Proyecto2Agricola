/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vista;

/**
 *
 * @author je110
 */
public class IntFrmTablaUsuarios extends javax.swing.JInternalFrame {

    /**
     * Creates new form IntFrmTablaUsuarios
     */
    public IntFrmTablaUsuarios() {
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
       
        javax.swing.table.JTableHeader header = tblUsuarios.getTableHeader();
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
    
        tblUsuarios.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
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
    
        tblUsuarios.setGridColor(new java.awt.Color(45, 95, 63));
        tblUsuarios.setShowGrid(true);
        tblUsuarios.setRowHeight(30);
        tblUsuarios.setFillsViewportHeight(true);
    
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
        pnlFiltro = new javax.swing.JPanel();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        lblRegistrados = new javax.swing.JLabel();
        scpTabla = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        pnlBotones = new javax.swing.JPanel();
        btnEditar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestión de Usuarios");
        setToolTipText("");

        pnlPrincipal.setBackground(new java.awt.Color(45, 95, 63));

        pnlTitulo.setBackground(new java.awt.Color(45, 95, 63));

        lblTitulo.setFont(new java.awt.Font("Bell MT", 1, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/usuario.png"))); // NOI18N
        lblTitulo.setText("Gestiones de Usuarios");

        javax.swing.GroupLayout pnlTituloLayout = new javax.swing.GroupLayout(pnlTitulo);
        pnlTitulo.setLayout(pnlTituloLayout);
        pnlTituloLayout.setHorizontalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTituloLayout.createSequentialGroup()
                .addContainerGap(217, Short.MAX_VALUE)
                .addComponent(lblTitulo)
                .addGap(215, 215, 215))
        );
        pnlTituloLayout.setVerticalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pnlFiltro.setBackground(new java.awt.Color(45, 95, 63));
        pnlFiltro.setLayout(new java.awt.BorderLayout());

        lblBuscar.setBackground(new java.awt.Color(204, 255, 204));
        lblBuscar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Buscar.png"))); // NOI18N
        lblBuscar.setOpaque(true);
        pnlFiltro.add(lblBuscar, java.awt.BorderLayout.LINE_START);

        txtBuscar.setBackground(new java.awt.Color(204, 255, 204));
        txtBuscar.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        pnlFiltro.add(txtBuscar, java.awt.BorderLayout.CENTER);

        lblRegistrados.setFont(new java.awt.Font("Bell MT", 1, 24)); // NOI18N
        lblRegistrados.setText("Usuarios Registrados");

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID ", "Nombre", "Usuario", "Contraseña", "Rol", "Estado"
            }
        ));
        scpTabla.setViewportView(tblUsuarios);

        pnlBotones.setBackground(new java.awt.Color(45, 95, 63));
        pnlBotones.setLayout(new java.awt.GridLayout(1, 4));

        btnEditar.setBackground(new java.awt.Color(204, 255, 204));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Editar.png"))); // NOI18N
        pnlBotones.add(btnEditar);

        btnActualizar.setBackground(new java.awt.Color(204, 255, 204));
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Refrescar.png"))); // NOI18N
        pnlBotones.add(btnActualizar);

        btnEliminar.setBackground(new java.awt.Color(204, 255, 204));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Eliminar.png"))); // NOI18N
        pnlBotones.add(btnEliminar);

        btnCancelar.setBackground(new java.awt.Color(204, 255, 204));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Cancelar.png"))); // NOI18N
        pnlBotones.add(btnCancelar);

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRegistrados)
                .addGap(247, 247, 247))
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpTabla, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlFiltro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addComponent(pnlTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRegistrados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scpTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblRegistrados;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlFiltro;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JScrollPane scpTabla;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
