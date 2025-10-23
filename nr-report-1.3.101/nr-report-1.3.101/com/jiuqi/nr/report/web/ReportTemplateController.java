/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.report.web;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.report.common.DeletePM;
import com.jiuqi.nr.report.dto.ReportTemplateDTO;
import com.jiuqi.nr.report.service.IReportTemplateManageService;
import com.jiuqi.nr.report.web.vo.NameCheckVO;
import com.jiuqi.nr.report.web.vo.ReportTemplateVO;
import com.jiuqi.nr.task.api.aop.TaskLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"api/v1/report/template/"})
@Api(tags={"\u5206\u6790\u62a5\u544a\u6a21\u677f\u670d\u52a1"})
public class ReportTemplateController {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IReportTemplateManageService reportTemplateManageService;

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u5206\u6790\u62a5\u8868\u6a21\u677f")
    @GetMapping(value={"batch_get/{formSchemeKey}"})
    public List<ReportTemplateVO> listReportTemplate(@PathVariable String formSchemeKey) {
        List<ReportTemplateDTO> templates = this.reportTemplateManageService.listReportTemplateByScheme(formSchemeKey);
        templates.sort(Comparator.comparing(ReportTemplateDTO::getUpdateTime));
        return templates.stream().map(ReportTemplateVO::new).collect(Collectors.toList());
    }

    @ApiOperation(value="\u67e5\u8be2\u5206\u6790\u62a5\u8868\u6a21\u677f")
    @GetMapping(value={"get/{key}"})
    public ReportTemplateVO getReportTemplate(@PathVariable String key) {
        ReportTemplateDTO template = this.reportTemplateManageService.getReportTemplate(key);
        return new ReportTemplateVO(template);
    }

    @ApiOperation(value="\u66f4\u65b0\u5206\u6790\u62a5\u8868\u6a21\u677f")
    @PostMapping(value={"update"})
    @TaskLog(operation="\u66f4\u65b0\u5206\u6790\u62a5\u8868\u6a21\u677f")
    public void updateReportTemplate(@RequestBody ReportTemplateVO obj) {
        this.reportTemplateManageService.updateReportTemplate(obj);
    }

    @ApiOperation(value="\u5220\u9664\u5206\u6790\u62a5\u8868\u6a21\u677f")
    @PostMapping(value={"delete"})
    @Transactional(rollbackFor={Exception.class})
    @TaskLog(operation="\u5220\u9664\u5206\u6790\u62a5\u8868\u6a21\u677f")
    public void deleteReportTemplate(@RequestBody DeletePM deletePM) {
        this.reportTemplateManageService.deleteReportTemplate(deletePM);
    }

    @ApiOperation(value="\u65b0\u589e\u5206\u6790\u62a5\u8868\u6a21\u677f")
    @PostMapping(value={"add"})
    @Transactional(rollbackFor={Exception.class})
    @TaskLog(operation="\u65b0\u589e\u5206\u6790\u62a5\u8868\u6a21\u677f")
    public void insertReportTemplate(@RequestParam MultipartFile file, @RequestParam String formSchemeKey) throws JQException {
        this.reportTemplateManageService.insertReportTemplate(file, formSchemeKey);
    }

    @ApiOperation(value="\u4e0a\u4f20\u5206\u6790\u62a5\u8868\u6a21\u677f\u6587\u4ef6")
    @PostMapping(value={"upload"})
    @ResponseBody
    @Transactional(rollbackFor={Exception.class})
    @TaskLog(operation="\u4e0a\u4f20\u5206\u6790\u62a5\u8868\u6a21\u677f\u6587\u4ef6")
    public void uploadReportTemplate(@RequestParam MultipartFile file, @RequestParam String templateKey, @RequestParam String formSchemeKey) throws JQException {
        this.reportTemplateManageService.updateReportTemplateFile(file, templateKey, formSchemeKey);
    }

    @ApiOperation(value="\u4e0b\u8f7d\u5206\u6790\u62a5\u8868\u6a21\u677f\u9644\u4ef6")
    @PostMapping(value={"download/{fileKey}"})
    public void exportReportTemplate(HttpServletResponse response, @PathVariable String fileKey) throws IOException {
        this.reportTemplateManageService.exportReportTemplateFile(response, fileKey);
    }

    @ApiOperation(value="\u540d\u79f0\u68c0\u67e5")
    @PostMapping(value={"check"})
    public boolean nameCheck(@RequestBody NameCheckVO checkVO) throws IOException {
        return this.reportTemplateManageService.nameCheck(checkVO);
    }
}

