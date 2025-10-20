/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelMultiSheetExecutor
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.gcreport.invest.investbill.executor;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.invest.investbill.executor.FairValueBillImportTask;
import com.jiuqi.gcreport.invest.investbill.executor.InvestBillImportTask;
import com.jiuqi.gcreport.invest.investbill.executor.InvestBillItemImportTask;
import com.jiuqi.np.log.LogHelper;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvestBillImportExecutorImpl
extends AbstractImportExcelMultiSheetExecutor {
    @Autowired
    private InvestBillImportTask investBillImportTask;
    @Autowired
    private InvestBillItemImportTask investBillItemImportTask;
    @Autowired
    private FairValueBillImportTask fairValueBillImportTask;

    public String getName() {
        return "InvestBillImportExecutor";
    }

    protected Object importExcelSheets(ImportContext context, List<ImportExcelSheet> excelSheets) {
        Map params = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String defineCode = (String)params.get("defineCode");
        if (StringUtils.isEmpty((String)defineCode)) {
            return "defineCode(\u5355\u636e\u6807\u8bc6)\u4e0d\u80fd\u4e3a\u7a7a";
        }
        Map sheetName2SheetMap = excelSheets.stream().collect(Collectors.toMap(ImportExcelSheet::getSheetName, Function.identity(), (o1, o2) -> o1));
        if (null == sheetName2SheetMap.get("\u6295\u8d44\u53f0\u8d26\u53d8\u52a8") && null == sheetName2SheetMap.get("\u6295\u8d44\u53f0\u8d26") && null == sheetName2SheetMap.get("\u516c\u5141\u4ef7\u503c\u8c03\u6574")) {
            return "\u5bfc\u5165\u6587\u4ef6\u5185\u5bb9\u4e0d\u6b63\u786e";
        }
        StringBuilder log = new StringBuilder(128);
        ImportExcelSheet excelSheet = (ImportExcelSheet)sheetName2SheetMap.get("\u6295\u8d44\u53f0\u8d26\u53d8\u52a8");
        HashMap<String, String> unitAndOppUnitCode2SrcIdMap = new HashMap<String, String>();
        Map<ArrayKey, List<Map<String, Object>>> subKey2RecordMap = this.investBillItemImportTask.investBillImport(params, this.getExcelSheetDatas(excelSheet), log);
        excelSheet = (ImportExcelSheet)sheetName2SheetMap.get("\u6295\u8d44\u53f0\u8d26");
        log.append((CharSequence)this.investBillImportTask.investBillImport(params, this.getExcelSheetDatas(excelSheet), subKey2RecordMap, unitAndOppUnitCode2SrcIdMap));
        excelSheet = (ImportExcelSheet)sheetName2SheetMap.get("\u516c\u5141\u4ef7\u503c\u8c03\u6574");
        log.append((CharSequence)this.fairValueBillImportTask.fairValueBillImport(params, this.getExcelSheetDatas(excelSheet), unitAndOppUnitCode2SrcIdMap));
        this.addInvestBillImportLog(context.getParam());
        return log;
    }

    private void addInvestBillImportLog(String param) {
        Map params = (Map)JsonUtils.readValue((String)param, Map.class);
        String mergeUnit = (String)params.get("mergeUnit");
        String acctYear = ConverterUtils.getAsString(params.get("acctYear"));
        String operateTypeTitle = String.format("\u5bfc\u51fa-\u5e74\u5ea6%1s-\u6267\u884c\u64cd\u4f5c\u5355\u4f4d%2s", acctYear, mergeUnit);
        LogHelper.info((String)"\u5408\u5e76-\u6295\u8d44\u53f0\u8d26", (String)operateTypeTitle, (String)operateTypeTitle);
    }

    private List<Object[]> getExcelSheetDatas(ImportExcelSheet excelSheet) {
        if (null == excelSheet) {
            return Collections.EMPTY_LIST;
        }
        return excelSheet.getExcelSheetDatas();
    }

    protected int[] getReadSheetNos() {
        return null;
    }
}

