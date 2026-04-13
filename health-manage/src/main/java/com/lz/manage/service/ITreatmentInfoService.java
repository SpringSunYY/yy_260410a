package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.TreatmentInfo;
import com.lz.manage.model.vo.treatmentInfo.TreatmentInfoVo;
import com.lz.manage.model.dto.treatmentInfo.TreatmentInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 诊疗记录Service接口
 * 
 * @author YY
 * @date 2026-04-13
 */
public interface ITreatmentInfoService extends IService<TreatmentInfo>
{
    //region mybatis代码
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
     * 批量删除诊疗记录
     * 
     * @param ids 需要删除的诊疗记录主键集合
     * @return 结果
     */
    public int deleteTreatmentInfoByIds(Long[] ids);

    /**
     * 删除诊疗记录信息
     * 
     * @param id 诊疗记录主键
     * @return 结果
     */
    public int deleteTreatmentInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param treatmentInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<TreatmentInfo> getQueryWrapper(TreatmentInfoQuery treatmentInfoQuery);

    /**
     * 转换vo
     *
     * @param treatmentInfoList TreatmentInfo集合
     * @return TreatmentInfoVO集合
     */
    List<TreatmentInfoVo> convertVoList(List<TreatmentInfo> treatmentInfoList);
}
