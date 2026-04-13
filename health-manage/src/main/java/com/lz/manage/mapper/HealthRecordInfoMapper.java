package com.lz.manage.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.manage.model.domain.HealthRecordInfo;

import java.util.List;

/**
 * 健康档案Mapper接口
 *
 * @author YY
 * @date 2026-04-13
 */
public interface HealthRecordInfoMapper extends BaseMapper<HealthRecordInfo> {
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
     * 删除健康档案
     *
     * @param id 健康档案主键
     * @return 结果
     */
    public int deleteHealthRecordInfoById(Long id);

    /**
     * 批量删除健康档案
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHealthRecordInfoByIds(Long[] ids);

    default HealthRecordInfo selectHealthRecordInfoByCode(String recordCode) {
        return selectOne(new LambdaQueryWrapper<HealthRecordInfo>().eq(HealthRecordInfo::getRecordCode, recordCode));
    }

    ;
}
