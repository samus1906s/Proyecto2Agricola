/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vista;

import Controlador.ControladorCultivo;
import DTOs.DTOCultivo;
import Modelo.TiposCultivo;
import Modelo.EstadoCrecimiento;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import javax.swing.JOptionPane;
import java.time.ZoneId;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
/**
 *
 * @author Valdelomaar
 */
public class IntFrmCultivo extends javax.swing.JInternalFrame {

    private ControladorCultivo controlador;
    private boolean modoEdicion = false;
    private DTOCultivo cultivoActual;

    public IntFrmCultivo() {
        this.controlador = new ControladorCultivo();
        initComponents();
        personalizarTituloYBorde();
        cbbTipo.setModel(new javax.swing.DefaultComboBoxModel(TiposCultivo.values()));
        cbbEstado.setModel(new javax.swing.DefaultComboBoxModel(EstadoCrecimiento.values()));
        limpiarCampos();
    }
    
    private void guardarCultivo() {
        try {
            if (!validarCampos()) {
                return;
            }

            DTOCultivo dto = crearDTODesdeFormulario();
        
            boolean exito;
        
            if (modoEdicion) {

                exito = controlador.actualizarCultivo(dto);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "✅ ¡Cultivo actualizado correctamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ No se pudo actualizar el cultivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                exito = controlador.registrarCultivo(dto);
                if (exito) {

                    int idGenerado = dto.getIdCultivo();
                    txtID.setText(String.valueOf(idGenerado));
                
                    JOptionPane.showMessageDialog(this, "✅ ¡Cultivo registrado con éxito!\n" +"📋 ID asignado: " + idGenerado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al registrar el cultivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {

        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del cultivo es obligatorio", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }

        try {
            
            double area = Double.parseDouble(txtArea.getText().trim());
            if (area <= 0) {
                JOptionPane.showMessageDialog(this, "El área debe ser mayor a 0", "Valor inválido", JOptionPane.WARNING_MESSAGE);
                txtArea.requestFocus();
                return false;
            }           
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El área debe ser un número válido", "Valor inválido", JOptionPane.WARNING_MESSAGE);
            txtArea.requestFocus();
            return false;
        }

        if (dateFechaSiembra.getDate() == null) {
            JOptionPane.showMessageDialog(this, "La fecha de siembra es obligatoria", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    
        if (dateFechaCosecha.getDate() == null) {
            JOptionPane.showMessageDialog(this, "La fecha de cosecha es obligatoria", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (dateFechaCosecha.getDate().before(dateFechaSiembra.getDate())) {
            JOptionPane.showMessageDialog(this, "La fecha de cosecha debe ser posterior a la fecha de siembra", "Fechas inválidas", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    
        return true;
    }

    private DTOCultivo crearDTODesdeFormulario() {
        DTOCultivo dto = new DTOCultivo();

        if (modoEdicion && cultivoActual != null) {
            dto.setIdCultivo(cultivoActual.getIdCultivo());
        }

        dto.setNombre(txtNombre.getText().trim());
        dto.setAreaSembrada(Double.parseDouble(txtArea.getText().trim()));

        dto.setTipo((TiposCultivo) cbbTipo.getSelectedItem());
        dto.setEstado((EstadoCrecimiento) cbbEstado.getSelectedItem());

        dto.setFechaSiembra(dateFechaSiembra.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dto.setFechaCosecha(dateFechaCosecha.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    
        return dto;
    }

    public void cargarCultivoParaEdicion(DTOCultivo cultivo) {
        this.cultivoActual = cultivo;
        this.modoEdicion = true;

        txtID.setText(String.valueOf(cultivo.getIdCultivo()));
        txtNombre.setText(cultivo.getNombre());
        txtArea.setText(String.valueOf(cultivo.getAreaSembrada()));

        cbbTipo.setSelectedItem(cultivo.getTipo());
        cbbEstado.setSelectedItem(cultivo.getEstado());

        dateFechaSiembra.setDate(java.sql.Date.valueOf(cultivo.getFechaSiembra()));
        dateFechaCosecha.setDate(java.sql.Date.valueOf(cultivo.getFechaCosecha()));

        lblRegistroCultivos.setText("Editar Cultivo");
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtArea.setText("");
    
        if (cbbTipo.getItemCount() > 0) {
            cbbTipo.setSelectedIndex(0);
        }
    
        if (cbbEstado.getItemCount() > 0) {
            cbbEstado.setSelectedIndex(0);
        }
    
        dateFechaSiembra.setDate(null);
        dateFechaCosecha.setDate(null);

        modoEdicion = false;
        cultivoActual = null;
        lblRegistroCultivos.setText("Registros de Cultivos");
    
        txtNombre.requestFocus();
    }

    private void personalizarTituloYBorde() {
        try {
            BasicInternalFrameUI ui =(BasicInternalFrameUI) this.getUI();

            Color verdeTitulo = new Color(232, 245, 233);  
            JComponent titleBar = ui.getNorthPane();
            titleBar.setBackground(verdeTitulo);
            titleBar.setOpaque(true);

            this.setBorder(BorderFactory.createLineBorder(new Color(232, 245, 233), 4));

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
        lblRegistroCultivos = new javax.swing.JLabel();
        pnlInformacionCultivo = new javax.swing.JPanel();
        lblID = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblArea = new javax.swing.JLabel();
        lblTipo = new javax.swing.JLabel();
        lblFechaSiembra = new javax.swing.JLabel();
        lblFechaCosecha = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtArea = new javax.swing.JTextField();
        cbbTipo = new javax.swing.JComboBox<>();
        cbbEstado = new javax.swing.JComboBox<>();
        dateFechaSiembra = new com.toedter.calendar.JDateChooser();
        dateFechaCosecha = new com.toedter.calendar.JDateChooser();
        lblHoja = new javax.swing.JLabel();
        pnlBotones = new javax.swing.JPanel();
        btnLimpiar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnMostrarTabla = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cultivos");
        setVerifyInputWhenFocusTarget(false);
        setVisible(true);

        pnlColorFondo.setBackground(new java.awt.Color(45, 95, 63));

        pnlTitulo.setBackground(new java.awt.Color(45, 95, 63));

        lblRegistroCultivos.setFont(new java.awt.Font("Bell MT", 1, 24)); // NOI18N
        lblRegistroCultivos.setForeground(new java.awt.Color(255, 255, 255));
        lblRegistroCultivos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRegistroCultivos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/registro.png"))); // NOI18N
        lblRegistroCultivos.setText("Registros de Cultivos");
        pnlTitulo.add(lblRegistroCultivos);

        pnlInformacionCultivo.setBackground(new java.awt.Color(204, 255, 204));

        lblID.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblID.setText("ID Cultivo:");

        lblNombre.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNombre.setText("Nombre del Cultivo:");

        lblArea.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblArea.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblArea.setText("Área Sembrada:");

        lblTipo.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblTipo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTipo.setText("Tipo de Cultivo:");

        lblFechaSiembra.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblFechaSiembra.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechaSiembra.setText("Fecha de Siembra:");

        lblFechaCosecha.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblFechaCosecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechaCosecha.setText("Fecha de Cosecha:");

        lblEstado.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstado.setText("Estado de Crecimiento:");

        txtID.setEditable(false);
        txtID.setEnabled(false);

        dateFechaSiembra.setDateFormatString("dd/MM/yyyy");

        dateFechaCosecha.setDateFormatString("dd/MM/yyyy");

        lblHoja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHoja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Hoja.png"))); // NOI18N

        javax.swing.GroupLayout pnlInformacionCultivoLayout = new javax.swing.GroupLayout(pnlInformacionCultivo);
        pnlInformacionCultivo.setLayout(pnlInformacionCultivoLayout);
        pnlInformacionCultivoLayout.setHorizontalGroup(
            pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                        .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                                .addComponent(lblEstado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                                        .addComponent(lblNombre)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                                        .addComponent(lblFechaCosecha)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dateFechaCosecha, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                                        .addComponent(lblFechaSiembra)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dateFechaSiembra, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                                        .addComponent(lblTipo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                                        .addComponent(lblArea)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 19, Short.MAX_VALUE)))
                        .addGap(6, 6, 6))
                    .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                        .addComponent(lblID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHoja, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnlInformacionCultivoLayout.setVerticalGroup(
            pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                .addComponent(lblHoja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(32, 32, 32)
                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblID)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblArea)
                    .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipo)
                    .addComponent(cbbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblFechaSiembra)
                    .addComponent(dateFechaSiembra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateFechaCosecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFechaCosecha))
                .addGap(18, 18, 18)
                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstado)
                    .addComponent(cbbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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
                            .addComponent(pnlInformacionCultivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        pnlColorFondoLayout.setVerticalGroup(
            pnlColorFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlColorFondoLayout.createSequentialGroup()
                .addComponent(pnlTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(pnlInformacionCultivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(pnlColorFondo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardarCultivo();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnMostrarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarTablaActionPerformed
        IntFrmTablaCultivo tabla = new IntFrmTablaCultivo();
    
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
    private javax.swing.JComboBox<String> cbbTipo;
    private com.toedter.calendar.JDateChooser dateFechaCosecha;
    private com.toedter.calendar.JDateChooser dateFechaSiembra;
    private javax.swing.JLabel lblArea;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFechaCosecha;
    private javax.swing.JLabel lblFechaSiembra;
    private javax.swing.JLabel lblHoja;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblRegistroCultivos;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlColorFondo;
    private javax.swing.JPanel pnlInformacionCultivo;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JTextField txtArea;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
