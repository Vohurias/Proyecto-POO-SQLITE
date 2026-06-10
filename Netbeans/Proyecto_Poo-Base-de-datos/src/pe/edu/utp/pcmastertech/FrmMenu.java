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

public class FrmMenu extends JFrame {

    private JLabel lblBienvenida;
    private JButton btnCargarUsuarios;
    private JTable tblUsuarios;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPane;

    // El constructor ahora exige recibir el nombre y rol del usuario logueado
    public FrmMenu(String nombreUsuario, String rolUsuario) {
        // 1. Configuración básica de la ventana de menú
        setTitle("Sistema de Inventario - Panel Principal");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // Distribución por zonas (Norte, Centro, Sur)

        // 2. Encabezado del Menú (Zona Norte)
        JPanel pnlNorte = new JPanel(new GridLayout(2, 1));
        pnlNorte.setBackground(new Color(45, 52, 54)); // Un color gris oscuro elegante
        
        lblBienvenida = new JLabel("¡Bienvenido, " + nombreUsuario + "!", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        lblBienvenida.setForeground(Color.WHITE);
        
        JLabel lblRol = new JLabel("Rol: " + rolUsuario, SwingConstants.CENTER);
        lblRol.setFont(new Font("Arial", Font.ITALIC, 13));
        lblRol.setForeground(Color.LIGHT_GRAY);
        
        pnlNorte.add(lblBienvenida);
        pnlNorte.add(lblRol);
        add(pnlNorte, BorderLayout.NORTH);

        // 3. Estructura de la Tabla de Datos (Zona Centro)
        // Definimos las columnas idénticas a nuestra estructura en la base de datos
        String[] columnas = {"DNI", "Nombre Completo", "Nombre Usuario", "Cargo"};
        modeloTabla = new DefaultTableModel(columnas, 0); // 0 indica que inicia vacía
        tblUsuarios = new JTable(modeloTabla);
        scrollPane = new JScrollPane(tblUsuarios); // Permite barras de desplazamiento si hay muchos datos
        add(scrollPane, BorderLayout.CENTER);

        // 4. Panel de Acciones/Botones (Zona Sur)
        JPanel pnlSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnCargarUsuarios = new JButton("Consultar Usuarios (SELECT * FROM Usuario)");
        btnCargarUsuarios.setFont(new Font("Arial", Font.BOLD, 12));
        pnlSur.add(btnCargarUsuarios);
        add(pnlSur, BorderLayout.SOUTH);

        // 5. Escuchador de eventos para el botón de consulta
        btnCargarUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarConsultaUsuarios();
            }
        });
    }

    // Método paso a paso que conecta la tabla visual con SQLite
    private void ejecutarConsultaUsuarios() {
        // Limpiamos la tabla por si ya tenía datos de una consulta previa
        modeloTabla.setRowCount(0);

        String sql = "SELECT dni, nombre, usuario, cargo FROM Usuario";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Recorremos las filas que devolvió la base de datos una por una
            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String usuario = rs.getString("usuario");
                String cargoRaw = rs.getString("cargo");
                
                // Homologamos visualmente el indicador de cargo
                String cargoReal = cargoRaw.equals("A") ? "Administrador" : "Técnico";

                // Creamos un arreglo de objetos con los datos de esta fila
                Object[] fila = {dni, nombre, usuario, cargoReal};
                
                // Agregamos la fila directamente al modelo visual de la tabla
                modeloTabla.addRow(fila);
            }

            if (modeloTabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No se encontraron registros.", "Consulta Vacía", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al extraer usuarios de SQLite: " + ex.getMessage(), "Error de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
}