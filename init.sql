create database seckill;

use database seckill;

-- 秒杀库存表 --
create table  seckill(
  `seckill_id`  bigint not null auto_increment comment '商品库存id',
  `name` varchar(120) not null comment '商品名称',
  `number` int not null comment '库存数量',
  `start_time` timestamp not null comment '秒杀开始时间',
  `end_time` timestamp not null comment '秒杀结束时间',
  `create_time` timestamp  not null default current_timestamp comment '创建时间',
  `update_time` timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
  primary key (`seckill_id`),
  key `idx_start_time`(`start_time`),
  key `idx_end_time`(`end_time`),
  key `idx_create_time`(`create_time`)
) ENGINE=InnoDB auto_increment=1000 default charset=UTF8 comment '秒杀库存表';

insert into seckill(name,number,start_time, end_time) values ('iphone xs限时500元抢', 500,'2019-05-07 15:05:00', '2019-05-07 15:06:00');
insert into seckill(name,number,start_time, end_time) values ('小米6限时10元抢', 500,'2019-05-07 15:05:00', '2019-05-07 15:06:00');
insert into seckill(name,number,start_time, end_time) values ('动感耳机限时1元抢', 500,'2019-05-07 15:05:00', '2019-05-07 15:06:00');
insert into seckill(name,number,start_time, end_time) values ('红米note限时5元抢', 500,'2019-05-07 15:05:00', '2019-05-07 15:06:00');

-- 秒杀明细表 --
create table success_killed(
  `id` bigint not null auto_increment comment '主键',
  `seckill_id` bigint not null comment '商品库存id',
  `user_phone` bigint not null comment '用户手机号',
  `state` tinyint not null default -1 comment '状态标识 -1：无效 0：成功 1：已付款',
  `create_time` timestamp  not null default current_timestamp comment '创建时间',
  `update_time` timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
  primary key(`id`),
  unique key `uk_secid_id`(`seckill_id`,`user_phone`)
  key `idx_create_time`(`create_time`)
) ENGINE=InnoDB auto_increment=1000 default charset=UTF8 comment '秒杀明细表';


