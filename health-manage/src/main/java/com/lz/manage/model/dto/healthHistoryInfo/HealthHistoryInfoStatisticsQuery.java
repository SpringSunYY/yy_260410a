package com.lz.manage.model.dto.healthHistoryInfo;

import com.lz.manage.model.domain.HealthHistoryInfo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 健康记录Query对象 tb_health_history_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class HealthHistoryInfoStatisticsQuery implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 居民
     */
    @NotNull(message = "居民不能为空")
    private Long residentId;

    /**
     * 测量类型
     */
    private String measureType;


    @NotEmpty(message = "开始时间不能为空")
    private String startTime;

    @NotEmpty(message = "结束时间不能为空")
    private String endTime;


    /**
     * 对象转封装类
     *
     * @param healthHistoryInfoQuery 查询对象
     * @return HealthHistoryInfo
     */
    public static HealthHistoryInfo queryToObj(HealthHistoryInfoStatisticsQuery healthHistoryInfoQuery) {
        if (healthHistoryInfoQuery == null) {
            return null;
        }
        HealthHistoryInfo healthHistoryInfo = new HealthHistoryInfo();
        BeanUtils.copyProperties(healthHistoryInfoQuery, healthHistoryInfo);
        return healthHistoryInfo;
    }
}
