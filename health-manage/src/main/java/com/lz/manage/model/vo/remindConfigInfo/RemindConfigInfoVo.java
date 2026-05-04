package com.lz.manage.model.vo.remindConfigInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.RemindConfigInfo;
/**
 * 提醒配置Vo对象 tb_remind_config_info
 *
 * @author YY
 * @date 2026-05-04
 */
@Data
public class RemindConfigInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 居民 */
    private Long residentId;

    /** 提醒类型 */
    private String remindType;

    /** 提醒标题 */
    private String remindTitle;

    /** 提醒内容 */
    private String remindContent;

    /** 提醒时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date remindTime;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /** 每天提醒次数 */
    private Long remindFrequency;

    /** 提醒间隔分钟 */
    private Long remindInterval;

    /** 上次提醒时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastRemindTime;

    /** 下次提醒时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date nextRemindTime;

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
     * @param remindConfigInfo RemindConfigInfo实体对象
     * @return RemindConfigInfoVo
     */
    public static RemindConfigInfoVo objToVo(RemindConfigInfo remindConfigInfo) {
        if (remindConfigInfo == null) {
            return null;
        }
        RemindConfigInfoVo remindConfigInfoVo = new RemindConfigInfoVo();
        BeanUtils.copyProperties(remindConfigInfo, remindConfigInfoVo);
        return remindConfigInfoVo;
    }
}
