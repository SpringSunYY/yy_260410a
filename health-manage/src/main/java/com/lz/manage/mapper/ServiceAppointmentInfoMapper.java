package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.ServiceAppointmentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 服务预约Mapper接口
 * 
 * @author YY
 * @date 2026-04-13
 */
public interface ServiceAppointmentInfoMapper extends BaseMapper<ServiceAppointmentInfo>
{
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
     * 删除服务预约
     * 
     * @param id 服务预约主键
     * @return 结果
     */
    public int deleteServiceAppointmentInfoById(Long id);

    /**
     * 批量删除服务预约
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteServiceAppointmentInfoByIds(Long[] ids);
}
