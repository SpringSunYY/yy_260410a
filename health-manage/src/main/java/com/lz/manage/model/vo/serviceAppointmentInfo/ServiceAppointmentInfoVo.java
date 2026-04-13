package com.lz.manage.model.vo.serviceAppointmentInfo;

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
public class ServiceAppointmentInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 居民
     */
    private Long residentId;
    private String residentName;

    /**
     * 服务类型
     */
    private String serviceType;

    /**
     * 联系方式
     */
    private String contactMethod;

    /**
     * 预约标题
     */
    private String appointmentTitle;

    /**
     * 预约原因
     */
    private String appointmentReason;

    /**
     * 预约日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date appointmentTime;

    /**
     * 具体时间
     */
    private String dateType;

    /**
     * 状态
     */
    private String status;

    /**
     * 预约医生
     */
    private Long appointmentUserId;
    private String appointmentUserName;

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
     * 所属用户
     */
    private Long userId;
    private String userName;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


    /**
     * 对象转封装类
     *
     * @param serviceAppointmentInfo ServiceAppointmentInfo实体对象
     * @return ServiceAppointmentInfoVo
     */
    public static ServiceAppointmentInfoVo objToVo(ServiceAppointmentInfo serviceAppointmentInfo) {
        if (serviceAppointmentInfo == null) {
            return null;
        }
        ServiceAppointmentInfoVo serviceAppointmentInfoVo = new ServiceAppointmentInfoVo();
        BeanUtils.copyProperties(serviceAppointmentInfo, serviceAppointmentInfoVo);
        return serviceAppointmentInfoVo;
    }
}
