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
import com.lz.manage.mapper.RemindConfigInfoMapper;
import com.lz.manage.model.domain.RemindConfigInfo;
import com.lz.manage.service.IRemindConfigInfoService;
import com.lz.manage.model.dto.remindConfigInfo.RemindConfigInfoQuery;
import com.lz.manage.model.vo.remindConfigInfo.RemindConfigInfoVo;

/**
 * 提醒配置Service业务层处理
 *
 * @author YY
 * @date 2026-05-04
 */
@Service
public class RemindConfigInfoServiceImpl extends ServiceImpl<RemindConfigInfoMapper, RemindConfigInfo> implements IRemindConfigInfoService
{

    @Resource
    private RemindConfigInfoMapper remindConfigInfoMapper;

    //region mybatis代码
    /**
     * 查询提醒配置
     *
     * @param id 提醒配置主键
     * @return 提醒配置
     */
    @Override
    public RemindConfigInfo selectRemindConfigInfoById(Long id)
    {
        return remindConfigInfoMapper.selectRemindConfigInfoById(id);
    }

    /**
     * 查询提醒配置列表
     *
     * @param remindConfigInfo 提醒配置
     * @return 提醒配置
     */
    @Override
    public List<RemindConfigInfo> selectRemindConfigInfoList(RemindConfigInfo remindConfigInfo)
    {
        return remindConfigInfoMapper.selectRemindConfigInfoList(remindConfigInfo);
    }

    /**
     * 新增提醒配置
     *
     * @param remindConfigInfo 提醒配置
     * @return 结果
     */
    @Override
    public int insertRemindConfigInfo(RemindConfigInfo remindConfigInfo)
    {
        remindConfigInfo.setCreateTime(DateUtils.getNowDate());
        return remindConfigInfoMapper.insertRemindConfigInfo(remindConfigInfo);
    }

    /**
     * 修改提醒配置
     *
     * @param remindConfigInfo 提醒配置
     * @return 结果
     */
    @Override
    public int updateRemindConfigInfo(RemindConfigInfo remindConfigInfo)
    {
        remindConfigInfo.setUpdateTime(DateUtils.getNowDate());
        return remindConfigInfoMapper.updateRemindConfigInfo(remindConfigInfo);
    }

    /**
     * 批量删除提醒配置
     *
     * @param ids 需要删除的提醒配置主键
     * @return 结果
     */
    @Override
    public int deleteRemindConfigInfoByIds(Long[] ids)
    {
        return remindConfigInfoMapper.deleteRemindConfigInfoByIds(ids);
    }

    /**
     * 删除提醒配置信息
     *
     * @param id 提醒配置主键
     * @return 结果
     */
    @Override
    public int deleteRemindConfigInfoById(Long id)
    {
        return remindConfigInfoMapper.deleteRemindConfigInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<RemindConfigInfo> getQueryWrapper(RemindConfigInfoQuery remindConfigInfoQuery){
        QueryWrapper<RemindConfigInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = remindConfigInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = remindConfigInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long residentId = remindConfigInfoQuery.getResidentId();
        queryWrapper.eq( StringUtils.isNotNull(residentId),"resident_id",residentId);

        String remindType = remindConfigInfoQuery.getRemindType();
        queryWrapper.eq(StringUtils.isNotEmpty(remindType) ,"remind_type",remindType);

        String remindTitle = remindConfigInfoQuery.getRemindTitle();
        queryWrapper.like(StringUtils.isNotEmpty(remindTitle) ,"remind_title",remindTitle);

        Long userId = remindConfigInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        String createBy = remindConfigInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = remindConfigInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<RemindConfigInfoVo> convertVoList(List<RemindConfigInfo> remindConfigInfoList) {
        if (StringUtils.isEmpty(remindConfigInfoList)) {
            return Collections.emptyList();
        }
        return remindConfigInfoList.stream().map(RemindConfigInfoVo::objToVo).collect(Collectors.toList());
    }
}
