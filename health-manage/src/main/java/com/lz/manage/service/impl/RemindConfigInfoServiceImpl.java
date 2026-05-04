package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.annotation.DataScope;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.RemindConfigInfoMapper;
import com.lz.manage.model.domain.RemindConfigInfo;
import com.lz.manage.model.domain.ResidentInfo;
import com.lz.manage.model.dto.remindConfigInfo.RemindConfigInfoQuery;
import com.lz.manage.model.vo.remindConfigInfo.RemindConfigInfoVo;
import com.lz.manage.service.IRemindConfigInfoService;
import com.lz.manage.service.IResidentInfoService;
import com.lz.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 提醒配置Service业务层处理
 *
 * @author YY
 * @date 2026-05-04
 */
@Service
public class RemindConfigInfoServiceImpl extends ServiceImpl<RemindConfigInfoMapper, RemindConfigInfo> implements IRemindConfigInfoService {

    @Resource
    private RemindConfigInfoMapper remindConfigInfoMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IResidentInfoService residentInfoService;

    //region mybatis代码

    /**
     * 查询提醒配置
     *
     * @param id 提醒配置主键
     * @return 提醒配置
     */
    @Override
    public RemindConfigInfo selectRemindConfigInfoById(Long id) {
        return remindConfigInfoMapper.selectRemindConfigInfoById(id);
    }

    /**
     * 查询提醒配置列表
     *
     * @param remindConfigInfo 提醒配置
     * @return 提醒配置
     */
    @Override
    @DataScope(deptAlias = "tb_remind_config_info", userAlias = "tb_remind_config_info")
    public List<RemindConfigInfo> selectRemindConfigInfoList(RemindConfigInfo remindConfigInfo) {
        List<RemindConfigInfo> remindConfigInfos = remindConfigInfoMapper.selectRemindConfigInfoList(remindConfigInfo);
        for (RemindConfigInfo info : remindConfigInfos) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            ResidentInfo residentInfo = residentInfoService.selectResidentInfoById(info.getResidentId());
            if (StringUtils.isNotNull(residentInfo)) {
                info.setResidentName(residentInfo.getResidentName());
            }
        }
        return remindConfigInfos;
    }

    /**
     * 新增提醒配置
     *
     * @param remindConfigInfo 提醒配置
     * @return 结果
     */
    @Override
    public int insertRemindConfigInfo(RemindConfigInfo remindConfigInfo) {
        //先查询居民是否存在
        ResidentInfo residentInfo = residentInfoService.selectResidentInfoById(remindConfigInfo.getResidentId());
        if (StringUtils.isNull(residentInfo)) {
            throw new ServiceException("居民不存在");
        }
        remindConfigInfo.setUserId(residentInfo.getUserId());

        initCalculateNextRemindTime(remindConfigInfo);

        remindConfigInfo.setCreateBy(SecurityUtils.getUsername());
        remindConfigInfo.setCreateTime(DateUtils.getNowDate());
        return remindConfigInfoMapper.insertRemindConfigInfo(remindConfigInfo);
    }

    private static void initCalculateNextRemindTime(RemindConfigInfo remindConfigInfo) {
        // 1. 开始时间不能晚于结束时间（可以为同一天）
        Date startTime = remindConfigInfo.getStartTime();
        Date endTime = remindConfigInfo.getEndTime();
        if (StringUtils.isNotNull(startTime) && StringUtils.isNotNull(endTime)) {
            LocalDate start = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate end = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (start.isAfter(end)) {
                throw new ServiceException("开始时间不能晚于结束时间");
            }
        }

        // 2. 计算下次提醒时间 nextRemindTime = startTime 日期 + remindTime 时间
        LocalTime remindTime = remindConfigInfo.getRemindTime();

        if (StringUtils.isNotNull(startTime) && StringUtils.isNotNull(remindTime)) {
            LocalDate startDate = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDateTime nextDt = LocalDateTime.of(startDate, remindTime);
            remindConfigInfo.setNextRemindTime(DateUtils.toDate(nextDt));
        }
    }

    /**
     * 修改提醒配置
     *
     * @param remindConfigInfo 提醒配置
     * @return 结果
     */
    @Override
    public int updateRemindConfigInfo(RemindConfigInfo remindConfigInfo) {
        //先查询居民是否存在
        ResidentInfo residentInfo = residentInfoService.selectResidentInfoById(remindConfigInfo.getResidentId());
        if (StringUtils.isNull(residentInfo)) {
            throw new ServiceException("居民不存在");
        }
        //查询数据库内容
        RemindConfigInfo remindConfigInfoOld = remindConfigInfoMapper.selectRemindConfigInfoById(remindConfigInfo.getId());
        if (StringUtils.isNull(remindConfigInfoOld)) {
            throw new ServiceException("提醒配置不存在");
        }
        if (!remindConfigInfo.getResidentId().equals(remindConfigInfoOld.getResidentId())) {
            throw new ServiceException("居民不可修改");
        }
        initCalculateNextRemindTime(remindConfigInfo);
        remindConfigInfo.setUserId(residentInfo.getUserId());
        remindConfigInfo.setUpdateBy(SecurityUtils.getUsername());
        remindConfigInfo.setUpdateTime(DateUtils.getNowDate());
        return remindConfigInfoMapper.updateRemindConfigInfo(remindConfigInfo);
    }

    /**
     * 批量删除提醒配置
     *
     * @param ids 需要删除的提醒配置主键
     * @return 结果
     */
    @Override
    public int deleteRemindConfigInfoByIds(Long[] ids) {
        return remindConfigInfoMapper.deleteRemindConfigInfoByIds(ids);
    }

    /**
     * 删除提醒配置信息
     *
     * @param id 提醒配置主键
     * @return 结果
     */
    @Override
    public int deleteRemindConfigInfoById(Long id) {
        return remindConfigInfoMapper.deleteRemindConfigInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<RemindConfigInfo> getQueryWrapper(RemindConfigInfoQuery remindConfigInfoQuery) {
        QueryWrapper<RemindConfigInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = remindConfigInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = remindConfigInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long residentId = remindConfigInfoQuery.getResidentId();
        queryWrapper.eq(StringUtils.isNotNull(residentId), "resident_id", residentId);

        String remindType = remindConfigInfoQuery.getRemindType();
        queryWrapper.eq(StringUtils.isNotEmpty(remindType), "remind_type", remindType);

        String remindTitle = remindConfigInfoQuery.getRemindTitle();
        queryWrapper.like(StringUtils.isNotEmpty(remindTitle), "remind_title", remindTitle);

        Long userId = remindConfigInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        String createBy = remindConfigInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy), "create_by", createBy);

        Date createTime = remindConfigInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<RemindConfigInfoVo> convertVoList(List<RemindConfigInfo> remindConfigInfoList) {
        if (StringUtils.isEmpty(remindConfigInfoList)) {
            return Collections.emptyList();
        }
        return remindConfigInfoList.stream().map(RemindConfigInfoVo::objToVo).collect(Collectors.toList());
    }
}
