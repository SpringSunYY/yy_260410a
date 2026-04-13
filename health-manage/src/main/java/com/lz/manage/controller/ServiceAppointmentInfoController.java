package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.lz.manage.model.dto.serviceAppointmentInfo.ServiceAppointmentInfoAudit;
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
import com.lz.manage.model.domain.ServiceAppointmentInfo;
import com.lz.manage.model.vo.serviceAppointmentInfo.ServiceAppointmentInfoVo;
import com.lz.manage.model.dto.serviceAppointmentInfo.ServiceAppointmentInfoQuery;
import com.lz.manage.model.dto.serviceAppointmentInfo.ServiceAppointmentInfoInsert;
import com.lz.manage.model.dto.serviceAppointmentInfo.ServiceAppointmentInfoEdit;
import com.lz.manage.service.IServiceAppointmentInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 服务预约Controller
 *
 * @author YY
 * @date 2026-04-13
 */
@RestController
@RequestMapping("/manage/serviceAppointmentInfo")
public class ServiceAppointmentInfoController extends BaseController
{
    @Resource
    private IServiceAppointmentInfoService serviceAppointmentInfoService;

    /**
     * 查询服务预约列表
     */
    @PreAuthorize("@ss.hasPermi('manage:serviceAppointmentInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ServiceAppointmentInfoQuery serviceAppointmentInfoQuery)
    {
        ServiceAppointmentInfo serviceAppointmentInfo = ServiceAppointmentInfoQuery.queryToObj(serviceAppointmentInfoQuery);
        startPage();
        List<ServiceAppointmentInfo> list = serviceAppointmentInfoService.selectServiceAppointmentInfoList(serviceAppointmentInfo);
        List<ServiceAppointmentInfoVo> listVo= list.stream().map(ServiceAppointmentInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出服务预约列表
     */
    @PreAuthorize("@ss.hasPermi('manage:serviceAppointmentInfo:export')")
    @Log(title = "服务预约", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ServiceAppointmentInfoQuery serviceAppointmentInfoQuery)
    {
        ServiceAppointmentInfo serviceAppointmentInfo = ServiceAppointmentInfoQuery.queryToObj(serviceAppointmentInfoQuery);
        List<ServiceAppointmentInfo> list = serviceAppointmentInfoService.selectServiceAppointmentInfoList(serviceAppointmentInfo);
        ExcelUtil<ServiceAppointmentInfo> util = new ExcelUtil<ServiceAppointmentInfo>(ServiceAppointmentInfo.class);
        util.exportExcel(response, list, "服务预约数据");
    }

    /**
     * 获取服务预约详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:serviceAppointmentInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        ServiceAppointmentInfo serviceAppointmentInfo = serviceAppointmentInfoService.selectServiceAppointmentInfoById(id);
        return success(ServiceAppointmentInfoVo.objToVo(serviceAppointmentInfo));
    }

    /**
     * 新增服务预约
     */
    @PreAuthorize("@ss.hasPermi('manage:serviceAppointmentInfo:add')")
    @Log(title = "服务预约", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ServiceAppointmentInfoInsert serviceAppointmentInfoInsert)
    {
        ServiceAppointmentInfo serviceAppointmentInfo = ServiceAppointmentInfoInsert.insertToObj(serviceAppointmentInfoInsert);
        return toAjax(serviceAppointmentInfoService.insertServiceAppointmentInfo(serviceAppointmentInfo));
    }

    /**
     * 修改服务预约
     */
    @PreAuthorize("@ss.hasPermi('manage:serviceAppointmentInfo:edit')")
    @Log(title = "服务预约", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ServiceAppointmentInfoEdit serviceAppointmentInfoEdit)
    {
        ServiceAppointmentInfo serviceAppointmentInfo = ServiceAppointmentInfoEdit.editToObj(serviceAppointmentInfoEdit);
        return toAjax(serviceAppointmentInfoService.updateServiceAppointmentInfo(serviceAppointmentInfo));
    }

    /**
     * 审核服务预约
     */
    @PreAuthorize("@ss.hasPermi('manage:serviceAppointmentInfo:audit')")
    @Log(title = "服务预约", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody ServiceAppointmentInfoAudit serviceAppointmentInfoAudit){
        ServiceAppointmentInfo serviceAppointmentInfo = ServiceAppointmentInfoAudit.auditToObj(serviceAppointmentInfoAudit);
        return toAjax(serviceAppointmentInfoService.auditServiceAppointmentInfo(serviceAppointmentInfo));
    }

    /**
     * 删除服务预约
     */
    @PreAuthorize("@ss.hasPermi('manage:serviceAppointmentInfo:remove')")
    @Log(title = "服务预约", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(serviceAppointmentInfoService.deleteServiceAppointmentInfoByIds(ids));
    }
}
