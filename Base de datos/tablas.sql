CREATE TABLE Producto(
  codigoProducto integer PRIMARY KEY,
  marca text,
  modelo text,
  precioUnitario real,
  cantidadStock int,
  stockMinimo int ,
  fechaIngreso text,
  categoriaID int,
  proveedorID int,
FOREIGN KEY (categoriaID) references Categoria(codigo),
FOREIGN KEY (proveedorID) references Proveedor(idProveedor)
);

Create Table Proveedor(
  idProveedor int PRIMARY KEY,
  empresa text,
  ruc text,
  direccion text, 
  telefono text,
  correo text,
  personaContacto text
);

Create TABLE Categoria(
  codigo int PRIMARY KEY,
  nombre text
);


CREATE TABLE Usuario(
  nombre text,
  correoElectronico text,
  telefono text,
  dni int PRIMARY KEY,
  contrasenia text ,
  cargo char,
  usuario Text
)

