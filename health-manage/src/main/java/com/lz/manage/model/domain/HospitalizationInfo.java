package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.lz.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * 住院记录对象 tb_hospitalization_info
 *
 * @author YY
 * @date 2026-04-13
 */
@TableName("tb_hospitalization_info")
@Data
public class HospitalizationInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 居民 */
    @Excel(name = "居民")
    private String residentId;

    /** 入院时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入院时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inTime;

    /** 出院时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出院时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date outTime;

    /** 住院机构 */
    @Excel(name = "住院机构")
    private String hospitalName;

    /** 住院原因 */
    @Excel(name = "住院原因")
    private String hospitalReason;

    /** 状态 */
    @Excel(name = "状态", dictType = "health_hospitalization_status")
    private String status;

    /** 附件 */
    @Excel(name = "附件")
    private String appendix;

    /** 所属用户 */
    @Excel(name = "所属用户")
    private Long userId;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
