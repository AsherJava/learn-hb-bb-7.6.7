/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.print.web.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.print.exception.ExcelPrintSettingException;
import com.jiuqi.nr.print.service.IExcelPrintSettingService;
import com.jiuqi.nr.print.web.vo.ExcelPrintSettingVO;
import com.jiuqi.nr.task.api.aop.TaskLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@Api(tags={"Excel\u6253\u5370\u8bbe\u7f6e"})
@RequestMapping(value={"api/v2/print/"})
public class ExcelPrintSettingController {
    @Autowired
    private IExcelPrintSettingService excelPrintSettingService;

    @ApiOperation(value="\u67e5\u8be2Excel\u6253\u5370\u8bbe\u7f6e")
    @RequestMapping(value={"excel/setting/query/{printSchemeKey}/{formKey}"}, method={RequestMethod.GET})
    public ExcelPrintSettingVO query(@PathVariable String printSchemeKey, @PathVariable String formKey) throws JQException {
        try {
            return this.excelPrintSettingService.get(printSchemeKey, formKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExcelPrintSettingException.PRINT_SETTING_QUERY_FAIL);
        }
    }

    @ApiOperation(value="\u4fdd\u5b58Excel\u6253\u5370\u8bbe\u7f6e")
    @RequestMapping(value={"excel/setting/save"}, method={RequestMethod.POST})
    @TaskLog(operation="\u4fdd\u5b58Excel\u6253\u5370\u8bbe\u7f6e")
    public void save(@RequestBody ExcelPrintSettingVO vo) throws JQException {
        try {
            this.excelPrintSettingService.save(vo);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExcelPrintSettingException.PRINT_SETTING_SAVE_FAIL);
        }
    }

    @ApiOperation(value="\u91cd\u7f6eExcel\u6253\u5370\u8bbe\u7f6e")
    @RequestMapping(value={"excel/setting/reset/{printSchemeKey}/{formKey}"}, method={RequestMethod.GET})
    @TaskLog(operation="\u91cd\u7f6eExcel\u6253\u5370\u8bbe\u7f6e")
    public ExcelPrintSettingVO reset(@PathVariable String printSchemeKey, @PathVariable String formKey) throws JQException {
        try {
            return this.excelPrintSettingService.reset(printSchemeKey, formKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExcelPrintSettingException.PRINT_SETTING_RESET_FAIL);
        }
    }

    @ApiOperation(value="\u6e05\u9664Excel\u6253\u5370\u8bbe\u7f6e")
    @RequestMapping(value={"excel/setting/clear/{printSchemeKey}"}, method={RequestMethod.GET})
    @TaskLog(operation="\u6e05\u9664Excel\u6253\u5370\u8bbe\u7f6e")
    public void clear(@PathVariable String printSchemeKey) throws JQException {
        try {
            this.excelPrintSettingService.delete(printSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExcelPrintSettingException.PRINT_SETTING_DELETE_FAIL);
        }
    }
}

