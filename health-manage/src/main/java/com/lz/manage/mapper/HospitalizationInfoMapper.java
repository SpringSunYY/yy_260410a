package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.HospitalizationInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 住院记录Mapper接口
 * 
 * @author YY
 * @date 2026-04-13
 */
public interface HospitalizationInfoMapper extends BaseMapper<HospitalizationInfo>
{
    /**
     * 查询住院记录
     * 
     * @param id 住院记录主键
     * @return 住院记录
     */
    public HospitalizationInfo selectHospitalizationInfoById(Long id);

    /**
     * 查询住院记录列表
     * 
     * @param hospitalizationInfo 住院记录
     * @return 住院记录集合
     */
    public List<HospitalizationInfo> selectHospitalizationInfoList(HospitalizationInfo hospitalizationInfo);

    /**
     * 新增住院记录
     * 
     * @param hospitalizationInfo 住院记录
     * @return 结果
     */
    public int insertHospitalizationInfo(HospitalizationInfo hospitalizationInfo);

    /**
     * 修改住院记录
     * 
     * @param hospitalizationInfo 住院记录
     * @return 结果
     */
    public int updateHospitalizationInfo(HospitalizationInfo hospitalizationInfo);

    /**
     * 删除住院记录
     * 
     * @param id 住院记录主键
     * @return 结果
     */
    public int deleteHospitalizationInfoById(Long id);

    /**
     * 批量删除住院记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHospitalizationInfoByIds(Long[] ids);
}
