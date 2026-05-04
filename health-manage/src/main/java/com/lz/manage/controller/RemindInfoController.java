package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.enums.BusinessType;
import com.lz.manage.model.domain.RemindInfo;
import com.lz.manage.model.vo.remindInfo.RemindInfoVo;
import com.lz.manage.model.dto.remindInfo.RemindInfoQuery;
import com.lz.manage.model.dto.remindInfo.RemindInfoInsert;
import com.lz.manage.model.dto.remindInfo.RemindInfoEdit;
import com.lz.manage.service.IRemindInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 提醒记录Controller
 *
 * @author YY
 * @date 2026-05-04
 */
@RestController
@RequestMapping("/manage/remindInfo")
public class RemindInfoController extends BaseController
{
    @Resource
    private IRemindInfoService remindInfoService;

    /**
     * 查询提醒记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:remindInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(RemindInfoQuery remindInfoQuery)
    {
        RemindInfo remindInfo = RemindInfoQuery.queryToObj(remindInfoQuery);
        startPage();
        List<RemindInfo> list = remindInfoService.selectRemindInfoList(remindInfo);
        List<RemindInfoVo> listVo= list.stream().map(RemindInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出提醒记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:remindInfo:export')")
    @Log(title = "提醒记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RemindInfoQuery remindInfoQuery)
    {
        RemindInfo remindInfo = RemindInfoQuery.queryToObj(remindInfoQuery);
        List<RemindInfo> list = remindInfoService.selectRemindInfoList(remindInfo);
        ExcelUtil<RemindInfo> util = new ExcelUtil<RemindInfo>(RemindInfo.class);
        util.exportExcel(response, list, "提醒记录数据");
    }

    /**
     * 获取提醒记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:remindInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        RemindInfo remindInfo = remindInfoService.selectRemindInfoById(id);
        return success(RemindInfoVo.objToVo(remindInfo));
    }

    /**
     * 新增提醒记录
     */
    @PreAuthorize("@ss.hasPermi('manage:remindInfo:add')")
    @Log(title = "提醒记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RemindInfoInsert remindInfoInsert)
    {
        RemindInfo remindInfo = RemindInfoInsert.insertToObj(remindInfoInsert);
        return toAjax(remindInfoService.insertRemindInfo(remindInfo));
    }

    /**
     * 修改提醒记录
     */
    @PreAuthorize("@ss.hasPermi('manage:remindInfo:edit')")
    @Log(title = "提醒记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RemindInfoEdit remindInfoEdit)
    {
        RemindInfo remindInfo = RemindInfoEdit.editToObj(remindInfoEdit);
        return toAjax(remindInfoService.updateRemindInfo(remindInfo));
    }

    /**
     * 删除提醒记录
     */
    @PreAuthorize("@ss.hasPermi('manage:remindInfo:remove')")
    @Log(title = "提醒记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(remindInfoService.deleteRemindInfoByIds(ids));
    }
}
