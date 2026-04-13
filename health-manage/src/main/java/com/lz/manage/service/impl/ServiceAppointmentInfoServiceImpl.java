package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.enums.HealthAppointmentStatusEnum;
import com.lz.manage.mapper.ServiceAppointmentInfoMapper;
import com.lz.manage.model.domain.ResidentInfo;
import com.lz.manage.model.domain.ServiceAppointmentInfo;
import com.lz.manage.model.dto.serviceAppointmentInfo.ServiceAppointmentInfoQuery;
import com.lz.manage.model.vo.serviceAppointmentInfo.ServiceAppointmentInfoVo;
import com.lz.manage.service.IResidentInfoService;
import com.lz.manage.service.IServiceAppointmentInfoService;
import com.lz.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务预约Service业务层处理
 *
 * @author YY
 * @date 2026-04-13
 */
@Service
public class ServiceAppointmentInfoServiceImpl extends ServiceImpl<ServiceAppointmentInfoMapper, ServiceAppointmentInfo> implements IServiceAppointmentInfoService {

    @Resource
    private ServiceAppointmentInfoMapper serviceAppointmentInfoMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IResidentInfoService residentInfoService;

    //region mybatis代码

    /**
     * 查询服务预约
     *
     * @param id 服务预约主键
     * @return 服务预约
     */
    @Override
    public ServiceAppointmentInfo selectServiceAppointmentInfoById(Long id) {
        return serviceAppointmentInfoMapper.selectServiceAppointmentInfoById(id);
    }

    /**
     * 查询服务预约列表
     *
     * @param serviceAppointmentInfo 服务预约
     * @return 服务预约
     */
    @Override
    public List<ServiceAppointmentInfo> selectServiceAppointmentInfoList(ServiceAppointmentInfo serviceAppointmentInfo) {
        if (SecurityUtils.hasRole("doctor") && !SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            serviceAppointmentInfo.setAppointmentUserId(SecurityUtils.getUserId());
        }
        if (SecurityUtils.hasRole("common") && !SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            serviceAppointmentInfo.setUserId(SecurityUtils.getUserId());
        }
        List<ServiceAppointmentInfo> serviceAppointmentInfos = serviceAppointmentInfoMapper.selectServiceAppointmentInfoList(serviceAppointmentInfo);
        for (ServiceAppointmentInfo info : serviceAppointmentInfos) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            ResidentInfo residentInfo = residentInfoService.selectResidentInfoById(info.getResidentId());
            if (StringUtils.isNotNull(residentInfo)) {
                info.setResidentName(residentInfo.getResidentName());
            }
            SysUser appointmentUser = sysUserService.selectUserById(info.getAppointmentUserId());
            if (StringUtils.isNotNull(appointmentUser)) {
                info.setAppointmentUserName(appointmentUser.getUserName());
            }
        }
        return serviceAppointmentInfos;
    }

    /**
     * 新增服务预约
     *
     * @param serviceAppointmentInfo 服务预约
     * @return 结果
     */
    @Override
    public int insertServiceAppointmentInfo(ServiceAppointmentInfo serviceAppointmentInfo) {
        //先查询居民是否存在
        ResidentInfo residentInfo = residentInfoService.selectResidentInfoById(serviceAppointmentInfo.getResidentId());
        if (StringUtils.isNull(residentInfo)) {
            throw new ServiceException("居民不存在");
        }
        //查询预约用户是否存在
        SysUser sysUser = sysUserService.selectUserById(serviceAppointmentInfo.getAppointmentUserId());
        if (StringUtils.isNull(sysUser)) {
            throw new ServiceException("预约用户不存在");
        }
        serviceAppointmentInfo.setStatus(HealthAppointmentStatusEnum.HEALTH_APPOINTMENT_STATUS_1.getValue());
        serviceAppointmentInfo.setUserId(residentInfo.getUserId());
        serviceAppointmentInfo.setCreateBy(SecurityUtils.getUsername());
        serviceAppointmentInfo.setCreateTime(DateUtils.getNowDate());
        return serviceAppointmentInfoMapper.insertServiceAppointmentInfo(serviceAppointmentInfo);
    }

    /**
     * 修改服务预约
     *
     * @param serviceAppointmentInfo 服务预约
     * @return 结果
     */
    @Override
    public int updateServiceAppointmentInfo(ServiceAppointmentInfo serviceAppointmentInfo) {
        //查询是否存在
        ServiceAppointmentInfo serviceAppointmentInfoOld = serviceAppointmentInfoMapper.selectServiceAppointmentInfoById(serviceAppointmentInfo.getId());
        if (StringUtils.isNull(serviceAppointmentInfoOld)) {
            throw new ServiceException("服务预约不存在");
        }
        //如果不是待确认不可以修改
        if (!HealthAppointmentStatusEnum.HEALTH_APPOINTMENT_STATUS_1.getValue().equals(serviceAppointmentInfoOld.getStatus())) {
            throw new ServiceException("非待确认状态不可以修改");
        }
        //居民不可以修改
        if (!serviceAppointmentInfoOld.getResidentId().equals(serviceAppointmentInfo.getResidentId())) {
            throw new ServiceException("居民不可以修改");
        }
        serviceAppointmentInfo.setUpdateBy(SecurityUtils.getUsername());
        serviceAppointmentInfo.setUpdateTime(DateUtils.getNowDate());
        return serviceAppointmentInfoMapper.updateServiceAppointmentInfo(serviceAppointmentInfo);
    }

    @Override
    public int auditServiceAppointmentInfo(ServiceAppointmentInfo serviceAppointmentInfo) {
        //查询是否存在
        ServiceAppointmentInfo serviceAppointmentInfoOld = serviceAppointmentInfoMapper.selectServiceAppointmentInfoById(serviceAppointmentInfo.getId());
        if (StringUtils.isNull(serviceAppointmentInfoOld)) {
            throw new ServiceException("服务预约不存在");
        }
        //如果是已取消或者已完成不可以修改
        if (HealthAppointmentStatusEnum.HEALTH_APPOINTMENT_STATUS_3.getValue().equals(serviceAppointmentInfoOld.getStatus())
            || HealthAppointmentStatusEnum.HEALTH_APPOINTMENT_STATUS_4.getValue().equals(serviceAppointmentInfoOld.getStatus())) {
            throw new ServiceException("已完成或者已取消状态不可以修改");
        }
        return serviceAppointmentInfoMapper.updateServiceAppointmentInfo(serviceAppointmentInfo);
    }

    /**
     * 批量删除服务预约
     *
     * @param ids 需要删除的服务预约主键
     * @return 结果
     */
    @Override
    public int deleteServiceAppointmentInfoByIds(Long[] ids) {
        return serviceAppointmentInfoMapper.deleteServiceAppointmentInfoByIds(ids);
    }

    /**
     * 删除服务预约信息
     *
     * @param id 服务预约主键
     * @return 结果
     */
    @Override
    public int deleteServiceAppointmentInfoById(Long id) {
        return serviceAppointmentInfoMapper.deleteServiceAppointmentInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<ServiceAppointmentInfo> getQueryWrapper(ServiceAppointmentInfoQuery serviceAppointmentInfoQuery) {
        QueryWrapper<ServiceAppointmentInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = serviceAppointmentInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = serviceAppointmentInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long residentId = serviceAppointmentInfoQuery.getResidentId();
        queryWrapper.eq(StringUtils.isNotNull(residentId), "resident_id", residentId);

        String serviceType = serviceAppointmentInfoQuery.getServiceType();
        queryWrapper.eq(StringUtils.isNotEmpty(serviceType), "service_type", serviceType);

        String appointmentTitle = serviceAppointmentInfoQuery.getAppointmentTitle();
        queryWrapper.like(StringUtils.isNotEmpty(appointmentTitle), "appointment_title", appointmentTitle);

        Date appointmentTime = serviceAppointmentInfoQuery.getAppointmentTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginAppointmentTime")) && StringUtils.isNotNull(params.get("endAppointmentTime")), "appointment_time", params.get("beginAppointmentTime"), params.get("endAppointmentTime"));

        String dateType = serviceAppointmentInfoQuery.getDateType();
        queryWrapper.eq(StringUtils.isNotEmpty(dateType), "date_type", dateType);

        String status = serviceAppointmentInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        Long appointmentUserId = serviceAppointmentInfoQuery.getAppointmentUserId();
        queryWrapper.eq(StringUtils.isNotNull(appointmentUserId), "appointment_user_id", appointmentUserId);

        Long userId = serviceAppointmentInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        String createBy = serviceAppointmentInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy), "create_by", createBy);

        Date createTime = serviceAppointmentInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<ServiceAppointmentInfoVo> convertVoList(List<ServiceAppointmentInfo> serviceAppointmentInfoList) {
        if (StringUtils.isEmpty(serviceAppointmentInfoList)) {
            return Collections.emptyList();
        }
        return serviceAppointmentInfoList.stream().map(ServiceAppointmentInfoVo::objToVo).collect(Collectors.toList());
    }
}
