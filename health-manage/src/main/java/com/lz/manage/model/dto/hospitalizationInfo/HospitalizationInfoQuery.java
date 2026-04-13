package com.lz.manage.model.dto.hospitalizationInfo;

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
import com.lz.manage.model.domain.HospitalizationInfo;
/**
 * 住院记录Query对象 tb_hospitalization_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class HospitalizationInfoQuery implements Serializable
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

    /** 状态 */
    private String status;

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
     * @param hospitalizationInfoQuery 查询对象
     * @return HospitalizationInfo
     */
    public static HospitalizationInfo queryToObj(HospitalizationInfoQuery hospitalizationInfoQuery) {
        if (hospitalizationInfoQuery == null) {
            return null;
        }
        HospitalizationInfo hospitalizationInfo = new HospitalizationInfo();
        BeanUtils.copyProperties(hospitalizationInfoQuery, hospitalizationInfo);
        return hospitalizationInfo;
    }
}
