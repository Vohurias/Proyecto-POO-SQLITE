package pe.edu.utp.pcmastertech;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    // Al usar "jdbc:sqlite:ProyectoPoo.db", Java busca el archivo en la raíz del proyecto
    private static final String URL = "jdbc:sqlite:ProyectoPoo.db";
    private static Connection conectar = null;

    public static Connection getConexion() {
        try {
            // Validamos si la conexión no existe o si fue cerrada previamente
            if (conectar == null || conectar.isClosed()) {
                // Cargamos explícitamente el driver de SQLite que está en tu carpeta lib
                Class.forName("org.sqlite.JDBC");
                conectar = DriverManager.getConnection(URL);
                System.out.println("[OK] Conexión establecida con la base de datos SQLite.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("[ERROR] No se encontró el Driver de SQLite en las librerías: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[ERROR] No se pudo conectar a la base de datos: " + e.getMessage());
        }
        return conectar;
    }
}