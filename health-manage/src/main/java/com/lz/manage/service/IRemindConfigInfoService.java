package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.RemindConfigInfo;
import com.lz.manage.model.vo.remindConfigInfo.RemindConfigInfoVo;
import com.lz.manage.model.dto.remindConfigInfo.RemindConfigInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 提醒配置Service接口
 * 
 * @author YY
 * @date 2026-05-04
 */
public interface IRemindConfigInfoService extends IService<RemindConfigInfo>
{
    //region mybatis代码
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
     * 批量删除提醒配置
     * 
     * @param ids 需要删除的提醒配置主键集合
     * @return 结果
     */
    public int deleteRemindConfigInfoByIds(Long[] ids);

    /**
     * 删除提醒配置信息
     * 
     * @param id 提醒配置主键
     * @return 结果
     */
    public int deleteRemindConfigInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param remindConfigInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<RemindConfigInfo> getQueryWrapper(RemindConfigInfoQuery remindConfigInfoQuery);

    /**
     * 转换vo
     *
     * @param remindConfigInfoList RemindConfigInfo集合
     * @return RemindConfigInfoVO集合
     */
    List<RemindConfigInfoVo> convertVoList(List<RemindConfigInfo> remindConfigInfoList);
}
