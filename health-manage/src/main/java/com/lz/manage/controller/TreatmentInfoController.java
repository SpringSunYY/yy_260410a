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
import com.lz.manage.model.domain.TreatmentInfo;
import com.lz.manage.model.vo.treatmentInfo.TreatmentInfoVo;
import com.lz.manage.model.dto.treatmentInfo.TreatmentInfoQuery;
import com.lz.manage.model.dto.treatmentInfo.TreatmentInfoInsert;
import com.lz.manage.model.dto.treatmentInfo.TreatmentInfoEdit;
import com.lz.manage.service.ITreatmentInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 诊疗记录Controller
 *
 * @author YY
 * @date 2026-04-13
 */
@RestController
@RequestMapping("/manage/treatmentInfo")
public class TreatmentInfoController extends BaseController
{
    @Resource
    private ITreatmentInfoService treatmentInfoService;

    /**
     * 查询诊疗记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:treatmentInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(TreatmentInfoQuery treatmentInfoQuery)
    {
        TreatmentInfo treatmentInfo = TreatmentInfoQuery.queryToObj(treatmentInfoQuery);
        startPage();
        List<TreatmentInfo> list = treatmentInfoService.selectTreatmentInfoList(treatmentInfo);
        List<TreatmentInfoVo> listVo= list.stream().map(TreatmentInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出诊疗记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:treatmentInfo:export')")
    @Log(title = "诊疗记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TreatmentInfoQuery treatmentInfoQuery)
    {
        TreatmentInfo treatmentInfo = TreatmentInfoQuery.queryToObj(treatmentInfoQuery);
        List<TreatmentInfo> list = treatmentInfoService.selectTreatmentInfoList(treatmentInfo);
        ExcelUtil<TreatmentInfo> util = new ExcelUtil<TreatmentInfo>(TreatmentInfo.class);
        util.exportExcel(response, list, "诊疗记录数据");
    }

    /**
     * 获取诊疗记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:treatmentInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        TreatmentInfo treatmentInfo = treatmentInfoService.selectTreatmentInfoById(id);
        return success(TreatmentInfoVo.objToVo(treatmentInfo));
    }

    /**
     * 新增诊疗记录
     */
    @PreAuthorize("@ss.hasPermi('manage:treatmentInfo:add')")
    @Log(title = "诊疗记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TreatmentInfoInsert treatmentInfoInsert)
    {
        TreatmentInfo treatmentInfo = TreatmentInfoInsert.insertToObj(treatmentInfoInsert);
        return toAjax(treatmentInfoService.insertTreatmentInfo(treatmentInfo));
    }

    /**
     * 修改诊疗记录
     */
    @PreAuthorize("@ss.hasPermi('manage:treatmentInfo:edit')")
    @Log(title = "诊疗记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TreatmentInfoEdit treatmentInfoEdit)
    {
        TreatmentInfo treatmentInfo = TreatmentInfoEdit.editToObj(treatmentInfoEdit);
        return toAjax(treatmentInfoService.updateTreatmentInfo(treatmentInfo));
    }

    /**
     * 删除诊疗记录
     */
    @PreAuthorize("@ss.hasPermi('manage:treatmentInfo:remove')")
    @Log(title = "诊疗记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(treatmentInfoService.deleteTreatmentInfoByIds(ids));
    }
}
