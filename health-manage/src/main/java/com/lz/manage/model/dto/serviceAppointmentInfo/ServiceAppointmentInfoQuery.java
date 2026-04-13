package com.lz.manage.model.dto.serviceAppointmentInfo;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.ServiceAppointmentInfo;
/**
 * 服务预约Query对象 tb_service_appointment_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class ServiceAppointmentInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 居民 */
    private Long residentId;

    /** 服务类型 */
    private String serviceType;

    /** 预约标题 */
    private String appointmentTitle;

    /** 预约日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date appointmentTime;

    /** 具体时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateType;

    /** 状态 */
    private String status;

    /** 预约医生 */
    private Long appointmentUserId;

    /** 所属用户 */
    private Long userId;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param serviceAppointmentInfoQuery 查询对象
     * @return ServiceAppointmentInfo
     */
    public static ServiceAppointmentInfo queryToObj(ServiceAppointmentInfoQuery serviceAppointmentInfoQuery) {
        if (serviceAppointmentInfoQuery == null) {
            return null;
        }
        ServiceAppointmentInfo serviceAppointmentInfo = new ServiceAppointmentInfo();
        BeanUtils.copyProperties(serviceAppointmentInfoQuery, serviceAppointmentInfo);
        return serviceAppointmentInfo;
    }
}
