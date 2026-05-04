package com.lz.manage.controller;

import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.core.page.TableDataInfo;
import com.lz.common.enums.BusinessType;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.manage.model.domain.RemindConfigInfo;
import com.lz.manage.model.dto.remindConfigInfo.RemindConfigInfoEdit;
import com.lz.manage.model.dto.remindConfigInfo.RemindConfigInfoInsert;
import com.lz.manage.model.dto.remindConfigInfo.RemindConfigInfoQuery;
import com.lz.manage.model.vo.remindConfigInfo.RemindConfigInfoVo;
import com.lz.manage.service.IRemindConfigInfoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 提醒配置Controller
 *
 * @author YY
 * @date 2026-05-04
 */
@RestController
@RequestMapping("/manage/remindConfigInfo")
public class RemindConfigInfoController extends BaseController {
    @Resource
    private IRemindConfigInfoService remindConfigInfoService;

    /**
     * 查询提醒配置列表
     */
    @PreAuthorize("@ss.hasPermi('manage:remindConfigInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(RemindConfigInfoQuery remindConfigInfoQuery) {
        RemindConfigInfo remindConfigInfo = RemindConfigInfoQuery.queryToObj(remindConfigInfoQuery);
        startPage();
        List<RemindConfigInfo> list = remindConfigInfoService.selectRemindConfigInfoList(remindConfigInfo);
        List<RemindConfigInfoVo> listVo = list.stream().map(RemindConfigInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出提醒配置列表
     */
    @PreAuthorize("@ss.hasPermi('manage:remindConfigInfo:export')")
    @Log(title = "提醒配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RemindConfigInfoQuery remindConfigInfoQuery) {
        RemindConfigInfo remindConfigInfo = RemindConfigInfoQuery.queryToObj(remindConfigInfoQuery);
        List<RemindConfigInfo> list = remindConfigInfoService.selectRemindConfigInfoList(remindConfigInfo);
        ExcelUtil<RemindConfigInfo> util = new ExcelUtil<RemindConfigInfo>(RemindConfigInfo.class);
        util.exportExcel(response, list, "提醒配置数据");
    }

    /**
     * 获取提醒配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:remindConfigInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        RemindConfigInfo remindConfigInfo = remindConfigInfoService.selectRemindConfigInfoById(id);
        return success(RemindConfigInfoVo.objToVo(remindConfigInfo));
    }

    /**
     * 新增提醒配置
     */
    @PreAuthorize("@ss.hasPermi('manage:remindConfigInfo:add')")
    @Log(title = "提醒配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RemindConfigInfoInsert remindConfigInfoInsert) {
        RemindConfigInfo remindConfigInfo = RemindConfigInfoInsert.insertToObj(remindConfigInfoInsert);
        return toAjax(remindConfigInfoService.insertRemindConfigInfo(remindConfigInfo));
    }

    /**
     * 修改提醒配置
     */
    @PreAuthorize("@ss.hasPermi('manage:remindConfigInfo:edit')")
    @Log(title = "提醒配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RemindConfigInfoEdit remindConfigInfoEdit) {
        RemindConfigInfo remindConfigInfo = RemindConfigInfoEdit.editToObj(remindConfigInfoEdit);
        return toAjax(remindConfigInfoService.updateRemindConfigInfo(remindConfigInfo));
    }

    /**
     * 删除提醒配置
     */
    @PreAuthorize("@ss.hasPermi('manage:remindConfigInfo:remove')")
    @Log(title = "提醒配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(remindConfigInfoService.deleteRemindConfigInfoByIds(ids));
    }
}
