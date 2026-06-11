package pe.edu.utp.pcmastertech;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FrmProductos extends JFrame {

    private JTable tblProductos;
    private DefaultTableModel modeloTabla;
    private JButton btnCargar, btnVolver;
    private JLabel lblTitulo;
    // Guardamos los datos del usuario actual para devolvérselos al menú si decide regresar
    private String nombreUsuarioActual;
    private String rolUsuarioActual;

    public FrmProductos(String nombreUsuario, String rolUsuario) {
        this.nombreUsuarioActual = nombreUsuario;
        this.rolUsuarioActual = rolUsuario;

        setTitle("Gestión de Inventario - Productos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Encabezado de la pantalla
        lblTitulo = new JLabel("CATÁLOGO DE PRODUCTOS - PCMASTERTECH", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // 2. Tabla de Productos (Ajustada a columnas típicas de hardware)
        String[] columnas = {"ID", "Descripción/Componente", "Marca", "Precio (S/.)", "Stock"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tblProductos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tblProductos);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Botones de acción inferiores
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnCargar = new JButton("Actualizar Tabla");
        btnVolver = new JButton("Volver al Menú");
        
        pnlBotones.add(btnCargar);
        pnlBotones.add(btnVolver);
        add(pnlBotones, BorderLayout.SOUTH);

        // --- EVENTOS ---
        
        // Cargar datos de SQLite
        btnCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarProductosDesdeBD();
            }
        });

        // Regresar al menú destruyendo esta ventana
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmMenu menu = new FrmMenu(nombreUsuarioActual, rolUsuarioActual);
                menu.setVisible(true);
                dispose(); // Libera la memoria de la ventana de productos
            }
        });
    }

    private void cargarProductosDesdeBD() {
        modeloTabla.setRowCount(0); // Limpiar datos previos

        // Consulta SQL estándar de tu catálogo
        String sql = "SELECT id, descripcion, marca, precio, stock FROM Producto";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String descripcion = rs.getString("descripcion");
                String marca = rs.getString("marca");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");

                Object[] fila = {id, descripcion, marca, precio, stock};
                modeloTabla.addRow(fila);
            }

            if(modeloTabla.getRowCount() == 0) {
                 JOptionPane.showMessageDialog(this, "La tabla Producto está vacía en SQLite.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error de SQLite al leer productos: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }
}