package com.imooc.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 秒杀信息表
 * @author : wang zns
 * @date : 2019-05-07
 */
@Table(name = "seckill")
@Data
@Entity
@DynamicUpdate // 使mysql的on update生效，即update_time不会因为值相同就不更新update_time
@NoArgsConstructor
@AllArgsConstructor
public class SecKill {

    /**
     * 秒杀id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seckillId;
    /**
     * 秒杀商品名称
     */
    private String name;
    /**
     * 商品剩余库存
     */
    private Integer number;
    /**
     * 秒杀开始时间
     */
    private Date startTime;
    /**
     * 秒杀结束时间
     */
    private Date endTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;


}
