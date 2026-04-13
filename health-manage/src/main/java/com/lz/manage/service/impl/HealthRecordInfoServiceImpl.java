package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.annotation.DataScope;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.HealthRecordInfoMapper;
import com.lz.manage.model.domain.HealthRecordInfo;
import com.lz.manage.model.domain.ResidentInfo;
import com.lz.manage.model.dto.healthRecordInfo.HealthRecordInfoQuery;
import com.lz.manage.model.vo.healthRecordInfo.HealthRecordInfoVo;
import com.lz.manage.service.IHealthRecordInfoService;
import com.lz.manage.service.IResidentInfoService;
import com.lz.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 健康档案Service业务层处理
 *
 * @author YY
 * @date 2026-04-13
 */
@Service
public class HealthRecordInfoServiceImpl extends ServiceImpl<HealthRecordInfoMapper, HealthRecordInfo> implements IHealthRecordInfoService {

    @Resource
    private HealthRecordInfoMapper healthRecordInfoMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IResidentInfoService residentInfoService;
    //region mybatis代码

    /**
     * 查询健康档案
     *
     * @param id 健康档案主键
     * @return 健康档案
     */
    @Override
    public HealthRecordInfo selectHealthRecordInfoById(Long id) {
        return healthRecordInfoMapper.selectHealthRecordInfoById(id);
    }

    /**
     * 查询健康档案列表
     *
     * @param healthRecordInfo 健康档案
     * @return 健康档案
     */
    @Override
    @DataScope(deptAlias = "tb_health_record_info", userAlias = "tb_health_record_info")
    public List<HealthRecordInfo> selectHealthRecordInfoList(HealthRecordInfo healthRecordInfo) {
        List<HealthRecordInfo> healthRecordInfos = healthRecordInfoMapper.selectHealthRecordInfoList(healthRecordInfo);
        for (HealthRecordInfo info : healthRecordInfos) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            ResidentInfo residentInfo = residentInfoService.selectResidentInfoById(info.getResidentId());
            if (StringUtils.isNotNull(residentInfo)) {
                info.setResidentName(residentInfo.getResidentName());
            }
        }
        return healthRecordInfos;
    }

    /**
     * 新增健康档案
     *
     * @param healthRecordInfo 健康档案
     * @return 结果
     */
    @Override
    public int insertHealthRecordInfo(HealthRecordInfo healthRecordInfo) {
        //根据编号查询是否有这个编号的
        HealthRecordInfo healthRecordInfoOld = healthRecordInfoMapper.selectHealthRecordInfoByCode(healthRecordInfo.getRecordCode());
        if (StringUtils.isNotNull(healthRecordInfoOld)) {
            throw new ServiceException("编号已存在");
        }
        //先查询居民是否存在
        ResidentInfo residentInfo = residentInfoService.selectResidentInfoById(healthRecordInfo.getResidentId());
        if (StringUtils.isNull(residentInfo)) {
            throw new ServiceException("居民不存在");
        }
        healthRecordInfo.setUserId(residentInfo.getUserId());
        healthRecordInfo.setResidentId(residentInfo.getId());
        healthRecordInfo.setCreateBy(SecurityUtils.getUsername());
        healthRecordInfo.setCreateTime(DateUtils.getNowDate());
        return healthRecordInfoMapper.insertHealthRecordInfo(healthRecordInfo);
    }

    /**
     * 修改健康档案
     *
     * @param healthRecordInfo 健康档案
     * @return 结果
     */
    @Override
    public int updateHealthRecordInfo(HealthRecordInfo healthRecordInfo) {
        //查询是否存在，居民不可修改
        HealthRecordInfo healthRecordInfoOld = healthRecordInfoMapper.selectHealthRecordInfoById(healthRecordInfo.getId());
        if (StringUtils.isNull(healthRecordInfoOld)) {
            throw new ServiceException("居民不存在");
        }
        if (!healthRecordInfoOld.getResidentId().equals(healthRecordInfo.getResidentId())) {
            throw new ServiceException("居民不可修改");
        }
        healthRecordInfo.setUpdateBy(SecurityUtils.getUsername());
        healthRecordInfo.setUpdateTime(DateUtils.getNowDate());
        return healthRecordInfoMapper.updateHealthRecordInfo(healthRecordInfo);
    }

    /**
     * 批量删除健康档案
     *
     * @param ids 需要删除的健康档案主键
     * @return 结果
     */
    @Override
    public int deleteHealthRecordInfoByIds(Long[] ids) {
        return healthRecordInfoMapper.deleteHealthRecordInfoByIds(ids);
    }

    /**
     * 删除健康档案信息
     *
     * @param id 健康档案主键
     * @return 结果
     */
    @Override
    public int deleteHealthRecordInfoById(Long id) {
        return healthRecordInfoMapper.deleteHealthRecordInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<HealthRecordInfo> getQueryWrapper(HealthRecordInfoQuery healthRecordInfoQuery) {
        QueryWrapper<HealthRecordInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = healthRecordInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long residentId = healthRecordInfoQuery.getResidentId();
        queryWrapper.eq(StringUtils.isNotNull(residentId), "resident_id", residentId);

        String recordCode = healthRecordInfoQuery.getRecordCode();
        queryWrapper.like(StringUtils.isNotEmpty(recordCode), "record_code", recordCode);

        String bloodType = healthRecordInfoQuery.getBloodType();
        queryWrapper.eq(StringUtils.isNotEmpty(bloodType), "blood_type", bloodType);

        String rhType = healthRecordInfoQuery.getRhType();
        queryWrapper.eq(StringUtils.isNotEmpty(rhType), "rh_type", rhType);

        Long userId = healthRecordInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        String createBy = healthRecordInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy), "create_by", createBy);

        Date createTime = healthRecordInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<HealthRecordInfoVo> convertVoList(List<HealthRecordInfo> healthRecordInfoList) {
        if (StringUtils.isEmpty(healthRecordInfoList)) {
            return Collections.emptyList();
        }
        return healthRecordInfoList.stream().map(HealthRecordInfoVo::objToVo).collect(Collectors.toList());
    }
}
