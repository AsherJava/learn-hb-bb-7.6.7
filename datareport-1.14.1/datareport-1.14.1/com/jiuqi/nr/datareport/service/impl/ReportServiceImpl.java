/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.ReportTemplateDefine
 *  com.jiuqi.nvwa.office.template.document.DocumentTemplate
 */
package com.jiuqi.nr.datareport.service.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.datareport.constant.ReportErrorEnum;
import com.jiuqi.nr.datareport.helper.ReportHelper;
import com.jiuqi.nr.datareport.obj.ReportDataParam;
import com.jiuqi.nr.datareport.obj.ReportObj;
import com.jiuqi.nr.datareport.obj.ReportQueryParam;
import com.jiuqi.nr.datareport.obj.ReportTemplateObj;
import com.jiuqi.nr.datareport.service.IReportService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.ReportTemplateDefine;
import com.jiuqi.nvwa.office.template.document.DocumentTemplate;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ReportServiceImpl
implements IReportService {
    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ReportHelper reportHelper;
    @Value(value="${jiuqi.nr.datareport.exp-format-docx:false}")
    private boolean expDocx;

    @Override
    public List<ReportTemplateObj> list(ReportQueryParam param, ExecutorContext executorContext) throws Exception {
        List reportTemplates = this.runTimeViewController.getReportTemplateByScheme(param.getFormSchemeKey());
        if (CollectionUtils.isEmpty(reportTemplates)) {
            return Collections.emptyList();
        }
        reportTemplates.removeIf(o -> {
            try {
                return !this.reportHelper.filterCondition(param.getFormSchemeKey(), param.getDimensionSet(), executorContext, o.getCondition());
            }
            catch (JQException e) {
                logger.error(e.getMessage(), e);
                return false;
            }
        });
        ArrayList<ReportTemplateObj> queryResult = new ArrayList<ReportTemplateObj>();
        for (ReportTemplateDefine reportTemplate : reportTemplates) {
            queryResult.add(new ReportTemplateObj(reportTemplate, ""));
        }
        return queryResult.stream().sorted(Comparator.comparing(ReportTemplateObj::getUpdateTime)).collect(Collectors.toList());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public byte[] export(ReportDataParam param, ExecutorContext executorContext) throws Exception {
        ReportObj reportObj = param.getReportObj();
        if (reportObj == null) {
            throw new JQException((ErrorEnum)ReportErrorEnum.REPORT_EXCEPTION_200);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] reportTemplateFileByte = this.runTimeViewController.getReportTemplateFile(reportObj.getFileKey());
        byte[] finalRptTemp = this.runTimeViewController.injectReplyToRpt(reportTemplateFileByte, param.getInjectContext());
        ByteArrayInputStream reportTemplateFileIS = new ByteArrayInputStream(finalRptTemp);
        try {
            executorContext.setAutoDataMasking(true);
            DocumentTemplate dt = this.reportHelper.parseReport(reportTemplateFileIS, param.getReportEnv(), reportObj.getKey(), executorContext);
            if (this.expDocx) {
                dt.saveDocxDocument((OutputStream)outputStream);
            } else {
                dt.saveDocument((OutputStream)outputStream);
            }
            byte[] byArray = outputStream.toByteArray();
            return byArray;
        }
        finally {
            outputStream.flush();
            outputStream.close();
        }
    }
}

