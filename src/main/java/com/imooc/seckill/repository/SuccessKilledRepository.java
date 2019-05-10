package com.imooc.seckill.repository;

import com.imooc.seckill.entity.SuccessKilled;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : wang zns
 * @date : 2019-05-07
 */
public interface SuccessKilledRepository extends JpaRepository<SuccessKilled, Long> {

    /**
     * 根据秒杀id和用户手机号进行查询
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKilled findBySeckillIdAndUserPhone(Long seckillId, Long userPhone);


}
