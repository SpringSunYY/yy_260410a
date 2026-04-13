package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.HospitalizationInfoMapper;
import com.lz.manage.model.domain.HospitalizationInfo;
import com.lz.manage.model.domain.ResidentInfo;
import com.lz.manage.model.dto.hospitalizationInfo.HospitalizationInfoQuery;
import com.lz.manage.model.vo.hospitalizationInfo.HospitalizationInfoVo;
import com.lz.manage.service.IHospitalizationInfoService;
import com.lz.manage.service.IResidentInfoService;
import com.lz.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 住院记录Service业务层处理
 *
 * @author YY
 * @date 2026-04-13
 */
@Service
public class HospitalizationInfoServiceImpl extends ServiceImpl<HospitalizationInfoMapper, HospitalizationInfo> implements IHospitalizationInfoService {

    @Resource
    private HospitalizationInfoMapper hospitalizationInfoMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IResidentInfoService residentInfoService;

    //region mybatis代码

    /**
     * 查询住院记录
     *
     * @param id 住院记录主键
     * @return 住院记录
     */
    @Override
    public HospitalizationInfo selectHospitalizationInfoById(Long id) {
        return hospitalizationInfoMapper.selectHospitalizationInfoById(id);
    }

    /**
     * 查询住院记录列表
     *
     * @param hospitalizationInfo 住院记录
     * @return 住院记录
     */
    @Override
    public List<HospitalizationInfo> selectHospitalizationInfoList(HospitalizationInfo hospitalizationInfo) {
        List<HospitalizationInfo> hospitalizationInfos = hospitalizationInfoMapper.selectHospitalizationInfoList(hospitalizationInfo);
        for (HospitalizationInfo info : hospitalizationInfos) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            ResidentInfo residentInfo = residentInfoService.selectResidentInfoById(info.getResidentId());
            if (StringUtils.isNotNull(residentInfo)) {
                info.setResidentName(residentInfo.getResidentName());
            }
        }
        return hospitalizationInfos;
    }

    /**
     * 新增住院记录
     *
     * @param hospitalizationInfo 住院记录
     * @return 结果
     */
    @Override
    public int insertHospitalizationInfo(HospitalizationInfo hospitalizationInfo) {
        //先查询居民是否存在
        ResidentInfo residentInfo = residentInfoService.selectResidentInfoById(hospitalizationInfo.getResidentId());
        if (StringUtils.isNull(residentInfo)) {
            throw new ServiceException("居民不存在");
        }
        hospitalizationInfo.setUserId(residentInfo.getUserId());
        hospitalizationInfo.setCreateBy(SecurityUtils.getUsername());
        hospitalizationInfo.setCreateTime(DateUtils.getNowDate());
        return hospitalizationInfoMapper.insertHospitalizationInfo(hospitalizationInfo);
    }

    /**
     * 修改住院记录
     *
     * @param hospitalizationInfo 住院记录
     * @return 结果
     */
    @Override
    public int updateHospitalizationInfo(HospitalizationInfo hospitalizationInfo) {
        //先查询是否有数据
        HospitalizationInfo hospitalizationInfoOld = selectHospitalizationInfoById(hospitalizationInfo.getId());
        if (StringUtils.isNull(hospitalizationInfoOld)) {
            throw new ServiceException("住院记录不存在");
        }
        //判断是否更改了居民
        if (!hospitalizationInfoOld.getResidentId().equals(hospitalizationInfo.getResidentId())) {
            throw new ServiceException("不能修改居民");
        }
        hospitalizationInfo.setUpdateBy(SecurityUtils.getUsername());
        hospitalizationInfo.setUpdateTime(DateUtils.getNowDate());
        return hospitalizationInfoMapper.updateHospitalizationInfo(hospitalizationInfo);
    }

    /**
     * 批量删除住院记录
     *
     * @param ids 需要删除的住院记录主键
     * @return 结果
     */
    @Override
    public int deleteHospitalizationInfoByIds(Long[] ids) {
        return hospitalizationInfoMapper.deleteHospitalizationInfoByIds(ids);
    }

    /**
     * 删除住院记录信息
     *
     * @param id 住院记录主键
     * @return 结果
     */
    @Override
    public int deleteHospitalizationInfoById(Long id) {
        return hospitalizationInfoMapper.deleteHospitalizationInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<HospitalizationInfo> getQueryWrapper(HospitalizationInfoQuery hospitalizationInfoQuery) {
        QueryWrapper<HospitalizationInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = hospitalizationInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = hospitalizationInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long residentId = hospitalizationInfoQuery.getResidentId();
        queryWrapper.eq(StringUtils.isNotNull(residentId), "resident_id", residentId);

        Date inTime = hospitalizationInfoQuery.getInTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginInTime")) && StringUtils.isNotNull(params.get("endInTime")), "in_time", params.get("beginInTime"), params.get("endInTime"));

        Date outTime = hospitalizationInfoQuery.getOutTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginOutTime")) && StringUtils.isNotNull(params.get("endOutTime")), "out_time", params.get("beginOutTime"), params.get("endOutTime"));

        String hospitalName = hospitalizationInfoQuery.getHospitalName();
        queryWrapper.like(StringUtils.isNotEmpty(hospitalName), "hospital_name", hospitalName);

        String status = hospitalizationInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        Long userId = hospitalizationInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        String createBy = hospitalizationInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy), "create_by", createBy);

        Date createTime = hospitalizationInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<HospitalizationInfoVo> convertVoList(List<HospitalizationInfo> hospitalizationInfoList) {
        if (StringUtils.isEmpty(hospitalizationInfoList)) {
            return Collections.emptyList();
        }
        return hospitalizationInfoList.stream().map(HospitalizationInfoVo::objToVo).collect(Collectors.toList());
    }
}
