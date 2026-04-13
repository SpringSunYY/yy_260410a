package com.lz.manage.model.vo.healthRecordInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.HealthRecordInfo;
/**
 * 健康档案Vo对象 tb_health_record_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class HealthRecordInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 居民 */
    private String residentId;

    /** 档案编号 */
    private String recordCode;

    /** 血型 */
    private String bloodType;

    /** RH类型 */
    private String rhType;

    /** 既往病史 */
    private String pastHistory;

    /** 家族病史 */
    private String familyHistory;

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
     * @param healthRecordInfo HealthRecordInfo实体对象
     * @return HealthRecordInfoVo
     */
    public static HealthRecordInfoVo objToVo(HealthRecordInfo healthRecordInfo) {
        if (healthRecordInfo == null) {
            return null;
        }
        HealthRecordInfoVo healthRecordInfoVo = new HealthRecordInfoVo();
        BeanUtils.copyProperties(healthRecordInfo, healthRecordInfoVo);
        return healthRecordInfoVo;
    }
}
