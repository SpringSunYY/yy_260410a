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
import com.lz.manage.mapper.ResidentInfoMapper;
import com.lz.manage.model.domain.ResidentInfo;
import com.lz.manage.service.IResidentInfoService;
import com.lz.manage.model.dto.residentInfo.ResidentInfoQuery;
import com.lz.manage.model.vo.residentInfo.ResidentInfoVo;

/**
 * 居民信息Service业务层处理
 *
 * @author YY
 * @date 2026-04-13
 */
@Service
public class ResidentInfoServiceImpl extends ServiceImpl<ResidentInfoMapper, ResidentInfo> implements IResidentInfoService
{

    @Resource
    private ResidentInfoMapper residentInfoMapper;

    //region mybatis代码
    /**
     * 查询居民信息
     *
     * @param id 居民信息主键
     * @return 居民信息
     */
    @Override
    public ResidentInfo selectResidentInfoById(Long id)
    {
        return residentInfoMapper.selectResidentInfoById(id);
    }

    /**
     * 查询居民信息列表
     *
     * @param residentInfo 居民信息
     * @return 居民信息
     */
    @Override
    public List<ResidentInfo> selectResidentInfoList(ResidentInfo residentInfo)
    {
        return residentInfoMapper.selectResidentInfoList(residentInfo);
    }

    /**
     * 新增居民信息
     *
     * @param residentInfo 居民信息
     * @return 结果
     */
    @Override
    public int insertResidentInfo(ResidentInfo residentInfo)
    {
        residentInfo.setCreateTime(DateUtils.getNowDate());
        return residentInfoMapper.insertResidentInfo(residentInfo);
    }

    /**
     * 修改居民信息
     *
     * @param residentInfo 居民信息
     * @return 结果
     */
    @Override
    public int updateResidentInfo(ResidentInfo residentInfo)
    {
        residentInfo.setUpdateTime(DateUtils.getNowDate());
        return residentInfoMapper.updateResidentInfo(residentInfo);
    }

    /**
     * 批量删除居民信息
     *
     * @param ids 需要删除的居民信息主键
     * @return 结果
     */
    @Override
    public int deleteResidentInfoByIds(Long[] ids)
    {
        return residentInfoMapper.deleteResidentInfoByIds(ids);
    }

    /**
     * 删除居民信息信息
     *
     * @param id 居民信息主键
     * @return 结果
     */
    @Override
    public int deleteResidentInfoById(Long id)
    {
        return residentInfoMapper.deleteResidentInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<ResidentInfo> getQueryWrapper(ResidentInfoQuery residentInfoQuery){
        QueryWrapper<ResidentInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = residentInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        String residentName = residentInfoQuery.getResidentName();
        queryWrapper.like(StringUtils.isNotEmpty(residentName) ,"resident_name",residentName);

        String gender = residentInfoQuery.getGender();
        queryWrapper.eq(StringUtils.isNotEmpty(gender) ,"gender",gender);

        Long userId = residentInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        String createBy = residentInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = residentInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<ResidentInfoVo> convertVoList(List<ResidentInfo> residentInfoList) {
        if (StringUtils.isEmpty(residentInfoList)) {
            return Collections.emptyList();
        }
        return residentInfoList.stream().map(ResidentInfoVo::objToVo).collect(Collectors.toList());
    }
}
