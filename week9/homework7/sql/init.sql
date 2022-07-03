create database if not exists db_a DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
create database if not exists db_b DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;

create table if not exists db_a.t_account(
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL default current_timestamp,
  `update_time` datetime NOT NULL default current_timestamp on update current_timestamp,
  `user_code` varchar(20) not null,
  `amount` decimal(12,4) not null,
  `frozen` decimal(12,4) not null,
  `currency_type` varchar(10) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

insert db_a.t_account(user_code,amount,frozen,currency_type)values
('a',1,0,'usd'),
('a',0,0,'cny');

create table if not exists db_b.t_account(
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL default current_timestamp,
  `update_time` datetime NOT NULL default current_timestamp on update current_timestamp,
  `user_code` varchar(20) not null,
  `amount` decimal(12,4) not null,
  `frozen` decimal(12,4) not null,
  `currency_type` varchar(10) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

insert db_b.t_account(user_code,amount,frozen,currency_type)values
('b',0,0,'usd'),
('b',7,0,'cny');