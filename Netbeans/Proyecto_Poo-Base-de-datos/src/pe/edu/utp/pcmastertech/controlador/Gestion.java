package pe.edu.utp.pcmastertech.controlador;

import pe.edu.utp.pcmastertech.modelo.Productos;
import java.util.List;

public interface Gestion {
    public boolean agregarProducto(Productos p);
    public boolean modificarProducto(Productos p);
    public boolean eliminarProducto(String codigo);
    public List<Productos> consultarProductos();
}