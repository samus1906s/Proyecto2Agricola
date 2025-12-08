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
import com.toedter.calendar.JDateChooser; 
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 *
 * @author samue
 */
public class IntFrmAlmacen extends javax.swing.JInternalFrame {
    
    private int idSeleccionado = -1;
    private ControladorAlmacen controlador;
    private AlmacenDTO almacenSeleccionado;
   

    /**
     * Creates new form IntFrmAlmacen
     */
    public IntFrmAlmacen() {
        initComponents();
        controlador = new ControladorAlmacen();
        almacenSeleccionado = null;
        cargarTabla();
        cargarCombos();
    }
    

    private LocalDate toLocalDate(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

   
    private Date toDate(LocalDate localDate) {
        if (localDate == null) return null;
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

   
    private void mostrarMensaje(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }


    private void mostrarError(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
    }


    private boolean validarRequeridos(JComponent... campos) {
        for (JComponent c : campos) {
            if (c instanceof JTextField && ((JTextField) c).getText().trim().isEmpty()) return false;
            if (c instanceof JComboBox && ((JComboBox<?>) c).getSelectedItem() == null) return false;
            if (c instanceof JDateChooser && ((JDateChooser) c).getDate() == null) return false;
        }
        return true;
    }


    private void limpiar() {
        txtId.setText("");
        txtCantidad.setText("");
        jDateChooserIngreso.setDate(null);
        jDateChooserEgreso.setDate(null);
        ComboBoxProduccion.setSelectedIndex(0);
        ComboBoxEstado.setSelectedIndex(0);
        txtBuscar.setText("");
        almacenSeleccionado = null;
    }



    private void guardar() {
        
    if (!validarRequeridos(ComboBoxProduccion, txtCantidad, jDateChooserIngreso, ComboBoxEstado)) {
        mostrarError("Faltan datos requeridos", "Error");
        return;
    }

    try {
        Object selectedItem = ComboBoxProduccion.getSelectedItem();
        if (selectedItem == null) {
            mostrarError("Debe seleccionar una producción válida", "Error");
            return;
        }
        int produccionId = Integer.parseInt(selectedItem.toString());

        
        double cantidad = Double.parseDouble(txtCantidad.getText());
        LocalDate fechaIngreso = toLocalDate(jDateChooserIngreso.getDate());
        LocalDate fechaEgreso = jDateChooserEgreso.getDate() != null 
                                ? toLocalDate(jDateChooserEgreso.getDate()) 
                                : null;
        EstadoAlmacen estado = EstadoAlmacen.valueOf(ComboBoxEstado.getSelectedItem().toString());

       
        AlmacenDTO dto = new AlmacenDTO(0, produccionId, cantidad, fechaIngreso, fechaEgreso, estado);

        
        if (controlador.registrarAlmacen(dto)) {
            mostrarMensaje("Registro agregado correctamente", "Éxito");
            limpiar();
            cargarTabla();
        } else {
            mostrarError("No se pudo agregar el registro", "Error");
        }

    } catch (NumberFormatException ex) {
        mostrarError("Cantidad o ID de producción inválido", "Error");
    } catch (Exception ex) {
        mostrarError("Error: " + ex.getMessage(), "Error");
    }
    }


   private void eliminar() {
    if (idSeleccionado == -1) {
        mostrarError("Debe seleccionar un registro para eliminar", "Error");
        return;
    }

    try {
        if (controlador.eliminarAlmacen(idSeleccionado)) {
            mostrarMensaje("Registro eliminado correctamente", "Éxito");
            limpiar();
            cargarTabla();
            idSeleccionado = -1; 
        } else {
            mostrarError("No se pudo eliminar el registro", "Error");
        }

    } catch (Exception ex) {
        mostrarError("Error: " + ex.getMessage(), "Error");
    }
}
    
private void actualizar() {
   if (idSeleccionado == -1) {
        mostrarError("Debe seleccionar un registro para modificar", "Error");
        return;
    }

    try {
        int produccionId = Integer.parseInt(ComboBoxProduccion.getSelectedItem().toString());
        double cantidad = Double.parseDouble(txtCantidad.getText());

        LocalDate fechaIngreso = jDateChooserIngreso.getDate()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate fechaEgreso = null;
        if (jDateChooserEgreso.getDate() != null) {
            fechaEgreso = jDateChooserEgreso.getDate()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        EstadoAlmacen estado = EstadoAlmacen.valueOf(
                ComboBoxEstado.getSelectedItem().toString()
        );


        AlmacenDTO dto = new AlmacenDTO(
                idSeleccionado,
                produccionId,
                cantidad,
                fechaIngreso,
                fechaEgreso,
                estado
        );

  
        if (controlador.actualizarAlmacen(dto)) {
            mostrarMensaje("Registro modificado correctamente", "Éxito");
            limpiar();
            cargarTabla();
            idSeleccionado = -1;
        } else {
            mostrarError("No se pudo modificar el registro", "Error");
        }

    } catch (Exception ex) {
        mostrarError("Error al modificar: " + ex.getMessage(), "Error");
    }
}



    private void mostrarDatos(AlmacenDTO dto) {
        almacenSeleccionado = dto;
        ComboBoxProduccion.setSelectedItem(dto.getProduccionId());
        txtCantidad.setText(String.valueOf(dto.getCantidadDisponible()));
        jDateChooserIngreso.setDate(toDate(dto.getFechaIngreso()));
        jDateChooserEgreso.setDate(dto.getFechaEgreso() != null ? toDate(dto.getFechaEgreso()) : null);
        ComboBoxEstado.setSelectedItem(dto.getEstado());
    }

    private void cargarTabla() {
        try {
            List<AlmacenDTO> lista = controlador.listarAlmacenes();
            DefaultTableModel model = (DefaultTableModel) TbtAlmacen.getModel();
            model.setRowCount(0);
            for (AlmacenDTO a : lista) {
                model.addRow(new Object[]{
                        a.getId(),
                        a.getProduccionId(),
                        a.getCantidadDisponible(),
                        a.getFechaIngreso(),
                        a.getFechaEgreso(),
                        a.getEstado()
                });
            }
        } catch (Exception ex) {
            mostrarError("Error al cargar datos: " + ex.getMessage(), "Error");
        }
    }

    private void buscar() {
           try {
        String texto = txtBuscar.getText().trim();


        if (texto.isEmpty()) {
            cargarTabla(); 
            return;
        }

        List<AlmacenDTO> lista = controlador.buscar(texto);

        DefaultTableModel model = (DefaultTableModel) TbtAlmacen.getModel();
        model.setRowCount(0);

        for (AlmacenDTO a : lista) {
            model.addRow(new Object[]{
                    a.getId(),
                    a.getProduccionId(),
                    a.getCantidadDisponible(),
                    a.getFechaIngreso(),
                    a.getFechaEgreso(),
                    a.getEstado()
            });
        }

    } catch (Exception ex) {
        mostrarError("Error al buscar datos: " + ex.getMessage(), "Error");
    }
    }
    
     private void cargarCombos() {

     ComboBoxEstado.setModel(new DefaultComboBoxModel<>(
        Arrays.stream(EstadoAlmacen.values())
              .map(Enum::name)
              .toArray(String[]::new)
    ));
    if (ComboBoxEstado.getItemCount() > 0) {
        ComboBoxEstado.setSelectedIndex(0);
    }

    try {
        List<ProduccionDTO> producciones = new ControladorProduccion().listarProducciones();
        DefaultComboBoxModel<String> modeloProduccion = new DefaultComboBoxModel<>();

        for (ProduccionDTO p : producciones) {
            modeloProduccion.addElement(String.valueOf(p.getIdProduccion()));
        }

        ComboBoxProduccion.setModel(modeloProduccion);
        if (modeloProduccion.getSize() > 0) {
            ComboBoxProduccion.setSelectedIndex(0);
        }

    } catch (Exception ex) {
        mostrarError("Error al cargar Producciones: " + ex.getMessage(), "Error");
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

        PanelPrincipal = new javax.swing.JPanel();
        ALMACEN = new javax.swing.JLabel();
        REGISTRO = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ComboBoxProduccion = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jDateChooserIngreso = new com.toedter.calendar.JDateChooser();
        jDateChooserEgreso = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        ComboBoxEstado = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        REGISTRO2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        ALMACEN1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbtAlmacen = new javax.swing.JTable();
        btnBuscar = new javax.swing.JButton();
        btnTablaAlertas = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 204, 102));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setOpaque(true);
        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                formAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        PanelPrincipal.setBackground(new java.awt.Color(0, 102, 51));

        ALMACEN.setBackground(new java.awt.Color(153, 255, 204));
        ALMACEN.setFont(new java.awt.Font("Bodoni MT", 1, 36)); // NOI18N
        ALMACEN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ALMACEN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/filtrar.png"))); // NOI18N
        ALMACEN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        ALMACEN.setOpaque(true);

        REGISTRO.setBackground(new java.awt.Color(0, 153, 102));
        REGISTRO.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel1.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel1.setText("Id:");

        ComboBoxProduccion.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        ComboBoxProduccion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel2.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel2.setText("Cantidad:");

        txtCantidad.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel3.setText("Fecha Ingreso:");

        jLabel4.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel4.setText("Fecha Egreso:");

        jDateChooserIngreso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jDateChooserEgreso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel5.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel5.setText("Estado:");

        ComboBoxEstado.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        ComboBoxEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel6.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel6.setText("Producción:");

        txtId.setEditable(false);
        txtId.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        txtId.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        txtId.setEnabled(false);
        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout REGISTROLayout = new javax.swing.GroupLayout(REGISTRO);
        REGISTRO.setLayout(REGISTROLayout);
        REGISTROLayout.setHorizontalGroup(
            REGISTROLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(REGISTROLayout.createSequentialGroup()
                .addGroup(REGISTROLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(REGISTROLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(REGISTROLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(REGISTROLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(REGISTROLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(REGISTROLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, REGISTROLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addGroup(REGISTROLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(REGISTROLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jDateChooserIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
                    .addGroup(REGISTROLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooserEgreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, REGISTROLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidad))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, REGISTROLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboBoxProduccion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, REGISTROLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboBoxEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, REGISTROLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtId)))
                .addContainerGap())
        );
        REGISTROLayout.setVerticalGroup(
            REGISTROLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(REGISTROLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(REGISTROLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(REGISTROLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBoxProduccion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(REGISTROLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(REGISTROLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooserIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(REGISTROLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooserEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(REGISTROLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        REGISTRO2.setBackground(new java.awt.Color(0, 153, 102));
        REGISTRO2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        btnGuardar.setBackground(new java.awt.Color(153, 255, 204));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Guardar.png"))); // NOI18N
        btnGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(153, 255, 204));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Eliminar.png"))); // NOI18N
        btnEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(153, 255, 204));
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Limpiar.png"))); // NOI18N
        btnLimpiar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(153, 255, 204));
        btnModificar.setFont(new java.awt.Font("Bell MT", 1, 24)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Refrescar.png"))); // NOI18N
        btnModificar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout REGISTRO2Layout = new javax.swing.GroupLayout(REGISTRO2);
        REGISTRO2.setLayout(REGISTRO2Layout);
        REGISTRO2Layout.setHorizontalGroup(
            REGISTRO2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(REGISTRO2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(REGISTRO2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(REGISTRO2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );
        REGISTRO2Layout.setVerticalGroup(
            REGISTRO2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, REGISTRO2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(REGISTRO2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(REGISTRO2Layout.createSequentialGroup()
                        .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(REGISTRO2Layout.createSequentialGroup()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        ALMACEN1.setBackground(new java.awt.Color(0, 153, 102));
        ALMACEN1.setFont(new java.awt.Font("Bodoni MT", 1, 36)); // NOI18N
        ALMACEN1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ALMACEN1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Grafica.png"))); // NOI18N
        ALMACEN1.setText("Gestión de Almacén");
        ALMACEN1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        ALMACEN1.setOpaque(true);

        txtBuscar.setBackground(new java.awt.Color(153, 255, 204));
        txtBuscar.setFont(new java.awt.Font("Bell MT", 1, 24)); // NOI18N
        txtBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });

        TbtAlmacen.setBackground(new java.awt.Color(153, 255, 204));
        TbtAlmacen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        TbtAlmacen.setFont(new java.awt.Font("Bell MT", 0, 14)); // NOI18N
        TbtAlmacen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "id", "ProduccionId", "Cantidad", "FechaIngreso", "FechaEgreso", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TbtAlmacen.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                TbtAlmacenAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        TbtAlmacen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbtAlmacenMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TbtAlmacen);

        btnBuscar.setBackground(new java.awt.Color(153, 255, 204));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Buscar.png"))); // NOI18N
        btnBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnTablaAlertas.setBackground(new java.awt.Color(153, 255, 204));
        btnTablaAlertas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Tabla.png"))); // NOI18N
        btnTablaAlertas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        btnTablaAlertas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTablaAlertasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelPrincipalLayout = new javax.swing.GroupLayout(PanelPrincipal);
        PanelPrincipal.setLayout(PanelPrincipalLayout);
        PanelPrincipalLayout.setHorizontalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ALMACEN1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelPrincipalLayout.createSequentialGroup()
                        .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(REGISTRO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(REGISTRO2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelPrincipalLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(ALMACEN, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 9, Short.MAX_VALUE))
                            .addGroup(PanelPrincipalLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1)
                                    .addGroup(PanelPrincipalLayout.createSequentialGroup()
                                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnTablaAlertas, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap())
        );
        PanelPrincipalLayout.setVerticalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ALMACEN1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelPrincipalLayout.createSequentialGroup()
                        .addComponent(REGISTRO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(REGISTRO2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(13, 13, 13))
                    .addGroup(PanelPrincipalLayout.createSequentialGroup()
                        .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtBuscar)
                            .addComponent(ALMACEN, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTablaAlertas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        buscar();
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void formAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_formAncestorAdded

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
       eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void TbtAlmacenAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_TbtAlmacenAncestorAdded
       cargarTabla();
    
    if (almacenSeleccionado != null) {
        mostrarDatos(almacenSeleccionado);
    }
    }//GEN-LAST:event_TbtAlmacenAncestorAdded

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void TbtAlmacenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbtAlmacenMouseClicked
    int fila = TbtAlmacen.getSelectedRow();
    if (fila == -1) return;

    idSeleccionado = Integer.parseInt(TbtAlmacen.getValueAt(fila, 0).toString());
    txtId.setText(String.valueOf(idSeleccionado));

    ComboBoxProduccion.setSelectedItem(
            TbtAlmacen.getValueAt(fila, 1).toString()
    );

    txtCantidad.setText(TbtAlmacen.getValueAt(fila, 2).toString());

    try {
        Object valor = TbtAlmacen.getValueAt(fila, 3);
        if (valor != null) {
            java.sql.Date fecha = java.sql.Date.valueOf(valor.toString());
            jDateChooserIngreso.setDate(fecha);
        } else {
            jDateChooserIngreso.setDate(null);
        }
    } catch (Exception e) {
        jDateChooserIngreso.setDate(null);
    }

    try {
        Object valor = TbtAlmacen.getValueAt(fila, 4);
        if (valor != null) {
            java.sql.Date fecha = java.sql.Date.valueOf(valor.toString());
            jDateChooserEgreso.setDate(fecha);
        } else {
            jDateChooserEgreso.setDate(null);
        }
    } catch (Exception e) {
        jDateChooserEgreso.setDate(null);
    }

    ComboBoxEstado.setSelectedItem(
            TbtAlmacen.getValueAt(fila, 5).toString()
    );
    }//GEN-LAST:event_TbtAlmacenMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
       actualizar();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnTablaAlertasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTablaAlertasActionPerformed
        try {
        // Evitar abrir varias veces
        for (JInternalFrame f : getDesktopPane().getAllFrames()) {
            if (f instanceof IntFrmAlertas) {
                f.toFront();
                f.requestFocus();
                return;
            }
        }

        IntFrmAlertas alertas = new IntFrmAlertas(controlador);
        getDesktopPane().add(alertas);
        alertas.setVisible(true);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al abrir alertas: " + e.getMessage());
    }
    }//GEN-LAST:event_btnTablaAlertasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ALMACEN;
    private javax.swing.JLabel ALMACEN1;
    private javax.swing.JComboBox<String> ComboBoxEstado;
    private javax.swing.JComboBox<String> ComboBoxProduccion;
    private javax.swing.JPanel PanelPrincipal;
    private javax.swing.JPanel REGISTRO;
    private javax.swing.JPanel REGISTRO2;
    private javax.swing.JTable TbtAlmacen;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnTablaAlertas;
    private com.toedter.calendar.JDateChooser jDateChooserEgreso;
    private com.toedter.calendar.JDateChooser jDateChooserIngreso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtId;
    // End of variables declaration//GEN-END:variables
}
