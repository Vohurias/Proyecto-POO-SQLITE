CREATE TABLE Producto(
  codigoProducto integer PRIMARY KEY,
  marca text,
  modelo text,
  precioUnitario real,
  cantidadStock int,
  stockMinimo int ,
  fechaIngreso text,
  categoriaID int,
FOREIGN KEY (categoriaID) references Producto

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
  nombre text,
);


CREATE TABLE Usuario(
  dni int PRIMARY KEY,
  contrasenia text ,
  cargo char,
  usuario Text
)
