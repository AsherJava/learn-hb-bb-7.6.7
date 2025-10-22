/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignReportTagDefine
 *  com.jiuqi.nr.definition.facade.DesignReportTemplateDefine
 *  com.jiuqi.nr.definition.facade.ReportTemplateDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.apache.shiro.authz.annotation.RequiresPermissions
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
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.facade.DesignReportTemplateDefine;
import com.jiuqi.nr.definition.facade.ReportTemplateDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.web.facade.ReportTemplateObj;
import com.jiuqi.nr.designer.web.rest.DeletePM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5206\u6790\u62a5\u544a\u6a21\u677f\u670d\u52a1"})
public class ReportTemplateController {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;

    @ApiOperation(value="\u67e5\u8be2\u5206\u6790\u62a5\u8868\u6a21\u677f")
    @GetMapping(value={"report/template/get/{taskKey}"})
    @RequiresPermissions(value={"nr:task_report:manage"})
    public List<ReportTemplateObj> doGet(@PathVariable String taskKey) throws JQException {
        List templates = this.nrDesignTimeController.getReportTemplateByTask(taskKey);
        Map<String, String> schemeTitleMap = this.nrDesignTimeController.queryFormSchemeByTask(taskKey).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, IBaseMetaItem::getTitle));
        return templates.stream().map(t -> this.toReportTemplateObj((DesignReportTemplateDefine)t, schemeTitleMap)).collect(Collectors.toList()).stream().sorted(Comparator.comparing(ReportTemplateObj::getOrder).thenComparing(ReportTemplateObj::getUpdateTime)).collect(Collectors.toList());
    }

    private ReportTemplateObj toReportTemplateObj(DesignReportTemplateDefine define, Map<String, String> schemeTitleMap) {
        return new ReportTemplateObj(define, schemeTitleMap.get(define.getFormSchemeKey()));
    }

    @ApiOperation(value="\u66f4\u65b0\u5206\u6790\u62a5\u8868\u6a21\u677f")
    @PostMapping(value={"report/template/update"})
    public void doUpdate(@RequestBody ReportTemplateObj obj) throws JQException {
        DesignReportTemplateDefine define = obj.toDesignReportTemplateDefine(() -> ((NRDesignTimeController)this.nrDesignTimeController).createReportTemplateDefine());
        this.nrDesignTimeController.updateReportTemplate(define);
        this.updateFormSchemeDate(obj.getFormSchemeKey());
    }

    @ApiOperation(value="\u5220\u9664\u5206\u6790\u62a5\u8868\u6a21\u677f")
    @PostMapping(value={"report/template/delete"})
    @RequiresPermissions(value={"nr:task_report:manage"})
    @Transactional(rollbackFor={Exception.class})
    public void doDelete(@RequestBody DeletePM deletePM) throws JQException {
        this.nrDesignTimeController.deleteReportTemplate(deletePM.getTemplateKeys());
        for (String templateKey : deletePM.getTemplateKeys()) {
            this.nrDesignTimeController.deleteTagsByRptKey(templateKey);
        }
        List<String> formSchemeKeys = deletePM.getFormSchemeKeys();
        if (formSchemeKeys != null && !formSchemeKeys.isEmpty()) {
            for (String formSchemeKey : formSchemeKeys) {
                this.updateFormSchemeDate(formSchemeKey);
            }
        }
    }

    @ApiOperation(value="\u65b0\u589e\u5206\u6790\u62a5\u8868\u6a21\u677f")
    @PostMapping(value={"report/template/add"})
    @Transactional(rollbackFor={Exception.class})
    public void doAdd(@RequestParam MultipartFile file, @RequestParam String taskKey, @RequestParam String formSchemeKey) throws JQException {
        Throwable throwable;
        InputStream inputStream;
        DesignReportTemplateDefine define = this.nrDesignTimeController.createReportTemplateDefine();
        define.setTaskKey(taskKey);
        define.setFormSchemeKey(formSchemeKey);
        String originalFileName = file.getOriginalFilename();
        String fileNameTemp = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        List templateList = this.nrDesignTimeController.getReportTemplateByScheme(formSchemeKey);
        Map<String, DesignReportTemplateDefine> templateMap = templateList.stream().collect(Collectors.toMap(ReportTemplateDefine::getFileName, o -> o));
        String fileName = "";
        if (templateMap.containsKey(fileNameTemp)) {
            int idx = 1;
            boolean exists = false;
            block22: do {
                exists = false;
                fileName = fileNameTemp + idx++;
                for (DesignReportTemplateDefine template : templateList) {
                    if (template.getFileName() == null || !template.getFileName().equals(fileName)) continue;
                    exists = true;
                    continue block22;
                }
            } while (exists);
        } else {
            fileName = fileNameTemp;
        }
        define.setFileName(fileName);
        define.setFileNameExp(fileNameTemp);
        define.setOrder(this.nrDesignTimeController.queryFormSchemeDefine(formSchemeKey).getOrder());
        try {
            inputStream = file.getInputStream();
            throwable = null;
            try {
                this.nrDesignTimeController.insertReportTemplate(define, originalFileName, inputStream);
                this.updateFormSchemeDate(formSchemeKey);
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (inputStream != null) {
                    if (throwable != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            }
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_199);
        }
        try {
            inputStream = file.getInputStream();
            throwable = null;
            try {
                List reportTagDefines = this.nrDesignTimeController.filterCustomTagsInRpt(inputStream, define.getKey());
                for (DesignReportTagDefine reportTagDefine : reportTagDefines) {
                    reportTagDefine.setType(1);
                }
                this.nrDesignTimeController.insertTags(reportTagDefines);
            }
            catch (Throwable throwable4) {
                throwable = throwable4;
                throw throwable4;
            }
            finally {
                if (inputStream != null) {
                    if (throwable != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable throwable5) {
                            throwable.addSuppressed(throwable5);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            }
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_199);
        }
    }

    private void updateFormSchemeDate(String formSchemeKey) throws JQException {
        DesignFormSchemeDefine formSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(formSchemeKey);
        if (formSchemeDefine != null) {
            formSchemeDefine.setUpdateTime(new Date());
            this.nrDesignTimeController.updateFormSchemeDefine(formSchemeDefine);
        }
    }

    @ApiOperation(value="\u4e0a\u4f20\u5206\u6790\u62a5\u8868\u6a21\u677f\u6587\u4ef6")
    @PostMapping(value={"report/template/upload"})
    @ResponseBody
    @Transactional(rollbackFor={Exception.class})
    public void doUpload(@RequestParam MultipartFile file, @RequestParam String templateKey, @RequestParam String formSchemeKey) throws JQException {
        Throwable throwable;
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
            throwable = null;
            try {
                String originalFileName = file.getOriginalFilename();
                String fileNameTemp = originalFileName.substring(0, originalFileName.lastIndexOf("."));
                List templateList = this.nrDesignTimeController.getReportTemplateByScheme(formSchemeKey);
                templateList.removeIf(o -> o.getKey().equals(templateKey));
                Map<String, DesignReportTemplateDefine> templateMap = templateList.stream().collect(Collectors.toMap(ReportTemplateDefine::getFileName, o -> o));
                String fileName = "";
                if (templateMap.containsKey(fileNameTemp)) {
                    int idx = 1;
                    boolean exists = false;
                    block22: do {
                        exists = false;
                        fileName = fileNameTemp + idx++;
                        for (DesignReportTemplateDefine template : templateList) {
                            if (template.getFileName() == null || !template.getFileName().equals(fileName)) continue;
                            exists = true;
                            continue block22;
                        }
                    } while (exists);
                } else {
                    fileName = fileNameTemp;
                }
                this.nrDesignTimeController.updateReportTemplate(templateKey, fileName, originalFileName, inputStream);
                this.updateFormSchemeDate(formSchemeKey);
            }
            catch (Throwable originalFileName) {
                throwable = originalFileName;
                throw originalFileName;
            }
            finally {
                if (inputStream != null) {
                    if (throwable != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable originalFileName) {
                            throwable.addSuppressed(originalFileName);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            }
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_199, (Throwable)e);
        }
        try {
            inputStream = file.getInputStream();
            throwable = null;
            try {
                List reportTagDefines = this.nrDesignTimeController.filterCustomTagsInRpt(inputStream, templateKey);
                List existTagDefines = this.nrDesignTimeController.queryAllTagsByRptKey(templateKey);
                for (DesignReportTagDefine existTagDefine : existTagDefines) {
                    reportTagDefines.removeIf(o -> o.getContent().equals(existTagDefine.getContent()));
                }
                for (DesignReportTagDefine reportTagDefine : reportTagDefines) {
                    reportTagDefine.setType(1);
                }
                this.nrDesignTimeController.insertTags(reportTagDefines);
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (inputStream != null) {
                    if (throwable != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            }
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_199, (Throwable)e);
        }
    }
}

