package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.TreatmentInfoMapper;
import com.lz.manage.model.domain.TreatmentInfo;
import com.lz.manage.service.ITreatmentInfoService;
import com.lz.manage.model.dto.treatmentInfo.TreatmentInfoQuery;
import com.lz.manage.model.vo.treatmentInfo.TreatmentInfoVo;

/**
 * 诊疗记录Service业务层处理
 *
 * @author YY
 * @date 2026-04-13
 */
@Service
public class TreatmentInfoServiceImpl extends ServiceImpl<TreatmentInfoMapper, TreatmentInfo> implements ITreatmentInfoService
{

    @Resource
    private TreatmentInfoMapper treatmentInfoMapper;

    //region mybatis代码
    /**
     * 查询诊疗记录
     *
     * @param id 诊疗记录主键
     * @return 诊疗记录
     */
    @Override
    public TreatmentInfo selectTreatmentInfoById(Long id)
    {
        return treatmentInfoMapper.selectTreatmentInfoById(id);
    }

    /**
     * 查询诊疗记录列表
     *
     * @param treatmentInfo 诊疗记录
     * @return 诊疗记录
     */
    @Override
    public List<TreatmentInfo> selectTreatmentInfoList(TreatmentInfo treatmentInfo)
    {
        return treatmentInfoMapper.selectTreatmentInfoList(treatmentInfo);
    }

    /**
     * 新增诊疗记录
     *
     * @param treatmentInfo 诊疗记录
     * @return 结果
     */
    @Override
    public int insertTreatmentInfo(TreatmentInfo treatmentInfo)
    {
        treatmentInfo.setCreateTime(DateUtils.getNowDate());
        return treatmentInfoMapper.insertTreatmentInfo(treatmentInfo);
    }

    /**
     * 修改诊疗记录
     *
     * @param treatmentInfo 诊疗记录
     * @return 结果
     */
    @Override
    public int updateTreatmentInfo(TreatmentInfo treatmentInfo)
    {
        treatmentInfo.setUpdateTime(DateUtils.getNowDate());
        return treatmentInfoMapper.updateTreatmentInfo(treatmentInfo);
    }

    /**
     * 批量删除诊疗记录
     *
     * @param ids 需要删除的诊疗记录主键
     * @return 结果
     */
    @Override
    public int deleteTreatmentInfoByIds(Long[] ids)
    {
        return treatmentInfoMapper.deleteTreatmentInfoByIds(ids);
    }

    /**
     * 删除诊疗记录信息
     *
     * @param id 诊疗记录主键
     * @return 结果
     */
    @Override
    public int deleteTreatmentInfoById(Long id)
    {
        return treatmentInfoMapper.deleteTreatmentInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<TreatmentInfo> getQueryWrapper(TreatmentInfoQuery treatmentInfoQuery){
        QueryWrapper<TreatmentInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = treatmentInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = treatmentInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long residentId = treatmentInfoQuery.getResidentId();
        queryWrapper.eq(StringUtils.isNotNull(residentId) ,"resident_id",residentId);

        Date visitTime = treatmentInfoQuery.getVisitTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginVisitTime"))&&StringUtils.isNotNull(params.get("endVisitTime")),"visit_time",params.get("beginVisitTime"),params.get("endVisitTime"));

        Long userId = treatmentInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        String createBy = treatmentInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = treatmentInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<TreatmentInfoVo> convertVoList(List<TreatmentInfo> treatmentInfoList) {
        if (StringUtils.isEmpty(treatmentInfoList)) {
            return Collections.emptyList();
        }
        return treatmentInfoList.stream().map(TreatmentInfoVo::objToVo).collect(Collectors.toList());
    }
}
