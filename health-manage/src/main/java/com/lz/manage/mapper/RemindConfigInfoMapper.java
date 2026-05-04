package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.RemindConfigInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 提醒配置Mapper接口
 * 
 * @author YY
 * @date 2026-05-04
 */
public interface RemindConfigInfoMapper extends BaseMapper<RemindConfigInfo>
{
    /**
     * 查询提醒配置
     * 
     * @param id 提醒配置主键
     * @return 提醒配置
     */
    public RemindConfigInfo selectRemindConfigInfoById(Long id);

    /**
     * 查询提醒配置列表
     * 
     * @param remindConfigInfo 提醒配置
     * @return 提醒配置集合
     */
    public List<RemindConfigInfo> selectRemindConfigInfoList(RemindConfigInfo remindConfigInfo);

    /**
     * 新增提醒配置
     * 
     * @param remindConfigInfo 提醒配置
     * @return 结果
     */
    public int insertRemindConfigInfo(RemindConfigInfo remindConfigInfo);

    /**
     * 修改提醒配置
     * 
     * @param remindConfigInfo 提醒配置
     * @return 结果
     */
    public int updateRemindConfigInfo(RemindConfigInfo remindConfigInfo);

    /**
     * 删除提醒配置
     * 
     * @param id 提醒配置主键
     * @return 结果
     */
    public int deleteRemindConfigInfoById(Long id);

    /**
     * 批量删除提醒配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRemindConfigInfoByIds(Long[] ids);
}
