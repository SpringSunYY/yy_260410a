package com.lz.manage.model.dto.healthRecordInfo;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.HealthRecordInfo;
/**
 * 健康档案Query对象 tb_health_record_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class HealthRecordInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 居民 */
    private String residentId;

    /** 档案编号 */
    private String recordCode;

    /** 血型 */
    private String bloodType;

    /** RH类型 */
    private String rhType;

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
     * @param healthRecordInfoQuery 查询对象
     * @return HealthRecordInfo
     */
    public static HealthRecordInfo queryToObj(HealthRecordInfoQuery healthRecordInfoQuery) {
        if (healthRecordInfoQuery == null) {
            return null;
        }
        HealthRecordInfo healthRecordInfo = new HealthRecordInfo();
        BeanUtils.copyProperties(healthRecordInfoQuery, healthRecordInfo);
        return healthRecordInfo;
    }
}
