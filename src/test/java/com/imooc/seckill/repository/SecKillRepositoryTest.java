package com.imooc.seckill.repository;

import com.imooc.seckill.SeckillApplication;
import com.imooc.seckill.entity.SecKill;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeckillApplication.class)
@Slf4j
public class SecKillRepositoryTest {

    @Autowired
    private SecKillRepository secKillRepository;

    @Test
    public void testFindById() {
        Optional<SecKill> optional = secKillRepository.findById(1000L);
        Assert.assertNotNull(optional.get());
        log.info("seckill -> {}", optional.get());
    }

    @Test
    public void testFindAll() {
        Pageable pageable = PageRequest.of(0, 100);
        Page<SecKill> secList = secKillRepository.findAll(pageable);
        List<SecKill> content = secList.getContent();
        Assert.assertNotEquals(0, content.size());
    }

    @Test
    public void testReduceNumber() {
        Optional<SecKill> optional = secKillRepository.findById(1000L);
        SecKill kill = optional.get();
        if (kill.getNumber() > 0) {
            kill.setNumber(kill.getNumber() - 1);
            secKillRepository.save(kill);
        }

    }




}