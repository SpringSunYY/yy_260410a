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
 * 提醒记录对象 tb_remind_info
 *
 * @author YY
 * @date 2026-05-04
 */
@TableName("tb_remind_info")
@Data
public class RemindInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 配置
     */
    @Excel(name = "配置")
    private Long reminderId;

    /**
     * 居民
     */
    @Excel(name = "居民", type = Excel.Type.IMPORT)
    private Long residentId;
    @Excel(name = "居民", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String residentName;

    /**
     * 提醒类型
     */
    @Excel(name = "提醒类型", dictType = "health_remind_type")
    private String reminderType;

    /**
     * 提醒标题
     */
    @Excel(name = "提醒标题")
    private String reminderTitle;

    /**
     * 提醒内容
     */
    @Excel(name = "提醒内容")
    private String reminderContent;

    /**
     * 提醒时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "提醒时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date remindTime;

    /**
     * 已读状态
     */
    @Excel(name = "已读状态", dictType = "health_read_status")
    private String readStatus;

    /**
     * 已读时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "已读时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date readTime;

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
