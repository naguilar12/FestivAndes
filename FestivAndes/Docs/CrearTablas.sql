-- Create schemas

-- Create tables
CREATE TABLE FESTIVAL
(
    id INTEGER NOT NULL,
    id_organizador INTEGER,
    name VARCHAR(50),
    anio INTEGER,
    pais VARCHAR(50),
    fechaInicio DATE,
    fechaFin DATE,
    CONSTRAINT festival_pk PRIMARY KEY(id)
);

CREATE TABLE COMPANIAS_EN_FESTIVAL
(
    id_festival INTEGER NOT NULL,
    id_compania INTEGER NOT NULL,
    CONSTRAINT companias_en_festival_pk PRIMARY KEY(id_festival, id_compania)
);

CREATE TABLE COMPANIA_TEATRO
(
    id INTEGER NOT NULL,
    id_representante INTEGER,
    nombre VARCHAR(50),
    fecha_llegada DATE,
    fecha_salida DATE,
    CONSTRAINT compania_teatro_pk PRIMARY KEY(id)
);

CREATE TABLE REPRESENTANTE
(
    id INTEGER NOT NULL,
    pagina VARCHAR(50),
    CONSTRAINT representante_pk PRIMARY KEY(id)
);

CREATE TABLE ESPECTACULO
(
    id INTEGER NOT NULL,
    nombre VARCHAR(50),
    duracion DOUBLE PRECISION,
    intermedio NUMBER(1,0),
    idioma VARCHAR(50),
    clasificacion VARCHAR(50),
    costo_realizacion DOUBLE PRECISION,
    publico_activo NUMBER(1,0),
    traduccion_subtitulos NUMBER(1,0),
    traduccion_audifonos NUMBER(1,0),
    descripcion VARCHAR(50),
    publico_objetivo VARCHAR(50),
    CONSTRAINT espectaculo_pk PRIMARY KEY(id)
);

CREATE TABLE COMPANIA_ESPECTACULO
(
    id_compania INTEGER NOT NULL,
    id_espectaculo INTEGER NOT NULL,
    CONSTRAINT compania_espectaculo_pk PRIMARY KEY(id_compania, id_espectaculo)
);

CREATE TABLE CATEGORIA_ESPECTACULO
(
    id_espectaculo INTEGER NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    CONSTRAINT categoria_espectaculo_pk PRIMARY KEY(id_espectaculo, categoria)
);

CREATE TABLE REQUERIMIENTOS_ESPECTACULOS
(
    id_espectaculo INTEGER NOT NULL,
    requerimiento VARCHAR(50) NOT NULL,
    CONSTRAINT requerimientos_espectaculos_pk PRIMARY KEY(id_espectaculo, requerimiento)
);

CREATE TABLE FUNCION
(
    id INTEGER NOT NULL,
    id_espectaculo INTEGER,
    id_sitio INTEGER,
    costo DOUBLE PRECISION,
    sillas_reservadas INTEGER,
    fecha_hora TIMESTAMP,
    ya_se_realizo NUMBER(1,0),
    CONSTRAINT funcion_pk PRIMARY KEY(id)
);

CREATE TABLE SITIO
(
    id INTEGER NOT NULL,
    direccion VARCHAR(50),
    capacidad INTEGER,
    lugar_abierto NUMBER(1,0),
    apto_incapacitados NUMBER(1,0),
    inicio_horario TIMESTAMP,
    fin_horario TIMESTAMP,
    proteccion_lluvia NUMBER(1,0),
    efectos_atmosfericos NUMBER(1,0),
    CONSTRAINT sitio_pk PRIMARY KEY(id)
);

CREATE TABLE BOLETA
(
    id_localidad INTEGER,
    id_funcion INTEGER,
    id_cliente INTEGER,
    ubicacion VARCHAR(100),
    estado INTEGER,
    costo DOUBLE PRECISION,
    CONSTRAINT boleta_pk PRIMARY KEY(id_localidad, id_funcion, id_cliente, ubicacion)
);

CREATE TABLE LOCALIDAD
(
    id INTEGER NOT NULL,
    id_sitio INTEGER,
    nombre VARCHAR(50),
    capacidad INTEGER,
    numerada NUMBER(1,0),
    CONSTRAINT localidad_pk PRIMARY KEY(id)
);

CREATE TABLE REQUERIMIENTOS_SITIO
(
    id_sitio INTEGER NOT NULL,
    requerimientos VARCHAR(50) NOT NULL,
    CONSTRAINT requerimientos_sitio_pk PRIMARY KEY(id_sitio, requerimientos)
);

CREATE TABLE SITIOS_EN_FESTIVAL
(
    id_festival INTEGER NOT NULL,
    id_sitio INTEGER NOT NULL,
    CONSTRAINT sitios_en_festival_pk PRIMARY KEY(id_festival, id_sitio)
);

CREATE TABLE ORGANIZADOR
(
    id INTEGER NOT NULL,
    id_festival INTEGER,
    CONSTRAINT organizador_pk PRIMARY KEY(id)
);

CREATE TABLE USUARIO
(
    id INTEGER NOT NULL,
    nombre VARCHAR(50),
    mail VARCHAR(50) UNIQUE,
    rol VARCHAR(50),
    CONSTRAINT usuario_pk PRIMARY KEY(id)
);

CREATE TABLE CLIENTE
(
    id INTEGER NOT NULL,
    id_festival INTEGER,
    contrasena VARCHAR(50),
    CONSTRAINT cliente_pk PRIMARY KEY(id)
);

CREATE TABLE PREFERENCIA
(
    id INTEGER NOT NULL,
    preferencia VARCHAR(50) NOT NULL,
    tipo VARCHAR(50),
    CONSTRAINT preferencia_pk PRIMARY KEY(id, preferencia)
);

CREATE TABLE OPERARIO
(
    id INTEGER NOT NULL,
    id_festival INTEGER,
    CONSTRAINT operario_pk PRIMARY KEY(id)
);


-- Create FKs
ALTER TABLE COMPANIA_TEATRO
    ADD    FOREIGN KEY (id_representante)
    REFERENCES REPRESENTANTE(id)
;
    
ALTER TABLE CATEGORIA_ESPECTACULO
    ADD    FOREIGN KEY (id_espectaculo)
    REFERENCES ESPECTACULO(id)
;
    
ALTER TABLE SITIOS_EN_FESTIVAL
    ADD    FOREIGN KEY (id_sitio)
    REFERENCES SITIO(id)
;
    
ALTER TABLE FUNCION
    ADD    FOREIGN KEY (id_espectaculo)
    REFERENCES ESPECTACULO(id)
;
    
ALTER TABLE FUNCION
    ADD    FOREIGN KEY (id_sitio)
    REFERENCES SITIO(id)
;
    
ALTER TABLE LOCALIDAD
    ADD    FOREIGN KEY (id_sitio)
    REFERENCES SITIO(id)
;
    
ALTER TABLE COMPANIAS_EN_FESTIVAL
    ADD    FOREIGN KEY (id_compania)
    REFERENCES COMPANIA_TEATRO(id)
;
    
ALTER TABLE COMPANIAS_EN_FESTIVAL
    ADD    FOREIGN KEY (id_festival)
    REFERENCES FESTIVAL(id)
;
    
ALTER TABLE SITIOS_EN_FESTIVAL
    ADD    FOREIGN KEY (id_festival)
    REFERENCES FESTIVAL(id)
;
    
ALTER TABLE COMPANIA_ESPECTACULO
    ADD    FOREIGN KEY (id_compania)
    REFERENCES COMPANIA_TEATRO(id)
;
    
ALTER TABLE REQUERIMIENTOS_SITIO
    ADD    FOREIGN KEY (id_sitio)
    REFERENCES SITIO(id)
;
    
ALTER TABLE COMPANIA_ESPECTACULO
    ADD    FOREIGN KEY (id_espectaculo)
    REFERENCES ESPECTACULO(id)
;
    
ALTER TABLE REQUERIMIENTOS_ESPECTACULOS
    ADD    FOREIGN KEY (id_espectaculo)
    REFERENCES ESPECTACULO(id)
;
    
ALTER TABLE BOLETA
    ADD    FOREIGN KEY (id_localidad)
    REFERENCES LOCALIDAD(id)
;
    
ALTER TABLE CLIENTE
    ADD    FOREIGN KEY (id_festival)
    REFERENCES FESTIVAL(id)
;
    
ALTER TABLE ORGANIZADOR
    ADD    FOREIGN KEY (id)
    REFERENCES USUARIO(id)
;
    
ALTER TABLE CLIENTE
    ADD    FOREIGN KEY (id)
    REFERENCES USUARIO(id)
;
    
ALTER TABLE REPRESENTANTE
    ADD    FOREIGN KEY (id)
    REFERENCES USUARIO(id)
;
    
ALTER TABLE PREFERENCIA
    ADD    FOREIGN KEY (id)
    REFERENCES USUARIO(id)
;
    
ALTER TABLE BOLETA
    ADD    FOREIGN KEY (id_funcion)
    REFERENCES FUNCION(id)
;
    
ALTER TABLE BOLETA
    ADD    FOREIGN KEY (id_cliente)
    REFERENCES CLIENTE(id)
;
    
ALTER TABLE ORGANIZADOR
    ADD    FOREIGN KEY (id_festival)
    REFERENCES FESTIVAL(id)
;
    
ALTER TABLE OPERARIO
    ADD    FOREIGN KEY (id)
    REFERENCES USUARIO(id)
;
    
ALTER TABLE OPERARIO
    ADD    FOREIGN KEY (id_festival)
    REFERENCES FESTIVAL(id)
;
    

-- Create Indexes

