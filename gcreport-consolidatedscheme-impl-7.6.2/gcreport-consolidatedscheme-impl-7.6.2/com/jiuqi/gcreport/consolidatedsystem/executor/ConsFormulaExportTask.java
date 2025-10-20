/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO
 */
package com.jiuqi.gcreport.consolidatedsystem.executor;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.consolidatedsystem.service.Formula.ConsolidatedFormulaService;
import com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ConsFormulaExportTask {
    @Autowired
    private ConsolidatedFormulaService consolidatedFormulaService;

    public ExportExcelSheet exportConsFormula(ExportContext context, String systemId) {
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(1), "\u4f53\u7cfb\u516c\u5f0f");
        String[] titles = new String[]{"\u516c\u5f0f", "\u8f93\u5165\u8c03\u6574", "\u81ea\u52a8\u62b5\u9500", "\u624b\u52a8\u62b5\u9500", "\u5e74\u7ed3", "\u4f7f\u7528\u89c4\u5219"};
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        rowDatas.add(titles);
        if (context.isTemplateExportFlag()) {
            exportExcelSheet.getRowDatas().addAll(rowDatas);
            return exportExcelSheet;
        }
        List<ConsolidatedFormulaVO> formulaVOS = this.consolidatedFormulaService.listConsFormulas(systemId, true);
        for (ConsolidatedFormulaVO formulaVO : formulaVOS) {
            Object[] rowData = new Object[titles.length];
            int colIndex = 0;
            rowData[colIndex++] = formulaVO.getFormula();
            rowData[colIndex++] = formulaVO.getInputFlag() != null && formulaVO.getInputFlag() == 1 ? "\u652f\u6301" : "\u4e0d\u652f\u6301";
            rowData[colIndex++] = formulaVO.getAntoFlag() != null && formulaVO.getAntoFlag() == 1 ? "\u652f\u6301" : "\u4e0d\u652f\u6301";
            rowData[colIndex++] = formulaVO.getManualFlag() != null && formulaVO.getManualFlag() == 1 ? "\u652f\u6301" : "\u4e0d\u652f\u6301";
            rowData[colIndex++] = formulaVO.getCarryOver() != null && formulaVO.getCarryOver() == 1 ? "\u652f\u6301" : "\u4e0d\u652f\u6301";
            List ruleList = formulaVO.getRuleBaseData();
            if (!CollectionUtils.isEmpty(ruleList)) {
                rowData[colIndex++] = ruleList.stream().map(rule -> rule.getLocalizedName()).collect(Collectors.joining(","));
            }
            rowDatas.add(rowData);
        }
        exportExcelSheet.getRowDatas().addAll(rowDatas);
        return exportExcelSheet;
    }
}

