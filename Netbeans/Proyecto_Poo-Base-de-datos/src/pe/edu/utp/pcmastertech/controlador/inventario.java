package pe.edu.utp.pcmastertech.controlador;

import pe.edu.utp.pcmastertech.controlador.Gestion;
import pe.edu.utp.pcmastertech.modelo.Productos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import pe.edu.utp.pcmastertech.Conexion;

public class inventario implements Gestion {

    // El atributo "-conexion" simulado mediante nuestra clase de acceso
    public inventario() {}

@Override
public boolean agregarProducto(Productos p) {
    String sql = "INSERT INTO Producto (codigoProducto, marca, modelo, precioUnitario, cantidadStock, stockMinimo, fechaIngreso, categoriaID, proveedorID) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection cn = Conexion.getConexion();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        ps.setInt(1, Integer.parseInt(p.getCodigo()));
        ps.setString(2, p.getMarca());
        ps.setString(3, p.getModelo());
        ps.setDouble(4, p.getPrecioUnitario());
        ps.setInt(5, p.getCantidadStock());
        ps.setInt(6, p.getStockMinimo());
        ps.setString(7, p.getFechaIngreso());
        ps.setInt(8, Integer.parseInt(p.getNombreCategoria())); // Aquí viajará el ID extraído del combo
        ps.setInt(9, Integer.parseInt(p.getNombreProveedor()));  // Aquí viajará el ID extraído del combo

        return ps.executeUpdate() > 0;

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al insertar en la base de datos:\n" + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}

    @Override
    public boolean modificarProducto(Productos p) {
        // Lógica de actualización que completaremos en el siguiente paso
        return false;
    }

    @Override
    public boolean eliminarProducto(String codigo) {
        // Lógica de eliminación que completaremos en el siguiente paso
        return false;
    }

    @Override
    public List<Productos> consultarProductos() {
        return consultarProductosConFiltro("");
    }

    // Método de soporte para reutilizar la lógica con el cuadro de búsqueda de la interfaz
    public List<Productos> consultarProductosConFiltro(String criterio) {
        List<Productos> lista = new ArrayList<>();
        String sql;

        if (criterio.isEmpty()) {
            sql = "SELECT P.codigoProducto, P.marca, P.modelo, P.precioUnitario, P.cantidadStock, "
                + "P.stockMinimo, P.fechaIngreso, C.nombre AS nombreCategoria, Pr.empresa AS nombreProveedor "
                + "FROM Producto P "
                + "INNER JOIN Categoria C ON P.categoriaID = C.codigo "
                + "INNER JOIN Proveedor Pr ON P.proveedorID = Pr.idProveedor";
        } else {
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

            if (!criterio.isEmpty()) {
                String comodin = "%" + criterio + "%";
                ps.setString(1, comodin);
                ps.setString(2, comodin);
                ps.setString(3, comodin);
                ps.setString(4, comodin);
                ps.setString(5, comodin);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Productos p = new Productos();
                    // Conversión segura de int (Base de datos) a String (Requisito estricto UML)
                    p.setCodigo(String.valueOf(rs.getInt("codigoProducto")));
                    p.setMarca(rs.getString("marca"));
                    p.setModelo(rs.getString("modelo"));
                    p.setPrecioUnitario(rs.getDouble("precioUnitario"));
                    p.setCantidadStock(rs.getInt("cantidadStock"));
                    p.setStockMinimo(rs.getInt("stockMinimo"));
                    p.setFechaIngreso(rs.getString("fechaIngreso"));
                    p.setNombreCategoria(rs.getString("nombreCategoria"));
                    p.setNombreProveedor(rs.getString("nombreProveedor"));
                    lista.add(p);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
    // Método para obtener todas las categorías de la BD en formato de texto para el ComboBox
public java.util.List<String> obtenerNombresCategorias() {
    java.util.List<String> lista = new ArrayList<>();
    String sql = "SELECT codigo || ' - ' || nombre AS item FROM Categoria";
    try (Connection cn = Conexion.getConexion();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            lista.add(rs.getString("item"));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return lista;
}

    // Método para obtener todos los proveedores de la BD en formato de texto para el ComboBox
    public java.util.List<String> obtenerNombresProveedores() {
        java.util.List<String> lista = new ArrayList<>();
        String sql = "SELECT idProveedor || ' - ' || empresa AS item FROM Proveedor";
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(rs.getString("item"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}