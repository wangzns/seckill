package com.imooc.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 秒杀明细表
 * @author : wang zns
 * @date : 2019-05-07
 */
@Table(name = "success_killed")
@Data
@Entity
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessKilled {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 秒杀id
     */

    private Long seckillId;
    /**
     * 用户电话
     */
    private Long userPhone;
    /**
     * 状态
     */
    private Integer state;

    private Date createTime;

    private Date updateTime;



}

