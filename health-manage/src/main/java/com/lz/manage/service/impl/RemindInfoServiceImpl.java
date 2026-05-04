package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.exception.ServiceException;
import com.lz.manage.enums.HealthReadStatusEnum;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.RemindInfoMapper;
import com.lz.manage.model.domain.RemindInfo;
import com.lz.manage.service.IRemindInfoService;
import com.lz.manage.model.dto.remindInfo.RemindInfoQuery;
import com.lz.manage.model.vo.remindInfo.RemindInfoVo;
import com.lz.manage.service.IRemindConfigInfoService;
import com.lz.manage.model.domain.RemindConfigInfo;

/**
 * 提醒记录Service业务层处理
 *
 * @author YY
 * @date 2026-05-04
 */
@Service
public class RemindInfoServiceImpl extends ServiceImpl<RemindInfoMapper, RemindInfo> implements IRemindInfoService
{

    @Resource
    private RemindInfoMapper remindInfoMapper;

    @Resource
    private IRemindConfigInfoService remindConfigInfoService;

    //region mybatis代码
    /**
     * 查询提醒记录
     *
     * @param id 提醒记录主键
     * @return 提醒记录
     */
    @Override
    public RemindInfo selectRemindInfoById(Long id)
    {
        return remindInfoMapper.selectRemindInfoById(id);
    }

    /**
     * 查询提醒记录列表
     *
     * @param remindInfo 提醒记录
     * @return 提醒记录
     */
    @Override
    public List<RemindInfo> selectRemindInfoList(RemindInfo remindInfo)
    {
        return remindInfoMapper.selectRemindInfoList(remindInfo);
    }

    /**
     * 新增提醒记录
     *
     * @param remindInfo 提醒记录
     * @return 结果
     */
    @Override
    public int insertRemindInfo(RemindInfo remindInfo)
    {
        remindInfo.setCreateTime(DateUtils.getNowDate());
        return remindInfoMapper.insertRemindInfo(remindInfo);
    }

    /**
     * 修改提醒记录
     *
     * @param remindInfo 提醒记录
     * @return 结果
     */
    @Override
    public int updateRemindInfo(RemindInfo remindInfo)
    {
        remindInfo.setUpdateTime(DateUtils.getNowDate());
        return remindInfoMapper.updateRemindInfo(remindInfo);
    }

    /**
     * 批量删除提醒记录
     *
     * @param ids 需要删除的提醒记录主键
     * @return 结果
     */
    @Override
    public int deleteRemindInfoByIds(Long[] ids)
    {
        return remindInfoMapper.deleteRemindInfoByIds(ids);
    }

    /**
     * 删除提醒记录信息
     *
     * @param id 提醒记录主键
     * @return 结果
     */
    @Override
    public int deleteRemindInfoById(Long id)
    {
        return remindInfoMapper.deleteRemindInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<RemindInfo> getQueryWrapper(RemindInfoQuery remindInfoQuery){
        QueryWrapper<RemindInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = remindInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = remindInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long reminderId = remindInfoQuery.getReminderId();
        queryWrapper.eq( StringUtils.isNotNull(reminderId),"reminder_id",reminderId);

        Long residentId = remindInfoQuery.getResidentId();
        queryWrapper.eq( StringUtils.isNotNull(residentId),"resident_id",residentId);

        String reminderType = remindInfoQuery.getReminderType();
        queryWrapper.eq(StringUtils.isNotEmpty(reminderType) ,"reminder_type",reminderType);

        String reminderTitle = remindInfoQuery.getReminderTitle();
        queryWrapper.eq(StringUtils.isNotEmpty(reminderTitle) ,"reminder_title",reminderTitle);

        String readStatus = remindInfoQuery.getReadStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(readStatus) ,"read_status",readStatus);

        Date readTime = remindInfoQuery.getReadTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginReadTime"))&&StringUtils.isNotNull(params.get("endReadTime")),"read_time",params.get("beginReadTime"),params.get("endReadTime"));

        Long userId = remindInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        String createBy = remindInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = remindInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<RemindInfoVo> convertVoList(List<RemindInfo> remindInfoList) {
        if (StringUtils.isEmpty(remindInfoList)) {
            return Collections.emptyList();
        }
        return remindInfoList.stream().map(RemindInfoVo::objToVo).collect(Collectors.toList());
    }

    @Override
    public void autoSendRemind() {
        Date currentTime = DateUtils.getNowDate();

        // 1. 查询当前时间需要发送的提醒配置（精确到分钟）
        List<RemindConfigInfo> configsToSend = remindConfigInfoService.lambdaQuery()
                .isNotNull(RemindConfigInfo::getNextRemindTime)
                .apply("DATE_FORMAT(next_remind_time, '%Y-%m-%d %H:%i') = DATE_FORMAT({0}, '%Y-%m-%d %H:%i')", currentTime)
                .le(RemindConfigInfo::getStartTime, currentTime)
                .and(w -> w.isNull(RemindConfigInfo::getEndTime).or().ge(RemindConfigInfo::getEndTime, currentTime))
                .list();

        if (configsToSend == null || configsToSend.isEmpty()) {
            return;
        }

        for (RemindConfigInfo config : configsToSend) {
            // 2. 创建提醒记录
            RemindInfo remindInfo = new RemindInfo();
            remindInfo.setReminderId(config.getId());
            remindInfo.setResidentId(config.getResidentId());
            remindInfo.setReminderType(config.getRemindType());
            remindInfo.setReminderTitle(config.getRemindTitle());
            remindInfo.setReminderContent(config.getRemindContent());
            remindInfo.setRemindTime(currentTime);
            remindInfo.setReadStatus(HealthReadStatusEnum.HEALTH_READ_STATUS_0.getValue());
            remindInfo.setUserId(config.getUserId());
            remindInfo.setCreateBy(config.getCreateBy());
            remindInfoMapper.insertRemindInfo(remindInfo);

            // 3. 更新配置的提醒时间
            updateNextRemindTime(config, currentTime);
        }
    }

    /**
     * 更新下一次提醒时间
     * 规则：
     * 1. 如果是一天一次：下次提醒时间为第二天同一时间
     * 2. 如果是一天多次：上次提醒时间 + 间隔分钟，但最后一次不能超过当天
     */
    private void updateNextRemindTime(RemindConfigInfo config, Date currentRemindTime) {
        Long remindFrequency = config.getRemindFrequency();
        Long remindInterval = config.getRemindInterval();

        LocalDateTime nowDt = currentRemindTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDate today = nowDt.toLocalDate();
        LocalTime baseTime = config.getRemindTime();

        Date newNextRemindTime;

        if (remindFrequency == null || remindFrequency <= 1) {
            // 一天一次：第二天同一时间
            newNextRemindTime = DateUtils.toDate(today.plusDays(1).atTime(baseTime));
        } else {
            // 一天多次
            // 计算今天已发送的次数（从第一次提醒时间开始，到当前时间）
            LocalDateTime firstToday = LocalDateTime.of(today, baseTime);
            long sentCount = 0;
            if (nowDt.isAfter(firstToday.minusMinutes(1))) {
                sentCount = 1 + (java.time.Duration.between(firstToday, nowDt).toMinutes() / remindInterval);
            }

            if (sentCount >= remindFrequency) {
                // 今天次数已用完，更新为第二天第一次提醒时间
                newNextRemindTime = DateUtils.toDate(today.plusDays(1).atTime(baseTime));
            } else {
                // 还有次数，更新为当前时间 + 间隔分钟
                LocalDateTime nextDt = nowDt.plusMinutes(remindInterval);
                // 如果计算出的下次时间已经超过当天最后一次提醒时间，则更新为第二天第一次
                LocalTime lastTime = baseTime.plusMinutes((remindFrequency - 1) * remindInterval);
                if (nextDt.toLocalTime().isAfter(lastTime)) {
                    newNextRemindTime = DateUtils.toDate(today.plusDays(1).atTime(baseTime));
                } else {
                    newNextRemindTime = DateUtils.toDate(nextDt);
                }
            }
        }

        // 更新配置
        config.setLastRemindTime(currentRemindTime);
        config.setNextRemindTime(newNextRemindTime);
        config.setUpdateTime(DateUtils.getNowDate());
        remindConfigInfoService.updateById(config);
    }
}
