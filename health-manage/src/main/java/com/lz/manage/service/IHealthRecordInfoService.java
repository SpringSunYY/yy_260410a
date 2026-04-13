package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.HealthRecordInfo;
import com.lz.manage.model.vo.healthRecordInfo.HealthRecordInfoVo;
import com.lz.manage.model.dto.healthRecordInfo.HealthRecordInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 健康档案Service接口
 * 
 * @author YY
 * @date 2026-04-13
 */
public interface IHealthRecordInfoService extends IService<HealthRecordInfo>
{
    //region mybatis代码
    /**
     * 查询健康档案
     * 
     * @param id 健康档案主键
     * @return 健康档案
     */
    public HealthRecordInfo selectHealthRecordInfoById(Long id);

    /**
     * 查询健康档案列表
     * 
     * @param healthRecordInfo 健康档案
     * @return 健康档案集合
     */
    public List<HealthRecordInfo> selectHealthRecordInfoList(HealthRecordInfo healthRecordInfo);

    /**
     * 新增健康档案
     * 
     * @param healthRecordInfo 健康档案
     * @return 结果
     */
    public int insertHealthRecordInfo(HealthRecordInfo healthRecordInfo);

    /**
     * 修改健康档案
     * 
     * @param healthRecordInfo 健康档案
     * @return 结果
     */
    public int updateHealthRecordInfo(HealthRecordInfo healthRecordInfo);

    /**
     * 批量删除健康档案
     * 
     * @param ids 需要删除的健康档案主键集合
     * @return 结果
     */
    public int deleteHealthRecordInfoByIds(Long[] ids);

    /**
     * 删除健康档案信息
     * 
     * @param id 健康档案主键
     * @return 结果
     */
    public int deleteHealthRecordInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param healthRecordInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<HealthRecordInfo> getQueryWrapper(HealthRecordInfoQuery healthRecordInfoQuery);

    /**
     * 转换vo
     *
     * @param healthRecordInfoList HealthRecordInfo集合
     * @return HealthRecordInfoVO集合
     */
    List<HealthRecordInfoVo> convertVoList(List<HealthRecordInfo> healthRecordInfoList);
}
