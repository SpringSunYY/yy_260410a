package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.HealthHistoryInfoMapper;
import com.lz.manage.model.domain.HealthHistoryInfo;
import com.lz.manage.service.IHealthHistoryInfoService;
import com.lz.manage.model.dto.healthHistoryInfo.HealthHistoryInfoQuery;
import com.lz.manage.model.vo.healthHistoryInfo.HealthHistoryInfoVo;

/**
 * 健康记录Service业务层处理
 *
 * @author YY
 * @date 2026-04-13
 */
@Service
public class HealthHistoryInfoServiceImpl extends ServiceImpl<HealthHistoryInfoMapper, HealthHistoryInfo> implements IHealthHistoryInfoService
{

    @Resource
    private HealthHistoryInfoMapper healthHistoryInfoMapper;

    //region mybatis代码
    /**
     * 查询健康记录
     *
     * @param id 健康记录主键
     * @return 健康记录
     */
    @Override
    public HealthHistoryInfo selectHealthHistoryInfoById(Long id)
    {
        return healthHistoryInfoMapper.selectHealthHistoryInfoById(id);
    }

    /**
     * 查询健康记录列表
     *
     * @param healthHistoryInfo 健康记录
     * @return 健康记录
     */
    @Override
    public List<HealthHistoryInfo> selectHealthHistoryInfoList(HealthHistoryInfo healthHistoryInfo)
    {
        return healthHistoryInfoMapper.selectHealthHistoryInfoList(healthHistoryInfo);
    }

    /**
     * 新增健康记录
     *
     * @param healthHistoryInfo 健康记录
     * @return 结果
     */
    @Override
    public int insertHealthHistoryInfo(HealthHistoryInfo healthHistoryInfo)
    {
        healthHistoryInfo.setCreateTime(DateUtils.getNowDate());
        return healthHistoryInfoMapper.insertHealthHistoryInfo(healthHistoryInfo);
    }

    /**
     * 修改健康记录
     *
     * @param healthHistoryInfo 健康记录
     * @return 结果
     */
    @Override
    public int updateHealthHistoryInfo(HealthHistoryInfo healthHistoryInfo)
    {
        healthHistoryInfo.setUpdateTime(DateUtils.getNowDate());
        return healthHistoryInfoMapper.updateHealthHistoryInfo(healthHistoryInfo);
    }

    /**
     * 批量删除健康记录
     *
     * @param ids 需要删除的健康记录主键
     * @return 结果
     */
    @Override
    public int deleteHealthHistoryInfoByIds(Long[] ids)
    {
        return healthHistoryInfoMapper.deleteHealthHistoryInfoByIds(ids);
    }

    /**
     * 删除健康记录信息
     *
     * @param id 健康记录主键
     * @return 结果
     */
    @Override
    public int deleteHealthHistoryInfoById(Long id)
    {
        return healthHistoryInfoMapper.deleteHealthHistoryInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<HealthHistoryInfo> getQueryWrapper(HealthHistoryInfoQuery healthHistoryInfoQuery){
        QueryWrapper<HealthHistoryInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = healthHistoryInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = healthHistoryInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String residentId = healthHistoryInfoQuery.getResidentId();
        queryWrapper.eq(StringUtils.isNotEmpty(residentId) ,"resident_id",residentId);

        Date measureTime = healthHistoryInfoQuery.getMeasureTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginMeasureTime"))&&StringUtils.isNotNull(params.get("endMeasureTime")),"measure_time",params.get("beginMeasureTime"),params.get("endMeasureTime"));

        String measureType = healthHistoryInfoQuery.getMeasureType();
        queryWrapper.eq(StringUtils.isNotEmpty(measureType) ,"measure_type",measureType);

        Long userId = healthHistoryInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        String createBy = healthHistoryInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = healthHistoryInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<HealthHistoryInfoVo> convertVoList(List<HealthHistoryInfo> healthHistoryInfoList) {
        if (StringUtils.isEmpty(healthHistoryInfoList)) {
            return Collections.emptyList();
        }
        return healthHistoryInfoList.stream().map(HealthHistoryInfoVo::objToVo).collect(Collectors.toList());
    }
}
