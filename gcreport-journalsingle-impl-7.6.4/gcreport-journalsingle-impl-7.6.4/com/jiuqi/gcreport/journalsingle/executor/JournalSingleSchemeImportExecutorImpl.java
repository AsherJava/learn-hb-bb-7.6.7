/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelModelExecutor
 *  com.jiuqi.gcreport.common.util.JsonUtils
 */
package com.jiuqi.gcreport.journalsingle.executor;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelModelExecutor;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.journalsingle.entity.JournalSingleSchemeExcelModel;
import com.jiuqi.gcreport.journalsingle.service.IJournalSingleSubjectService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JournalSingleSchemeImportExecutorImpl
extends AbstractImportExcelModelExecutor<JournalSingleSchemeExcelModel> {
    @Autowired
    private IJournalSingleSubjectService journalSingleSubjectService;

    protected JournalSingleSchemeImportExecutorImpl() {
        super(JournalSingleSchemeExcelModel.class);
    }

    public String getName() {
        return "JournalSingleSchemeImportExecutor";
    }

    protected Object importExcelModels(ImportContext context, List<JournalSingleSchemeExcelModel> rowDatas) {
        Map param = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String jRelateSchemeId = ConverterUtils.getAsString(param.get("jRelateSchemeId"));
        StringBuffer result = this.journalSingleSubjectService.importSubject(rowDatas, jRelateSchemeId);
        return result;
    }
}

