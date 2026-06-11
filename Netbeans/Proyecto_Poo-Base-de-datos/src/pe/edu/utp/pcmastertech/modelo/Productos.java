package pe.edu.utp.pcmastertech.modelo;

public class Productos {
    // Atributos privados exactos del UML
    private String codigo;
    private String marca;
    private String modelo;
    private double precioUnitario;
    private int cantidadStock;
    private int stockMinimo;
    private String fechaIngreso;

    // Atributos relacionales necesarios para los JOINS de la base de datos
    private String nombreCategoria;
    private String nombreProveedor;

    public Productos() {}

    public Productos(String codigo, String marca, String modelo, double precioUnitario, 
                     int cantidadStock, int stockMinimo, String fechaIngreso) {
        this.codigo = codigo;
        this.marca = marca;
        this.modelo = modelo;
        this.precioUnitario = precioUnitario;
        this.cantidadStock = cantidadStock;
        this.stockMinimo = stockMinimo;
        this.fechaIngreso = fechaIngreso;
    }

    // Getters y Setters exigidos por el estándar POO
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public int getCantidadStock() { return cantidadStock; }
    public void setCantidadStock(int cantidadStock) { this.cantidadStock = cantidadStock; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public String getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(String fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }

    public String getNombreProveedor() { return nombreProveedor; }
    public void setNombreProveedor(String nombreProveedor) { this.nombreProveedor = nombreProveedor; }
}