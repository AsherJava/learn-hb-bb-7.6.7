/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelOneSheetExecutor
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckGroupVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeTreeVO
 */
package com.jiuqi.gcreport.financialcheckImpl.scheme.executor.scheme;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelOneSheetExecutor;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeService;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckGroupVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeTreeVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinancialCheckSchemeExportExecutor
extends AbstractExportExcelOneSheetExecutor {
    @Autowired
    private FinancialCheckSchemeService financialCheckSchemeService;

    public String getName() {
        return "FinancialCheckSchemeExportExecutor";
    }

    protected ExportExcelSheet exportExcelSheet(ExportContext context, Workbook workbook) {
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), "\u5bf9\u8d26\u65b9\u6848");
        boolean isTemplateExportFlag = context.isTemplateExportFlag();
        if (!isTemplateExportFlag) {
            FinancialCheckGroupVO condition = (FinancialCheckGroupVO)JsonUtils.readValue((String)context.getParam(), FinancialCheckGroupVO.class);
            ArrayList schemesOrdered = new ArrayList();
            List<FinancialCheckSchemeTreeVO> financialCheckSchemeTreeVOS = this.financialCheckSchemeService.treeCheckGroup(condition, false);
            FinancialCheckSchemeTreeVO root = financialCheckSchemeTreeVOS.get(0);
            for (FinancialCheckSchemeTreeVO financialCheckSchemeTreeVO : root.getChildren()) {
                if (CollectionUtils.isEmpty((Collection)financialCheckSchemeTreeVO.getChildren())) continue;
                schemesOrdered.addAll(financialCheckSchemeTreeVO.getChildren());
            }
        }
        return exportExcelSheet;
    }

    protected void callBackWorkbook(ExportContext context, Workbook workbook) {
    }

    protected void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        boolean isTemplateExportFlag = context.isTemplateExportFlag();
        if (row.getRowNum() == 0 || row.getRowNum() == 1 && isTemplateExportFlag && !"\u5bfc\u5165\u65f6\u5220\u9664\u672c\u884c".equals(cellValue)) {
            CellStyle titleHeadStringStyle = this.buildDefaultHeadCellStyle(workbook);
            titleHeadStringStyle.setAlignment(HorizontalAlignment.CENTER);
            titleHeadStringStyle.setWrapText(true);
            cell.setCellStyle(titleHeadStringStyle);
        }
    }

    protected void callBackSheet(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
    }

    private void handleHead() {
    }
}

