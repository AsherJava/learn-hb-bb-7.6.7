/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.report.web;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.report.service.IReportTagManageService;
import com.jiuqi.nr.report.web.vo.ExportTagParam;
import com.jiuqi.nr.task.api.aop.TaskLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value={"api/v1/report/tag/"})
@Api(tags={"\u5206\u6790\u62a5\u544a\u6a21\u677f\u81ea\u5b9a\u4e49\u6807\u7b7e\u6a21\u5757"})
public class ImportExportUtilsController {
    @Autowired
    private IReportTagManageService reportTagManageService;

    @ApiOperation(value="\u5bfc\u5165\u81ea\u5b9a\u4e49\u6807\u7b7e\u6570\u636e")
    @PostMapping(value={"import-tag-info"})
    @Transactional(rollbackFor={Exception.class})
    @ResponseBody
    @TaskLog(operation="\u5bfc\u5165\u81ea\u5b9a\u4e49\u6807\u7b7e\u6570\u636e")
    public void importTagInfo(@RequestParam(value="file") MultipartFile file, @RequestParam String rptKeyImport) throws JQException {
        this.reportTagManageService.importTagInfo(file, rptKeyImport);
    }

    @ApiOperation(value="\u5bfc\u51fa\u81ea\u5b9a\u4e49\u6807\u7b7e\u6570\u636e")
    @PostMapping(value={"export-tag-info"})
    @ResponseBody
    public void exportTagInfo(@RequestBody ExportTagParam exportTagParam, HttpServletResponse response) throws Exception {
        this.reportTagManageService.exportTagInfo(exportTagParam, response);
    }
}

