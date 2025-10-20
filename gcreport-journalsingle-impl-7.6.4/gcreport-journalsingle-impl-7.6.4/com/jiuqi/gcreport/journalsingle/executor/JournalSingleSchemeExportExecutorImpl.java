/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor
 *  com.jiuqi.gcreport.common.util.JsonUtils
 */
package com.jiuqi.gcreport.journalsingle.executor;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.journalsingle.entity.JournalSingleSchemeExcelModel;
import com.jiuqi.gcreport.journalsingle.service.IJournalSingleSubjectService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JournalSingleSchemeExportExecutorImpl
extends AbstractExportExcelModelExecutor<JournalSingleSchemeExcelModel> {
    @Autowired
    private IJournalSingleSubjectService journalSingleSubjectService;

    protected JournalSingleSchemeExportExecutorImpl() {
        super(JournalSingleSchemeExcelModel.class);
    }

    public String getName() {
        return "JournalSingleSchemeExportExecutor";
    }

    protected List<JournalSingleSchemeExcelModel> exportExcelModels(ExportContext context) {
        Map param = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String parentId = ConverterUtils.getAsString(param.get("parentId"));
        Boolean isAllChildren = ConverterUtils.getAsBoolean(param.get("isAllChildren"));
        context.getProgressData().setProgressValueAndRefresh(0.1);
        List<JournalSingleSchemeExcelModel> result = this.journalSingleSubjectService.exportSubjectData(parentId, isAllChildren);
        context.getProgressData().setProgressValueAndRefresh(0.8);
        return result;
    }
}

