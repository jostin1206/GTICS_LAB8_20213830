CREATE DATABASE estudiantes;
CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    gpa DECIMAL(4, 2) CHECK (gpa > 3.5),
    facultad VARCHAR(50),
    creditos_completados INT,
    UNIQUE (id)
);

INSERT INTO students(nombre, gpa, facultad, creditos_completados)
VALUES 
    ('Pepito Pérez', 16.0, 'Ingenieria', 120),
    ('Juana López', 15.5, 'Ciencias Sociales', 110);

