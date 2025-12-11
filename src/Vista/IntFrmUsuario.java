/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vista;

import Controlador.ControladorUsuario;
import Controlador.SesionUsuario;
import DTOs.UsuarioDTO;
import Modelo.EstadoUsuario;
import Modelo.RolUsuario;
import java.awt.Color;
import java.awt.Window;
import java.beans.PropertyChangeListener;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.SwingUtilities;
/**
 *
 * @author je110
 */
public class IntFrmUsuario extends javax.swing.JInternalFrame {

    private ControladorUsuario controlador;
    private UsuarioDTO usuarioActual;
    private boolean modoEdicion = false;
    private boolean esPrimerRegistro = false;

    public IntFrmUsuario() {
        initComponents();
        personalizarTituloYBorde();
        this.controlador = new ControladorUsuario();
        this.esPrimerRegistro = false;
        limpiarCampos();
    }

    private void guardarUsuario() {
        try {
            if (!validarCampos()) {
                return;
            }

            UsuarioDTO dto = crearDTODesdeFormulario();
            boolean exito;
        
            if (modoEdicion) {
                String nuevaContrasena = new String(pswContraseña.getPassword());
            
                if (!nuevaContrasena.isEmpty()) {
                    exito = controlador.actualizarUsuarioConContrasena(dto, nuevaContrasena);
                } else {
                    exito = controlador.actualizarUsuario(dto);
                }           
                if (exito) {
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo actualizar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if (esPrimerRegistro) {
                    guardarYMostrarLogin();
                    return;
                }
                exito = controlador.registrarUsuario(dto);           
                if (exito) {
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo registrar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {

        String nombreCompleto = txtNombreCompleto.getText().trim();
        if (nombreCompleto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre completo es obligatorio", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtNombreCompleto.requestFocus();
            return false;
        }
    
        if (!nombreCompleto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(this, "El nombre completo solo debe contener letras y espacios", "Nombre inválido", JOptionPane.WARNING_MESSAGE);
            txtNombreCompleto.requestFocus();
            return false;
        }

        String usuario = txtUsuario.getText().trim();
        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario es obligatorio", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtUsuario.requestFocus();
            return false;
        }
    
        if (!usuario.matches("[a-zA-Z0-9_]+")) {
            JOptionPane.showMessageDialog(this, "El usuario solo debe contener letras, números y guiones bajos", "Usuario inválido", JOptionPane.WARNING_MESSAGE);
            txtUsuario.requestFocus();
            return false;
        }

        String email = txtEmail.getText().trim();
        if (!email.isEmpty()) {
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(this, "Formato de email inválido", "Email inválido", JOptionPane.WARNING_MESSAGE);
                txtEmail.requestFocus();
                return false;
            }
        }

        String contrasena = new String(pswContraseña.getPassword());
        String confirmar = new String(pswConfirmar.getPassword());

        if (!modoEdicion) {
            if (contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La contraseña es obligatoria", "Campo requerido", JOptionPane.WARNING_MESSAGE);
                pswContraseña.requestFocus();
                return false;
            }
    
            if (contrasena.length() < 6) {
                JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 6 caracteres", "Contraseña inválida", JOptionPane.WARNING_MESSAGE);
                pswContraseña.requestFocus();
                return false;
            }
        }

        if (!contrasena.isEmpty() || !confirmar.isEmpty()) {
            if (!contrasena.equals(confirmar)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error de validación", JOptionPane.WARNING_MESSAGE);
                pswConfirmar.requestFocus();
                return false;
            }
        }

        return true;
    }

    private UsuarioDTO crearDTODesdeFormulario() {
        UsuarioDTO dto = new UsuarioDTO();

        if (modoEdicion && usuarioActual != null) {
            dto.setId(usuarioActual.getId());
        }

        dto.setNombreCompleto(txtNombreCompleto.getText().trim());
        dto.setUsuario(txtUsuario.getText().trim());
        dto.setEmail(txtEmail.getText().trim());

        String rolTexto = cmbRol.getSelectedItem().toString().toUpperCase();
        dto.setRol(RolUsuario.valueOf(rolTexto));
    
        String contrasena = new String(pswContraseña.getPassword());
        if (!contrasena.isEmpty()) {
            dto.setContrasena(contrasena);
        }
 
        String estadoTexto = cmbEstado.getSelectedItem().toString().toUpperCase();
        dto.setEstado(EstadoUsuario.valueOf(estadoTexto));

        return dto;
    }

    public void cargarUsuarioParaEdicion(UsuarioDTO usuario) {
        this.usuarioActual = usuario;
        this.modoEdicion = true;

        txtIDUsuario.setText(String.valueOf(usuario.getId()));
        txtNombreCompleto.setText(usuario.getNombreCompleto());
        txtUsuario.setText(usuario.getUsuario());
        txtEmail.setText(usuario.getEmail());
 
        cmbRol.setSelectedItem(usuario.getRol());
        cmbEstado.setSelectedItem(usuario.getEstado());
 
        pswContraseña.setText("");
        pswConfirmar.setText("");

        btnRegistrar.setText("Actualizar");
        lblTitulo.setText("Editar Usuario");
    }

    private void limpiarCampos() {
        txtIDUsuario.setText("");
        txtNombreCompleto.setText("");
        txtUsuario.setText("");
        txtEmail.setText("");
        pswContraseña.setText("");
        pswConfirmar.setText("");
 
        cmbRol.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);

        if (!esPrimerRegistro) {
            cmbRol.setEnabled(true);
            cmbEstado.setEnabled(true);
        }

        modoEdicion = false;
        usuarioActual = null;
        lblTitulo.setText("Registros de Usuarios");

        txtNombreCompleto.requestFocus();
    }

    public void configurarPrimerRegistro() {
        this.esPrimerRegistro = true;
    
        lblTitulo.setText("Primer Registro");
    
        cmbRol.setSelectedItem("ADMINISTRADOR");
        cmbRol.setEnabled(false);
    
        cmbEstado.setSelectedItem("ACTIVO");
        cmbEstado.setEnabled(false);
    
        txtNombreCompleto.requestFocus();
    }

    private void guardarYMostrarLogin() {
        try {
            if (!validarCampos()) {
                return;
            }

            UsuarioDTO dto = crearDTODesdeFormulario();
            dto.setRol(RolUsuario.ADMINISTRADOR);
            dto.setEstado(EstadoUsuario.ACTIVO);
        
            boolean exito = controlador.registrarUsuario(dto);
        
            if (exito) {
                JOptionPane.showMessageDialog(this,"¡Usuario administrador registrado correctamente!\n\n" +"Ahora puede iniciar sesión con sus credenciales.","Registro exitoso",JOptionPane.INFORMATION_MESSAGE);
            
                this.dispose();
            
                Window[] windows = Window.getWindows();
                FrmMenuPrincipal menu = null;
            
                    for (Window window : windows) {
                        if (window instanceof FrmMenuPrincipal) {
                            menu = (FrmMenuPrincipal) window;
                            break;
                        }
                    }           
                    if (menu != null) {
                        final FrmMenuPrincipal menuFinal = menu;
                        SwingUtilities.invokeLater(() -> {
                        JdlLogin login = new JdlLogin(menuFinal, true);
                        login.setLocationRelativeTo(menuFinal);
                        login.setVisible(true);
                    
                        if (!SesionUsuario.getInstance().haySesionActiva()) {
                            System.exit(0);
                        } else {
                            menuFinal.inicializarDespuesDeLogin();
                        }
                    });
                }
            }     
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error al registrar el primer usuario:\n" + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void configurarModoNormal() {
        this.esPrimerRegistro = false;
        cmbRol.setEnabled(true);
        cmbEstado.setEnabled(true);
        lblTitulo.setText("Registros de Usuarios");
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

        pnlPrincipal = new javax.swing.JPanel();
        pnlRegistro = new javax.swing.JPanel();
        lblIDUsuario = new javax.swing.JLabel();
        lblNombreCompleto = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblRol = new javax.swing.JLabel();
        lblContraseña = new javax.swing.JLabel();
        lblConfirmar = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        txtIDUsuario = new javax.swing.JTextField();
        txtNombreCompleto = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();
        txtEmail = new javax.swing.JFormattedTextField();
        cmbRol = new javax.swing.JComboBox<>();
        pswContraseña = new javax.swing.JPasswordField();
        pswConfirmar = new javax.swing.JPasswordField();
        cmbEstado = new javax.swing.JComboBox<>();
        pnlImagen = new javax.swing.JPanel();
        lblImagen = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        pnlBotones = new javax.swing.JPanel();
        btnLimpiar = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        btnMostrarTabla = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Administrar Usuarios");

        pnlPrincipal.setBackground(new java.awt.Color(45, 95, 63));

        pnlRegistro.setBackground(new java.awt.Color(204, 255, 204));

        lblIDUsuario.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblIDUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIDUsuario.setText("ID Usuario:");

        lblNombreCompleto.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblNombreCompleto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNombreCompleto.setText("Nombre Completo:");

        lblUsuario.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuario.setText("Usuario:");

        lblEmail.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblEmail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmail.setText("Email:");

        lblRol.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblRol.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRol.setText("Rol:");

        lblContraseña.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblContraseña.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContraseña.setText("Contraseña:");

        lblConfirmar.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblConfirmar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConfirmar.setText("Confirmar:");

        lblEstado.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstado.setText("Estado:");

        txtIDUsuario.setEditable(false);
        txtIDUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIDUsuario.setEnabled(false);

        txtNombreCompleto.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtEmail.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        cmbRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADMINISTRADOR", "TRABAJADOR" }));

        pswContraseña.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        pswConfirmar.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "INACTIVO" }));

        pnlImagen.setBackground(new java.awt.Color(204, 255, 204));

        lblImagen.setBackground(new java.awt.Color(204, 255, 204));
        lblImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Hoja.png"))); // NOI18N

        javax.swing.GroupLayout pnlImagenLayout = new javax.swing.GroupLayout(pnlImagen);
        pnlImagen.setLayout(pnlImagenLayout);
        pnlImagenLayout.setHorizontalGroup(
            pnlImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImagen, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
        );
        pnlImagenLayout.setVerticalGroup(
            pnlImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImagen, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlRegistroLayout = new javax.swing.GroupLayout(pnlRegistro);
        pnlRegistro.setLayout(pnlRegistroLayout);
        pnlRegistroLayout.setHorizontalGroup(
            pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegistroLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegistroLayout.createSequentialGroup()
                        .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlRegistroLayout.createSequentialGroup()
                                .addComponent(lblEstado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlRegistroLayout.createSequentialGroup()
                                .addComponent(lblNombreCompleto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlRegistroLayout.createSequentialGroup()
                                .addComponent(lblIDUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIDUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(162, Short.MAX_VALUE))
                    .addGroup(pnlRegistroLayout.createSequentialGroup()
                        .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlRegistroLayout.createSequentialGroup()
                                .addComponent(lblRol)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(pnlRegistroLayout.createSequentialGroup()
                                    .addComponent(lblConfirmar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(pswConfirmar))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlRegistroLayout.createSequentialGroup()
                                    .addComponent(lblContraseña)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(pswContraseña, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
                                .addGroup(pnlRegistroLayout.createSequentialGroup()
                                    .addComponent(lblUsuario)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtUsuario)))
                            .addGroup(pnlRegistroLayout.createSequentialGroup()
                                .addComponent(lblEmail)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))))
        );
        pnlRegistroLayout.setVerticalGroup(
            pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegistroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtIDUsuario)
                    .addComponent(lblIDUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreCompleto)
                    .addComponent(txtNombreCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegistroLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblUsuario)
                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmail)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblRol)
                            .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblContraseña)
                            .addComponent(pswContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegistroLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(pnlImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConfirmar)
                    .addComponent(pswConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstado)
                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblTitulo.setFont(new java.awt.Font("Bell MT", 1, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/usuario.png"))); // NOI18N
        lblTitulo.setText("Registros de Usuarios");

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

        btnRegistrar.setBackground(new java.awt.Color(204, 255, 204));
        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Guardar.png"))); // NOI18N
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
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(lblTitulo))
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlRegistro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 8, Short.MAX_VALUE))
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
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

    private void btnMostrarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarTablaActionPerformed
        IntFrmTablaUsuarios tabla = new IntFrmTablaUsuarios();

        JDesktopPane desktop = this.getDesktopPane();
        if (desktop != null) {
            desktop.add(tabla);
            tabla.setVisible(true);
            tabla.toFront();
        }
    }//GEN-LAST:event_btnMostrarTablaActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        guardarUsuario();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnMostrarTabla;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<String> cmbRol;
    private javax.swing.JLabel lblConfirmar;
    private javax.swing.JLabel lblContraseña;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblIDUsuario;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblNombreCompleto;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlImagen;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JPanel pnlRegistro;
    private javax.swing.JPasswordField pswConfirmar;
    private javax.swing.JPasswordField pswContraseña;
    private javax.swing.JFormattedTextField txtEmail;
    private javax.swing.JTextField txtIDUsuario;
    private javax.swing.JTextField txtNombreCompleto;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
