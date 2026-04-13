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
import com.lz.manage.model.domain.ResidentInfo;
import com.lz.manage.model.vo.residentInfo.ResidentInfoVo;
import com.lz.manage.model.dto.residentInfo.ResidentInfoQuery;
import com.lz.manage.model.dto.residentInfo.ResidentInfoInsert;
import com.lz.manage.model.dto.residentInfo.ResidentInfoEdit;
import com.lz.manage.service.IResidentInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 居民信息Controller
 *
 * @author YY
 * @date 2026-04-13
 */
@RestController
@RequestMapping("/manage/residentInfo")
public class ResidentInfoController extends BaseController
{
    @Resource
    private IResidentInfoService residentInfoService;

    /**
     * 查询居民信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:residentInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResidentInfoQuery residentInfoQuery)
    {
        ResidentInfo residentInfo = ResidentInfoQuery.queryToObj(residentInfoQuery);
        startPage();
        List<ResidentInfo> list = residentInfoService.selectResidentInfoList(residentInfo);
        List<ResidentInfoVo> listVo= list.stream().map(ResidentInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出居民信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:residentInfo:export')")
    @Log(title = "居民信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ResidentInfoQuery residentInfoQuery)
    {
        ResidentInfo residentInfo = ResidentInfoQuery.queryToObj(residentInfoQuery);
        List<ResidentInfo> list = residentInfoService.selectResidentInfoList(residentInfo);
        ExcelUtil<ResidentInfo> util = new ExcelUtil<ResidentInfo>(ResidentInfo.class);
        util.exportExcel(response, list, "居民信息数据");
    }

    /**
     * 获取居民信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:residentInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        ResidentInfo residentInfo = residentInfoService.selectResidentInfoById(id);
        return success(ResidentInfoVo.objToVo(residentInfo));
    }

    /**
     * 新增居民信息
     */
    @PreAuthorize("@ss.hasPermi('manage:residentInfo:add')")
    @Log(title = "居民信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResidentInfoInsert residentInfoInsert)
    {
        ResidentInfo residentInfo = ResidentInfoInsert.insertToObj(residentInfoInsert);
        return toAjax(residentInfoService.insertResidentInfo(residentInfo));
    }

    /**
     * 修改居民信息
     */
    @PreAuthorize("@ss.hasPermi('manage:residentInfo:edit')")
    @Log(title = "居民信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResidentInfoEdit residentInfoEdit)
    {
        ResidentInfo residentInfo = ResidentInfoEdit.editToObj(residentInfoEdit);
        return toAjax(residentInfoService.updateResidentInfo(residentInfo));
    }

    /**
     * 删除居民信息
     */
    @PreAuthorize("@ss.hasPermi('manage:residentInfo:remove')")
    @Log(title = "居民信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(residentInfoService.deleteResidentInfoByIds(ids));
    }
}
