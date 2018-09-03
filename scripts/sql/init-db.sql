USE mysql;

-- -----------------------------------------------------
-- Schema farm_control
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS farm_control;

CREATE SCHEMA IF NOT EXISTS farm_control
  DEFAULT CHARACTER SET utf8mb4;

-- -----------------------------------------------------
-- Users
-- -----------------------------------------------------
DROP USER IF EXISTS 'farm_control'@'localhost';
DROP USER IF EXISTS 'farm_control'@'%';

CREATE USER IF NOT EXISTS 'farm_control'@'localhost'
  IDENTIFIED BY 'control123';

CREATE USER IF NOT EXISTS 'farm_control'@'%'
  IDENTIFIED BY 'control123';

GRANT ALL PRIVILEGES ON wcapture.* TO 'farm_control'@'localhost'
IDENTIFIED BY 'control123';

GRANT ALL PRIVILEGES ON wcapture.* TO 'farm_control'@'%'
IDENTIFIED BY 'control123';

FLUSH PRIVILEGES;