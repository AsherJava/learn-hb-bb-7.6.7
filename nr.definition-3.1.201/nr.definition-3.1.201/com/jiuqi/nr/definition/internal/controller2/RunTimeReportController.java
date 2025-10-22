/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.controller2;

import com.jiuqi.nr.definition.api.IRunTimeReportController;
import com.jiuqi.nr.definition.facade.ReportTagDefine;
import com.jiuqi.nr.definition.facade.ReportTemplateDefine;
import com.jiuqi.nr.definition.internal.service.RuntimeReportTemplateService;
import com.jiuqi.nr.definition.reportTag.service.IRuntimeReportTagService;
import java.io.OutputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RunTimeReportController
implements IRunTimeReportController {
    private static final Logger logger = LoggerFactory.getLogger(RunTimeReportController.class);
    @Autowired
    private RuntimeReportTemplateService runtimeReportTemplateService;
    @Autowired
    private IRuntimeReportTagService runtimeReportTagService;

    @Override
    public ReportTemplateDefine getReportTemplate(String key) {
        return this.runtimeReportTemplateService.getReportTemplate(key);
    }

    @Override
    public List<ReportTemplateDefine> listReportTemplateByFormScheme(String formScheme) {
        return this.runtimeReportTemplateService.getReportTemplateByScheme(formScheme);
    }

    @Override
    public byte[] getReportTemplateFile(String fileKey) {
        return this.runtimeReportTemplateService.getReportTemplateFile(fileKey);
    }

    @Override
    public void getReportTemplateFile(String fileKey, OutputStream outputStream) {
        this.runtimeReportTemplateService.getReportTemplateFile(fileKey, outputStream);
    }

    @Override
    public List<ReportTagDefine> listReportTagByReportTemplate(String reportTemplate) {
        return this.runtimeReportTagService.queryAllTagsByRptKey(reportTemplate);
    }
}

