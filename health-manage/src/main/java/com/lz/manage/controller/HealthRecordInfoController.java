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
import com.lz.manage.model.domain.HealthRecordInfo;
import com.lz.manage.model.vo.healthRecordInfo.HealthRecordInfoVo;
import com.lz.manage.model.dto.healthRecordInfo.HealthRecordInfoQuery;
import com.lz.manage.model.dto.healthRecordInfo.HealthRecordInfoInsert;
import com.lz.manage.model.dto.healthRecordInfo.HealthRecordInfoEdit;
import com.lz.manage.service.IHealthRecordInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 健康档案Controller
 *
 * @author YY
 * @date 2026-04-13
 */
@RestController
@RequestMapping("/manage/healthRecordInfo")
public class HealthRecordInfoController extends BaseController
{
    @Resource
    private IHealthRecordInfoService healthRecordInfoService;

    /**
     * 查询健康档案列表
     */
    @PreAuthorize("@ss.hasPermi('manage:healthRecordInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(HealthRecordInfoQuery healthRecordInfoQuery)
    {
        HealthRecordInfo healthRecordInfo = HealthRecordInfoQuery.queryToObj(healthRecordInfoQuery);
        startPage();
        List<HealthRecordInfo> list = healthRecordInfoService.selectHealthRecordInfoList(healthRecordInfo);
        List<HealthRecordInfoVo> listVo= list.stream().map(HealthRecordInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出健康档案列表
     */
    @PreAuthorize("@ss.hasPermi('manage:healthRecordInfo:export')")
    @Log(title = "健康档案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HealthRecordInfoQuery healthRecordInfoQuery)
    {
        HealthRecordInfo healthRecordInfo = HealthRecordInfoQuery.queryToObj(healthRecordInfoQuery);
        List<HealthRecordInfo> list = healthRecordInfoService.selectHealthRecordInfoList(healthRecordInfo);
        ExcelUtil<HealthRecordInfo> util = new ExcelUtil<HealthRecordInfo>(HealthRecordInfo.class);
        util.exportExcel(response, list, "健康档案数据");
    }

    /**
     * 获取健康档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:healthRecordInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        HealthRecordInfo healthRecordInfo = healthRecordInfoService.selectHealthRecordInfoById(id);
        return success(HealthRecordInfoVo.objToVo(healthRecordInfo));
    }

    /**
     * 新增健康档案
     */
    @PreAuthorize("@ss.hasPermi('manage:healthRecordInfo:add')")
    @Log(title = "健康档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HealthRecordInfoInsert healthRecordInfoInsert)
    {
        HealthRecordInfo healthRecordInfo = HealthRecordInfoInsert.insertToObj(healthRecordInfoInsert);
        return toAjax(healthRecordInfoService.insertHealthRecordInfo(healthRecordInfo));
    }

    /**
     * 修改健康档案
     */
    @PreAuthorize("@ss.hasPermi('manage:healthRecordInfo:edit')")
    @Log(title = "健康档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HealthRecordInfoEdit healthRecordInfoEdit)
    {
        HealthRecordInfo healthRecordInfo = HealthRecordInfoEdit.editToObj(healthRecordInfoEdit);
        return toAjax(healthRecordInfoService.updateHealthRecordInfo(healthRecordInfo));
    }

    /**
     * 删除健康档案
     */
    @PreAuthorize("@ss.hasPermi('manage:healthRecordInfo:remove')")
    @Log(title = "健康档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(healthRecordInfoService.deleteHealthRecordInfoByIds(ids));
    }
}
