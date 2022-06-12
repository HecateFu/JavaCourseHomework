-- 用户
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `user_code` varchar(30) NOT NULL COMMENT '用户编号',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(200) DEFAULT NULL COMMENT '密码',
  `phone` char(11) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像图片路径',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_code_UNIQUE` (`user_code`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`),
  UNIQUE KEY `phone_UNIQUE` (`phone`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 商品
CREATE TABLE `product` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_name` varchar(50) NOT NULL COMMENT '商品名称',
  `sku` varchar(50) NOT NULL COMMENT '商品编号',
  `price` decimal(8,2) NOT NULL COMMENT '商品价格',
  `brand` varchar(50) DEFAULT NULL COMMENT '品牌',
  `model` varchar(45) DEFAULT NULL COMMENT '型号',
  `dimension` varchar(45) DEFAULT NULL COMMENT '规格',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `productcol_UNIQUE` (`sku`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';


-- 订单
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL,
  `order_code` varchar(30) NOT NULL COMMENT '订单编号',
  `order_status` char(2) NOT NULL COMMENT '订单状态',
  `amount` decimal(10,2) NOT NULL COMMENT '订单商品总金额',
  `post_fee` decimal(10,2) NOT NULL COMMENT '订单运费金额',
  `payment` decimal(10,2) NOT NULL COMMENT '订单实付金额',
  `order_user` varchar(30) NOT NULL COMMENT '下单用户，user.user_code',
  `receiver_addr` varchar(200) DEFAULT NULL COMMENT '收货人地址',
  `receiver_phone` varchar(45) DEFAULT NULL COMMENT '收货人电话',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `pay_code` varchar(45) DEFAULT NULL COMMENT '支付单号',
  `post_time` datetime DEFAULT NULL COMMENT '发货时间',
  `exchange_code` varchar(45) DEFAULT NULL COMMENT '快递单号',
  `received_time` datetime DEFAULT NULL COMMENT '签收时间',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_code_UNIQUE` (`order_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

CREATE TABLE `order_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_code` varchar(30) DEFAULT NULL COMMENT '订单编号，order.order_code',
  `product_sku` varchar(30) NOT NULL COMMENT '订单商品编号，product.sku',
  `product_num` int(10) unsigned NOT NULL COMMENT '订单商品数量',
  `product_price` decimal(10,2) NOT NULL COMMENT '订单商品价格',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单商品明细表';

