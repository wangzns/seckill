package com.imooc.seckill.manager;

import com.alibaba.fastjson.JSON;
import com.imooc.seckill.common.RedisConstants;
import com.imooc.seckill.entity.SecKill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;


/**
 * @author : wang zns
 * @date : 2019-05-10
 */
@Component
public class RedisManager {


    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 存入seckill
     * @param secKill
     */
    public void putSeckill(SecKill secKill) {
        redisTemplate.opsForValue().set(RedisConstants.SECKILL_PREFIX +secKill.getSeckillId(),
                JSON.toJSONString(secKill), RedisConstants.SECKILL_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    /**
     * 取到seckill
     * @param secKillid
     * @return
     */
    public SecKill getSeckill(Long secKillid) {
        String secString = (String) redisTemplate.opsForValue().get(RedisConstants.SECKILL_PREFIX + secKillid);
        return JSON.parseObject(secString, SecKill.class);
    }

}
