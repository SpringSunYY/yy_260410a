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
import com.lz.manage.model.domain.HealthHistoryInfo;
import com.lz.manage.model.vo.healthHistoryInfo.HealthHistoryInfoVo;
import com.lz.manage.model.dto.healthHistoryInfo.HealthHistoryInfoQuery;
import com.lz.manage.model.dto.healthHistoryInfo.HealthHistoryInfoInsert;
import com.lz.manage.model.dto.healthHistoryInfo.HealthHistoryInfoEdit;
import com.lz.manage.service.IHealthHistoryInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 健康记录Controller
 *
 * @author YY
 * @date 2026-04-13
 */
@RestController
@RequestMapping("/manage/healthHistoryInfo")
public class HealthHistoryInfoController extends BaseController
{
    @Resource
    private IHealthHistoryInfoService healthHistoryInfoService;

    /**
     * 查询健康记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:healthHistoryInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(HealthHistoryInfoQuery healthHistoryInfoQuery)
    {
        HealthHistoryInfo healthHistoryInfo = HealthHistoryInfoQuery.queryToObj(healthHistoryInfoQuery);
        startPage();
        List<HealthHistoryInfo> list = healthHistoryInfoService.selectHealthHistoryInfoList(healthHistoryInfo);
        List<HealthHistoryInfoVo> listVo= list.stream().map(HealthHistoryInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出健康记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:healthHistoryInfo:export')")
    @Log(title = "健康记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HealthHistoryInfoQuery healthHistoryInfoQuery)
    {
        HealthHistoryInfo healthHistoryInfo = HealthHistoryInfoQuery.queryToObj(healthHistoryInfoQuery);
        List<HealthHistoryInfo> list = healthHistoryInfoService.selectHealthHistoryInfoList(healthHistoryInfo);
        ExcelUtil<HealthHistoryInfo> util = new ExcelUtil<HealthHistoryInfo>(HealthHistoryInfo.class);
        util.exportExcel(response, list, "健康记录数据");
    }

    /**
     * 获取健康记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:healthHistoryInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        HealthHistoryInfo healthHistoryInfo = healthHistoryInfoService.selectHealthHistoryInfoById(id);
        return success(HealthHistoryInfoVo.objToVo(healthHistoryInfo));
    }

    /**
     * 新增健康记录
     */
    @PreAuthorize("@ss.hasPermi('manage:healthHistoryInfo:add')")
    @Log(title = "健康记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HealthHistoryInfoInsert healthHistoryInfoInsert)
    {
        HealthHistoryInfo healthHistoryInfo = HealthHistoryInfoInsert.insertToObj(healthHistoryInfoInsert);
        return toAjax(healthHistoryInfoService.insertHealthHistoryInfo(healthHistoryInfo));
    }

    /**
     * 修改健康记录
     */
    @PreAuthorize("@ss.hasPermi('manage:healthHistoryInfo:edit')")
    @Log(title = "健康记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HealthHistoryInfoEdit healthHistoryInfoEdit)
    {
        HealthHistoryInfo healthHistoryInfo = HealthHistoryInfoEdit.editToObj(healthHistoryInfoEdit);
        return toAjax(healthHistoryInfoService.updateHealthHistoryInfo(healthHistoryInfo));
    }

    /**
     * 删除健康记录
     */
    @PreAuthorize("@ss.hasPermi('manage:healthHistoryInfo:remove')")
    @Log(title = "健康记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(healthHistoryInfoService.deleteHealthHistoryInfoByIds(ids));
    }
}
