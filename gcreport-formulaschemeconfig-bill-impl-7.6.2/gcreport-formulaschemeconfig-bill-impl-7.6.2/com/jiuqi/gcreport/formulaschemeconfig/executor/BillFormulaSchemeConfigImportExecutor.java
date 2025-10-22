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
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigTableVO
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.gcreport.formulaschemeconfig.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.formulaschemeconfig.executor.BillFormulaSchemeConfigImportTask;
import com.jiuqi.gcreport.formulaschemeconfig.service.BillFormulaSchemeConfigService;
import com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigTableVO;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillFormulaSchemeConfigImportExecutor
extends AbstractImportExcelMultiSheetExecutor {
    @Autowired
    private BillFormulaSchemeConfigImportTask billFormulaSchemeConfigImportTask;
    @Autowired
    IRunTimeViewController iRunTimeViewController;
    @Autowired
    private BillFormulaSchemeConfigService billFormulaSchemeConfigService;

    public String getName() {
        return "BillFormulaSchemeConfigImportExecutor";
    }

    protected Object importExcelSheets(ImportContext context, List<ImportExcelSheet> excelSheets) {
        Map params = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String billId = (String)params.get("billId");
        if (StringUtils.isEmpty((String)billId)) {
            return "\u5355\u636e\u4e0d\u80fd\u4e3a\u7a7a";
        }
        StringBuilder errorMsg = new StringBuilder(128);
        try {
            for (ImportExcelSheet excelSheet : excelSheets) {
                StringBuilder errorLog = new StringBuilder(128);
                if ("\u7b56\u7565".equals(excelSheet.getSheetName())) {
                    List<BillFormulaSchemeConfigTableVO> formulaBatchStrategyList = this.billFormulaSchemeConfigImportTask.formulaSchemeConfigImport(params, excelSheet.getExcelSheetDatas(), "batchStrategy", errorLog);
                    this.billFormulaSchemeConfigService.importFormulaSchemeConfig("strategySetting", formulaBatchStrategyList);
                } else if ("\u5355\u4f4d".equals(excelSheet.getSheetName())) {
                    List<BillFormulaSchemeConfigTableVO> formulaBatchUnitList = this.billFormulaSchemeConfigImportTask.formulaSchemeConfigImport(params, excelSheet.getExcelSheetDatas(), "batchUnit", errorLog);
                    this.billFormulaSchemeConfigService.importFormulaSchemeConfig("unitSetting", formulaBatchUnitList);
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
        return errorMsg;
    }

    protected int[] getReadSheetNos() {
        return null;
    }
}

