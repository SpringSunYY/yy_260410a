package com.lz.manage.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lz.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 服务预约对象 tb_service_appointment_info
 *
 * @author YY
 * @date 2026-04-13
 */
@TableName("tb_service_appointment_info")
@Data
public class ServiceAppointmentInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 居民
     */
    @Excel(name = "居民", type = Excel.Type.IMPORT)
    private Long residentId;
    @TableField(exist = false)
    @Excel(name = "居民", type = Excel.Type.EXPORT)
    private String residentName;

    /**
     * 服务类型
     */
    @Excel(name = "服务类型", dictType = "healt_service_type")
    private String serviceType;

    /**
     * 联系方式
     */
    @Excel(name = "联系方式")
    private String contactMethod;

    /**
     * 预约标题
     */
    @Excel(name = "预约标题")
    private String appointmentTitle;

    /**
     * 预约原因
     */
    @Excel(name = "预约原因")
    private String appointmentReason;

    /**
     * 预约日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预约日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date appointmentTime;

    /**
     * 具体时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String dateType;

    /**
     * 状态
     */
    @Excel(name = "状态", dictType = "health_appointment_status")
    private String status;

    /**
     * 预约医生
     */
    @Excel(name = "预约医生", type = Excel.Type.IMPORT)
    private Long appointmentUserId;
    @TableField(exist = false)
    @Excel(name = "预约医生", type = Excel.Type.EXPORT)
    private String appointmentUserName;

    /**
     * 服务时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "服务时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date serviceTime;

    /**
     * 附件
     */
    @Excel(name = "附件")
    private String appendix;

    /**
     * 所属用户
     */
    @Excel(name = "所属用户", type = Excel.Type.IMPORT)
    private Long userId;
    @Excel(name = "所属用户", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String userName;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新人
     */
    @Excel(name = "更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
