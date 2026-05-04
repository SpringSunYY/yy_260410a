package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.RemindInfo;
import com.lz.manage.model.vo.remindInfo.RemindInfoVo;
import com.lz.manage.model.dto.remindInfo.RemindInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 提醒记录Service接口
 * 
 * @author YY
 * @date 2026-05-04
 */
public interface IRemindInfoService extends IService<RemindInfo>
{
    //region mybatis代码
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
     * 批量删除提醒记录
     * 
     * @param ids 需要删除的提醒记录主键集合
     * @return 结果
     */
    public int deleteRemindInfoByIds(Long[] ids);

    /**
     * 删除提醒记录信息
     * 
     * @param id 提醒记录主键
     * @return 结果
     */
    public int deleteRemindInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param remindInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<RemindInfo> getQueryWrapper(RemindInfoQuery remindInfoQuery);

    /**
     * 转换vo
     *
     * @param remindInfoList RemindInfo集合
     * @return RemindInfoVO集合
     */
    List<RemindInfoVo> convertVoList(List<RemindInfo> remindInfoList);

    void autoSendRemind();
}
