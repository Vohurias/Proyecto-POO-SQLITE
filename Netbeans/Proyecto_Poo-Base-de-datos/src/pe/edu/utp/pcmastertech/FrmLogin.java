/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package pe.edu.utp.pcmastertech;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FrmLogin extends JFrame {

    // Componentes de la interfaz
    private JTextField txtUsuario;
    private JPasswordField txtContrasenia;
    private JButton btnIngresar;
    private JLabel lblUsuario, lblContrasenia, lblTitulo;

    public FrmLogin() {
        // Configuración de la ventana principal (JFrame)
        setTitle("Acceso al Sistema - PCMasterTech");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setLayout(new GridLayout(4, 1, 10, 10)); // Organización en rejilla simple

        // 1. Título del Formulario
        lblTitulo = new JLabel("PCMASTERTECH LOGIN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitulo);

        // 2. Panel para el campo Usuario
        JPanel pnlUsuario = new JPanel(new FlowLayout());
        lblUsuario = new JLabel("Usuario:       ");
        txtUsuario = new JTextField(15);
        pnlUsuario.add(lblUsuario);
        pnlUsuario.add(txtUsuario);
        add(pnlUsuario);

        // 3. Panel para el campo Contraseña
        JPanel pnlContrasenia = new JPanel(new FlowLayout());
        lblContrasenia = new JLabel("Contraseña: ");
        txtContrasenia = new JPasswordField(15);
        pnlContrasenia.add(lblContrasenia);
        pnlContrasenia.add(txtContrasenia);
        add(pnlContrasenia);

        // 4. Botón de Acción
        JPanel pnlBoton = new JPanel(new FlowLayout());
        btnIngresar = new JButton("Iniciar Sesión");
        pnlBoton.add(btnIngresar);
        add(pnlBoton);

        // Asignar el evento "Escuchador" al botón
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarAcceso();
            }
        });
    }

    // Método para conectar la interfaz con la base de datos
    private void validarAcceso() {
        String usuario = txtUsuario.getText().trim();
        // Convertimos el arreglo de caracteres del JPasswordField a String limpio
        String contrasenia = new String(txtContrasenia.getPassword()).trim();

        // Validación simple de campos vacíos en la UI
        if (usuario.isEmpty() || contrasenia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Consulta SQL para validar credenciales y traer el rol
        String sql = "SELECT nombre, cargo FROM Usuario WHERE usuario = ? AND contrasenia = ?";

        // Conexión directa usando nuestra clase Conexion
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            // Inyección segura de parámetros para evitar SQL Injection
            ps.setString(1, usuario);
            ps.setString(2, contrasenia);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nombreEmpleado = rs.getString("nombre");
                    String cargo = rs.getString("cargo");
                    
                    // Decodificar el tipo de cargo
                    String rol = cargo.equals("A") ? "Administrador" : "Técnico";

                    JOptionPane.showMessageDialog(this, "¡Bienvenido " + nombreEmpleado + "!\nRol: " + rol, "Acceso Concedido", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Aquí abriríamos el siguiente formulario (Inventario) en el futuro...
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método Main exclusivo para ejecutar este formulario de manera independiente
    public static void main(String[] args) {
        // Ejecución segura de hilos de interfaz gráfica en Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrmLogin().setVisible(true);
            }
        });
    }
}