INSERT INTO Categoria (codigo, nombre) VALUES 
(1, 'Procesadores (CPU)'),
(2, 'Tarjetas gráficas (GPU)'),
(3, 'Memorias RAM'),
(4, 'Discos Duros / SSD'),
(5, 'Placas Madre'),
(6, 'Fuentes de Poder'),
(7, 'Gabinetes'),
(8, 'Periféricos (mouse, teclado, cámara)'),
(9, 'Sistemas de refrigeración'),
(10, 'Monitores'),
(11, 'Laptops'),
(12, 'Unidades Ópticas'),
(13, 'Tarjetas de Red / Wi-Fi'),
(14, 'Cables y Adaptadores'),
(15, 'Pastas Térmicas'),
(16, 'Mandos / Joysticks'),
(17, 'Sillas Gamer'),
(18, 'Audífonos / Headsets'),
(19, 'Estabilizadores / UPS'),
(20, 'Herramientas de ensamblaje');


INSERT INTO Proveedor (idProveedor, empresa, ruc, direccion, telefono, correo, personaContacto) VALUES
(1, 'Deltron Perú S.A.', '20100456781', 'Av. Argentina 1440, Lima', '014116000', 'ventas@deltron.com.pe', 'Carlos Mendoza'),
(2, 'Intcomex Perú', '20501234567', 'Av. Materiales 3045, Lima', '017152000', 'soporte@intcomex.com', 'Ana Chang'),
(3, 'TechData Wholesale', '20456789123', 'Calle Los Mirtos 230, San Isidro', '012214567', 'contacto@techdata.pe', 'Luis Alberto Arce'),
(4, 'Digicorp Bolivia-Perú', '20334455667', 'Av. Ejército 702, Arequipa', '054253647', 'ventas.aqp@digicorp.pe', 'Marcos Quispe'),
(5, 'In जवाब Distribuciones', '20601122334', 'Parque Industrial Siglo XX, Arequipa', '054405060', 'importaciones@injawab.pe', 'Jorge Villanueva'),
(6, 'Halion International', '20556677889', 'Av. Wilson 1250, Lima', '014243536', 'halion.ventas@halion.com.pe', 'Kevin Guerrero'),
(7, 'Memory Kings Wholesale', '20448833112', 'Av. Garcilaso de la Vega 1358, Lima', '016194500', 'mayoristas@mk.com.pe', 'Sofía Benavente'),
(8, 'Grupo Wilson Corp', '20112233445', 'Jr. Camaná 1120, Lima', '013324578', 'informes@wilsoncorp.pe', 'Ricardo Gareca'),
(9, 'PC Link S.A.C.', '20518943210', 'Av. República de Panamá 3545, San Isidro', '012158000', 'compras@pclink.com.pe', 'Elena Flores'),
(10, 'Asus Latam Import', '20998877665', 'Av. Rivera Navarrete 450, San Isidro', '014421516', 'latam.sales@asus.com', 'Alexandre Ziebert'),
(11, 'Corsair Perú Trading', '20776655443', 'Av. Camino Real 124, San Isidro', '015102030', 'peru_support@corsair.com', 'Mariano Closs'),
(12, 'Kingston Technology Corp', '20123456789', 'Vía Principal 102, San Borja', '013456789', 'sales@kingston.com.pe', 'Diana Prince'),
(13, 'MSI Official Distributor', '20887766554', 'Calle Dean Valdivia 145, San Isidro', '016047000', 'msi_peru@msi.com', 'Bruce Wayne'),
(14, 'Gigabyte Tech Perú', '20493827165', 'Av. Javier Prado Este 2450, San Borja', '013728100', 'ventas@gigabyte.com.pe', 'Peter Parker'),
(15, 'Western Digital Latam', '20665544332', 'Calle Amador Merino Reyna 295, San Isidro', '014219500', 'wd.soporte@wdc.com', 'Clark Kent'),
(16, 'Seagate Peru S.A.C.', '20554433221', 'Av. Canaval y Moreyra 522, San Isidro', '017109000', 'contacto@seagate.pe', 'Tony Stark'),
(17, 'EVGA Wholesale Import', '20332211004', 'Av. Aviación 3450, San Borja', '012253696', 'import@evga.pe', 'Barry Allen'),
(18, 'AMD Componentes Perú', '20121212343', 'Av. Benavides 1540, Miraflores', '014457898', 'amd.peru@amd.com', 'Arthur Curry'),
(19, 'Intel Technology Peru', '20847362514', 'Av. Larco 740, Miraflores', '012418500', 'intel.sales@intel.com', 'Victor Stone'),
(20, 'Logitech Mayoristas S.A.', '20506070809', 'Av. Primavera 1040, Santiago de Surco', '013749000', 'distribucion@logitech.pe', 'Oliver Queen');

INSERT INTO Producto (codigoProducto, marca, modelo, precioUnitario, cantidadStock, stockMinimo, fechaIngreso, categoriaID, proveedorID) VALUES
(1001, 'Intel', 'Core i7-13700K', 1650.00, 25, 5, '2026-05-10', 1, 19),
(1002, 'AMD', 'Ryzen 5 7600X', 1150.00, 40, 8, '2026-05-12', 1, 18),
(1003, 'NVIDIA ASUS', 'RTX 4070 Ti SUPER 16GB', 3850.00, 12, 3, '2026-05-15', 2, 10),
(1004, 'MSI', 'GeForce RTX 4060 Ventus 2X', 1550.00, 30, 6, '2026-05-16', 2, 13),
(1005, 'Corsair', 'Vengeance DDR5 32GB (2x16GB) 6000MHz', 620.00, 50, 10, '2026-05-18', 3, 11),
(1006, 'Kingston', 'Fury Beast DDR4 16GB 3200MHz', 210.00, 15, 12, '2026-05-19', 3, 12), -- Alerta visual (Stock bajo: 15 <= 12)
(1007, 'Samsung', '990 PRO NVMe M.2 SSD 2TB', 780.00, 35, 7, '2026-05-20', 4, 1),
(1008, 'Western Digital', 'WD Blue 2TB HDD SATA3', 260.00, 45, 10, '2026-05-21', 4, 15),
(1009, 'ASUS', 'ROG STRIX Z790-E GAMING WIFI', 2100.00, 8, 4, '2026-05-22', 5, 10),
(1010, 'Gigabyte', 'B650 AORUS ELITE AX', 1050.00, 2, 5, '2026-05-23', 5, 14), -- Alerta visual (Stock bajo: 2 < 5)
(1011, 'EVGA', 'SuperNOVA 850 GT 80+ Gold', 650.00, 18, 5, '2026-05-25', 6, 17),
(1012, 'Corsair', 'RM750e 750W 80+ Gold Modular', 540.00, 22, 5, '2026-05-26', 6, 11),
(1013, 'Lian Li', '011 Dynamic EVO Black', 820.00, 14, 3, '2026-05-27', 7, 3),
(1014, 'Antryx', 'FX 900 Chromatic', 280.00, 40, 8, '2026-05-28', 7, 8),
(1015, 'Logitech', 'G Pro X Superlight 2 Wireless', 620.00, 28, 6, '2026-05-29', 8, 20),
(1016, 'Razer', 'Huntsman V3 Pro TKL Keyboard', 950.00, 15, 4, '2026-06-01', 8, 7),
(1017, 'Logitech', 'C920s Pro HD Webcam', 340.00, 3, 5, '2026-06-02', 8, 20), -- Alerta visual (Stock bajo: 3 < 5)
(1018, 'Crucial', 'BX500 1TB SSD SATA', 290.00, 60, 15, '2026-06-03', 4, 2),
(1019, 'Intel', 'Core i5-12400F', 680.00, 55, 12, '2026-06-04', 1, 19),
(1020, 'NVIDIA Gigabyte', 'RTX 4080 SUPER Gaming OC', 5600.00, 7, 2, '2026-06-05', 2, 14);


INSERT INTO Usuario (nombre, correoElectronico, telefono, dni, contrasenia, cargo, usuario) VALUES
('Juan Pérez Gómez', 'jperez@pcmastertech.com', '987654321', 11223344, 'admin123', 'A', 'jperez_admin'),
('Maria Mendoza Ruiz', 'mmendoza@pcmastertech.com', '951753456', 22334455, 'tec2026', 'T', 'mmendoza_tec'),
('Eliot Povea Velasco', 'epovea@pcmastertech.com', '963852741', 33445566, 'eliot789', 'A', 'eliot_dev'),
('Carlos Fuentes Ortiz', 'cfuentes@pcmastertech.com', '912345678', 44556677, 'cfuentes99', 'T', 'cfuentes_tec'),
('Laura Torres Vega', 'ltorres@pcmastertech.com', '981276345', 55667788, 'laura123', 'T', 'ltorres_tec'),
('Pedro Castillo Diaz', 'pcastillo@pcmastertech.com', '999888777', 66778899, 'pedrito00', 'T', 'pcastillo_tec'),
('Ana Paula Solis', 'asolis@pcmastertech.com', '944556677', 77889900, 'anaP4ula', 'A', 'asolis_admin'),
('Luis Miranda Flores', 'lmiranda@pcmastertech.com', '933221100', 88990011, 'luis_mir', 'T', 'lmiranda_tec'),
('Sofia Vergara Luna', 'svergara@pcmastertech.com', '922446688', 99001122, 'sofia_luna', 'T', 'svergara_tec'),
('Diego Maradona Oro', 'dmaradona@pcmastertech.com', '911335577', 12345678, 'dieguito10', 'T', 'dmaradona_tec'),
('Roberto Gómez Bolaños', 'rgomez@pcmastertech.com', '955443322', 87654321, 'chespirito', 'T', 'rgomez_tec'),
('Carmen Aristegui F.', 'caristegui@pcmastertech.com', '966778811', 23456789, 'carmen_news', 'A', 'caristegui_admin'),
('Andrés Silva Paz', 'asilva@pcmastertech.com', '977889922', 34567890, 'andres987', 'T', 'asilva_tec'),
('Patricia Barreto L.', 'pbarreto@pcmastertech.com', '988990033', 45678901, 'patty_b', 'T', 'pbarreto_tec'),
('Gabriel Garcia M.', 'ggarcia@pcmastertech.com', '999001144', 56789012, 'macondo10', 'T', 'ggarcia_tec'),
('Isabel Allende Ll.', 'iallende@pcmastertech.com', '911223355', 67890123, 'espiritus3', 'T', 'iallende_tec'),
('Mario Vargas Llosa', 'mvargas@pcmastertech.com', '922334466', 78901234, 'arequipa50', 'A', 'mvargas_admin'),
('Julio Cortázar D.', 'jcortazar@pcmastertech.com', '933445577', 89012345, 'rayuela7', 'T', 'jcortazar_tec'),
('Jorge Luis Borges', 'jborges@pcmastertech.com', '944556688', 90123456, 'alephtres', 'T', 'jborges_tec'),
('Gabriela Mistral P.', 'gmistral@pcmastertech.com', '955667799', '01234567', 'desolacion', 'T', 'gmistral_tec');


