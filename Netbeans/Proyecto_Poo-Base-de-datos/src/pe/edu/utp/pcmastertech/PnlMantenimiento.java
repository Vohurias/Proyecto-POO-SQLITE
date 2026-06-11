package pe.edu.utp.pcmastertech;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PnlMantenimiento extends JPanel {

    private JButton btnAgregar, btnActualizar, btnEliminar;

    public PnlMantenimiento() {
        // GridBagLayout para centrar el bloque de botones perfectamente en pantallas Linux/Arch
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Contenedor interno: 3 filas, 1 columna, 20 píxeles de separación vertical
        JPanel pnlBotones = new JPanel(new GridLayout(3, 1, 0, 20));
        pnlBotones.setBackground(Color.WHITE);

        // Definición de los 3 botones principales de la gestión
        btnAgregar = new JButton("Agregar Nuevo Producto");
        btnActualizar = new JButton("Actualizar Producto Existente");
        btnEliminar = new JButton("Eliminar Producto del Sistema");

        // Estilo visual estándar para los botones
        Font fuenteBotones = new Font("Arial", Font.BOLD, 14);
        btnAgregar.setFont(fuenteBotones);
        btnActualizar.setFont(fuenteBotones);
        btnEliminar.setFont(fuenteBotones);

        // Dimensiones idénticas para que se vean simétricos
        Dimension tamanoBoton = new Dimension(280, 50);
        btnAgregar.setPreferredSize(tamanoBoton);
        btnActualizar.setPreferredSize(tamanoBoton);
        btnEliminar.setPreferredSize(tamanoBoton);

        // Agregamos los botones al contenedor interno
        pnlBotones.add(btnAgregar);
        pnlBotones.add(btnActualizar);
        pnlBotones.add(btnEliminar);

        // Posicionamos el contenedor en el centro del panel principal
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(pnlBotones, gbc);

        // =================================================================
        // ACCIONES TEMPORALES DE CONTROL (Solo interfaz por ahora)
        // =================================================================
        
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Instanciamos la ventana flotante del formulario
                FrmAgregarProducto ventanaAgregar = new FrmAgregarProducto();

                // La hacemos visible en pantalla por encima del menú principal
                ventanaAgregar.setVisible(true);
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PnlMantenimiento.this, 
                    "Interfaz de ACTUALIZAR detectada. Listo para solicitar el código.", 
                    "Gestión de Productos", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PnlMantenimiento.this, 
                    "Interfaz de ELIMINAR detectada. Listo para remover de la base de datos.", 
                    "Gestión de Productos", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}