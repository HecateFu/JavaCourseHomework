create database if not exists db_order DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;

use db_order;

create table if not exists t_order (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `number` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `status` tinyint(4) NOT NULL,
  `product_id` varchar(128) NOT NULL,
  `total_amount` decimal(10,0) NOT NULL,
  `count` int(4) NOT NULL,
  `user_id` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;



create database if not exists db_stock DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;

use db_stock;

CREATE TABLE `t_inventory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` VARCHAR(128) NOT NULL,
  `total_inventory` int(10) NOT NULL COMMENT '总库存',
  `lock_inventory` int(10) NOT NULL COMMENT '锁定库存',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

insert into db_stock.t_inventory(product_id,total_inventory,lock_inventory)value('123',100,0);
