package com.lz.manage.model.vo.hospitalizationInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.HospitalizationInfo;
/**
 * 住院记录Vo对象 tb_hospitalization_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class HospitalizationInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 居民 */
    private String residentId;

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
     * @param hospitalizationInfo HospitalizationInfo实体对象
     * @return HospitalizationInfoVo
     */
    public static HospitalizationInfoVo objToVo(HospitalizationInfo hospitalizationInfo) {
        if (hospitalizationInfo == null) {
            return null;
        }
        HospitalizationInfoVo hospitalizationInfoVo = new HospitalizationInfoVo();
        BeanUtils.copyProperties(hospitalizationInfo, hospitalizationInfoVo);
        return hospitalizationInfoVo;
    }
}
