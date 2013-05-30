
-- -----------------------------------------------------
-- Table personas
-- -----------------------------------------------------
CREATE  TABLE persona (
  id INT NOT NULL AUTO_INCREMENT,
  apellido VARCHAR(255) NULL DEFAULT NULL ,
  fechaNacimiento DATETIME NULL DEFAULT NULL ,
  nombre VARCHAR(255) NULL DEFAULT NULL ,
  telefono INT NULL DEFAULT NULL ,
  PRIMARY KEY (id) );


-- -----------------------------------------------------
-- Table profesor
-- -----------------------------------------------------
CREATE  TABLE profesor (
  grado VARCHAR(255) NULL DEFAULT NULL ,
  id INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id) ,
    FOREIGN KEY (id)REFERENCES persona (id));


-- -----------------------------------------------------
-- Table cursos
-- -----------------------------------------------------

CREATE  TABLE curso (
  codigo INT NOT NULL AUTO_INCREMENT,
  creditos VARCHAR(255) NULL DEFAULT NULL ,
  nombre VARCHAR(255) NULL DEFAULT NULL ,
  responsable INT NOT NULL ,
  PRIMARY KEY (codigo), 
  FOREIGN KEY (responsable)REFERENCES profesor (id)
  );

-- -----------------------------------------------------
-- Table enseñan
-- -----------------------------------------------------
CREATE  TABLE  ensenan (
  codigo INT NOT NULL ,
  ensenan INT NOT NULL ,
  PRIMARY KEY (codigo, ensenan) ,
  
  FOREIGN KEY (codigo)   REFERENCES curso(codigo),
  FOREIGN KEY (ensenan)  REFERENCES profesor (id));

-- -----------------------------------------------------
-- Table estudiantes
-- -----------------------------------------------------
CREATE  TABLE estudiante(
  fechaIngreso DATETIME NULL DEFAULT NULL ,
  matricula VARCHAR(255) NULL DEFAULT NULL ,
  id INT NOT NULL AUTO_INCREMENT ,
  PRIMARY KEY (id) ,
  FOREIGN KEY (id)REFERENCES persona(id));


-- -----------------------------------------------------
-- Table inscripciones
-- -----------------------------------------------------
CREATE  TABLE inscripciones (
  codigo INT NOT NULL ,
  estudiante INT NOT NULL ,
  fecha DATETIME NULL ,
  PRIMARY KEY (codigo, estudiante) ,
    FOREIGN KEY (estudiante) REFERENCES estudiante (id),
    FOREIGN KEY (codigo )  REFERENCES curso(codigo ));

