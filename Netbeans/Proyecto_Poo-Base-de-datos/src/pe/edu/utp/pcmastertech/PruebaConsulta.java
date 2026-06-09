package pe.edu.utp.pcmastertech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PruebaConsulta {
    public static void main(String[] args) {
        // 1. Llamamos a nuestra clase Conexion para obtener el puente a SQLite
        Connection cn = Conexion.getConexion();
        
        // 2. Definimos la misma consulta simple que probaste en tu archivo consulta.sql
        String sql = "SELECT dni, nombre, usuario, cargo FROM Usuario";
        
        // Declaramos los objetos de JDBC fuera del try para poder cerrarlos correctamente
        PreparedStatement ps = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                // Preparar la consulta SQL de manera segura
                ps = cn.prepareStatement(sql);
                
                // Ejecutar el query y almacenar las filas devueltas en el ResultSet (memoria)
                rs = ps.executeQuery();
                
                // Pintar cabecera en la consola de NetBeans
                System.out.println("\n==================================================================");
                System.out.println("          LISTA DE USUARIOS REGISTRADOS EN PCMASTERTECH           ");
                System.out.println("==================================================================");
                System.out.printf("%-10s | %-25s | %-15s | %-5s\n", "DNI", "NOMBRE", "USUARIO", "CARGO");
                System.out.println("------------------------------------------------------------------");
                
                // Recorrer el ResultSet fila por fila mientras existan registros
                int contador = 0;
                while (rs.next()) {
                    int dni = rs.getInt("dni");
                    String nombre = rs.getString("nombre");
                    String usuario = rs.getString("usuario");
                    String cargo = rs.getString("cargo");
                    
                    // Imprimir la fila actual con formato alineado
                    System.out.printf("%-10d | %-25s | %-15s | %-5s\n", dni, nombre, usuario, cargo);
                    contador++;
                }
                System.out.println("------------------------------------------------------------------");
                System.out.println("Total de registros recuperados con éxito: " + contador);
                System.out.println("==================================================================");
                
            } catch (SQLException e) {
                System.out.println("[ERROR] Falló la ejecución de la consulta: " + e.getMessage());
            } finally {
                // BUENA PRÁCTICA: Cerramos todos los flujos abiertos en orden inverso
                try {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                    if (cn != null) cn.close();
                    System.out.println("[INFO] Recursos de la base de datos cerrados correctamente.");
                } catch (SQLException ex) {
                    System.out.println("[ERROR] No se pudieron cerrar los recursos: " + ex.getMessage());
                }
            }
        } else {
            System.out.println("[CRÍTICO] No se pudo ejecutar la consulta porque la conexión es nula.");
        }
    }
}