package com.imooc.seckill.common;

/**
 * @author : wang zns
 * @date : 2019-05-10
 */
public class RedisConstants {
    /**
     * redis key prefix
     */
    public static final String SECKILL_PREFIX = "seckill:";

    /**
     * redis key expireTime
     */
    public static final Long SECKILL_EXPIRE_TIME = 3600L;

    public static final Long LOCK_EXPIRE_TIME = 10 * 1000L;
}
