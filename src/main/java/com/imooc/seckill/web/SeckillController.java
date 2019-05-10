package com.imooc.seckill.web;

import com.imooc.seckill.dto.Exposer;
import com.imooc.seckill.dto.SeckillExecution;
import com.imooc.seckill.entity.SecKill;
import com.imooc.seckill.exception.SeckillException;
import com.imooc.seckill.service.SeckillService;
import com.imooc.seckill.service.SpecialService.TimerService;
import com.imooc.seckill.vo.Result;
import com.imooc.seckill.vo.SecKillVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.TimerTask;

/**
 * @author : wang zns
 * @date : 2019-05-08
 */
@Controller
@RequestMapping("/seckill")
@Slf4j
public class SeckillController {

    @Autowired
    private SeckillService seckillService;
    @Autowired
    private TimerService timerService;

    /**
     * 列表页
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Model model) {
        List<SecKill> seckillList = seckillService.getSeckillList();
        model.addAttribute("list", seckillList);
        return "list";
    }

    /**
     * 详情页
     * @param seckillId
     * @param model
     * @return
     */
    @GetMapping("/{seckillId}/detail")
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        SecKill kill = seckillService.getById(seckillId);
        if (kill == null) {
            return "forward:/seckill/list";
        }
        SecKillVO secKillVO = new SecKillVO();
        BeanUtils.copyProperties(kill,secKillVO);
        secKillVO.setStartTimestamp(kill.getStartTime().getTime());
        secKillVO.setEndTimestamp(kill.getEndTime().getTime());
        model.addAttribute("seckill", secKillVO);
        // 只要秒杀未结束，则启动定时任务
        if (secKillVO.getStartTimestamp() < secKillVO.getEndTimestamp()
                && System.currentTimeMillis() < secKillVO.getEndTimestamp()) {
            timerService.executeTimeCountdown(secKillVO.getStartTimestamp(), secKillVO.getEndTimestamp());
        }
        return "detail";
    }

    @GetMapping(value = "/{seckillId}/exposer")
    @ResponseBody
    public Result<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            return Result.success(exposer);
        } catch (SeckillException e) {
            log.error("【暴露秒杀接口】失败，seckillId={}", seckillId);
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/{seckillId}/{md5}/execution")
    @ResponseBody
    public Result<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                            @PathVariable("md5") String md5,
                                            @CookieValue("killPhone") Long userPhone) {
        if (userPhone == null) {
            return Result.error("未知用户");
        }
        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5);
            return Result.success(execution);
        } catch (SeckillException e) {
            log.error("【秒杀】 失败，{} ", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取系统时间 long
     * @return
     */
    @RequestMapping("/time/now")
    @ResponseBody
    public Result<Long> time() {
        return Result.success(System.currentTimeMillis());
    }







}
