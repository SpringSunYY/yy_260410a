package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
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
}
