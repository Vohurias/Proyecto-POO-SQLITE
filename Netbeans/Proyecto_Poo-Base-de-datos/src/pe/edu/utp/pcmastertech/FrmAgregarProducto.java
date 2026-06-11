package pe.edu.utp.pcmastertech;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pe.edu.utp.pcmastertech.controlador.inventario;
import pe.edu.utp.pcmastertech.modelo.Productos;

public class FrmAgregarProducto extends JFrame {

    private JTextField txtCodigo, txtMarca, txtModelo, txtPrecio, txtStock, txtStockMin, txtFecha;
    // Reemplazamos los JTextFields por JComboBoxes (las flechitas desplegables)
    private JComboBox<String> cmbCategoria, cmbProveedor; 
    private JButton btnGuardar, btnCancelar;

    public FrmAgregarProducto() {
        setTitle("Registrar Nuevo Producto - PC Master Tech");
        setSize(450, 480);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel pnlFormulario = new JPanel(new GridBagLayout());
        pnlFormulario.setBackground(Color.WHITE);
        pnlFormulario.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);

        // Inicializamos los campos de texto estándar
        txtCodigo = new JTextField(15);
        txtMarca = new JTextField(15);
        txtModelo = new JTextField(15);
        txtPrecio = new JTextField(15);
        txtStock = new JTextField(15);
        txtStockMin = new JTextField(15);
        txtFecha = new JTextField(15);
        txtFecha.setText("2026-06-11"); // Fecha por defecto

        // Inicializamos los ComboBoxes y los poblamos consultando al controlador
        inventario controller = new inventario();
        
        cmbCategoria = new JComboBox<>();
        for (String cat : controller.obtenerNombresCategorias()) {
            cmbCategoria.addItem(cat);
        }

        cmbProveedor = new JComboBox<>();
        for (String prov : controller.obtenerNombresProveedores()) {
            cmbProveedor.addItem(prov);
        }

        // Posicionamos los componentes en la rejilla visual
        colocarComponente(pnlFormulario, gbc, 0, "Código Producto:", txtCodigo);
        colocarComponente(pnlFormulario, gbc, 1, "Marca:", txtMarca);
        colocarComponente(pnlFormulario, gbc, 2, "Modelo:", txtModelo);
        colocarComponente(pnlFormulario, gbc, 3, "Precio Unitario (S/.):", txtPrecio);
        colocarComponente(pnlFormulario, gbc, 4, "Stock Inicial:", txtStock);
        colocarComponente(pnlFormulario, gbc, 5, "Stock Mínimo:", txtStockMin);
        colocarComponente(pnlFormulario, gbc, 6, "Fecha de Ingreso:", txtFecha);
        colocarComponente(pnlFormulario, gbc, 7, "Categoría:", cmbCategoria);
        colocarComponente(pnlFormulario, gbc, 8, "Proveedor:", cmbProveedor);

        add(pnlFormulario, BorderLayout.CENTER);

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        pnlBotones.setBackground(new Color(240, 240, 240));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnCancelar);
        add(pnlBotones, BorderLayout.SOUTH);

        // Evento Cancelar
        btnCancelar.addActionListener(e -> dispose());

        // Evento Guardar con validaciones estrictas
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. VALIDACIÓN: Verificar que ningún campo de texto esté vacío
                if (txtCodigo.getText().trim().isEmpty() || 
                    txtMarca.getText().trim().isEmpty() || 
                    txtModelo.getText().trim().isEmpty() || 
                    txtPrecio.getText().trim().isEmpty() || 
                    txtStock.getText().trim().isEmpty() || 
                    txtStockMin.getText().trim().isEmpty() || 
                    txtFecha.getText().trim().isEmpty()) {
                    
                    JOptionPane.showMessageDialog(FrmAgregarProducto.this, 
                        "Error: Todos los campos del formulario deben ser llenados obligatoriamente.", 
                        "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                    return; // Detiene la ejecución, impidiendo el registro
                }

                // 2. VALIDACIÓN: Verificar que haya selecciones válidas en los combos
                if (cmbCategoria.getSelectedItem() == null || cmbProveedor.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(FrmAgregarProducto.this, 
                        "Error: Debes seleccionar una Categoría y un Proveedor válidos.", 
                        "Relaciones Faltantes", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    // Validamos que los formatos numéricos sean correctos antes de enviar
                    Integer.parseInt(txtCodigo.getText().trim());
                    Double.parseDouble(txtPrecio.getText().trim());
                    Integer.parseInt(txtStock.getText().trim());
                    Integer.parseInt(txtStockMin.getText().trim());
                    
                    // 3. EXTRACCIÓN DE DATOS RELACIONALES: Convertimos "1 - Procesadores" a "1"
                    String itemCategoria = (String) cmbCategoria.getSelectedItem();
                    String idCategoria = itemCategoria.split(" - ")[0]; // Rompe la cadena en el guión y toma el ID

                    String itemProveedor = (String) cmbProveedor.getSelectedItem();
                    String idProveedor = itemProveedor.split(" - ")[0]; // Rompe la cadena en el guión y toma el ID

                    // 4. MAPEO EN LA ENTIDAD DEL UML
                    Productos nuevoProducto = new Productos(
                        txtCodigo.getText().trim(),
                        txtMarca.getText().trim(),
                        txtModelo.getText().trim(),
                        Double.parseDouble(txtPrecio.getText().trim()),
                        Integer.parseInt(txtStock.getText().trim()),
                        Integer.parseInt(txtStockMin.getText().trim()),
                        txtFecha.getText().trim()
                    );
                    
                    // Pasamos temporalmente los códigos relacionales limpios en estos atributos auxiliares
                    nuevoProducto.setNombreCategoria(idCategoria);
                    nuevoProducto.setNombreProveedor(idProveedor);

                    // 5. ENVÍO AL CONTROLADOR
                    if (controller.agregarProducto(nuevoProducto)) {
                        JOptionPane.showMessageDialog(FrmAgregarProducto.this, 
                            "¡Producto guardado exitosamente en el inventario!", 
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        dispose(); // Cerramos la ventana únicamente si la inserción fue exitosa
                    }

                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(FrmAgregarProducto.this, 
                        "Error de formato: El Código, Precio, Stock y Stock Mínimo deben ser números válidos.", 
                        "Formato Incorrecto", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void colocarComponente(JPanel panel, GridBagConstraints gbc, int fila, String textoEtiqueta, JComponent componente) {
        gbc.gridy = fila;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        JLabel lbl = new JLabel(textoEtiqueta);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lbl, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(componente, gbc);
    }
}