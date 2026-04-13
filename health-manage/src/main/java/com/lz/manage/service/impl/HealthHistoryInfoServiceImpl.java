package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.HealthHistoryInfoMapper;
import com.lz.manage.model.domain.HealthHistoryInfo;
import com.lz.manage.model.domain.ResidentInfo;
import com.lz.manage.model.dto.healthHistoryInfo.HealthHistoryInfoQuery;
import com.lz.manage.model.vo.healthHistoryInfo.HealthHistoryInfoVo;
import com.lz.manage.service.IHealthHistoryInfoService;
import com.lz.manage.service.IResidentInfoService;
import com.lz.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 健康记录Service业务层处理
 *
 * @author YY
 * @date 2026-04-13
 */
@Service
public class HealthHistoryInfoServiceImpl extends ServiceImpl<HealthHistoryInfoMapper, HealthHistoryInfo> implements IHealthHistoryInfoService {

    @Resource
    private HealthHistoryInfoMapper healthHistoryInfoMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IResidentInfoService residentInfoService;

    //region mybatis代码

    /**
     * 查询健康记录
     *
     * @param id 健康记录主键
     * @return 健康记录
     */
    @Override
    public HealthHistoryInfo selectHealthHistoryInfoById(Long id) {
        return healthHistoryInfoMapper.selectHealthHistoryInfoById(id);
    }

    /**
     * 查询健康记录列表
     *
     * @param healthHistoryInfo 健康记录
     * @return 健康记录
     */
    @Override
    public List<HealthHistoryInfo> selectHealthHistoryInfoList(HealthHistoryInfo healthHistoryInfo) {
        List<HealthHistoryInfo> healthHistoryInfos = healthHistoryInfoMapper.selectHealthHistoryInfoList(healthHistoryInfo);
        for (HealthHistoryInfo info : healthHistoryInfos) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            ResidentInfo residentInfo = residentInfoService.selectResidentInfoById(info.getResidentId());
            if (StringUtils.isNotNull(residentInfo)) {
                info.setResidentName(residentInfo.getResidentName());
            }
        }
        return healthHistoryInfos;
    }

    /**
     * 新增健康记录
     *
     * @param healthHistoryInfo 健康记录
     * @return 结果
     */
    @Override
    public int insertHealthHistoryInfo(HealthHistoryInfo healthHistoryInfo) {
        //先查询居民是否存在
        ResidentInfo residentInfo = residentInfoService.selectResidentInfoById(healthHistoryInfo.getResidentId());
        if (StringUtils.isNull(residentInfo)) {
            throw new ServiceException("居民不存在");
        }
        healthHistoryInfo.setUserId(residentInfo.getUserId());
        healthHistoryInfo.setCreateBy(SecurityUtils.getUsername());
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
    public int updateHealthHistoryInfo(HealthHistoryInfo healthHistoryInfo) {
        //先查询是否存在
        HealthHistoryInfo healthHistoryInfoOld = healthHistoryInfoMapper.selectHealthHistoryInfoById(healthHistoryInfo.getId());
        if (StringUtils.isNull(healthHistoryInfoOld)) {
            throw new ServiceException("健康记录不存在");
        }
        //居民不可修改
        if (!healthHistoryInfoOld.getResidentId().equals(healthHistoryInfo.getResidentId())) {
            throw new ServiceException("居民不可修改");
        }
        healthHistoryInfo.setUpdateBy(SecurityUtils.getUsername());
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
    public int deleteHealthHistoryInfoByIds(Long[] ids) {
        return healthHistoryInfoMapper.deleteHealthHistoryInfoByIds(ids);
    }

    /**
     * 删除健康记录信息
     *
     * @param id 健康记录主键
     * @return 结果
     */
    @Override
    public int deleteHealthHistoryInfoById(Long id) {
        return healthHistoryInfoMapper.deleteHealthHistoryInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<HealthHistoryInfo> getQueryWrapper(HealthHistoryInfoQuery healthHistoryInfoQuery) {
        QueryWrapper<HealthHistoryInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = healthHistoryInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = healthHistoryInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long residentId = healthHistoryInfoQuery.getResidentId();
        queryWrapper.eq(StringUtils.isNotNull(residentId), "resident_id", residentId);

        Date measureTime = healthHistoryInfoQuery.getMeasureTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginMeasureTime")) && StringUtils.isNotNull(params.get("endMeasureTime")), "measure_time", params.get("beginMeasureTime"), params.get("endMeasureTime"));

        String measureType = healthHistoryInfoQuery.getMeasureType();
        queryWrapper.eq(StringUtils.isNotEmpty(measureType), "measure_type", measureType);

        Long userId = healthHistoryInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        String createBy = healthHistoryInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy), "create_by", createBy);

        Date createTime = healthHistoryInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

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
