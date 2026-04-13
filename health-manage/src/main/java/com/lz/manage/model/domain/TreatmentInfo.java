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
 * 诊疗记录对象 tb_treatment_info
 *
 * @author YY
 * @date 2026-04-13
 */
@TableName("tb_treatment_info")
@Data
public class TreatmentInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 居民 */
    @Excel(name = "居民")
    private String residentId;

    /** 就诊时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "就诊时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date visitTime;

    /** 诊断结果 */
    @Excel(name = "诊断结果")
    private String diagnosis;

    /** 用药处方 */
    @Excel(name = "用药处方")
    private String prescription;

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
