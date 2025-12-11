/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vista;

import Controlador.ControladorAlmacen;
import DTOs.AlmacenDTO;
import Modelo.EstadoAlmacen;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author samue
 */
public class IntFrmAlertas extends javax.swing.JInternalFrame {
    
    private final ControladorAlmacen controlador;
 
    public IntFrmAlertas(ControladorAlmacen controlador) {
        initComponents();
        this.controlador = controlador;
        cargarAlertas();
        personalizarTabla();
        personalizarTituloYBorde(); 
    }
    
    private void cargarAlertas() {
        DefaultTableModel model = (DefaultTableModel) tblAlertas.getModel();
        model.setRowCount(0);

      
        model.setColumnIdentifiers(new Object[]{
            "ID", "Prod ID", "Cantidad", "F. Ingreso", "F. Egreso", 
            "Días Total", "Días Extra", "Tiempo Restante", "Estado"
        });

        try {
            List<AlmacenDTO> lista = controlador.listarAlmacenes();
            LocalDate hoy = LocalDate.now();
            
            int diasInicioAlerta = 20; 
            int diasLimite = 30;       

            for (AlmacenDTO a : lista) {
                long diasTotal = 0;

                if (a.getFechaIngreso() != null) {
                    diasTotal = ChronoUnit.DAYS.between(a.getFechaIngreso(), hoy);
                }
                if (diasTotal < 0) diasTotal = 0;

                long diasDeMas = (diasTotal > diasLimite) ? (diasTotal - diasLimite) : 0;

                
                if (a.getEstado() != EstadoAlmacen.DANADO) {
                    boolean cambio = false;
                    if (diasTotal > diasLimite) {
                        if (a.getEstado() != EstadoAlmacen.VENCIDO) {
                            a.setEstado(EstadoAlmacen.VENCIDO);
                            cambio = true;
                        }
                    } else if (diasTotal >= diasInicioAlerta && diasTotal <= diasLimite) {
                        if (a.getEstado() != EstadoAlmacen.PROXIMO_A_VENCER) {
                            a.setEstado(EstadoAlmacen.PROXIMO_A_VENCER);
                            cambio = true;
                        }
                    }
                    if (cambio) controlador.actualizarAlmacen(a);
                }

                                String tiempoRestanteStr;
                    long diasParaVencer = diasLimite - diasTotal;

                   
                    if (a.getEstado() == EstadoAlmacen.DANADO) {
                        tiempoRestanteStr = "No Aplica"; // 
                    } 
                    else if (a.getEstado() == EstadoAlmacen.VENCIDO) {
                        tiempoRestanteStr = "Vencido hace " + Math.abs(diasParaVencer) + " días";
                    }
                    else if (diasParaVencer > 0) {
                        tiempoRestanteStr = "Quedan " + diasParaVencer + " días";
                    } 
                    else if (diasParaVencer == 0) {
                        tiempoRestanteStr = "Vence HOY";
                    } 
                    else {
                      
                        tiempoRestanteStr = "Tiempo agotado";
                    }

                Object[] fila = new Object[]{
                    a.getId(), 
                    a.getProduccionId(), 
                    a.getCantidadDisponible(), 
                    a.getFechaIngreso(), 
                    a.getFechaEgreso(), 
                    diasTotal, 
                    diasDeMas,
                    tiempoRestanteStr, 
                    a.getEstado() 
                };
                model.addRow(fila);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void personalizarTituloYBorde() {
        try {
            BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();

            Color verdeTitulo = new java.awt.Color(232, 245, 233);
            JComponent titleBar = ui.getNorthPane();
            titleBar.setBackground(verdeTitulo);
            titleBar.setOpaque(true);

            this.setBorder(BorderFactory.createLineBorder(new Color(204, 255, 204), 4));

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
        JTableHeader header = tblAlertas.getTableHeader();
        header.setBackground(new Color(45, 95, 63));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setOpaque(true);

        tblAlertas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                // PALETA DE COLORES
                Color cVencido = new Color(255, 225, 225); 
                Color cDanado  = new Color(255, 230, 200); 
                Color cProximo = new Color(255, 250, 200); 
                Color cBien    = new Color(225, 255, 235); 
                
                Color tRojo    = new Color(150, 0, 0);
                Color tNaranja = new Color(180, 80, 0);
                Color tAmarillo= new Color(120, 120, 0);
                Color tVerde   = new Color(0, 102, 51);

                if (!isSelected) {
                    try {
                        Object valDias = table.getValueAt(row, 5);
                        
                        
                        String estadoTexto = (table.getValueAt(row, 8) != null) ? table.getValueAt(row, 8).toString() : "";
                        
                        long dias = (valDias != null) ? Long.parseLong(valDias.toString()) : 0;

                        switch (estadoTexto) {
                            case "VENCIDO":
                                setBackground(cVencido);
                                setForeground(tRojo);
                                setFont(getFont().deriveFont(Font.BOLD));
                                break;
                                
                            case "DANADO": 
                                setBackground(cDanado);
                                setForeground(tNaranja);
                                setFont(getFont().deriveFont(Font.BOLD));
                                if(column == 8) setText("DAÑADO"); 
                                break;
                                
                            case "PROXIMO_A_VENCER":
                                setBackground(cProximo);
                                setForeground(tAmarillo);
                                setFont(getFont().deriveFont(Font.BOLD));
                                break;
                                
                            case "FRESCO":
                            case "EN_BUEN_ESTADO":
                                if (dias > 30) {
                                    setBackground(cVencido); 
                                    setForeground(tRojo);
                                    if(column == 8) setText("¡REVISAR! " + value);
                                } else {
                                    setBackground(cBien);
                                    setForeground(tVerde);
                                    setFont(getFont().deriveFont(Font.PLAIN));
                                }
                                break;
                                
                            default:
                                setBackground(Color.WHITE);
                                setForeground(Color.BLACK);
                        }

                    } catch (Exception ex) {
                        setBackground(Color.WHITE);
                        setForeground(Color.BLACK);
                    }
                } else {
                    setBackground(new Color(200, 230, 201)); 
                    setForeground(Color.BLACK);
                }
                
                setHorizontalAlignment(SwingConstants.CENTER);
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
                
                return this;
            }
        });
        
        tblAlertas.setGridColor(new Color(230, 230, 230));
        tblAlertas.setShowVerticalLines(true);
        tblAlertas.setShowHorizontalLines(true);
        tblAlertas.setRowHeight(30); 
        tblAlertas.setFillsViewportHeight(true);
        scpTabla.getViewport().setBackground(Color.WHITE);
        
        personalizarScrollBars();
    }
    
    private void personalizarScrollBars() {
        Color verdeOscuro = new Color(45, 95, 63);
        Color verdeClaro = new Color(204, 255, 204);
    
        scpTabla.getVerticalScrollBar().setUI(
            new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = verdeOscuro;
                this.trackColor = verdeClaro;
            }
        });
    
        scpTabla.getHorizontalScrollBar().setUI(
            new BasicScrollBarUI() {
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

        pnlFondo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        scpTabla = new javax.swing.JScrollPane();
        tblAlertas = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Alertas");
        setOpaque(true);

        pnlFondo.setBackground(new java.awt.Color(45, 95, 63));

        lblTitulo.setBackground(new java.awt.Color(45, 95, 63));
        lblTitulo.setFont(new java.awt.Font("Bell MT", 1, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Grafica.png"))); // NOI18N
        lblTitulo.setText("Productos Almacenados Por Tiempo Prolongado");
        lblTitulo.setOpaque(true);

        tblAlertas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "ProduccionId", "Cantidad", "FechaIngreso", "FechaEgreso", "Dias en Almacen", "Estado"
            }
        ));
        scpTabla.setViewportView(tblAlertas);

        javax.swing.GroupLayout pnlFondoLayout = new javax.swing.GroupLayout(pnlFondo);
        pnlFondo.setLayout(pnlFondoLayout);
        pnlFondoLayout.setHorizontalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 918, Short.MAX_VALUE)
                    .addComponent(scpTabla))
                .addContainerGap())
        );
        pnlFondoLayout.setVerticalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scpTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFondo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlFondo;
    private javax.swing.JScrollPane scpTabla;
    private javax.swing.JTable tblAlertas;
    // End of variables declaration//GEN-END:variables
}
