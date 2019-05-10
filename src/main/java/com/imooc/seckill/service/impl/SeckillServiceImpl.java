package com.imooc.seckill.service.impl;

import com.imooc.seckill.dto.Exposer;
import com.imooc.seckill.dto.SeckillExecution;
import com.imooc.seckill.entity.SecKill;
import com.imooc.seckill.entity.SuccessKilled;
import com.imooc.seckill.enums.SeckillStateEnum;
import com.imooc.seckill.exception.SeckillException;
import com.imooc.seckill.manager.RedisManager;
import com.imooc.seckill.repository.SecKillRepository;
import com.imooc.seckill.repository.SuccessKilledRepository;
import com.imooc.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author : wang zns
 * @date : 2019-05-07
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    /**
     * md5盐值
     */
    private static final String SALT = "dasdsad131dad21";

    @Autowired
    private RedisManager redisManager;
    @Autowired
    private SecKillRepository secKillRepository;
    @Autowired
    private SuccessKilledRepository successKilledRepository;


    @Override
    public List<SecKill> getSeckillList() {
        return secKillRepository.findAll();
    }

    @Override
    public SecKill getById(Long seckillId) {
        return secKillRepository.findById(seckillId).orElse(null);
    }

    /**
     * 暴露秒杀接口
     * @param seckillId
     * @return
     */
    @Override
    public Exposer exportSeckillUrl(Long seckillId) {
        // 将秒杀信息放入redis中， 减少数据库压力
        SecKill secKill = redisManager.getSeckill(seckillId);
        if (secKill == null) {
            // 从数据库中获取
            Optional<SecKill> secKillOptional = secKillRepository.findById(seckillId);
            secKill = secKillOptional.get();
            redisManager.putSeckill(secKill);
        }
        long nowTime = System.currentTimeMillis();
        // 若秒杀未开始或秒杀已结束, 则暴露为不可用状态
        if (nowTime < secKill.getStartTime().getTime() || nowTime > secKill.getEndTime().getTime()) {
            return Exposer.builder().exposed(false).now(nowTime).start(secKill.getStartTime().getTime())
                    .end(secKill.getEndTime().getTime()).seckillId(secKill.getSeckillId()).build();
        }
        // 秒杀可用时
        return Exposer.builder().exposed(true).now(nowTime).start(secKill.getStartTime().getTime())
                .end(secKill.getEndTime().getTime()).seckillId(secKill.getSeckillId())
                .md5(md5Operate(secKill.getSeckillId()))
                .build();

    }

    /**
     * 执行秒杀
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     */
    @Override
    @Transactional
    public SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5) throws SeckillException {

        // 判断md5值
        String realMd5Value = md5Operate(seckillId);
        if (!md5.equals(realMd5Value)) {
            throw new SeckillException("秒杀失败，涉嫌数据被修改");
        }
        Optional<SecKill> secKillOptional = secKillRepository.findById(seckillId);
        if (!secKillOptional.isPresent()) {
            throw new SeckillException("该商品秒杀不存在");
        }
        // 判断该用户是否已经秒杀过该商品，避免同一用户重复秒杀
        SuccessKilled killRecord = successKilledRepository.findBySeckillIdAndUserPhone(seckillId, userPhone);
        if (killRecord != null) {
            throw new SeckillException("请勿重复秒杀");
        }
        // 减库存
        SecKill secKill = secKillOptional.get();
        if (secKill.getNumber() <= 0) {
            throw new SeckillException("已被全部抢光了~");
        }
        secKill.setNumber(secKill.getNumber() - 1);
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

    /**
     * md5 加盐处理
     * @param seckillId
     * @return
     */
    private String md5Operate(Long seckillId) {
        String beforeMd5 = seckillId + "#" + SALT;
        return DigestUtils.md5DigestAsHex(beforeMd5.getBytes());
    }





}
