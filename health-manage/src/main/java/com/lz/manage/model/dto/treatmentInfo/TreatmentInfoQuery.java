package com.lz.manage.model.dto.treatmentInfo;

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
import com.lz.manage.model.domain.TreatmentInfo;
/**
 * 诊疗记录Query对象 tb_treatment_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class TreatmentInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 居民 */
    private Long residentId;

    /** 就诊时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date visitTime;

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
     * @param treatmentInfoQuery 查询对象
     * @return TreatmentInfo
     */
    public static TreatmentInfo queryToObj(TreatmentInfoQuery treatmentInfoQuery) {
        if (treatmentInfoQuery == null) {
            return null;
        }
        TreatmentInfo treatmentInfo = new TreatmentInfo();
        BeanUtils.copyProperties(treatmentInfoQuery, treatmentInfo);
        return treatmentInfo;
    }
}
