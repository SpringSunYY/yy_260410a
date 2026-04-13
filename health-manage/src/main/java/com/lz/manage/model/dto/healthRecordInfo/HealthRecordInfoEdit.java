package com.lz.manage.model.dto.healthRecordInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.HealthRecordInfo;
/**
 * 健康档案Vo对象 tb_health_record_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class HealthRecordInfoEdit implements Serializable
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

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param healthRecordInfoEdit 编辑对象
     * @return HealthRecordInfo
     */
    public static HealthRecordInfo editToObj(HealthRecordInfoEdit healthRecordInfoEdit) {
        if (healthRecordInfoEdit == null) {
            return null;
        }
        HealthRecordInfo healthRecordInfo = new HealthRecordInfo();
        BeanUtils.copyProperties(healthRecordInfoEdit, healthRecordInfo);
        return healthRecordInfo;
    }
}
