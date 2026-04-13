package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.HospitalizationInfo;
import com.lz.manage.model.vo.hospitalizationInfo.HospitalizationInfoVo;
import com.lz.manage.model.dto.hospitalizationInfo.HospitalizationInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 住院记录Service接口
 * 
 * @author YY
 * @date 2026-04-13
 */
public interface IHospitalizationInfoService extends IService<HospitalizationInfo>
{
    //region mybatis代码
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
     * 批量删除住院记录
     * 
     * @param ids 需要删除的住院记录主键集合
     * @return 结果
     */
    public int deleteHospitalizationInfoByIds(Long[] ids);

    /**
     * 删除住院记录信息
     * 
     * @param id 住院记录主键
     * @return 结果
     */
    public int deleteHospitalizationInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param hospitalizationInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<HospitalizationInfo> getQueryWrapper(HospitalizationInfoQuery hospitalizationInfoQuery);

    /**
     * 转换vo
     *
     * @param hospitalizationInfoList HospitalizationInfo集合
     * @return HospitalizationInfoVO集合
     */
    List<HospitalizationInfoVo> convertVoList(List<HospitalizationInfo> hospitalizationInfoList);
}
