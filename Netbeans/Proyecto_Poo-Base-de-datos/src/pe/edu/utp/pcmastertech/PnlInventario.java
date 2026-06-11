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

public class PnlInventario extends JPanel {

    private JTable tblProductos;
    private DefaultTableModel modeloTabla;
    private JButton btnRefrescar;

    public PnlInventario() {
        // Configuración visual del panel (Contenido derecho del menú)
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        // 1. Título superior
        JLabel lblTitulo = new JLabel("INVENTARIO GENERAL DE PRODUCTOS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        // 2. Tabla gráfica adaptada a tu esquema real de 9 columnas
        String[] columnas = {
            "Código", "Marca", "Modelo", "Precio Unit. (S/.)", 
            "Stock Actual", "Stock Mínimo", "Fecha Ingreso", "Cat. ID", "Prov. ID"
        };
        modeloTabla = new DefaultTableModel(columnas, 0);
        tblProductos = new JTable(modeloTabla);
        
        // Hacer que la tabla sea scrolleable horizontalmente si las columnas son anchas
        tblProductos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(tblProductos);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Botón inferior para ejecutar la consulta
        btnRefrescar = new JButton("Ejecutar Consulta (SELECT * FROM Producto)");
        btnRefrescar.setFont(new Font("Arial", Font.BOLD, 12));
        add(btnRefrescar, BorderLayout.SOUTH);

        // Evento del botón
        btnRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                traerTodoElInventario();
            }
        });

        // Carga automática inicial
        traerTodoElInventario();
    }

    private void traerTodoElInventario() {
        modeloTabla.setRowCount(0); // Limpiar datos previos

        String sql = "SELECT * FROM Producto";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Recorremos el cursor extrayendo las 9 columnas en orden estricto
            while (rs.next()) {
                int codigo = rs.getInt("codigoProducto");
                String brand = rs.getString("marca");
                String model = rs.getString("modelo");
                double precio = rs.getDouble("precioUnitario");
                int stock = rs.getInt("cantidadStock");
                int stockMin = rs.getInt("stockMinimo");
                String fecha = rs.getString("fechaIngreso");
                int catId = rs.getInt("categoriaID");
                int provId = rs.getInt("proveedorID");

                // Empaquetamos la fila completa con los 9 elementos
                Object[] fila = {codigo, brand, model, precio, stock, stockMin, fecha, catId, provId};
                
                // Agregamos la fila al modelo de la tabla
                modeloTabla.addRow(fila);
            }

            if (modeloTabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "La tabla Producto está vacía.", "Sin Registros", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error de SQLite: " + ex.getMessage(), "Fallo en Consulta", JOptionPane.ERROR_MESSAGE);
        }
    }
}