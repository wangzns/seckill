package com.imooc.seckill.dto;

import com.imooc.seckill.entity.SuccessKilled;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装秒杀执行结果
 * @author : wang zns
 * @date : 2019-05-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeckillExecution {

    private Long seckillId;
    /**
     * 秒杀后的状态码
     */
    private int state;
    /**
     * 秒杀后的状态信息
     */
    private String stateInfo;

    private SuccessKilled successKilled;

}
