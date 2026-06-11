package pe.edu.utp.pcmastertech;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmMenu extends JFrame {

    // Contenedor de la derecha que cambiará de contenido
    private JPanel pnlContenedorDerecho;
    private JButton btnInventario, btnProductos, btnCerrarSesion;
    private JLabel lblStatus;

    public FrmMenu(String nombreUsuario, String rolUsuario) {
        setTitle("PCMasterTech - Panel de Control");
        setSize(900, 550); // Ventana más ancha para soportar el flujo lateral
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Usamos BorderLayout para dividir la ventana por regiones cardinales
        setLayout(new BorderLayout());

        // =================================================================
        // 1. PANEL LATERAL IZQUIERDO (Sidebar - Región OESTE)
        // =================================================================
        JPanel pnlSidebar = new JPanel();
        pnlSidebar.setBackground(new Color(45, 52, 54)); // Gris oscuro
        pnlSidebar.setPreferredSize(new Dimension(230, 550)); // Ancho fijo
        pnlSidebar.setLayout(new BoxLayout(pnlSidebar, BoxLayout.Y_AXIS)); // Organización vertical
        pnlSidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        // Información de Sesión en el Sidebar
        JLabel lblUser = new JLabel("<html><b>Usuario:</b><br>" + nombreUsuario + "</html>");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JLabel lblRol = new JLabel("Permisos: " + rolUsuario);
        lblRol.setForeground(Color.LIGHT_GRAY);
        lblRol.setFont(new Font("Arial", Font.ITALIC, 11));

        // Botones de Navegación Lateral
        btnInventario = new JButton("Módulo Inventario");
        btnProductos = new JButton("Mantenimiento Prod."); // Preparado para agregar/editar después
        btnCerrarSesion = new JButton("Cerrar Sesión");

        // Ajustamos los tamaños de los botones para que se vean uniformes en el Sidebar
        btnInventario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnProductos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnCerrarSesion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        // Agregamos los componentes al panel izquierdo dejando espacios verticales
        pnlSidebar.add(lblUser);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlSidebar.add(lblRol);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 40))); // Espacio separador largo
        pnlSidebar.add(btnInventario);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        pnlSidebar.add(btnProductos);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 150))); // Espacio empujador
        pnlSidebar.add(btnCerrarSesion);

        // Insertamos el Sidebar completo a la izquierda de la ventana
        add(pnlSidebar, BorderLayout.WEST);

        // =================================================================
        // 2. PANEL CONTENEDOR DERECHO (Región CENTRO)
        // =================================================================
        pnlContenedorDerecho = new JPanel(new BorderLayout());
        pnlContenedorDerecho.setBackground(Color.WHITE);
        
        // Vista inicial por defecto (Mensaje de bienvenida central)
        lblStatus = new JLabel("Seleccione un módulo del menú izquierdo para comenzar.", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 14));
        lblStatus.setForeground(Color.GRAY);
        pnlContenedorDerecho.add(lblStatus, BorderLayout.CENTER);

        // Insertamos el contenedor en el espacio restante de la derecha
        add(pnlContenedorDerecho, BorderLayout.CENTER);

        // =================================================================
        // 3. LÓGICA DE INTERCAMBIO DINÁMICO (Eventos de Botones)
        // =================================================================

        // Acción al presionar Módulo Inventario (Carga la clase que creamos antes)
        btnInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Instanciamos el panel con la tabla y los filtros superiores
                PnlInventario panelInv = new PnlInventario();
                cambiarContenidoPanelDerecho(panelInv);
            }
        });

        // Acción al presionar Mantenimiento de Productos (Vacío por ahora)
        btnProductos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creamos un panel temporal en blanco para que no rompa el programa
                JPanel pnlTemporal = new JPanel(new BorderLayout());
                pnlTemporal.setBackground(Color.WHITE);
                JLabel lblTemp = new JLabel("Módulo de inserción y edición de productos (Próxima Entrega).", SwingConstants.CENTER);
                pnlTemporal.add(lblTemp, BorderLayout.CENTER);
                
                cambiarContenidoPanelDerecho(pnlTemporal);
            }
        });

        // Acción de Salida
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmLogin login = new FrmLogin();
                login.setVisible(true);
                dispose();
            }
        });
    }

    // El método maestro paso a paso para limpiar y reinsertar paneles dinámicamente
    private void cambiarContenidoPanelDerecho(JPanel nuevoPanel) {
        pnlContenedorDerecho.removeAll(); // Borra absolutamente todo lo que esté cargado a la derecha
        pnlContenedorDerecho.add(nuevoPanel, BorderLayout.CENTER); // Inserta la nueva pieza del rompecabezas
        pnlContenedorDerecho.revalidate(); // Le dice a Java que recalcule los layouts gráficos
        pnlContenedorDerecho.repaint(); // Redibuja los píxeles en la pantalla inmediatamente
    }
}