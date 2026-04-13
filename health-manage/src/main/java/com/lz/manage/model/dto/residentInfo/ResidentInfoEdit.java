package com.lz.manage.model.dto.residentInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.ResidentInfo;
/**
 * 居民信息Vo对象 tb_resident_info
 *
 * @author YY
 * @date 2026-04-13
 */
@Data
public class ResidentInfoEdit implements Serializable
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

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param residentInfoEdit 编辑对象
     * @return ResidentInfo
     */
    public static ResidentInfo editToObj(ResidentInfoEdit residentInfoEdit) {
        if (residentInfoEdit == null) {
            return null;
        }
        ResidentInfo residentInfo = new ResidentInfo();
        BeanUtils.copyProperties(residentInfoEdit, residentInfo);
        return residentInfo;
    }
}
