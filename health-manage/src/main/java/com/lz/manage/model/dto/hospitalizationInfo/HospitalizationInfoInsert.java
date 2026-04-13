package com.lz.manage.model.dto.hospitalizationInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.HospitalizationInfo;
/**
 * 住院记录Vo对象 tb_hospitalization_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class HospitalizationInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 居民 */
    private Long residentId;

    /** 入院时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date inTime;

    /** 出院时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date outTime;

    /** 住院机构 */
    private String hospitalName;

    /** 住院原因 */
    private String hospitalReason;

    /** 状态 */
    private String status;

    /** 附件 */
    private String appendix;

    /** 所属用户 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param hospitalizationInfoInsert 插入对象
     * @return HospitalizationInfoInsert
     */
    public static HospitalizationInfo insertToObj(HospitalizationInfoInsert hospitalizationInfoInsert) {
        if (hospitalizationInfoInsert == null) {
            return null;
        }
        HospitalizationInfo hospitalizationInfo = new HospitalizationInfo();
        BeanUtils.copyProperties(hospitalizationInfoInsert, hospitalizationInfo);
        return hospitalizationInfo;
    }
}
