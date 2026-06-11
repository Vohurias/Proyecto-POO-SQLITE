package pe.edu.utp.pcmastertech;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // Ruta relativa apuntando directamente a tu base de datos SQLite
    private static final String URL = "jdbc:sqlite:ProyectoPoo.db";
    private static Connection cn = null;

    public static Connection getConexion() throws SQLException {
        try {
            // Forzamos la carga manual del driver por si el IDE se marea
            Class.forName("org.sqlite.JDBC");
            
            // Si la conexión está cerrada o es nula, creamos una nueva
            if (cn == null || cn.isClosed()) {
                cn = DriverManager.getConnection(URL);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de SQLite en las librerías.");
            e.printStackTrace();
            throw new SQLException(e);
        }
        return cn;
    }
}