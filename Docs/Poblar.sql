--DATOS

INSERT INTO USUARIO (ID,NOMBRE,MAIL,ROL) VALUES(1,'Nicolas','n.aguilar','Organizador');
INSERT INTO USUARIO (ID,NOMBRE,MAIL,ROL) VALUES(2,'Sarah','sd.solano','Administradora');
INSERT INTO USUARIO (ID,NOMBRE,MAIL,ROL) VALUES(3,'Camilo','caml','Musico');
INSERT INTO USUARIO (ID,NOMBRE,MAIL,ROL) VALUES(4,'Miguel','miguelMM','Financiero');
INSERT INTO USUARIO (ID,NOMBRE,MAIL,ROL) VALUES(5,'Daniela','d.jaimes','Ingeniera');
INSERT INTO USUARIO (ID,NOMBRE,MAIL,ROL) VALUES(6,'Sebastian','js.millan','Gerente');
INSERT INTO USUARIO (ID,NOMBRE,MAIL,ROL) VALUES(7,'Natalia','n.barreto','Secretaria');
INSERT INTO USUARIO (ID,NOMBRE,MAIL,ROL) VALUES(8,'Maria','mdr.leon','Profesora');
INSERT INTO USUARIO (ID,NOMBRE,MAIL,ROL) VALUES(9,'Fabiola','fabilejarde','Tesorera');
INSERT INTO USUARIO (ID,NOMBRE,MAIL,ROL) VALUES(10,'Daniel','dani.arbe','Vagabundo');
INSERT INTO USUARIO (ID,NOMBRE,MAIL,ROL) VALUES(11,'Valentina','valentable','Revisora fiscal');
INSERT INTO USUARIO (ID,NOMBRE,MAIL,ROL) VALUES(12,'Mauricio','mauro.vsc','Contratista');
INSERT INTO USUARIO (ID,NOMBRE,MAIL,ROL) VALUES(13,'David','davidal.v','Tecnico');
INSERT INTO USUARIO (ID,NOMBRE,MAIL,ROL) VALUES(99,'null','null','null');

INSERT INTO FESTIVAL (ID,ID_ORGANIZADOR,NAME,ANIO,PAIS,FECHAINICIO,FECHAFIN) VALUES(1,6,'Estereo Picnic',2017,'Colombia','27-03-2017','31-03-2017');
INSERT INTO FESTIVAL (ID,ID_ORGANIZADOR,NAME,ANIO,PAIS,FECHAINICIO,FECHAFIN) VALUES(2,11,'Festival de Samba',2010,'Brazil','02-01-2016','04-11-2016');
INSERT INTO FESTIVAL (ID,ID_ORGANIZADOR,NAME,ANIO,PAIS,FECHAINICIO,FECHAFIN) VALUES(6,1,'Rock al parque',2015,'Rusia','15-08-2010','20-12-2020');
INSERT INTO FESTIVAL (ID,ID_ORGANIZADOR,NAME,ANIO,PAIS,FECHAINICIO,FECHAFIN) VALUES(4,13,'Salsa festival',2011,'Barranquilla','17-04-1990','28-06-2005');
INSERT INTO FESTIVAL (ID,ID_ORGANIZADOR,NAME,ANIO,PAIS,FECHAINICIO,FECHAFIN) VALUES(5,2,'Baum festival',2003,'Argentina','25-07-2001','29-10-2030');
INSERT INTO FESTIVAL (ID,ID_ORGANIZADOR,NAME,ANIO,PAIS,FECHAINICIO,FECHAFIN) VALUES(3,11,'Ultra music festival',2012,'Miami','10-03-2017','11-03-2017');

INSERT INTO ORGANIZADOR (ID, ID_FESTIVAL) VALUES (1, 3);
INSERT INTO ORGANIZADOR (ID, ID_FESTIVAL) VALUES (2, 5);
INSERT INTO ORGANIZADOR (ID, ID_FESTIVAL) VALUES (6, 4);
INSERT INTO ORGANIZADOR (ID, ID_FESTIVAL) VALUES (11,2);
INSERT INTO ORGANIZADOR (ID, ID_FESTIVAL) VALUES (13,4);

INSERT INTO OPERARIO (ID, ID_FESTIVAL) VALUES (3,1);
INSERT INTO OPERARIO (ID, ID_FESTIVAL) VALUES (4,2);
INSERT INTO OPERARIO (ID, ID_FESTIVAL) VALUES (8,3);
INSERT INTO OPERARIO (ID, ID_FESTIVAL) VALUES (9,2);
INSERT INTO OPERARIO (ID, ID_FESTIVAL) VALUES (10,5);

insert into CLIENTE (ID, ID_FESTIVAL, CONTRASENA) values (1, 5, '2wulCFJFPy');
insert into CLIENTE (ID, ID_FESTIVAL, CONTRASENA) values (2, 1, 'T3KYyVrHboJ');
insert into CLIENTE (ID, ID_FESTIVAL, CONTRASENA) values (3, 5, 'vij8yn9OI');
insert into CLIENTE (ID, ID_FESTIVAL, CONTRASENA) values (4, 5, 'F6MucXp1lLln');
insert into CLIENTE (ID, ID_FESTIVAL, CONTRASENA) values (5, 4, 'Ys0ntuN9');
insert into CLIENTE (ID, ID_FESTIVAL, CONTRASENA) values (6, 3, 'ptoYigg');
insert into CLIENTE (ID, ID_FESTIVAL, CONTRASENA) values (7, 2, 'kBj9HXsXN');
insert into CLIENTE (ID, ID_FESTIVAL, CONTRASENA) values (8, 1, 'heJHDLKMMx3y');
insert into CLIENTE (ID, ID_FESTIVAL, CONTRASENA) values (9, 2, 'LSfkNS');
insert into CLIENTE (ID, ID_FESTIVAL, CONTRASENA) values (10, 5, 'rjLa1zh0O3Rz');
insert into CLIENTE (ID, ID_FESTIVAL, CONTRASENA) values (99, 3, 'null');

INSERT INTO REPRESENTANTE (ID,PAGINA) VALUES(3,'www.camilo.com');
INSERT INTO REPRESENTANTE (ID,PAGINA) VALUES(8,'www.fabiola.com');
INSERT INTO REPRESENTANTE (ID,PAGINA) VALUES(12,'www.mauricio.com');

INSERT INTO COMPANIA_TEATRO (ID,ID_REPRESENTANTE,NOMBRE,FECHA_LLEGADA,FECHA_SALIDA) VALUES(1,3,'Teatro Julio Mario', '06-10-2005', '25-08-2016');
INSERT INTO COMPANIA_TEATRO (ID,ID_REPRESENTANTE,NOMBRE,FECHA_LLEGADA,FECHA_SALIDA) VALUES(2,8,'Teatro colon', '03-07-2002', '28-08-2015');
INSERT INTO COMPANIA_TEATRO (ID,ID_REPRESENTANTE,NOMBRE,FECHA_LLEGADA,FECHA_SALIDA) VALUES(3,12, 'Teatro Bellas Artes', '01-01-1990', '06-09-2016');
INSERT INTO COMPANIA_TEATRO (ID,ID_REPRESENTANTE,NOMBRE,FECHA_LLEGADA,FECHA_SALIDA) VALUES(4,8, 'Teatro Gaitan', '04-06-1950', '08-12-2017');


insert into SITIO (ID, NOMBRE_SITIO, DIRECCION, CAPACIDAD, LUGAR_ABIERTO, APTO_INCAPACITADOS, INICIO_HORARIO, FIN_HORARIO, PROTECCION_LLUVIA, EFECTOS_ATMOSFERICOS) values (1, 'Teatro Espa�ol','208 Pepper Wood', 34, 0, 1, '03-JUN-15 12.00.00', '20-ENE-17 11.11.08', 0, 0);
insert into SITIO (ID, NOMBRE_SITIO, DIRECCION, CAPACIDAD, LUGAR_ABIERTO, APTO_INCAPACITADOS, INICIO_HORARIO, FIN_HORARIO, PROTECCION_LLUVIA, EFECTOS_ATMOSFERICOS) values (2, 'Teatro Latino', '005 Aberg Crossing', 66, 1, 1, '12-MAY-98 12.00.00', '12-DIC-06 12.00.00', 1, 1);
insert into SITIO (ID, NOMBRE_SITIO, DIRECCION, CAPACIDAD, LUGAR_ABIERTO, APTO_INCAPACITADOS, INICIO_HORARIO, FIN_HORARIO, PROTECCION_LLUVIA, EFECTOS_ATMOSFERICOS) values (3, 'Teatro Catalan', '1413 Amoth Alley', 97, 1, 0, '20-NOV-97 12.00.00', '15-OCT-98 12.00.00', 1, 1);
insert into SITIO (ID, NOMBRE_SITIO, DIRECCION, CAPACIDAD, LUGAR_ABIERTO, APTO_INCAPACITADOS, INICIO_HORARIO, FIN_HORARIO, PROTECCION_LLUVIA, EFECTOS_ATMOSFERICOS) values (4, 'Calle Cultural', '72 Charing Cross Parkway', 88, 1, 1, '07-JUN-01 12.00.00', '16-AGO-08 12.00.00', 0, 1);
insert into SITIO (ID, NOMBRE_SITIO, DIRECCION, CAPACIDAD, LUGAR_ABIERTO, APTO_INCAPACITADOS, INICIO_HORARIO, FIN_HORARIO, PROTECCION_LLUVIA, EFECTOS_ATMOSFERICOS) values (5, 'Coliseo de arte', '90010 Colorado Road', 42, 1, 0, '02-MAY-91 12.00.00', '01-MAR-10 12.00.00', 1, 0);
insert into SITIO (ID, NOMBRE_SITIO, DIRECCION, CAPACIDAD, LUGAR_ABIERTO, APTO_INCAPACITADOS, INICIO_HORARIO, FIN_HORARIO, PROTECCION_LLUVIA, EFECTOS_ATMOSFERICOS) values (6, 'Teatro de los sue�os', '03 Linden Lane', 90, 1, 1, '25-ABR-99 12.00.00', '19-DIC-06 12.00.00', 1, 0);
insert into SITIO (ID, NOMBRE_SITIO, DIRECCION, CAPACIDAD, LUGAR_ABIERTO, APTO_INCAPACITADOS, INICIO_HORARIO, FIN_HORARIO, PROTECCION_LLUVIA, EFECTOS_ATMOSFERICOS) values (7, 'Teatro Soler','0212 Hazelcrest Court', 59, 1, 0, '12-JUL-02 12.00.00', '17-AGO-05 12.00.00', 0, 0);
insert into SITIO (ID, NOMBRE_SITIO, DIRECCION, CAPACIDAD, LUGAR_ABIERTO, APTO_INCAPACITADOS, INICIO_HORARIO, FIN_HORARIO, PROTECCION_LLUVIA, EFECTOS_ATMOSFERICOS) values (8, 'Teatro Ingels','9 Di Loreto Drive', 48, 1, 1, '12-AGO-12 12.00.00', '10-NOV-15 12.00.00', 1, 0);
insert into SITIO (ID, NOMBRE_SITIO, DIRECCION, CAPACIDAD, LUGAR_ABIERTO, APTO_INCAPACITADOS, INICIO_HORARIO, FIN_HORARIO, PROTECCION_LLUVIA, EFECTOS_ATMOSFERICOS) values (9, 'Teatro Rooney','8903 Loeprich Park', 100, 0, 0, '09-JUN-93 12.00.00', '11-DIC-16 12.00.00', 1, 0);
insert into SITIO (ID, NOMBRE_SITIO, DIRECCION, CAPACIDAD, LUGAR_ABIERTO, APTO_INCAPACITADOS, INICIO_HORARIO, FIN_HORARIO, PROTECCION_LLUVIA, EFECTOS_ATMOSFERICOS) values (10, 'Teatro de Colombia', '188 Mcguire Lane', 75, 1, 0, '23-JUL-98 12.00.00', '12-ENE-06 12.00.00', 0, 0);

insert into LOCALIDAD (ID, ID_SITIO, NOMBRE, CAPACIDAD, NUMERADA) values (1, 10, 'VIP', 108, 1);
insert into LOCALIDAD (ID, ID_SITIO, NOMBRE, CAPACIDAD, NUMERADA) values (2, 2, 'Diamante', 20, 0);
insert into LOCALIDAD (ID, ID_SITIO, NOMBRE, CAPACIDAD, NUMERADA) values (3, 9, 'Oro', 115, 0);
insert into LOCALIDAD (ID, ID_SITIO, NOMBRE, CAPACIDAD, NUMERADA) values (4, 9, 'General', 138, 1);
insert into LOCALIDAD (ID, ID_SITIO, NOMBRE, CAPACIDAD, NUMERADA) values (5, 7, 'VIP', 29, 1);

insert into ESPECTACULO (ID, NOMBRE, DURACION, INTERMEDIO, IDIOMA, CLASIFICACION, COSTO_REALIZACION, PUBLICO_ACTIVO, TRADUCCION_SUBTITULOS, TRADUCCION_AUDIFONOS, DESCRIPCION, PUBLICO_OBJETIVO) values (1, 'Wuckert, Tillman and Williamson', 8.33, 1, 'Mongolian', 'Services', 131817.26, 1, 0, 1, 'Poisoning by heroin, accidental', 'NiNos');
insert into ESPECTACULO (ID, NOMBRE, DURACION, INTERMEDIO, IDIOMA, CLASIFICACION, COSTO_REALIZACION, PUBLICO_ACTIVO, TRADUCCION_SUBTITULOS, TRADUCCION_AUDIFONOS, DESCRIPCION, PUBLICO_OBJETIVO) values (2, 'Nikolaus-Gerhold', 4.38, 0, 'Fijian', 'Product Management', 110182.62, 2, 0, 1, 'Nondisp fx of shaft of 4th', 'Adultos');
insert into ESPECTACULO (ID, NOMBRE, DURACION, INTERMEDIO, IDIOMA, CLASIFICACION, COSTO_REALIZACION, PUBLICO_ACTIVO, TRADUCCION_SUBTITULOS, TRADUCCION_AUDIFONOS, DESCRIPCION, PUBLICO_OBJETIVO) values (3, 'Jenkins-Wintheiser', 11.97, 0, 'Quechua', 'Product Management', 132717.74, 1, 0, 9, 'Mycotic corneal ulcer', 'Adultos Mayores');
insert into ESPECTACULO (ID, NOMBRE, DURACION, INTERMEDIO, IDIOMA, CLASIFICACION, COSTO_REALIZACION, PUBLICO_ACTIVO, TRADUCCION_SUBTITULOS, TRADUCCION_AUDIFONOS, DESCRIPCION, PUBLICO_OBJETIVO) values (4, 'Rippin and Sons', 5.66, 0, 'Moldovan', 'Legal', 119186.74, 1, 0, 0, 'Encounter for fit/adjst of complete', 'Adultos Mayores');
insert into ESPECTACULO (ID, NOMBRE, DURACION, INTERMEDIO, IDIOMA, CLASIFICACION, COSTO_REALIZACION, PUBLICO_ACTIVO, TRADUCCION_SUBTITULOS, TRADUCCION_AUDIFONOS, DESCRIPCION, PUBLICO_OBJETIVO) values (5, 'Padberg, Heaney and Dietrich', 11.57, 1, 'Persian', 'Business Development', 157044.47, 1, 1, 1, 'Acute obstructive', 'NiNos');
insert into ESPECTACULO (ID, NOMBRE, DURACION, INTERMEDIO, IDIOMA, CLASIFICACION, COSTO_REALIZACION, PUBLICO_ACTIVO, TRADUCCION_SUBTITULOS, TRADUCCION_AUDIFONOS, DESCRIPCION, PUBLICO_OBJETIVO) values (6, 'Collins-Cruickshank', 8.55, 1, 'Quechua', 'Legal', 193138.6, 1, 1, 1, 'Nondisp oblique fx shaft', 'Familia');
insert into ESPECTACULO (ID, NOMBRE, DURACION, INTERMEDIO, IDIOMA, CLASIFICACION, COSTO_REALIZACION, PUBLICO_ACTIVO, TRADUCCION_SUBTITULOS, TRADUCCION_AUDIFONOS, DESCRIPCION, PUBLICO_OBJETIVO) values (7, 'Gutmann LLC', 2.26, 0, 'Latvian', 'Product Management', 156908.47, 1, 0, 1, 'Corrosion of third degree of trunk', 'Adultos Mayores');
insert into ESPECTACULO (ID, NOMBRE, DURACION, INTERMEDIO, IDIOMA, CLASIFICACION, COSTO_REALIZACION, PUBLICO_ACTIVO, TRADUCCION_SUBTITULOS, TRADUCCION_AUDIFONOS, DESCRIPCION, PUBLICO_OBJETIVO) values (8, 'Kuphal-Morar', 7.34, 0, 'Somali', 'Services', 180846.04, 1, 1, 0, 'Fall from scooter (nonmotorized)', 'Adultos');
insert into ESPECTACULO (ID, NOMBRE, DURACION, INTERMEDIO, IDIOMA, CLASIFICACION, COSTO_REALIZACION, PUBLICO_ACTIVO, TRADUCCION_SUBTITULOS, TRADUCCION_AUDIFONOS, DESCRIPCION, PUBLICO_OBJETIVO) values (9, 'Jakubowski LLC', 4.08, 1, 'Papiamento', 'Research and Development', 174517.28, 0, 0, 0, 'Nondisp bimalleol fx', 'Adultos');

insert into FUNCION (ID, ID_ESPECTACULO, ID_SITIO, COSTO, SILLAS_OCUPADAS, FECHA_HORA, YA_SE_REALIZO) values (1, 9, 4, 114211.94, 7, '10-ABR-17 12.00.00', 0);
insert into FUNCION (ID, ID_ESPECTACULO, ID_SITIO, COSTO, SILLAS_OCUPADAS, FECHA_HORA, YA_SE_REALIZO) values (2, 3, 1, 278529.77, 7, '20-NOV-10 12.00.00', 1);
insert into FUNCION (ID, ID_ESPECTACULO, ID_SITIO, COSTO, SILLAS_OCUPADAS, FECHA_HORA, YA_SE_REALIZO) values (3, 9, 3, 403348.89, 8, '02-ENE-11 12.00.00', 0);
insert into FUNCION (ID, ID_ESPECTACULO, ID_SITIO, COSTO, SILLAS_OCUPADAS, FECHA_HORA, YA_SE_REALIZO) values (4, 5, 2, 324011.0, 3, '12-SEP-09 12.00.00', 0);
insert into FUNCION (ID, ID_ESPECTACULO, ID_SITIO, COSTO, SILLAS_OCUPADAS, FECHA_HORA, YA_SE_REALIZO) values (5, 2, 1, 181142.47, 4, '20-AGO-10 12.00.00', 1);
insert into FUNCION (ID, ID_ESPECTACULO, ID_SITIO, COSTO, SILLAS_OCUPADAS, FECHA_HORA, YA_SE_REALIZO) values (6, 4, 5, 191709.97, 8, '05-FEB-98 12.00.00', 0);
insert into FUNCION (ID, ID_ESPECTACULO, ID_SITIO, COSTO, SILLAS_OCUPADAS, FECHA_HORA, YA_SE_REALIZO) values (7, 6, 6, 393055.89, 2, '26-SEP-17 12.00.00', 1);

insert into BOLETA ( ID_LOCALIDAD, ID_FUNCION, ID_CLIENTE, UBICACION, ESTADO, COSTO) values (5, 3, 9, 1, 0, 503229);
insert into BOLETA ( ID_LOCALIDAD, ID_FUNCION, ID_CLIENTE, UBICACION, ESTADO, COSTO) values (2, 5, 2, 15, 2, 452423);
insert into BOLETA ( ID_LOCALIDAD, ID_FUNCION, ID_CLIENTE, UBICACION, ESTADO, COSTO) values (2, 7, 7, 14, 1, 495338);
insert into BOLETA ( ID_LOCALIDAD, ID_FUNCION, ID_CLIENTE, UBICACION, ESTADO, COSTO) values (4, 6, 8, 20, 3, 358000);
insert into BOLETA ( ID_LOCALIDAD, ID_FUNCION, ID_CLIENTE, UBICACION, ESTADO, COSTO) values (1, 1, 3, 12, 0, 364500);
insert into BOLETA ( ID_LOCALIDAD, ID_FUNCION, ID_CLIENTE, UBICACION, ESTADO, COSTO) values (3, 2, 4, 11, 1, 453840);
insert into BOLETA ( ID_LOCALIDAD, ID_FUNCION, ID_CLIENTE, UBICACION, ESTADO, COSTO) values (2, 5, 10, 5, 3, 585411);
insert into BOLETA ( ID_LOCALIDAD, ID_FUNCION, ID_CLIENTE, UBICACION, ESTADO, COSTO) values (3, 5, 6, 8, 2, 339765);
insert into BOLETA ( ID_LOCALIDAD, ID_FUNCION, ID_CLIENTE, UBICACION, ESTADO, COSTO) values (2, 1, 7, 6, 1, 565982);
insert into BOLETA ( ID_LOCALIDAD, ID_FUNCION, ID_CLIENTE, UBICACION, ESTADO, COSTO) values (5, 6, 8, 7, 3, 384523);

insert into PREFERENCIA (ID, PREFERENCIA, TIPO) values (1, 'Drama', 'Categoria');
insert into PREFERENCIA (ID, PREFERENCIA, TIPO) values (3, 'Titeres', 'Categoria');
insert into PREFERENCIA (ID, PREFERENCIA, TIPO) values (4, 'Comparsas', 'Categoria');
insert into PREFERENCIA (ID, PREFERENCIA, TIPO) values (2, 'Drama', 'Genero');
insert into PREFERENCIA (ID, PREFERENCIA, TIPO) values (5, 'Comedia', 'Genero');
insert into PREFERENCIA (ID, PREFERENCIA, TIPO) values (6, 'Terror', 'Genero');
insert into PREFERENCIA (ID, PREFERENCIA, TIPO) values (7, 'Mayores de edad', 'Publico');
insert into PREFERENCIA (ID, PREFERENCIA, TIPO) values (8, 'NiNos', 'Publico');

insert into CATEGORIA_ESPECTACULO (ID_ESPECTACULO, CATEGORIA) values (3, 'nulla elit');
insert into CATEGORIA_ESPECTACULO (ID_ESPECTACULO, CATEGORIA) values (3, 'dignissim vestibulum');
insert into CATEGORIA_ESPECTACULO (ID_ESPECTACULO, CATEGORIA) values (1, 'sapien ut');
insert into CATEGORIA_ESPECTACULO (ID_ESPECTACULO, CATEGORIA) values (3, 'lacinia erat');
insert into CATEGORIA_ESPECTACULO (ID_ESPECTACULO, CATEGORIA) values (4, 'erat eros');

insert into REQUERIMIENTOS_ESPECTACULOS (ID_ESPECTACULO, REQUERIMIENTO) values (3, 'augue aliquam');
insert into REQUERIMIENTOS_ESPECTACULOS (ID_ESPECTACULO, REQUERIMIENTO) values (4, 'sed nisl');
insert into REQUERIMIENTOS_ESPECTACULOS (ID_ESPECTACULO, REQUERIMIENTO) values (4, 'dui');
insert into REQUERIMIENTOS_ESPECTACULOS (ID_ESPECTACULO, REQUERIMIENTO) values (4, 'pellentesque');
insert into REQUERIMIENTOS_ESPECTACULOS (ID_ESPECTACULO, REQUERIMIENTO) values (2, 'tellus');

insert into COMPANIA_ESPECTACULO (ID_ESPECTACULO, ID_COMPANIA) values (5, 4);
insert into COMPANIA_ESPECTACULO (ID_ESPECTACULO, ID_COMPANIA) values (8, 3);
insert into COMPANIA_ESPECTACULO (ID_ESPECTACULO, ID_COMPANIA) values (9, 2);
insert into COMPANIA_ESPECTACULO (ID_ESPECTACULO, ID_COMPANIA) values (2, 1);
insert into COMPANIA_ESPECTACULO (ID_ESPECTACULO, ID_COMPANIA) values (1, 3);

insert into COMPANIAS_EN_FESTIVAL (ID_FESTIVAL, ID_COMPANIA) values (2, 4);
insert into COMPANIAS_EN_FESTIVAL (ID_FESTIVAL, ID_COMPANIA) values (2, 3);
insert into COMPANIAS_EN_FESTIVAL (ID_FESTIVAL, ID_COMPANIA) values (5, 2);
insert into COMPANIAS_EN_FESTIVAL (ID_FESTIVAL, ID_COMPANIA) values (4, 1);
insert into COMPANIAS_EN_FESTIVAL (ID_FESTIVAL, ID_COMPANIA) values (4, 4);

insert into SITIOS_EN_FESTIVAL (ID_FESTIVAL, ID_SITIO) values (3, 5);
insert into SITIOS_EN_FESTIVAL (ID_FESTIVAL, ID_SITIO) values (2, 3);
insert into SITIOS_EN_FESTIVAL (ID_FESTIVAL, ID_SITIO) values (5, 3);
insert into SITIOS_EN_FESTIVAL (ID_FESTIVAL, ID_SITIO) values (4, 2);
insert into SITIOS_EN_FESTIVAL (ID_FESTIVAL, ID_SITIO) values (2, 2);

insert into REQUERIMIENTOS_SITIO (ID_SITIO, REQUERIMIENTOS) values (5, 'et');
insert into REQUERIMIENTOS_SITIO (ID_SITIO, REQUERIMIENTOS) values (4, 'dui');
insert into REQUERIMIENTOS_SITIO (ID_SITIO, REQUERIMIENTOS) values (4, 'a');
insert into REQUERIMIENTOS_SITIO (ID_SITIO, REQUERIMIENTOS) values (1, 'quis turpis');
insert into REQUERIMIENTOS_SITIO (ID_SITIO, REQUERIMIENTOS) values (3, 'dis');