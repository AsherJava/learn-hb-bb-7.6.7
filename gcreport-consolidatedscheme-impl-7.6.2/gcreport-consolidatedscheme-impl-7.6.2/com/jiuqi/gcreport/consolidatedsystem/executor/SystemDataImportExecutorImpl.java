/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.gcreport.consolidatedsystem.executor;

import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.executor.ConsFormulaImportTask;
import com.jiuqi.gcreport.consolidatedsystem.executor.SubjectDataImportTask;
import com.jiuqi.np.log.LogHelper;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemDataImportExecutorImpl
extends AbstractImportExcelMultiSheetExecutor {
    @Autowired
    private ConsFormulaImportTask consFormulaImportTask;
    @Autowired
    private SubjectDataImportTask subjectDataImportTask;
    @Autowired
    private ConsolidatedSystemDao consolidatedSystemDao;

    public String getName() {
        return "SystemDataImportExecutor";
    }

    protected int[] getReadSheetNos() {
        return null;
    }

    protected Object importExcelSheets(ImportContext context, List<ImportExcelSheet> excelSheets) {
        Map param = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        StringBuffer resultMsg = new StringBuffer();
        String systemId = (String)param.get("systemId");
        ConsolidatedSystemEO consolidatedSystemEO = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)systemId));
        context.getProgressData().setProgressValueAndRefresh(0.1);
        Map sheetName2SheetMap = excelSheets.stream().collect(Collectors.toMap(ImportExcelSheet::getSheetName, Function.identity(), (o1, o2) -> o1));
        ImportExcelSheet consSubjectExcelSheet = (ImportExcelSheet)sheetName2SheetMap.get("\u4f53\u7cfb\u79d1\u76ee");
        if (null != consSubjectExcelSheet) {
            resultMsg.append(this.subjectDataImportTask.uploadSubjects(context, systemId, consSubjectExcelSheet.getExcelSheetDatas()));
            LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u79d1\u76ee", (String)("\u79d1\u76ee\u5bfc\u5165-" + consolidatedSystemEO.getSystemName() + "\u5408\u5e76\u4f53\u7cfb"), (String)"");
        } else {
            resultMsg.append("\u6ca1\u6709\u627e\u5230\u3010\u4f53\u7cfb\u79d1\u76ee\u3011\u9875\u7b7e\uff0c\u8df3\u8fc7\u4f53\u7cfb\u79d1\u76ee\u5bfc\u5165\n");
        }
        context.getProgressData().setProgressValueAndRefresh(0.4);
        ImportExcelSheet consFormulaExcelSheet = (ImportExcelSheet)sheetName2SheetMap.get("\u4f53\u7cfb\u516c\u5f0f");
        if (null != consFormulaExcelSheet) {
            resultMsg = this.consFormulaImportTask.consFormulaImport(consFormulaExcelSheet, systemId, resultMsg);
            LogHelper.info((String)"\u5408\u5e76-\u4f53\u7cfb\u516c\u5f0f", (String)("\u516c\u5f0f\u5bfc\u5165-" + consolidatedSystemEO.getSystemName() + "\u5408\u5e76\u4f53\u7cfb"), (String)"");
        } else {
            resultMsg.append("\u6ca1\u6709\u627e\u5230\u3010\u4f53\u7cfb\u516c\u5f0f\u3011\u9875\u7b7e\uff0c\u8df3\u8fc7\u4f53\u7cfb\u516c\u5f0f\u5bfc\u5165\n");
        }
        context.getProgressData().setProgressValueAndRefresh(0.8);
        return resultMsg;
    }
}

