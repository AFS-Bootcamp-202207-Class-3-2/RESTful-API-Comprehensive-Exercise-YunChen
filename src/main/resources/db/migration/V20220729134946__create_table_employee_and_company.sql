
CREATE TABLE IF NOT EXISTS `company` (
    `id` varchar(50) NOT NULL DEFAULT '0',
    `create_time` datetime(6) DEFAULT NULL,
    `update_time` datetime(6) DEFAULT NULL,
    `company_name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


CREATE TABLE IF NOT EXISTS `employee` (
    `id` varchar(50) NOT NULL DEFAULT '"0"',
    `age` int DEFAULT NULL,
    `salary` int DEFAULT NULL,
    `gender` varchar(255) DEFAULT NULL,
    `create_time` datetime(6) DEFAULT NULL,
    `update_time` datetime(6) DEFAULT NULL,
    `company_name` varchar(255) DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    `company_id` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK5v50ed2bjh60n1gc7ifuxmgf4` (`company_id`),
    CONSTRAINT `FK5v50ed2bjh60n1gc7ifuxmgf4` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
