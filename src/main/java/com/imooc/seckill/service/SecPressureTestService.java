package com.imooc.seckill.service;

import com.imooc.seckill.dto.Exposer;
import com.imooc.seckill.dto.SeckillExecution;
import com.imooc.seckill.exception.SeckillException;

/**
 * 用于压力测试
 * @author : wang zns
 * @date : 2019-05-08
 */
public interface SecPressureTestService {

    /**
     * 执行秒杀操作
     * @param seckillId
     * @return
     */
    SeckillExecution executeSeckill(Long seckillId) throws SeckillException;

}
