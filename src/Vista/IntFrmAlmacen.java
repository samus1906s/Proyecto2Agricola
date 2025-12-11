/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vista;

import Controlador.ControladorAlmacen;
import Controlador.ControladorProduccion;
import DTOs.AlmacenDTO;
import DTOs.ProduccionDTO;
import Modelo.EstadoAlmacen;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
/**
 *
 * @author je110
 */
public class IntFrmAlmacen extends javax.swing.JInternalFrame {

    private ControladorAlmacen controlador;
    private ControladorProduccion controladorProduccion;
    private boolean modoEdicion = false;
    private AlmacenDTO almacenActual;
 
    public IntFrmAlmacen() {
        this.controlador = new ControladorAlmacen();
        this.controladorProduccion = new ControladorProduccion();
        initComponents();
        personalizarTituloYBorde();
        cargarComboBoxes();
        limpiarCampos();
    }
    
    private void cargarComboBoxes() {

        cbbEstado.setModel(new DefaultComboBoxModel<>(Arrays.stream(EstadoAlmacen.values()).map(Enum::name).toArray(String[]::new)));

        if (cbbEstado.getItemCount() > 0) {
            cbbEstado.setSelectedIndex(0);
        }
 
        try {
            List<ProduccionDTO> producciones = controladorProduccion.listarProducciones();
            DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
            
            for (ProduccionDTO p : producciones) {
                modelo.addElement(String.valueOf(p.getIdProduccion()));
            }
            
            cbbIDProduccion.setModel(modelo);
            
            if (cbbIDProduccion.getItemCount() > 0) {
                cbbIDProduccion.setSelectedIndex(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar producciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void guardarAlmacen() {
        try {
            if (!validarCampos()) {
                return;
            }

            AlmacenDTO dto = crearDTODesdeFormulario();
            boolean exito;
            
            if (modoEdicion) {
                exito = controlador.actualizarAlmacen(dto);
                if (exito) {
                   
                    mostrarMensajeTemporal("Actualizado");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                exito = controlador.registrarAlmacen(dto);
                if (exito) {
                    int idGenerado = dto.getId();
                    txtID.setText(String.valueOf(idGenerado));
                    
                   
                    mostrarMensajeTemporal("Actualizado");
                    
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    
    private void mostrarMensajeTemporal(String mensaje) {
        labelCreado.setText(mensaje);
        labelCreado.setForeground(new Color(0, 102, 0)); 
        
        
        javax.swing.Timer timer = new javax.swing.Timer(3000, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                labelCreado.setText(""); 
                ((javax.swing.Timer)e.getSource()).stop(); 
            }
        });
        timer.setRepeats(false); 
        timer.start();
    }

    private boolean validarCampos() {

        if (cbbIDProduccion.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una producción", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            cbbIDProduccion.requestFocus();
            return false;
        }

        try {
            double cantidad = Double.parseDouble(txtCantidad.getText().trim());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0", "Valor inválido", JOptionPane.WARNING_MESSAGE);
                txtCantidad.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número válido", "Valor inválido", JOptionPane.WARNING_MESSAGE);
            txtCantidad.requestFocus();
            return false;
        }

        if (dtcFechaIngreso.getDate() == null) {
            JOptionPane.showMessageDialog(this, "La fecha de ingreso es obligatoria", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (dtcFechaEgreso.getDate() != null) {
            if (dtcFechaEgreso.getDate().before(dtcFechaIngreso.getDate())) {
                JOptionPane.showMessageDialog(this, "La fecha de egreso debe ser posterior a la fecha de ingreso", "Fechas inválidas", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        
        return true;
    }

    private AlmacenDTO crearDTODesdeFormulario() {
        AlmacenDTO dto = new AlmacenDTO();

        if (modoEdicion && almacenActual != null) {
            dto.setId(almacenActual.getId());
        }

        dto.setProduccionId(Integer.parseInt(cbbIDProduccion.getSelectedItem().toString()));
        dto.setCantidadDisponible(Double.parseDouble(txtCantidad.getText().trim()));

        String estadoSeleccionado = cbbEstado.getSelectedItem().toString();
        dto.setEstado(EstadoAlmacen.valueOf(estadoSeleccionado));
        
        dto.setFechaIngreso(dtcFechaIngreso.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
  
        if (dtcFechaEgreso.getDate() != null) {
            dto.setFechaEgreso(dtcFechaEgreso.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        } else {
            dto.setFechaEgreso(null);
        }
        
        return dto;
    }

    public void cargarAlmacenParaEdicion(AlmacenDTO almacen) {
        this.almacenActual = almacen;
        this.modoEdicion = true;

        txtID.setText(String.valueOf(almacen.getId()));
        cbbIDProduccion.setSelectedItem(String.valueOf(almacen.getProduccionId()));
        txtCantidad.setText(String.valueOf(almacen.getCantidadDisponible()));

        cbbEstado.setSelectedItem(almacen.getEstado().name());

        dtcFechaIngreso.setDate(java.sql.Date.valueOf(almacen.getFechaIngreso()));
        
        if (almacen.getFechaEgreso() != null) {
            dtcFechaEgreso.setDate(java.sql.Date.valueOf(almacen.getFechaEgreso()));
        } else {
            dtcFechaEgreso.setDate(null);
        }

        lblRegistroAlmacen.setText("Editar Almacén");
    }

    private void limpiarCampos() {
        txtID.setText("");
        txtCantidad.setText("");
        
        if (cbbIDProduccion.getItemCount() > 0) {
            cbbIDProduccion.setSelectedIndex(0);
        }
        
        if (cbbEstado.getItemCount() > 0) {
            cbbEstado.setSelectedIndex(0);
        }
        
        dtcFechaIngreso.setDate(null);
        dtcFechaEgreso.setDate(null);

        modoEdicion = false;
        almacenActual = null;
        lblRegistroAlmacen.setText("Registros de Almacén");
        
        cbbIDProduccion.requestFocus();
    }

    private void personalizarTituloYBorde() {
        try {
            BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();

            Color verdeTitulo = new Color(232, 245, 233);  
            JComponent titleBar = ui.getNorthPane();
            titleBar.setBackground(verdeTitulo);
            titleBar.setOpaque(true);

            this.setBorder(BorderFactory.createLineBorder(new java.awt.Color(232, 245, 233), 4));

            PropertyChangeListener listener = evt -> {
                if ("frameType".equals(evt.getPropertyName())) {
                    this.setForeground(java.awt.Color.WHITE);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrpEstadosCrecimiento = new javax.swing.ButtonGroup();
        pnlColorFondo = new javax.swing.JPanel();
        pnlTitulo = new javax.swing.JPanel();
        lblRegistroAlmacen = new javax.swing.JLabel();
        pnlInformacionAlmacen = new javax.swing.JPanel();
        lblID = new javax.swing.JLabel();
        lblIDProduccion = new javax.swing.JLabel();
        lblCantidad = new javax.swing.JLabel();
        lblFechaIngreso = new javax.swing.JLabel();
        lblFechaEgreso = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        cbbEstado = new javax.swing.JComboBox<>();
        dtcFechaEgreso = new com.toedter.calendar.JDateChooser();
        lblHoja = new javax.swing.JLabel();
        dtcFechaIngreso = new com.toedter.calendar.JDateChooser();
        cbbIDProduccion = new javax.swing.JComboBox<>();
        labelCreado = new javax.swing.JLabel();
        pnlBotones = new javax.swing.JPanel();
        btnLimpiar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnMostrarTabla = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Almacén");
        setVerifyInputWhenFocusTarget(false);
        setVisible(true);

        pnlColorFondo.setBackground(new java.awt.Color(45, 95, 63));

        pnlTitulo.setBackground(new java.awt.Color(45, 95, 63));

        lblRegistroAlmacen.setFont(new java.awt.Font("Bell MT", 1, 24)); // NOI18N
        lblRegistroAlmacen.setForeground(new java.awt.Color(255, 255, 255));
        lblRegistroAlmacen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRegistroAlmacen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/registro.png"))); // NOI18N
        lblRegistroAlmacen.setText("Registros de Almacén");
        pnlTitulo.add(lblRegistroAlmacen);

        pnlInformacionAlmacen.setBackground(new java.awt.Color(204, 255, 204));

        lblID.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblID.setText("ID:");

        lblIDProduccion.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblIDProduccion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIDProduccion.setText("ID Producción:");

        lblCantidad.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblCantidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCantidad.setText("Cantidad:");

        lblFechaIngreso.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblFechaIngreso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechaIngreso.setText("Fecha Ingreso:");

        lblFechaEgreso.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblFechaEgreso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechaEgreso.setText("Fecha Egreso:");

        lblEstado.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstado.setText("Estado");

        txtID.setEditable(false);
        txtID.setEnabled(false);

        dtcFechaEgreso.setDateFormatString("dd/MM/yyyy");

        lblHoja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHoja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Hoja.png"))); // NOI18N

        dtcFechaIngreso.setDateFormatString("dd/MM/yyyy");

        labelCreado.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N

        javax.swing.GroupLayout pnlInformacionAlmacenLayout = new javax.swing.GroupLayout(pnlInformacionAlmacen);
        pnlInformacionAlmacen.setLayout(pnlInformacionAlmacenLayout);
        pnlInformacionAlmacenLayout.setHorizontalGroup(
            pnlInformacionAlmacenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInformacionAlmacenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInformacionAlmacenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInformacionAlmacenLayout.createSequentialGroup()
                        .addComponent(lblID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(192, Short.MAX_VALUE))
                    .addGroup(pnlInformacionAlmacenLayout.createSequentialGroup()
                        .addGroup(pnlInformacionAlmacenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInformacionAlmacenLayout.createSequentialGroup()
                                .addComponent(lblEstado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlInformacionAlmacenLayout.createSequentialGroup()
                                .addGroup(pnlInformacionAlmacenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnlInformacionAlmacenLayout.createSequentialGroup()
                                        .addComponent(lblIDProduccion)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbbIDProduccion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(pnlInformacionAlmacenLayout.createSequentialGroup()
                                        .addComponent(lblCantidad)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlInformacionAlmacenLayout.createSequentialGroup()
                                        .addComponent(lblFechaIngreso)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dtcFechaIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(pnlInformacionAlmacenLayout.createSequentialGroup()
                                        .addComponent(lblFechaEgreso)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dtcFechaEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelCreado)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInformacionAlmacenLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblHoja, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105))
        );
        pnlInformacionAlmacenLayout.setVerticalGroup(
            pnlInformacionAlmacenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInformacionAlmacenLayout.createSequentialGroup()
                .addComponent(lblHoja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(32, 32, 32)
                .addGroup(pnlInformacionAlmacenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblID)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(pnlInformacionAlmacenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIDProduccion)
                    .addComponent(cbbIDProduccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(pnlInformacionAlmacenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCantidad)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(pnlInformacionAlmacenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblFechaIngreso)
                    .addComponent(dtcFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(pnlInformacionAlmacenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblFechaEgreso)
                    .addComponent(dtcFechaEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pnlInformacionAlmacenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstado)
                    .addComponent(cbbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCreado))
                .addGap(52, 52, 52))
        );

        pnlBotones.setBackground(new java.awt.Color(45, 95, 63));
        pnlBotones.setLayout(new java.awt.GridLayout(1, 3));

        btnLimpiar.setBackground(new java.awt.Color(204, 255, 204));
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Limpiar.png"))); // NOI18N
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        pnlBotones.add(btnLimpiar);

        btnGuardar.setBackground(new java.awt.Color(204, 255, 204));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Guardar.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        pnlBotones.add(btnGuardar);

        btnMostrarTabla.setBackground(new java.awt.Color(204, 255, 204));
        btnMostrarTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Tabla.png"))); // NOI18N
        btnMostrarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarTablaActionPerformed(evt);
            }
        });
        pnlBotones.add(btnMostrarTabla);

        javax.swing.GroupLayout pnlColorFondoLayout = new javax.swing.GroupLayout(pnlColorFondo);
        pnlColorFondo.setLayout(pnlColorFondoLayout);
        pnlColorFondoLayout.setHorizontalGroup(
            pnlColorFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlColorFondoLayout.createSequentialGroup()
                .addGroup(pnlColorFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlColorFondoLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(pnlColorFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlInformacionAlmacen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        pnlColorFondoLayout.setVerticalGroup(
            pnlColorFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlColorFondoLayout.createSequentialGroup()
                .addComponent(pnlTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(pnlInformacionAlmacen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(pnlColorFondo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardarAlmacen();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnMostrarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarTablaActionPerformed
        IntFrmTablaAlmacen tabla = new IntFrmTablaAlmacen();
        
        JDesktopPane desktop = this.getDesktopPane();
        if (desktop != null) {
            desktop.add(tabla);
            tabla.setVisible(true);
            tabla.toFront();
        }
    }//GEN-LAST:event_btnMostrarTablaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGrpEstadosCrecimiento;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnMostrarTabla;
    private javax.swing.JComboBox<String> cbbEstado;
    private javax.swing.JComboBox<String> cbbIDProduccion;
    private com.toedter.calendar.JDateChooser dtcFechaEgreso;
    private com.toedter.calendar.JDateChooser dtcFechaIngreso;
    private javax.swing.JLabel labelCreado;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFechaEgreso;
    private javax.swing.JLabel lblFechaIngreso;
    private javax.swing.JLabel lblHoja;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblIDProduccion;
    private javax.swing.JLabel lblRegistroAlmacen;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlColorFondo;
    private javax.swing.JPanel pnlInformacionAlmacen;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtID;
    // End of variables declaration//GEN-END:variables
}
