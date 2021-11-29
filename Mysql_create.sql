CREATE TABLE `Postagem` (
	`Id` VARCHAR(255) NOT NULL AUTO_INCREMENT,
	`Content` VARCHAR(255) NOT NULL AUTO_INCREMENT,
	`LIKES` VARCHAR(255) NOT NULL AUTO_INCREMENT,
	`user_id` INT NOT NULL AUTO_INCREMENT,
	`Comment` INT NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (`Id`)
);

CREATE TABLE `Usuario` (
	`id` INT NOT NULL,
	`Name` VARCHAR(255) NOT NULL,
	`Email` VARCHAR(255) NOT NULL,
	`Password` VARCHAR(255) NOT NULL,
	`CRM_CRP` VARCHAR(255) NOT NULL,
	`LIKES` INT NOT NULL,
	`Type` VARCHAR(255) NOT NULL,
	`Phone` INT NOT NULL,
	`Comment` INT NOT NULL,
	`Post` INT NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Comentario` (
	`Id` INT NOT NULL AUTO_INCREMENT,
	`Text` VARCHAR(255) NOT NULL AUTO_INCREMENT,
	`Post` INT NOT NULL AUTO_INCREMENT,
	`User` VARCHAR(255) NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (`Id`)
);

CREATE TABLE `Type_User` (
	`CLASS` BINARY NOT NULL,
	`1 - Parents` INT NOT NULL,
	`2 - DOCTOR` INT NOT NULL
);

CREATE TABLE `Phones` (
	`HASHSET<>` VARCHAR(255) NOT NULL,
	`Phone 1` VARCHAR(255) NOT NULL,
	`Phone 2` VARCHAR(255) NOT NULL
);

ALTER TABLE `Postagem` ADD CONSTRAINT `Postagem_fk0` FOREIGN KEY (`user_id`) REFERENCES `Usuario`(`Post`);

ALTER TABLE `Usuario` ADD CONSTRAINT `Usuario_fk0` FOREIGN KEY (`Type`) REFERENCES `Type_User`(`CLASS`);

ALTER TABLE `Usuario` ADD CONSTRAINT `Usuario_fk1` FOREIGN KEY (`Phone`) REFERENCES `Phones`(`HASHSET<>`);

ALTER TABLE `Usuario` ADD CONSTRAINT `Usuario_fk2` FOREIGN KEY (`Comment`) REFERENCES `Comentario`(`User`);

ALTER TABLE `Usuario` ADD CONSTRAINT `Usuario_fk3` FOREIGN KEY (`Post`) REFERENCES `Postagem`(`user_id`);

ALTER TABLE `Comentario` ADD CONSTRAINT `Comentario_fk0` FOREIGN KEY (`Post`) REFERENCES `Postagem`(`Comment`);






