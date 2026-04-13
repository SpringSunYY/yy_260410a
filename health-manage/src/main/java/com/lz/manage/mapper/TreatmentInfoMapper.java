package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.TreatmentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 诊疗记录Mapper接口
 * 
 * @author YY
 * @date 2026-04-13
 */
public interface TreatmentInfoMapper extends BaseMapper<TreatmentInfo>
{
    /**
     * 查询诊疗记录
     * 
     * @param id 诊疗记录主键
     * @return 诊疗记录
     */
    public TreatmentInfo selectTreatmentInfoById(Long id);

    /**
     * 查询诊疗记录列表
     * 
     * @param treatmentInfo 诊疗记录
     * @return 诊疗记录集合
     */
    public List<TreatmentInfo> selectTreatmentInfoList(TreatmentInfo treatmentInfo);

    /**
     * 新增诊疗记录
     * 
     * @param treatmentInfo 诊疗记录
     * @return 结果
     */
    public int insertTreatmentInfo(TreatmentInfo treatmentInfo);

    /**
     * 修改诊疗记录
     * 
     * @param treatmentInfo 诊疗记录
     * @return 结果
     */
    public int updateTreatmentInfo(TreatmentInfo treatmentInfo);

    /**
     * 删除诊疗记录
     * 
     * @param id 诊疗记录主键
     * @return 结果
     */
    public int deleteTreatmentInfoById(Long id);

    /**
     * 批量删除诊疗记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTreatmentInfoByIds(Long[] ids);
}
