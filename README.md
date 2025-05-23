# README_PROYECTO
# El pom tiene las dependencias actuales para conectar con la base de datos ORACLE
# 1. Borrar desde linea 48 a linea 67 para trabajar con otra base de datos
# 2. Luego borrar lo indicado en application.properties


# Insertar datos en tabla producto

INSERT INTO PRODUCTO (nombre, precio, stock) VALUES ('Rosa Mística', 4990, 10); 
INSERT INTO PRODUCTO (nombre, precio, stock) VALUES ('Luz de Luna', 5990, 15);
INSERT INTO PRODUCTO (nombre, precio, stock) VALUES ('Esencia del Alba', 6990, 20); 
INSERT INTO PRODUCTO (nombre, precio, stock) VALUES ('Brisa Marina', 7990, 25); 
INSERT INTO PRODUCTO (nombre, precio, stock) VALUES ('Jardín Secreto', 8990, 30); 
INSERT INTO PRODUCTO (nombre, precio, stock) VALUES ('Fuego Nocturno', 9990, 12); 
INSERT INTO PRODUCTO (nombre, precio, stock) VALUES ('Cielo Estrellado', 10990, 18); 
INSERT INTO PRODUCTO (nombre, precio, stock) VALUES ('Aurora Boreal', 11990, 22); 
INSERT INTO PRODUCTO (nombre, precio, stock) VALUES ('Oasis Tropical', 12990, 16); 
INSERT INTO PRODUCTO (nombre, precio, stock) VALUES ('Sueño Dorado', 13990, 14); 
COMMIT