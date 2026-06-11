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
    private JTextField txtBuscar;
    private JButton btnBuscar, btnVerTodo;

    public PnlInventario() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        // 1. BARRA DE BÚSQUEDA SUPERIOR
        JPanel pnlBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlBusqueda.setBackground(Color.WHITE);
        pnlBusqueda.setBorder(BorderFactory.createTitledBorder("Buscador Avanzado de Hardware"));

        JLabel lblBuscar = new JLabel("Buscar Producto:");
        txtBuscar = new JTextField(25);
        btnBuscar = new JButton("Buscar");
        btnVerTodo = new JButton("Mostrar Todo");

        pnlBusqueda.add(lblBuscar);
        pnlBusqueda.add(txtBuscar);
        pnlBusqueda.add(btnBuscar);
        pnlBusqueda.add(btnVerTodo);

        add(pnlBusqueda, BorderLayout.NORTH);

        // 2. TABLA DE PRODUCTOS (Modificamos las cabeceras relacionales)
        String[] columnas = {
            "Código", "Marca", "Modelo", "Precio Unit. (S/.)", 
            "Stock Actual", "Stock Mínimo", "Fecha Ingreso", "Categoría", "Proveedor (Empresa)"
        };
        modeloTabla = new DefaultTableModel(columnas, 0);
        tblProductos = new JTable(modeloTabla);
        tblProductos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(tblProductos);
        add(scrollPane, BorderLayout.CENTER);

        // 3. EVENTOS DE LOS BOTONES
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String palabraABuscar = txtBuscar.getText().trim();
                if (palabraABuscar.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, escribe algo para buscar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    buscarEnInventario(palabraABuscar);
                }
            }
        });

        btnVerTodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtBuscar.setText("");
                buscarEnInventario("");
            }
        });

        // Carga automática inicial
        buscarEnInventario("");
    }

    private void buscarEnInventario(String criterio) {
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de inyectar datos

        String sql;
        
        // Si el criterio está vacío, ejecutamos el doble INNER JOIN base
        if (criterio.isEmpty()) {
            sql = "SELECT P.codigoProducto, P.marca, P.modelo, P.precioUnitario, P.cantidadStock, "
                + "P.stockMinimo, P.fechaIngreso, C.nombre AS nombreCategoria, Pr.empresa AS nombreProveedor "
                + "FROM Producto P "
                + "INNER JOIN Categoria C ON P.categoriaID = C.codigo "
                + "INNER JOIN Proveedor Pr ON P.proveedorID = Pr.idProveedor";
        } else {
            /* Si hay criterio, añadimos un WHERE robusto. 
               Ahora busca por: Código, Marca, Modelo, Nombre de Categoría o Nombre de Empresa del Proveedor.
            */
            sql = "SELECT P.codigoProducto, P.marca, P.modelo, P.precioUnitario, P.cantidadStock, "
                + "P.stockMinimo, P.fechaIngreso, C.nombre AS nombreCategoria, Pr.empresa AS nombreProveedor "
                + "FROM Producto P "
                + "INNER JOIN Categoria C ON P.categoriaID = C.codigo "
                + "INNER JOIN Proveedor Pr ON P.proveedorID = Pr.idProveedor "
                + "WHERE P.codigoProducto LIKE ? OR P.marca LIKE ? OR P.modelo LIKE ? "
                + "OR C.nombre LIKE ? OR Pr.empresa LIKE ?";
        }

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            // Inyectamos los comodines si existe un filtro de texto escrito
            if (!criterio.isEmpty()) {
                String comodin = "%" + criterio + "%";
                ps.setString(1, comodin); // P.codigoProducto
                ps.setString(2, comodin); // P.marca
                ps.setString(3, comodin); // P.modelo
                ps.setString(4, comodin); // C.nombre (Categoría)
                ps.setString(5, comodin); // Pr.empresa (Proveedor)
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int codigo = rs.getInt("codigoProducto");
                    String brand = rs.getString("marca");
                    String model = rs.getString("modelo");
                    double precio = rs.getDouble("precioUnitario");
                    int stock = rs.getInt("cantidadStock");
                    int stockMin = rs.getInt("stockMinimo");
                    String fecha = rs.getString("fechaIngreso");
                    
                    // Extraemos los textos de las consultas en lugar de los IDs numéricos
                    String nombreCategoria = rs.getString("nombreCategoria"); 
                    String nombreProveedor = rs.getString("nombreProveedor"); 

                    // Construimos la fila con los datos limpios y legibles
                    Object[] fila = {codigo, brand, model, precio, stock, stockMin, fecha, nombreCategoria, nombreProveedor};
                    modeloTabla.addRow(fila);
                }
            }

            if (modeloTabla.getRowCount() == 0 && !criterio.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron productos para: '" + criterio + "'", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error en la consulta relacional (Doble JOIN):\n" + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
}