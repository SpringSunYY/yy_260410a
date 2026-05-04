package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.RemindInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 提醒记录Mapper接口
 * 
 * @author YY
 * @date 2026-05-04
 */
public interface RemindInfoMapper extends BaseMapper<RemindInfo>
{
    /**
     * 查询提醒记录
     * 
     * @param id 提醒记录主键
     * @return 提醒记录
     */
    public RemindInfo selectRemindInfoById(Long id);

    /**
     * 查询提醒记录列表
     * 
     * @param remindInfo 提醒记录
     * @return 提醒记录集合
     */
    public List<RemindInfo> selectRemindInfoList(RemindInfo remindInfo);

    /**
     * 新增提醒记录
     * 
     * @param remindInfo 提醒记录
     * @return 结果
     */
    public int insertRemindInfo(RemindInfo remindInfo);

    /**
     * 修改提醒记录
     * 
     * @param remindInfo 提醒记录
     * @return 结果
     */
    public int updateRemindInfo(RemindInfo remindInfo);

    /**
     * 删除提醒记录
     * 
     * @param id 提醒记录主键
     * @return 结果
     */
    public int deleteRemindInfoById(Long id);

    /**
     * 批量删除提醒记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRemindInfoByIds(Long[] ids);
}
