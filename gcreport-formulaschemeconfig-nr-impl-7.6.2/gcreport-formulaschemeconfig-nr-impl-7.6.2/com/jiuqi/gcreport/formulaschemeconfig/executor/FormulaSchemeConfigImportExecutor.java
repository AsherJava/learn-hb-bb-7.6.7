/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigTableVO
 */
package com.jiuqi.gcreport.formulaschemeconfig.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.formulaschemeconfig.executor.FormulaSchemeConfigImportTask;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService;
import com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigTableVO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormulaSchemeConfigImportExecutor
extends AbstractImportExcelMultiSheetExecutor {
    @Autowired
    private FormulaSchemeConfigImportTask formulaSchemeConfigImportTask;
    @Autowired
    private FormulaSchemeConfigService formulaSchemeConfigService;

    public String getName() {
        return "FormulaSchemeConfigImportExecutor";
    }

    protected Object importExcelSheets(ImportContext context, List<ImportExcelSheet> excelSheets) {
        Map params = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String taskId = (String)params.get("taskId");
        if (StringUtils.isEmpty((String)taskId)) {
            return "\u62a5\u8868\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a";
        }
        String schemeId = (String)params.get("schemeId");
        if (StringUtils.isEmpty((String)schemeId)) {
            return "\u62a5\u8868\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a";
        }
        String entityId = (String)params.get("entityId");
        if (StringUtils.isEmpty((String)entityId)) {
            return "\u53e3\u5f84\u4e0d\u80fd\u4e3a\u7a7a";
        }
        StringBuilder errorMsg = new StringBuilder(128);
        try {
            for (ImportExcelSheet excelSheet : excelSheets) {
                StringBuilder errorLog = new StringBuilder(128);
                if ("\u7b56\u7565".equals(excelSheet.getSheetName())) {
                    List<NrFormulaSchemeConfigTableVO> formulaBatchStrategyList = this.formulaSchemeConfigImportTask.formulaSchemeConfigImport(params, excelSheet.getExcelSheetDatas(), "batchStrategy", errorLog);
                    this.formulaSchemeConfigService.importFormulaSchemeConfig("batchStrategy", formulaBatchStrategyList);
                } else if ("\u5355\u4f4d".equals(excelSheet.getSheetName())) {
                    List<NrFormulaSchemeConfigTableVO> formulaBatchUnitList = this.formulaSchemeConfigImportTask.formulaSchemeConfigImport(params, excelSheet.getExcelSheetDatas(), "batchUnit", errorLog);
                    this.formulaSchemeConfigService.importFormulaSchemeConfig("batchUnit", formulaBatchUnitList);
                } else {
                    errorLog.append("\u672a\u652f\u6301\u5bfc\u5165 \r\n");
                }
                if (StringUtils.isEmpty((String)errorLog.toString())) continue;
                errorMsg.append("\u3010").append(excelSheet.getSheetName()).append("\u9875\u7b7e\u3011").append((CharSequence)errorLog);
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        return StringUtils.isEmpty((String)errorMsg.toString()) ? null : errorMsg;
    }

    protected int[] getReadSheetNos() {
        return null;
    }
}

