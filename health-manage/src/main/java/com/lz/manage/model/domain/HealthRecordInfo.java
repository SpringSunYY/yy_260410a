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
 * 健康档案对象 tb_health_record_info
 *
 * @author YY
 * @date 2026-04-13
 */
@TableName("tb_health_record_info")
@Data
public class HealthRecordInfo implements Serializable {
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
     * 档案编号
     */
    @Excel(name = "档案编号")
    private String recordCode;

    /**
     * 血型
     */
    @Excel(name = "血型", dictType = "health_blood_type")
    private String bloodType;

    /**
     * RH类型
     */
    @Excel(name = "RH类型", dictType = "health_rh_type")
    private String rhType;

    /**
     * 既往病史
     */
    @Excel(name = "既往病史")
    private String pastHistory;

    /**
     * 家族病史
     */
    @Excel(name = "家族病史")
    private String familyHistory;

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
