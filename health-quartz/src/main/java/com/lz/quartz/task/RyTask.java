package com.lz.quartz.task;

import com.lz.manage.service.IRemindConfigInfoService;
import com.lz.manage.service.IRemindInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import com.lz.common.utils.StringUtils;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{

    @Resource
    private IRemindInfoService remindInfoService;
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }

    public void autoSendRemind() {
        System.out.println("开始执行自动发送提醒任务");
        remindInfoService.autoSendRemind();
    }
}
