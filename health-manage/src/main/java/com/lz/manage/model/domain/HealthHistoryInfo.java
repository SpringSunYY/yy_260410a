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
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 健康记录对象 tb_health_history_info
 *
 * @author YY
 * @date 2026-04-13
 */
@TableName("tb_health_history_info")
@Data
public class HealthHistoryInfo implements Serializable {
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
     * 测量时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "测量时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date measureTime;

    /**
     * 身高
     */
    @Excel(name = "身高")
    private BigDecimal height;

    /**
     * 体重
     */
    @Excel(name = "体重")
    private BigDecimal weight;

    /**
     * 收缩压mmHg
     */
    @Excel(name = "收缩压mmHg")
    private Long sbp;

    /**
     * 舒张压mmHg
     */
    @Excel(name = "舒张压mmHg")
    private Long dbp;

    /**
     * 血糖mmol/L
     */
    @Excel(name = "血糖mmol/L")
    private BigDecimal bloodGlucose;

    /**
     * 心率次/分
     */
    @Excel(name = "心率次/分")
    private Long heartRate;

    /**
     * 血脂
     */
    @Excel(name = "血脂")
    private Long bloodLipids;

    /**
     * 血压
     */
    @Excel(name = "血压")
    private Long bloodPressure;

    /**
     * 测量类型
     */
    @Excel(name = "测量类型", dictType = "health_measure_type")
    private String measureType;

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
