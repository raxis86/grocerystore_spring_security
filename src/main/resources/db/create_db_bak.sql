CREATE TABLE groceries (
  ID char(36) NOT NULL,
  PARENTID char(36) DEFAULT NULL,
  ISCATEGORY tinyint NOT NULL,
  NAME varchar(45) DEFAULT NULL,
  QUANTITY int(11) DEFAULT NULL,
  PRICE decimal(15,2) DEFAULT NULL,
  PRIMARY KEY (ID));
CREATE TABLE grocerylist (
  PK mediumint NOT NULL AUTO_INCREMENT,
  ID char(36) NOT NULL,
  GROCERYID char(36) NOT NULL,
  QUANTITY int(11) NOT NULL,
  PRIMARY KEY (PK));
CREATE TABLE orders (
  ID char(36) NOT NULL,
  USERID char(36) NOT NULL,
  STATUSID char(36) NOT NULL,
  PRICE decimal(15,2) DEFAULT NULL,
  DATETIME timestamp(6) NULL DEFAULT NULL,
  GROCERYLISTID char(36) NOT NULL,
  ADDRESS varchar(100) DEFAULT NULL,
  PRIMARY KEY (ID));
CREATE TABLE orderupdates (
  ID char(36) NOT NULL,
  STATUS varchar(45) NOT NULL,
  PRIMARY KEY (ID));
CREATE TABLE roles (
  ID char(36) NOT NULL,
  NAME varchar(45) NOT NULL,
  PRIMARY KEY (ID));
CREATE TABLE users (
  ID char(36) NOT NULL,
  ROLEID char(36) NOT NULL,
  NAME varchar(100) DEFAULT NULL,
  EMAIL varchar(100) DEFAULT NULL,
  PASSWORD char(40) NOT NULL,
  SALT char(40) NOT NULL,
  LASTNAME varchar(100) DEFAULT NULL,
  SURNAME varchar(100) DEFAULT NULL,
  ADDRESS varchar(100) DEFAULT NULL,
  PHONE varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID));