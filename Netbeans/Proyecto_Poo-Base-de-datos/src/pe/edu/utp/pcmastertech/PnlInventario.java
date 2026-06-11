package pe.edu.utp.pcmastertech;

import pe.edu.utp.pcmastertech.controlador.inventario;
import pe.edu.utp.pcmastertech.modelo.Productos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        // 2. TABLA DE PRODUCTOS
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
        modeloTabla.setRowCount(0);

        // Instanciamos la clase controladora respetando el nombre exacto del UML
        inventario controller = new inventario();
        java.util.List<Productos> listaProductos = controller.consultarProductosConFiltro(criterio);

        for (Productos p : listaProductos) {
            Object[] fila = {
                p.getCodigo(),
                p.getMarca(),
                p.getModelo(),
                p.getPrecioUnitario(),
                p.getCantidadStock(),
                p.getStockMinimo(),
                p.getFechaIngreso(),
                p.getNombreCategoria(),
                p.getNombreProveedor()
            };
            modeloTabla.addRow(fila);
        }

    }
}