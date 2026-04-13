package com.lz.manage.model.dto.serviceAppointmentInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.ServiceAppointmentInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 服务预约Vo对象 tb_service_appointment_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class ServiceAppointmentInfoAudit implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;
    /**
     * 状态
     */
    private String status;

    /**
     * 预约医生
     */
    private Long appointmentUserId;

    /**
     * 服务时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date serviceTime;

    /**
     * 附件
     */
    private String appendix;

    /**
     * 备注
     */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param serviceAppointmentInfoEdit 编辑对象
     * @return ServiceAppointmentInfo
     */
    public static ServiceAppointmentInfo auditToObj(ServiceAppointmentInfoAudit serviceAppointmentInfoEdit) {
        if (serviceAppointmentInfoEdit == null) {
            return null;
        }
        ServiceAppointmentInfo serviceAppointmentInfo = new ServiceAppointmentInfo();
        BeanUtils.copyProperties(serviceAppointmentInfoEdit, serviceAppointmentInfo);
        return serviceAppointmentInfo;
    }
}
