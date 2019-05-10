package com.imooc.seckill.enums;

import lombok.Getter;

/**
 * 秒杀状态
 *
 * @author : wang zns
 * @date : 2019-05-07
 */
@Getter
public enum SeckillStateEnum {
    INVALID(-1, "无效"),
    SUCCESS(0, "秒杀成功"),
    HAS_PAYED(1, "已付款");

    private Integer state;
    private String message;

    SeckillStateEnum(Integer state, String message) {
        this.state = state;
        this.message = message;
    }

    public static  SeckillStateEnum getSecKillEnumByState(Integer code) {
        for (SeckillStateEnum stateEnum : SeckillStateEnum.values()) {
            if (stateEnum.getState().equals(code)) {
                return stateEnum;
            }
        }
        return null;
    }


}
