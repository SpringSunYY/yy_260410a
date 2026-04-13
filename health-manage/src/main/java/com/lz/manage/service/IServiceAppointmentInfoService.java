package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.ServiceAppointmentInfo;
import com.lz.manage.model.vo.serviceAppointmentInfo.ServiceAppointmentInfoVo;
import com.lz.manage.model.dto.serviceAppointmentInfo.ServiceAppointmentInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 服务预约Service接口
 * 
 * @author YY
 * @date 2026-04-13
 */
public interface IServiceAppointmentInfoService extends IService<ServiceAppointmentInfo>
{
    //region mybatis代码
    /**
     * 查询服务预约
     * 
     * @param id 服务预约主键
     * @return 服务预约
     */
    public ServiceAppointmentInfo selectServiceAppointmentInfoById(Long id);

    /**
     * 查询服务预约列表
     * 
     * @param serviceAppointmentInfo 服务预约
     * @return 服务预约集合
     */
    public List<ServiceAppointmentInfo> selectServiceAppointmentInfoList(ServiceAppointmentInfo serviceAppointmentInfo);

    /**
     * 新增服务预约
     * 
     * @param serviceAppointmentInfo 服务预约
     * @return 结果
     */
    public int insertServiceAppointmentInfo(ServiceAppointmentInfo serviceAppointmentInfo);

    /**
     * 修改服务预约
     * 
     * @param serviceAppointmentInfo 服务预约
     * @return 结果
     */
    public int updateServiceAppointmentInfo(ServiceAppointmentInfo serviceAppointmentInfo);

    /**
     * 审核服务预约
     *
     * @param serviceAppointmentInfo 服务预约
     * @return 结果
     */
    int auditServiceAppointmentInfo(ServiceAppointmentInfo serviceAppointmentInfo);

    /**
     * 批量删除服务预约
     * 
     * @param ids 需要删除的服务预约主键集合
     * @return 结果
     */
    public int deleteServiceAppointmentInfoByIds(Long[] ids);

    /**
     * 删除服务预约信息
     * 
     * @param id 服务预约主键
     * @return 结果
     */
    public int deleteServiceAppointmentInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param serviceAppointmentInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<ServiceAppointmentInfo> getQueryWrapper(ServiceAppointmentInfoQuery serviceAppointmentInfoQuery);

    /**
     * 转换vo
     *
     * @param serviceAppointmentInfoList ServiceAppointmentInfo集合
     * @return ServiceAppointmentInfoVO集合
     */
    List<ServiceAppointmentInfoVo> convertVoList(List<ServiceAppointmentInfo> serviceAppointmentInfoList);
}
