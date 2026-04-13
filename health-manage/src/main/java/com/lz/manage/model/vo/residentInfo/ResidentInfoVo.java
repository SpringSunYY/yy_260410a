package com.lz.manage.model.vo.residentInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.ResidentInfo;
/**
 * 居民信息Vo对象 tb_resident_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class ResidentInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 居民名称 */
    private String residentName;

    /** 性别 */
    private String gender;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDateTime;

    /** 身份证号 */
    private String idCard;

    /** 联系电话 */
    private String contactPhone;

    /** 居住地址 */
    private String address;

    /** 紧急联系人 */
    private String emergencyContact;

    /** 紧急联系电话 */
    private String emergencyPhone;

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
     * @param residentInfo ResidentInfo实体对象
     * @return ResidentInfoVo
     */
    public static ResidentInfoVo objToVo(ResidentInfo residentInfo) {
        if (residentInfo == null) {
            return null;
        }
        ResidentInfoVo residentInfoVo = new ResidentInfoVo();
        BeanUtils.copyProperties(residentInfo, residentInfoVo);
        return residentInfoVo;
    }
}
