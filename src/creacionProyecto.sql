

CREATE table if NOT exists Usuarios (
Correo VARCHAR(40) NOT NULL,
Contrasena VARCHAR(10) NOT NULL,
Roll VARCHAR(15) NOT NULL,
PRIMARY KEY (Correo)
);

create table if NOT exists Familiares(
Correo_Familiar VARCHAR(40) NOT NULL,
PRIMARY KEY (Correo_Familiar),
CONSTRAINT FK_Correo_Familiar FOREIGN KEY (Correo_Familiar) REFERENCES Usuarios (Correo)
);

create table if NOT exists Supervisor(
Correo_Supervisor VARCHAR(40) NOT NULL,
PRIMARY KEY (Correo_Supervisor),
CONSTRAINT FK_Correo_Supervisor FOREIGN KEY (Correo_Supervisor) REFERENCES Usuarios (Correo)
);

create table if NOT exists Pacientes (
Correo_Usuario VARCHAR(40) NOT NULL,
Nombre VARCHAR(100) NOT NULL,
Fecha_Nacimiento DATETIME NOT NULL,
Direccion VARCHAR(100) NOT NULL,
Telefono TEXT NOT NULL,
Luz_Activado bit default 1,
Monitoreo_Activado bit default 1,
Pastillero_Activado bit default 1,
Caida_Activado bit default 1,
Correo_Familiar VARCHAR(40) NOT NULL,
Correo_Supervisor VARCHAR(40) NOT NULL,
PRIMARY KEY(Correo_Usuario),
CONSTRAINT FK_Correo_Familiar_Paciente FOREIGN KEY (Correo_Familiar) REFERENCES Familiares (Correo_Familiar),
CONSTRAINT FK_Correo_Supervisor_Paciente FOREIGN KEY (Correo_Supervisor) REFERENCES Supervisor (Correo_Supervisor)
);



create table if NOT exists Datos (
ID int NOT NULL AUTO_INCREMENT,
Fecha DATETIME NOT NULL,
Correo_Paciente VARCHAR(40) NOT NULL,
PRIMARY KEY(ID),
CONSTRAINT FK_Correo_Paciente_Dato FOREIGN KEY (Correo_Paciente) REFERENCES Pacientes(Correo_Usuario)
);

create table if NOT exists Presion (
ID INT  AUTO_INCREMENT NOT NULL,
Sensor_Presion int NOT NULL,
PRIMARY KEY(ID),
CONSTRAINT FK_ID_Presion FOREIGN KEY (ID) REFERENCES Datos(ID)
);

create table if NOT exists Acelerometro (
ID INT  AUTO_INCREMENT NOT NULL,
Sensor_Acelerometro int NOT NULL,
PRIMARY KEY(ID),
CONSTRAINT FK_ID_Acelerometro FOREIGN KEY (ID) REFERENCES Datos(ID)
);

create table if NOT exists Caida (
ID INT AUTO_INCREMENT NOT NULL,
Sensor_Caida int NOT NULL,
PRIMARY KEY(ID),
CONSTRAINT FK_ID_Caida FOREIGN KEY (ID) REFERENCES Datos(ID)
);

create table if NOT exists Mensajes (
ID INTEGER AUTO_INCREMENT NOT NULL,
Fecha DATETIME NOT NULL,
Texto TEXT NOT NULL,
Correo_Emisor VARCHAR(40) NOT NULL,
Correo_Receptor VARCHAR(40) NOT NULL,
PRIMARY KEY(ID)
);


create table if NOT exists Pastillero (
ID INT AUTO_INCREMENT NOT NULL,
Correo_Paciente VARCHAR(40) NOT NULL,
Fecha DATETIME NOT NULL,
Dia VARCHAR(1)  NOT NULL,
Pastilla VARCHAR(40) NOT NULL,
Dosis VARCHAR(100) NOT NULL,
PRIMARY KEY(ID),
CONSTRAINT FK_Correo_Pastilla FOREIGN KEY (Correo_Paciente) REFERENCES Pacientes(Correo_Usuario)
);

create table if NOT exists RecuperarContrasena (
Correo VARCHAR(40) NOT NULL,
Codigo VARCHAR(40) NOT NULL,
PRIMARY KEY(Correo),
CONSTRAINT FK_Recuperar_Correo FOREIGN KEY (Correo) REFERENCES Usuarios(Correo)
);

