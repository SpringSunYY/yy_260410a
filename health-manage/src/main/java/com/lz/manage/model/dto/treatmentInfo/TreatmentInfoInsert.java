package com.lz.manage.model.dto.treatmentInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.TreatmentInfo;
/**
 * 诊疗记录Vo对象 tb_treatment_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class TreatmentInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 居民 */
    private Long residentId;

    /** 就诊时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date visitTime;

    /** 诊断结果 */
    private String diagnosis;

    /** 用药处方 */
    private String prescription;

    /** 附件 */
    private String appendix;

    /** 所属用户 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param treatmentInfoInsert 插入对象
     * @return TreatmentInfoInsert
     */
    public static TreatmentInfo insertToObj(TreatmentInfoInsert treatmentInfoInsert) {
        if (treatmentInfoInsert == null) {
            return null;
        }
        TreatmentInfo treatmentInfo = new TreatmentInfo();
        BeanUtils.copyProperties(treatmentInfoInsert, treatmentInfo);
        return treatmentInfo;
    }
}
