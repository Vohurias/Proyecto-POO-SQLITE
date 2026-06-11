package pe.edu.utp.pcmastertech.controlador;

import pe.edu.utp.pcmastertech.controlador.Gestion;
import pe.edu.utp.pcmastertech.modelo.Productos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.utp.pcmastertech.Conexion;

public class inventario implements Gestion {

    // El atributo "-conexion" simulado mediante nuestra clase de acceso
    public inventario() {}

    @Override
    public boolean agregarProducto(Productos p) {
        // Lógica de inserción que completaremos en el siguiente paso
        return false;
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
}