package com.lz.manage.model.dto.remindInfo;

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
import com.lz.manage.model.domain.RemindInfo;
/**
 * 提醒记录Query对象 tb_remind_info
 *
 * @author YY
 * @date 2026-05-04
 */
@Data
public class RemindInfoQuery implements Serializable
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

    /** 已读状态 */
    private String readStatus;

    /** 已读时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date readTime;

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
     * @param remindInfoQuery 查询对象
     * @return RemindInfo
     */
    public static RemindInfo queryToObj(RemindInfoQuery remindInfoQuery) {
        if (remindInfoQuery == null) {
            return null;
        }
        RemindInfo remindInfo = new RemindInfo();
        BeanUtils.copyProperties(remindInfoQuery, remindInfo);
        return remindInfo;
    }
}
