package com.imooc.seckill.repository;

import com.imooc.seckill.entity.SuccessKilled;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SuccessKilledRepositoryTest {

    private static final Long secKillId = 1000L;

    private static final Long userPhone = 1234564564L;


    @Autowired
    private SuccessKilledRepository successKilledRepository;




    @Test
    public void testSaveSuccessKilled() {
        SuccessKilled successKilled = new SuccessKilled();
        successKilled.setSeckillId(secKillId);
        successKilled.setUserPhone(userPhone);
        successKilled.setState(0);
        SuccessKilled result = successKilledRepository.save(successKilled);
        log.info("result -> {}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void testFindBySecKillIdAndUserPhone() {
        SuccessKilled result = successKilledRepository.findBySeckillIdAndUserPhone(secKillId, userPhone);
        log.info("【testFindBySecKillIdAndUserPhone】 result -> {}", result);
        Assert.assertNotNull(result);
    }



}