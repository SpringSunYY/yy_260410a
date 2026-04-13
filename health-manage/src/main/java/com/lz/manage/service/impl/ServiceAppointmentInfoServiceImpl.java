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
import com.lz.manage.mapper.ServiceAppointmentInfoMapper;
import com.lz.manage.model.domain.ServiceAppointmentInfo;
import com.lz.manage.service.IServiceAppointmentInfoService;
import com.lz.manage.model.dto.serviceAppointmentInfo.ServiceAppointmentInfoQuery;
import com.lz.manage.model.vo.serviceAppointmentInfo.ServiceAppointmentInfoVo;

/**
 * 服务预约Service业务层处理
 *
 * @author YY
 * @date 2026-04-13
 */
@Service
public class ServiceAppointmentInfoServiceImpl extends ServiceImpl<ServiceAppointmentInfoMapper, ServiceAppointmentInfo> implements IServiceAppointmentInfoService
{

    @Resource
    private ServiceAppointmentInfoMapper serviceAppointmentInfoMapper;

    //region mybatis代码
    /**
     * 查询服务预约
     *
     * @param id 服务预约主键
     * @return 服务预约
     */
    @Override
    public ServiceAppointmentInfo selectServiceAppointmentInfoById(Long id)
    {
        return serviceAppointmentInfoMapper.selectServiceAppointmentInfoById(id);
    }

    /**
     * 查询服务预约列表
     *
     * @param serviceAppointmentInfo 服务预约
     * @return 服务预约
     */
    @Override
    public List<ServiceAppointmentInfo> selectServiceAppointmentInfoList(ServiceAppointmentInfo serviceAppointmentInfo)
    {
        return serviceAppointmentInfoMapper.selectServiceAppointmentInfoList(serviceAppointmentInfo);
    }

    /**
     * 新增服务预约
     *
     * @param serviceAppointmentInfo 服务预约
     * @return 结果
     */
    @Override
    public int insertServiceAppointmentInfo(ServiceAppointmentInfo serviceAppointmentInfo)
    {
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
    public int updateServiceAppointmentInfo(ServiceAppointmentInfo serviceAppointmentInfo)
    {
        serviceAppointmentInfo.setUpdateTime(DateUtils.getNowDate());
        return serviceAppointmentInfoMapper.updateServiceAppointmentInfo(serviceAppointmentInfo);
    }

    /**
     * 批量删除服务预约
     *
     * @param ids 需要删除的服务预约主键
     * @return 结果
     */
    @Override
    public int deleteServiceAppointmentInfoByIds(Long[] ids)
    {
        return serviceAppointmentInfoMapper.deleteServiceAppointmentInfoByIds(ids);
    }

    /**
     * 删除服务预约信息
     *
     * @param id 服务预约主键
     * @return 结果
     */
    @Override
    public int deleteServiceAppointmentInfoById(Long id)
    {
        return serviceAppointmentInfoMapper.deleteServiceAppointmentInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<ServiceAppointmentInfo> getQueryWrapper(ServiceAppointmentInfoQuery serviceAppointmentInfoQuery){
        QueryWrapper<ServiceAppointmentInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = serviceAppointmentInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = serviceAppointmentInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String residentId = serviceAppointmentInfoQuery.getResidentId();
        queryWrapper.eq(StringUtils.isNotEmpty(residentId) ,"resident_id",residentId);

        String serviceType = serviceAppointmentInfoQuery.getServiceType();
        queryWrapper.eq(StringUtils.isNotEmpty(serviceType) ,"service_type",serviceType);

        String appointmentTitle = serviceAppointmentInfoQuery.getAppointmentTitle();
        queryWrapper.like(StringUtils.isNotEmpty(appointmentTitle) ,"appointment_title",appointmentTitle);

        Date appointmentTime = serviceAppointmentInfoQuery.getAppointmentTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginAppointmentTime"))&&StringUtils.isNotNull(params.get("endAppointmentTime")),"appointment_time",params.get("beginAppointmentTime"),params.get("endAppointmentTime"));

        Date dateType = serviceAppointmentInfoQuery.getDateType();
        queryWrapper.eq( StringUtils.isNotNull(dateType),"date_type",dateType);

        String status = serviceAppointmentInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        Long appointmentUserId = serviceAppointmentInfoQuery.getAppointmentUserId();
        queryWrapper.eq( StringUtils.isNotNull(appointmentUserId),"appointment_user_id",appointmentUserId);

        Long userId = serviceAppointmentInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        String createBy = serviceAppointmentInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = serviceAppointmentInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

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
