-- MySQL Script generated by MySQL Workbench
-- 10/08/15 15:35:45
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema artAuction
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema artAuction
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `artAuction` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `artAuction` ;

-- -----------------------------------------------------
-- Table `artAuction`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `artAuction`.`account` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `username` VARCHAR(45) NOT NULL COMMENT '',
  `email` VARCHAR(100) NOT NULL COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  `dateofbirth` DATE NOT NULL COMMENT '',
  `passwordhash` VARCHAR(100) NOT NULL COMMENT '',
  `createdtimestamp` DATETIME NOT NULL COMMENT '',
  `modifiedtimestamp` DATETIME NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)  COMMENT '',
  UNIQUE INDEX `username_UNIQUE` (`username` ASC)  COMMENT '',
  UNIQUE INDEX `email_UNIQUE` (`email` ASC)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `artAuction`.`item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `artAuction`.`item` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `account_id` INT UNSIGNED NOT NULL COMMENT '',
  `title` VARCHAR(45) NOT NULL COMMENT '',
  `image` BLOB NULL COMMENT '',
  `description` VARCHAR(45) NOT NULL COMMENT '',
  `createdtimestamp` DATETIME NOT NULL COMMENT '',
  `closingtimestamp` DATETIME NOT NULL COMMENT '',
  `auction_type` VARCHAR(45) NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)  COMMENT '',
  INDEX `fk_painting_account_idx` (`account_id` ASC)  COMMENT '',
  CONSTRAINT `fk_painting_account`
    FOREIGN KEY (`account_id`)
    REFERENCES `artAuction`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `artAuction`.`bid`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `artAuction`.`bid` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `account_id` INT UNSIGNED NOT NULL COMMENT '',
  `item_id` INT UNSIGNED NOT NULL COMMENT '',
  `value` FLOAT NOT NULL COMMENT '',
  `createdtimestamp` DATETIME NOT NULL COMMENT '',
  INDEX `fk_bid_account1_idx` (`account_id` ASC)  COMMENT '',
  INDEX `fk_bid_painting1_idx` (`item_id` ASC)  COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)  COMMENT '',
  CONSTRAINT `fk_bid_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `artAuction`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bid_painting1`
    FOREIGN KEY (`item_id`)
    REFERENCES `artAuction`.`item` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `artAuction`.`securitytoken`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `artAuction`.`securitytoken` (
  `token` INT UNSIGNED NOT NULL COMMENT '',
  `account_id` INT UNSIGNED NOT NULL COMMENT '',
  `createdtimestamp` DATETIME NOT NULL COMMENT '',
  PRIMARY KEY (`token`)  COMMENT '',
  INDEX `fk_securitytoken_account1_idx` (`account_id` ASC)  COMMENT '',
  UNIQUE INDEX `token_UNIQUE` (`token` ASC)  COMMENT '',
  CONSTRAINT `fk_securitytoken_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `artAuction`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `artAuction`.`credit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `artAuction`.`credit` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `account_id` INT UNSIGNED NOT NULL COMMENT '',
  `cardtype` VARCHAR(45) NOT NULL COMMENT '',
  `ccv` INT(3) NOT NULL COMMENT '',
  `cardnumber` INT(16) NOT NULL COMMENT '',
  `expireydatestamp` DATETIME NOT NULL COMMENT '',
  `holdername` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_credit_account1_idx` (`account_id` ASC)  COMMENT '',
  CONSTRAINT `fk_credit_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `artAuction`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `artAuction`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `artAuction`.`address` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `account_id` INT UNSIGNED NOT NULL COMMENT '',
  `streetaddress` VARCHAR(100) NOT NULL COMMENT '',
  `postalcode` VARCHAR(6) NOT NULL COMMENT '',
  `apartmentnumber` VARCHAR(45) NULL COMMENT '',
  `city` VARCHAR(45) NOT NULL COMMENT '',
  `province` VARCHAR(45) NOT NULL COMMENT '',
  `type` VARCHAR(45) NOT NULL COMMENT '',
  `phone` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_address_account1_idx` (`account_id` ASC)  COMMENT '',
  CONSTRAINT `fk_address_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `artAuction`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
