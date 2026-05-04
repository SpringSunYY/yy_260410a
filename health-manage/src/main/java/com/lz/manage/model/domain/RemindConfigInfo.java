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
 * 提醒配置对象 tb_remind_config_info
 *
 * @author YY
 * @date 2026-05-04
 */
@TableName("tb_remind_config_info")
@Data
public class RemindConfigInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 居民 */
    @Excel(name = "居民")
    private Long residentId;

    /** 提醒类型 */
    @Excel(name = "提醒类型", dictType = "health_remind_type")
    private String remindType;

    /** 提醒标题 */
    @Excel(name = "提醒标题")
    private String remindTitle;

    /** 提醒内容 */
    @Excel(name = "提醒内容")
    private String remindContent;

    /** 提醒时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "提醒时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date remindTime;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 每天提醒次数 */
    @Excel(name = "每天提醒次数")
    private Long remindFrequency;

    /** 提醒间隔分钟 */
    @Excel(name = "提醒间隔分钟")
    private Long remindInterval;

    /** 上次提醒时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上次提醒时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastRemindTime;

    /** 下次提醒时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下次提醒时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date nextRemindTime;

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
