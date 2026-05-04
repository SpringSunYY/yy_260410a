package com.lz.manage.model.dto.remindInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.RemindInfo;
/**
 * 提醒记录Vo对象 tb_remind_info
 *
 * @author YY
 * @date 2026-05-04
 */
@Data
public class RemindInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 配置 */
    private Long reminderId;

    /** 居民 */
    private Long residentId;

    /** 提醒类型 */
    private String reminderType;

    /** 提醒标题 */
    private String reminderTitle;

    /** 提醒内容 */
    private String reminderContent;

    /** 提醒时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date remindTime;

    /** 已读状态 */
    private String readStatus;

    /** 已读时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date readTime;

    /** 所属用户 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param remindInfoEdit 编辑对象
     * @return RemindInfo
     */
    public static RemindInfo editToObj(RemindInfoEdit remindInfoEdit) {
        if (remindInfoEdit == null) {
            return null;
        }
        RemindInfo remindInfo = new RemindInfo();
        BeanUtils.copyProperties(remindInfoEdit, remindInfo);
        return remindInfo;
    }
}
