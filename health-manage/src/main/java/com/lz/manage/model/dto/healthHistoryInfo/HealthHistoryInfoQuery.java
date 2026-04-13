package com.lz.manage.model.dto.healthHistoryInfo;

import java.util.Map;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.HealthHistoryInfo;
/**
 * 健康记录Query对象 tb_health_history_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class HealthHistoryInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 居民 */
    private String residentId;

    /** 测量时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date measureTime;

    /** 测量类型 */
    private String measureType;

    /** 所属用户 */
    private Long userId;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param healthHistoryInfoQuery 查询对象
     * @return HealthHistoryInfo
     */
    public static HealthHistoryInfo queryToObj(HealthHistoryInfoQuery healthHistoryInfoQuery) {
        if (healthHistoryInfoQuery == null) {
            return null;
        }
        HealthHistoryInfo healthHistoryInfo = new HealthHistoryInfo();
        BeanUtils.copyProperties(healthHistoryInfoQuery, healthHistoryInfo);
        return healthHistoryInfo;
    }
}
