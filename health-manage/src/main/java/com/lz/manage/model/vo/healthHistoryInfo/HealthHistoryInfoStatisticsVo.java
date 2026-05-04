package com.lz.manage.model.vo.healthHistoryInfo;

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
public class HealthHistoryInfoStatisticsVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 测量时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date measureTime;


    /**
     * 血糖mmol/L
     */
    private BigDecimal bloodGlucose;


    /**
     * 血脂
     */
    private Long bloodLipids;


    /**
     * 血压
     */
    private Long bloodPressure;

}
