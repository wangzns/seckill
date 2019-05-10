package com.imooc.seckill.service.impl;

import com.imooc.seckill.common.RedisConstants;
import com.imooc.seckill.dto.Exposer;
import com.imooc.seckill.dto.SeckillExecution;
import com.imooc.seckill.entity.SecKill;
import com.imooc.seckill.entity.SuccessKilled;
import com.imooc.seckill.enums.SeckillStateEnum;
import com.imooc.seckill.exception.SeckillException;
import com.imooc.seckill.manager.RedisManager;
import com.imooc.seckill.repository.SecKillRepository;
import com.imooc.seckill.repository.SuccessKilledRepository;
import com.imooc.seckill.service.RedisLock;
import com.imooc.seckill.service.SecPressureTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 压力测试
 *
 * @author : wang zns
 * @date : 2019-05-08
 */
@Service
@Slf4j
public class SecPressureTestServiceImpl implements SecPressureTestService {


    @Autowired
    private SecKillRepository secKillRepository;
    @Autowired
    private SuccessKilledRepository successKilledRepository;


    /**
     * 执行秒杀(压测接口)
     *
     * @param seckillId
     * @return
     * @throws SeckillException
     */
    @Override
    @Transactional
    public SeckillExecution executeSeckill(Long seckillId) throws SeckillException {

        Optional<SecKill> secKillOptional = secKillRepository.findById(seckillId);
        if (!secKillOptional.isPresent()) {
            throw new SeckillException("该商品秒杀不存在");
        }
        // 判断该用户是否已经秒杀过该商品，避免同一用户重复秒杀
        int phone = (int) (Math.random() * 1000000000);
        Long userPhone = Long.valueOf(phone);
        SuccessKilled killRecord = successKilledRepository.findBySeckillIdAndUserPhone(seckillId, userPhone);
        if (killRecord != null) {
            throw new SeckillException("请勿重复秒杀");
        }
        SecKill secKill = secKillOptional.get();

        if (secKill.getNumber() <= 0) {
            throw new SeckillException("已被全部抢光了~");
        }
        // 减库存
        final int currentNumber = secKill.getNumber();
        log.info("改变库存 ，当前库存 = {}", currentNumber);
        secKill.setNumber(currentNumber - 1);
        secKillRepository.save(secKill);

        // 记录秒杀记录
        SuccessKilled successKilled = SuccessKilled.builder().seckillId(seckillId)
                .userPhone(userPhone).state(SeckillStateEnum.SUCCESS.getState()).build();
        SuccessKilled result = successKilledRepository.save(successKilled);
        SeckillExecution seckillExecution = SeckillExecution.builder().seckillId(seckillId)
                .state(result.getState())
                .stateInfo(SeckillStateEnum.getSecKillEnumByState(result.getState()).getMessage())
                .successKilled(successKilled).build();
        return seckillExecution;
    }


}
