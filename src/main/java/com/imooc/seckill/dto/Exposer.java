package com.imooc.seckill.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 暴露秒杀地址 dto
 * @author : wang zns
 * @date : 2019-05-07
 */
@Data
@Builder
public class Exposer {

    /**
     * 秒杀是否开启
     */
    private Boolean exposed;

    private String md5;

    private Long seckillId;
    /**
     * 当前系统时间
     */
    private Long now;
    /**
     * 开始时间
     */
    private Long start;
    /**
     * 结束时间
     */
    private Long end;




}
