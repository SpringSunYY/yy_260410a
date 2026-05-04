package com.lz.manage.model.dto.healthHistoryInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.HealthHistoryInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 健康记录Vo对象 tb_health_history_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class HealthHistoryInfoEdit implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 居民
     */
    private Long residentId;

    /**
     * 测量时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date measureTime;

    /**
     * 身高
     */
    private BigDecimal height;

    /**
     * 体重
     */
    private BigDecimal weight;

    /**
     * 收缩压mmHg
     */
    private Long sbp;

    /**
     * 舒张压mmHg
     */
    private Long dbp;

    /**
     * 血糖mmol/L
     */
    private BigDecimal bloodGlucose;

    /**
     * 心率次/分
     */
    private Long heartRate;

    /**
     * 血脂
     */
    private Long bloodLipids;


    /**
     * 测量类型
     */
    private String measureType;

    /**
     * 附件
     */
    private String appendix;

    /**
     * 所属用户
     */
    private Long userId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param healthHistoryInfoEdit 编辑对象
     * @return HealthHistoryInfo
     */
    public static HealthHistoryInfo editToObj(HealthHistoryInfoEdit healthHistoryInfoEdit) {
        if (healthHistoryInfoEdit == null) {
            return null;
        }
        HealthHistoryInfo healthHistoryInfo = new HealthHistoryInfo();
        BeanUtils.copyProperties(healthHistoryInfoEdit, healthHistoryInfo);
        return healthHistoryInfo;
    }
}
