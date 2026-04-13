package com.lz.manage.model.dto.serviceAppointmentInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.ServiceAppointmentInfo;
/**
 * 服务预约Vo对象 tb_service_appointment_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class ServiceAppointmentInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 居民 */
    private Long residentId;

    /** 服务类型 */
    private String serviceType;

    /** 联系方式 */
    private String contactMethod;

    /** 预约标题 */
    private String appointmentTitle;

    /** 预约原因 */
    private String appointmentReason;

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

    /** 服务时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date serviceTime;

    /** 附件 */
    private String appendix;

    /** 所属用户 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param serviceAppointmentInfoEdit 编辑对象
     * @return ServiceAppointmentInfo
     */
    public static ServiceAppointmentInfo editToObj(ServiceAppointmentInfoEdit serviceAppointmentInfoEdit) {
        if (serviceAppointmentInfoEdit == null) {
            return null;
        }
        ServiceAppointmentInfo serviceAppointmentInfo = new ServiceAppointmentInfo();
        BeanUtils.copyProperties(serviceAppointmentInfoEdit, serviceAppointmentInfo);
        return serviceAppointmentInfo;
    }
}
