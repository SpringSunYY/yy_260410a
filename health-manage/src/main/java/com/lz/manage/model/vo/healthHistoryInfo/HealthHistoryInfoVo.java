package com.lz.manage.model.vo.healthHistoryInfo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.HealthHistoryInfo;
/**
 * 健康记录Vo对象 tb_health_history_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class HealthHistoryInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 居民 */
    private Long residentId;

    /** 测量时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date measureTime;

    /** 身高 */
    private BigDecimal height;

    /** 体重 */
    private BigDecimal weight;

    /** 收缩压mmHg */
    private Long sbp;

    /** 舒张压mmHg */
    private Long dbp;

    /** 血糖mmol/L */
    private BigDecimal bloodGlucose;

    /** 心率次/分 */
    private Long heartRate;

    /** 测量类型 */
    private String measureType;

    /** 附件 */
    private String appendix;

    /** 所属用户 */
    private Long userId;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 备注 */
    private String remark;


     /**
     * 对象转封装类
     *
     * @param healthHistoryInfo HealthHistoryInfo实体对象
     * @return HealthHistoryInfoVo
     */
    public static HealthHistoryInfoVo objToVo(HealthHistoryInfo healthHistoryInfo) {
        if (healthHistoryInfo == null) {
            return null;
        }
        HealthHistoryInfoVo healthHistoryInfoVo = new HealthHistoryInfoVo();
        BeanUtils.copyProperties(healthHistoryInfo, healthHistoryInfoVo);
        return healthHistoryInfoVo;
    }
}
