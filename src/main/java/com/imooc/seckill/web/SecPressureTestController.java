package com.imooc.seckill.web;

import com.imooc.seckill.common.RedisConstants;
import com.imooc.seckill.dto.SeckillExecution;
import com.imooc.seckill.exception.SeckillException;
import com.imooc.seckill.service.RedisLock;
import com.imooc.seckill.service.SecPressureTestService;
import com.imooc.seckill.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 *  用于秒杀业务压力测试
 *  例如使用ab进行压测时
 *     输入 .\ab.exe -n 50 -c 30 127.0.0.1/kill/1000/execution
 *     -n 总请求数
 *     -c 并发数(线程数)
 * @author : wang zns
 * @date : 2019-05-08
 */
@Controller
@RequestMapping("/kill")
@Slf4j
public class SecPressureTestController {

    @Autowired
    private SecPressureTestService secPressureTestService;

    @Autowired
    private RedisLock redisLock;


    @RequestMapping("/{seckillId}/execution")
    @ResponseBody
    public Result<SeckillExecution> execute(@PathVariable(value = "seckillId",required = true) Long seckillId) {
        long currentTimeMills = System.currentTimeMillis();
        String redisLockValue = String.valueOf(currentTimeMills + RedisConstants.LOCK_EXPIRE_TIME);
        final boolean lock = redisLock.lock(String.valueOf(seckillId), redisLockValue);
        if (!lock) {
            throw new RuntimeException("人数过多，请稍后再试");
        }
        try {
            SeckillExecution execution = secPressureTestService.executeSeckill(seckillId);
            return Result.success(execution);
        } catch (Exception e) {
            log.error("【执行秒杀错误】,message={}", e.getMessage());
            return Result.error(e.getMessage());
        } finally {
            redisLock.unlock(String.valueOf(seckillId), redisLockValue);
        }

    }
}
