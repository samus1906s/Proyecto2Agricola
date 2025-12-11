/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vista;

import Controlador.ControladorTrabajador;
import DTOs.TrabajadorDTO;
import Modelo.TipoPuesto;
import Modelo.TrabajadorCampo;
import Modelo.Personas;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
/**
 *
 * @author Valdelomaar
 */
public class IntFrmTrabajador extends javax.swing.JInternalFrame {

    private ControladorTrabajador controlador;
    private boolean modoEdicion = false;
    private TrabajadorDTO trabajadorActual;

    public IntFrmTrabajador() {
        this.controlador = new ControladorTrabajador();
        initComponents();
        personalizarTituloYBorde();
        configurarComboBoxes();
        limpiarCampos();
    }
    
   private void configurarComboBoxes() {
        cmbPuesto.setModel(new DefaultComboBoxModel(TipoPuesto.values()));
        cbbTipoTrabajador.setModel(new DefaultComboBoxModel(TrabajadorCampo.values()));
    }

    private String obtenerTelefono() {
        String telefono = txtTelefono.getText();
        telefono = telefono.replaceAll("[^0-9-]", "");
        return telefono;
    }
    
    private void guardarTrabajador() {
        try {
            if (!validarCampos()) {
                return;
            }

            TrabajadorDTO dto = crearDTODesdeFormulario();
            boolean exito;
            
            if (modoEdicion) {
                exito = controlador.actualizarTrabajador(dto);
                if (exito) {
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ No se pudo actualizar el trabajador.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                exito = controlador.registrarTrabajador(dto);
                if (exito) {
                    int idGenerado = dto.getIdTrabajador();
                    txtIDTrabajador.setText(String.valueOf(idGenerado));
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al registrar el trabajador.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "❌ Error de validación:\n" + e.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {

        if (txtCedula.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La cédula es obligatoria", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtCedula.requestFocus();
            return false;
        }
        
        String cedula = txtCedula.getText().trim();
        if (!cedula.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "La cédula solo debe contener números", "Formato inválido", JOptionPane.WARNING_MESSAGE);
            txtCedula.requestFocus();
            return false;
        }

        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }

        String telefono = obtenerTelefono();
        if (telefono.isEmpty() || telefono.contains("_")) {
            JOptionPane.showMessageDialog(this, "El teléfono es obligatorio y debe estar completo", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtTelefono.requestFocus();
            return false;
        }

        if (!Personas.validarTelefono(telefono)) {
            JOptionPane.showMessageDialog(this, "Formato de teléfono inválido.\n" +"Formatos válidos: 00-00-00-00 o 00000000", "Teléfono inválido", JOptionPane.WARNING_MESSAGE);
            txtTelefono.requestFocus();
            return false;
        }

        String correo = txtCorreo.getText().trim();
        if (correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El correo electrónico es obligatorio", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtCorreo.requestFocus();
            return false;
        }
        
        if (!Personas.validarCorreo(correo)) {
            JOptionPane.showMessageDialog(this, "Formato de correo electrónico inválido", "Correo inválido", JOptionPane.WARNING_MESSAGE);
            txtCorreo.requestFocus();
            return false;
        }

        try {
            double salario = Double.parseDouble(txtSalario.getText().trim());
            if (salario <= 0) {
                JOptionPane.showMessageDialog(this, "El salario debe ser mayor a 0", "Valor inválido", JOptionPane.WARNING_MESSAGE);
                txtSalario.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El salario debe ser un número válido", "Valor inválido", JOptionPane.WARNING_MESSAGE);
            txtSalario.requestFocus();
            return false;
        }
        
        return true;
    }

    private TrabajadorDTO crearDTODesdeFormulario() {
        TrabajadorDTO dto = new TrabajadorDTO();

        if (modoEdicion && trabajadorActual != null) {
            dto.setIdTrabajador(trabajadorActual.getIdTrabajador());
        }

        dto.setCedula(txtCedula.getText().trim());
        dto.setNombre(txtNombre.getText().trim());
        dto.setTelefono(obtenerTelefono()); 
        dto.setCorreo(txtCorreo.getText().trim());
        dto.setPuesto((TipoPuesto) cmbPuesto.getSelectedItem());
        dto.setTipoTrabajador((TrabajadorCampo) cbbTipoTrabajador.getSelectedItem());
        dto.setSalario(Double.parseDouble(txtSalario.getText().trim()));
        
        return dto;
    }

    public void cargarTrabajadorParaEdicion(TrabajadorDTO trabajador) {
        this.trabajadorActual = trabajador;
        this.modoEdicion = true;

        txtIDTrabajador.setText(String.valueOf(trabajador.getIdTrabajador()));
        txtCedula.setText(trabajador.getCedula());
        txtNombre.setText(trabajador.getNombre());
        txtTelefono.setText(trabajador.getTelefono());
        txtCorreo.setText(trabajador.getCorreo());
        txtSalario.setText(String.valueOf(trabajador.getSalario()));

        cmbPuesto.setSelectedItem(trabajador.getPuesto());
        cbbTipoTrabajador.setSelectedItem(trabajador.getTipoTrabajador());

        lblRegistroTrabajadores.setText("Editar Trabajador");
    }

    private void limpiarCampos() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtSalario.setText("");
        
        if (cmbPuesto.getItemCount() > 0) {
            cmbPuesto.setSelectedIndex(0);
        }
        
        if (cbbTipoTrabajador.getItemCount() > 0) {
            cbbTipoTrabajador.setSelectedIndex(0);
        }

        modoEdicion = false;
        trabajadorActual = null;
        lblRegistroTrabajadores.setText("Registros de Trabajadores");
        
        txtCedula.requestFocus();
    }

    private void personalizarTituloYBorde() {
        try {
            BasicInternalFrameUI ui =(BasicInternalFrameUI) this.getUI();

            Color verdeTitulo = new Color(232, 245, 233);  
            JComponent titleBar = ui.getNorthPane();
            titleBar.setBackground(verdeTitulo);
            titleBar.setOpaque(true);

            this.setBorder(createLineBorder(new Color(232, 245, 233), 4));

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
        lblRegistroTrabajadores = new javax.swing.JLabel();
        pnlInformacionCultivo = new javax.swing.JPanel();
        lblIDTrabajador = new javax.swing.JLabel();
        lblCedula = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        lblPuesto = new javax.swing.JLabel();
        lblTipoTrabajador = new javax.swing.JLabel();
        txtIDTrabajador = new javax.swing.JTextField();
        txtCedula = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        cbbTipoTrabajador = new javax.swing.JComboBox<>();
        lblHoja = new javax.swing.JLabel();
        cmbPuesto = new javax.swing.JComboBox<>();
        txtCorreo = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JFormattedTextField();
        lblSalario = new javax.swing.JLabel();
        txtSalario = new javax.swing.JTextField();
        pnlBotones = new javax.swing.JPanel();
        btnLimpiar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnMostrarTabla = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Trabajador");
        setVerifyInputWhenFocusTarget(false);
        setVisible(true);

        pnlColorFondo.setBackground(new java.awt.Color(45, 95, 63));

        pnlTitulo.setBackground(new java.awt.Color(45, 95, 63));

        lblRegistroTrabajadores.setFont(new java.awt.Font("Bell MT", 1, 24)); // NOI18N
        lblRegistroTrabajadores.setForeground(new java.awt.Color(255, 255, 255));
        lblRegistroTrabajadores.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRegistroTrabajadores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/registro.png"))); // NOI18N
        lblRegistroTrabajadores.setText("Registros de Trabajadores");
        pnlTitulo.add(lblRegistroTrabajadores);

        pnlInformacionCultivo.setBackground(new java.awt.Color(204, 255, 204));

        lblIDTrabajador.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblIDTrabajador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIDTrabajador.setText("ID Trabajador:");

        lblCedula.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblCedula.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCedula.setText("Cedula:");

        lblNombre.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNombre.setText("Nombre:");

        lblTelefono.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblTelefono.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTelefono.setText("Teléfono:");

        lblCorreo.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblCorreo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCorreo.setText("Correo Eléctronico:");

        lblPuesto.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblPuesto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPuesto.setText("Puesto:");

        lblTipoTrabajador.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblTipoTrabajador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTipoTrabajador.setText("Tipo de Trabajador:");

        txtIDTrabajador.setEditable(false);
        txtIDTrabajador.setEnabled(false);

        lblHoja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHoja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Hoja.png"))); // NOI18N

        txtCorreo.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        try {
            txtTelefono.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-##-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        lblSalario.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        lblSalario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSalario.setText("Salario");

        txtSalario.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout pnlInformacionCultivoLayout = new javax.swing.GroupLayout(pnlInformacionCultivo);
        pnlInformacionCultivo.setLayout(pnlInformacionCultivoLayout);
        pnlInformacionCultivoLayout.setHorizontalGroup(
            pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInformacionCultivoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblHoja, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
            .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                        .addComponent(lblTipoTrabajador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbTipoTrabajador, 0, 126, Short.MAX_VALUE))
                    .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                        .addComponent(lblCorreo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCorreo))
                    .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                        .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlInformacionCultivoLayout.createSequentialGroup()
                                    .addComponent(lblCedula)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                                    .addComponent(lblIDTrabajador)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtIDTrabajador, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                                .addComponent(lblPuesto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNombre)
                                    .addComponent(lblTelefono))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                                .addComponent(lblSalario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlInformacionCultivoLayout.setVerticalGroup(
            pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInformacionCultivoLayout.createSequentialGroup()
                .addComponent(lblHoja, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIDTrabajador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIDTrabajador))
                .addGap(35, 35, 35)
                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCedula)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefono)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCorreo)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPuesto)
                    .addComponent(cmbPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoTrabajador)
                    .addComponent(cbbTipoTrabajador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(pnlInformacionCultivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSalario)
                    .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addGroup(pnlColorFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlInformacionCultivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlColorFondoLayout.setVerticalGroup(
            pnlColorFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlColorFondoLayout.createSequentialGroup()
                .addComponent(pnlTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(pnlInformacionCultivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(pnlColorFondo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardarTrabajador();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnMostrarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarTablaActionPerformed
        IntFrmTablaTrabajadores tabla = new IntFrmTablaTrabajadores();
    
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
    private javax.swing.JComboBox<String> cbbTipoTrabajador;
    private javax.swing.JComboBox<String> cmbPuesto;
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblHoja;
    private javax.swing.JLabel lblIDTrabajador;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPuesto;
    private javax.swing.JLabel lblRegistroTrabajadores;
    private javax.swing.JLabel lblSalario;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTipoTrabajador;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlColorFondo;
    private javax.swing.JPanel pnlInformacionCultivo;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtIDTrabajador;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSalario;
    private javax.swing.JFormattedTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
