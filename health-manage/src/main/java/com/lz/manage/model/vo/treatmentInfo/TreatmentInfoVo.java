package com.lz.manage.model.vo.treatmentInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.TreatmentInfo;
/**
 * 诊疗记录Vo对象 tb_treatment_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class TreatmentInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

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
     * @param treatmentInfo TreatmentInfo实体对象
     * @return TreatmentInfoVo
     */
    public static TreatmentInfoVo objToVo(TreatmentInfo treatmentInfo) {
        if (treatmentInfo == null) {
            return null;
        }
        TreatmentInfoVo treatmentInfoVo = new TreatmentInfoVo();
        BeanUtils.copyProperties(treatmentInfo, treatmentInfoVo);
        return treatmentInfoVo;
    }
}
