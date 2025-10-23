/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.cellbook.excel.ExcelToCellSheet
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.form.common.ExcelType;
import com.jiuqi.nr.task.form.formio.dto.FormulaParseResult;
import com.jiuqi.nr.task.form.formio.service.IExcelToCellSheetProviderExtend;
import com.jiuqi.nvwa.cellbook.excel.ExcelToCellSheet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class FormImportAnalysisDTO {
    private String fileKey;
    private String formGroupKey;
    private Workbook workbook;
    private ExcelType excelType;
    private Map<Map<String, String>, Sheet> sheetMap;
    private Map<String, List<FormulaParseResult>> checkResult;
    private ExcelToCellSheet excelImportUtil;
    private IExcelToCellSheetProviderExtend excelToCellSheetProviderExtend;

    public Workbook getWorkbook() {
        return this.workbook;
    }

    public void setWorkbook(Workbook workbook) {
        ExcelType excelType = workbook instanceof HSSFWorkbook ? ExcelType.XLSHXXF : ExcelType.XLSXXSSF;
        this.workbook = workbook;
        this.excelType = excelType;
    }

    public ExcelType getExcelType() {
        return this.excelType;
    }

    public Map<Map<String, String>, Sheet> getSheetMap() {
        return this.sheetMap;
    }

    public void setSheetMap(Map<Map<String, String>, Sheet> sheetMap) {
        this.sheetMap = sheetMap;
    }

    public ExcelToCellSheet getExcelImportUtil() {
        return this.excelImportUtil;
    }

    public void setExcelImportUtil(ExcelToCellSheet excelImportUtil) {
        this.excelImportUtil = excelImportUtil;
    }

    public IExcelToCellSheetProviderExtend getExcelToCellSheetProviderExtend() {
        return this.excelToCellSheetProviderExtend;
    }

    public void setExcelToCellSheetProviderExtend(IExcelToCellSheetProviderExtend excelToCellSheetProviderExtend) {
        this.excelToCellSheetProviderExtend = excelToCellSheetProviderExtend;
    }

    public Map<String, List<FormulaParseResult>> getCheckResult() {
        if (this.checkResult == null) {
            this.checkResult = new LinkedHashMap<String, List<FormulaParseResult>>();
        }
        return this.checkResult;
    }

    public List<FormulaParseResult> getCheckResult(String sheet) {
        Map<String, List<FormulaParseResult>> result = this.getCheckResult();
        return result.computeIfAbsent(sheet, k -> new ArrayList());
    }

    public void setCheckResult(Map<String, List<FormulaParseResult>> checkResult) {
        this.checkResult = checkResult;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }
}

