/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.gcreport.consolidatedsystem.executor;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.executor.ConsFormulaExportTask;
import com.jiuqi.gcreport.consolidatedsystem.executor.SubjectDataExportTask;
import com.jiuqi.np.log.LogHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemDataExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    private ConsFormulaExportTask consFormulaExportTask;
    @Autowired
    private SubjectDataExportTask subjectDataExportTask;
    @Autowired
    private ConsolidatedSystemDao consolidatedSystemDao;

    public String getName() {
        return "SystemDataExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        Map param = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String systemId = (String)param.get("systemId");
        ConsolidatedSystemEO consolidatedSystemEO = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)systemId));
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        context.getProgressData().setProgressValueAndRefresh(0.1);
        exportExcelSheets.add(this.subjectDataExportTask.exportExcelSheet(context, systemId, workbook));
        context.getProgressData().setProgressValueAndRefresh(0.4);
        exportExcelSheets.add(this.consFormulaExportTask.exportConsFormula(context, systemId));
        context.getProgressData().setProgressValueAndRefresh(0.8);
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u79d1\u76ee", (String)("\u79d1\u76ee\u5bfc\u51fa-" + consolidatedSystemEO.getSystemName() + "\u5408\u5e76\u4f53\u7cfb"), (String)"\u5bfc\u51fa\u5408\u5e76\u79d1\u76ee\u548c\u5408\u5e76\u9009\u9879");
        return exportExcelSheets;
    }

    protected void callBackSheet(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        if (excelSheet.getSheetName().equals("\u4f53\u7cfb\u516c\u5f0f")) {
            sheet.setColumnWidth(0, 12800);
            sheet.setColumnWidth(5, 25600);
        }
    }
}

