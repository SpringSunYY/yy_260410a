package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.ResidentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 居民信息Mapper接口
 * 
 * @author YY
 * @date 2026-04-13
 */
public interface ResidentInfoMapper extends BaseMapper<ResidentInfo>
{
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
     * 删除居民信息
     * 
     * @param id 居民信息主键
     * @return 结果
     */
    public int deleteResidentInfoById(Long id);

    /**
     * 批量删除居民信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResidentInfoByIds(Long[] ids);
}
