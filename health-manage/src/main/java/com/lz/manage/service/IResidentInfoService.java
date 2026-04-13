package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.ResidentInfo;
import com.lz.manage.model.vo.residentInfo.ResidentInfoVo;
import com.lz.manage.model.dto.residentInfo.ResidentInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 居民信息Service接口
 * 
 * @author YY
 * @date 2026-04-13
 */
public interface IResidentInfoService extends IService<ResidentInfo>
{
    //region mybatis代码
    /**
     * 查询居民信息
     * 
     * @param id 居民信息主键
     * @return 居民信息
     */
    public ResidentInfo selectResidentInfoById(Long id);

    /**
     * 查询居民信息列表
     * 
     * @param residentInfo 居民信息
     * @return 居民信息集合
     */
    public List<ResidentInfo> selectResidentInfoList(ResidentInfo residentInfo);

    /**
     * 新增居民信息
     * 
     * @param residentInfo 居民信息
     * @return 结果
     */
    public int insertResidentInfo(ResidentInfo residentInfo);

    /**
     * 修改居民信息
     * 
     * @param residentInfo 居民信息
     * @return 结果
     */
    public int updateResidentInfo(ResidentInfo residentInfo);

    /**
     * 批量删除居民信息
     * 
     * @param ids 需要删除的居民信息主键集合
     * @return 结果
     */
    public int deleteResidentInfoByIds(Long[] ids);

    /**
     * 删除居民信息信息
     * 
     * @param id 居民信息主键
     * @return 结果
     */
    public int deleteResidentInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param residentInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<ResidentInfo> getQueryWrapper(ResidentInfoQuery residentInfoQuery);

    /**
     * 转换vo
     *
     * @param residentInfoList ResidentInfo集合
     * @return ResidentInfoVO集合
     */
    List<ResidentInfoVo> convertVoList(List<ResidentInfo> residentInfoList);
}
