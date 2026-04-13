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
import com.lz.manage.model.domain.HospitalizationInfo;
import com.lz.manage.model.vo.hospitalizationInfo.HospitalizationInfoVo;
import com.lz.manage.model.dto.hospitalizationInfo.HospitalizationInfoQuery;
import com.lz.manage.model.dto.hospitalizationInfo.HospitalizationInfoInsert;
import com.lz.manage.model.dto.hospitalizationInfo.HospitalizationInfoEdit;
import com.lz.manage.service.IHospitalizationInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 住院记录Controller
 *
 * @author YY
 * @date 2026-04-13
 */
@RestController
@RequestMapping("/manage/hospitalizationInfo")
public class HospitalizationInfoController extends BaseController
{
    @Resource
    private IHospitalizationInfoService hospitalizationInfoService;

    /**
     * 查询住院记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:hospitalizationInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalizationInfoQuery hospitalizationInfoQuery)
    {
        HospitalizationInfo hospitalizationInfo = HospitalizationInfoQuery.queryToObj(hospitalizationInfoQuery);
        startPage();
        List<HospitalizationInfo> list = hospitalizationInfoService.selectHospitalizationInfoList(hospitalizationInfo);
        List<HospitalizationInfoVo> listVo= list.stream().map(HospitalizationInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出住院记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:hospitalizationInfo:export')")
    @Log(title = "住院记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HospitalizationInfoQuery hospitalizationInfoQuery)
    {
        HospitalizationInfo hospitalizationInfo = HospitalizationInfoQuery.queryToObj(hospitalizationInfoQuery);
        List<HospitalizationInfo> list = hospitalizationInfoService.selectHospitalizationInfoList(hospitalizationInfo);
        ExcelUtil<HospitalizationInfo> util = new ExcelUtil<HospitalizationInfo>(HospitalizationInfo.class);
        util.exportExcel(response, list, "住院记录数据");
    }

    /**
     * 获取住院记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:hospitalizationInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        HospitalizationInfo hospitalizationInfo = hospitalizationInfoService.selectHospitalizationInfoById(id);
        return success(HospitalizationInfoVo.objToVo(hospitalizationInfo));
    }

    /**
     * 新增住院记录
     */
    @PreAuthorize("@ss.hasPermi('manage:hospitalizationInfo:add')")
    @Log(title = "住院记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HospitalizationInfoInsert hospitalizationInfoInsert)
    {
        HospitalizationInfo hospitalizationInfo = HospitalizationInfoInsert.insertToObj(hospitalizationInfoInsert);
        return toAjax(hospitalizationInfoService.insertHospitalizationInfo(hospitalizationInfo));
    }

    /**
     * 修改住院记录
     */
    @PreAuthorize("@ss.hasPermi('manage:hospitalizationInfo:edit')")
    @Log(title = "住院记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HospitalizationInfoEdit hospitalizationInfoEdit)
    {
        HospitalizationInfo hospitalizationInfo = HospitalizationInfoEdit.editToObj(hospitalizationInfoEdit);
        return toAjax(hospitalizationInfoService.updateHospitalizationInfo(hospitalizationInfo));
    }

    /**
     * 删除住院记录
     */
    @PreAuthorize("@ss.hasPermi('manage:hospitalizationInfo:remove')")
    @Log(title = "住院记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hospitalizationInfoService.deleteHospitalizationInfoByIds(ids));
    }
}
