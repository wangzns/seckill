package com.imooc.seckill.service;

import com.imooc.seckill.dto.Exposer;
import com.imooc.seckill.dto.SeckillExecution;
import com.imooc.seckill.entity.SecKill;
import com.imooc.seckill.exception.SeckillException;

import java.util.List;

/**
 * 秒杀业务逻辑层
 * @author : wang zns
 * @date : 2019-05-07
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     * @return
     */
    List<SecKill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    SecKill getById(Long seckillId);


    /**
     * 秒杀开启则输出秒杀接口地址，
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(Long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5) throws SeckillException;



}
