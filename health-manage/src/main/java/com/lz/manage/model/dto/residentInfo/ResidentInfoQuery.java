package com.lz.manage.model.dto.residentInfo;

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
import com.lz.manage.model.domain.ResidentInfo;
/**
 * 居民信息Query对象 tb_resident_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class ResidentInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 居民名称 */
    private String residentName;

    /** 性别 */
    private String gender;

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
     * @param residentInfoQuery 查询对象
     * @return ResidentInfo
     */
    public static ResidentInfo queryToObj(ResidentInfoQuery residentInfoQuery) {
        if (residentInfoQuery == null) {
            return null;
        }
        ResidentInfo residentInfo = new ResidentInfo();
        BeanUtils.copyProperties(residentInfoQuery, residentInfo);
        return residentInfo;
    }
}
