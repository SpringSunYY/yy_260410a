package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.HealthHistoryInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.manage.model.dto.healthHistoryInfo.HealthHistoryInfoStatisticsQuery;
import com.lz.manage.model.vo.healthHistoryInfo.HealthHistoryInfoStatisticsVo;

/**
 * 健康记录Mapper接口
 * 
 * @author YY
 * @date 2026-04-13
 */
public interface HealthHistoryInfoMapper extends BaseMapper<HealthHistoryInfo>
{
    /**
     * 查询健康记录
     * 
     * @param id 健康记录主键
     * @return 健康记录
     */
    public HealthHistoryInfo selectHealthHistoryInfoById(Long id);

    /**
     * 查询健康记录列表
     * 
     * @param healthHistoryInfo 健康记录
     * @return 健康记录集合
     */
    public List<HealthHistoryInfo> selectHealthHistoryInfoList(HealthHistoryInfo healthHistoryInfo);

    /**
     * 新增健康记录
     * 
     * @param healthHistoryInfo 健康记录
     * @return 结果
     */
    public int insertHealthHistoryInfo(HealthHistoryInfo healthHistoryInfo);

    /**
     * 修改健康记录
     * 
     * @param healthHistoryInfo 健康记录
     * @return 结果
     */
    public int updateHealthHistoryInfo(HealthHistoryInfo healthHistoryInfo);

    /**
     * 删除健康记录
     * 
     * @param id 健康记录主键
     * @return 结果
     */
    public int deleteHealthHistoryInfoById(Long id);

    /**
     * 批量删除健康记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHealthHistoryInfoByIds(Long[] ids);

    List<HealthHistoryInfoStatisticsVo> healthHistoryInfoStatistics(HealthHistoryInfoStatisticsQuery healthHistoryInfoQuery);
}
