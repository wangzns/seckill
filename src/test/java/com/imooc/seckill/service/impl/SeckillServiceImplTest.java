package com.imooc.seckill.service.impl;

import com.imooc.seckill.dto.Exposer;
import com.imooc.seckill.dto.SeckillExecution;
import com.imooc.seckill.entity.SecKill;
import com.imooc.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SeckillServiceImplTest {

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<SecKill> seckillList = seckillService.getSeckillList();
        Assert.assertNotEquals(0, seckillList.size());
    }

    @Test
    public void getById() {
        SecKill kill = seckillService.getById(1000L);
        Assert.assertNotNull(kill);
    }

    @Test
    public void exportSeckillUrl() {
        Exposer exposer = seckillService.exportSeckillUrl(1000L);
        log.info("【exportSeckillUrl】 method, exposer -> {}", exposer);
        if (exposer.getExposed()) {
            SeckillExecution seckillExecution = seckillService.executeSeckill(1000L, 1234564564L, exposer.getMd5());
            log.info("【executeSeckill】 method, seckillExecution -> {}", seckillExecution);
        } else {
            log.info("不在秒杀时间范围内");
        }

    }

}