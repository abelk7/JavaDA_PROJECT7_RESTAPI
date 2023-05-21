DROP DaTABASE IF EXISTS demo;

CREATE DATABASE IF NOT EXISTS demo;

-- SELECT DATABASE
USE demo;


-- #TABLE bidlist
CREATE TABLE IF NOT EXISTS `bidlist`
(
    `bid_list_id`      BIGINT NOT NULL AUTO_INCREMENT,
    `account`          VARCHAR(255),
    `ask`              DOUBLE,
    `ask_quantity`     DOUBLE,
    `benchmark`        VARCHAR(255),
    `bid`              DOUBLE,
    `bid_list_date`    DATETIME,
    `bid_quantity`     DOUBLE,
    `book`             VARCHAR(255),
    `commentary`       VARCHAR(255),
    `creation_date`    DATETIME,
    `creation_name`    VARCHAR(255),
    `deal_name`        VARCHAR(255),
    `deal_type`        VARCHAR(255),
    `revision_date`    DATETIME,
    `revision_name`    VARCHAR(255),
    `security`         VARCHAR(255),
    `side`             VARCHAR(255),
    `source_list_id`   VARCHAR(255),
    `status`           VARCHAR(255),
    `trader`           VARCHAR(255),
    `type`             VARCHAR(255),
    PRIMARY KEY (bid_list_id)
    ) ENGINE = InnoDB;

-- #TABLE curvepoint
CREATE TABLE IF NOT EXISTS `curvepoint`
(
    `id`            BIGINT NOT NULL AUTO_INCREMENT,
    `as_of_date`    DATETIME,
    `creation_date` DATETIME,
    `curve_id`      BIGINT,
    `term`          DOUBLE,
    `value`         DOUBLE,
    PRIMARY KEY (id)
    ) ENGINE = InnoDB;

-- #TABLE rating
CREATE TABLE IF NOT EXISTS `rating`
(
    `id`            BIGINT NOT NULL AUTO_INCREMENT,
    `fitch_rating`  VARCHAR(255),
    `moodys_rating` VARCHAR(255),
    `order_number`  BIGINT,
    `sandp_rating`   VARCHAR(255),
    PRIMARY KEY (id)
    ) ENGINE = InnoDB;

-- #TABLE rulename
CREATE TABLE IF NOT EXISTS `rulename`
(
    `id`            BIGINT NOT NULL AUTO_INCREMENT,
    `description`   VARCHAR(255),
    `json`          VARCHAR(255),
    `name`          VARCHAR(255),
    `sql_part`      VARCHAR(255),
    `sql_str`       VARCHAR(255),
    `template`      VARCHAR(255),
    PRIMARY KEY (id)
    ) ENGINE = InnoDB;

-- #TABLE trade
CREATE TABLE IF NOT EXISTS `trade`
(
    `trade_id`            BIGINT NOT NULL AUTO_INCREMENT,
    `account`   VARCHAR(255),
    `benchmark`          VARCHAR(255),
    `book`          VARCHAR(255),
    `buy_price`         DOUBLE,
    `buy_quantity`         DOUBLE,
    `sql_part`      VARCHAR(255),
    `sql_str`       VARCHAR(255),
    `template`      VARCHAR(255),
    `creation_date` DATETIME,
    `creation_name`      VARCHAR(255),
    `deal_name`       VARCHAR(255),
    `deal_type`      VARCHAR(255),
    `revision_date` DATETIME,
    `revision_name`      VARCHAR(255),
    `security`       VARCHAR(255),
    `sell_price`         DOUBLE,
    `sell_quantity`         DOUBLE,
    `side`      VARCHAR(255),
    `source_list_id`       VARCHAR(255),
    `status`      VARCHAR(255),
    `trade_date` DATETIME,
    `trader`       VARCHAR(255),
    `type`      VARCHAR(255),
    PRIMARY KEY (trade_id)
    ) ENGINE = InnoDB;

-- #TABLE users
CREATE TABLE IF NOT EXISTS `users`
(
    `id`            BIGINT NOT NULL AUTO_INCREMENT,
    `fullname`      VARCHAR(255),
    `password`      VARCHAR(255),
    `role`          VARCHAR(255),
    `username`      VARCHAR(255),
    PRIMARY KEY(id)
    ) ENGINE = InnoDB;