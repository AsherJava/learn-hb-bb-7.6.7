/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.jtable.service.impl.JtableParamServiceImpl
 *  com.jiuqi.nvwa.authority.util.ExcelUtils
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.customExcelBatchImport.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.customExcelBatchImport.bean.CustomExcelAnalysisResultInfo;
import com.jiuqi.nr.customExcelBatchImport.bean.CustomExcelCheckResultInfo;
import com.jiuqi.nr.customExcelBatchImport.bean.ErrorInfo;
import com.jiuqi.nr.customExcelBatchImport.service.ICustomExcelAnalysisService;
import com.jiuqi.nr.customExcelBatchImport.service.impl.CustomExcelRegionTitleServiceImpl;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.jtable.service.impl.JtableParamServiceImpl;
import com.jiuqi.nvwa.authority.util.ExcelUtils;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.BaseFormulaEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class CustomExcelAnalysisServiceImpl
implements ICustomExcelAnalysisService {
    private static final Logger log = LoggerFactory.getLogger(CustomExcelAnalysisServiceImpl.class);
    @Resource
    private IRuntimeDataRegionService iRuntimeDataRegionService;
    @Resource
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Resource
    private IRunTimeViewController runtimeViewController;
    @Resource
    private JtableParamServiceImpl jtableParamService;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    private IDimensionProvider dimensionProvider;
    @Resource
    private CustomExcelRegionTitleServiceImpl customExcelRegionTitleService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    private static int sheetNameLength = 31;

    @Override
    public CustomExcelCheckResultInfo checkWorkbook(String formSchemeKey, String fileName, Workbook workBook, String checkType) {
        CustomExcelCheckResultInfo customExcelCheckResultInfo = new CustomExcelCheckResultInfo();
        Map<String, DataRegionDefine> titleDataRegionMap = this.customExcelRegionTitleService.getAllRegionTitleMap(formSchemeKey);
        if (fileName.endsWith(".xlsx")) {
            fileName = fileName.replace(".xlsx", "");
        } else if (fileName.endsWith(".xls")) {
            fileName = fileName.replace(".xls", "");
        }
        BaseFormulaEvaluator objFormulaEvaluator = null;
        if (workBook instanceof XSSFWorkbook) {
            objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook)workBook);
        } else if (workBook instanceof HSSFWorkbook) {
            objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook)workBook);
        }
        String errorMsg = null;
        String sheetName = "";
        ArrayList<String> errorInfoList = new ArrayList<String>();
        if (checkType.equals("analysis")) {
            int sheetNum = workBook.getNumberOfSheets();
            for (int index = 0; index < sheetNum; ++index) {
                Sheet sheet = workBook.getSheetAt(index);
                DataRegionDefine dataRegionDefine = titleDataRegionMap.get(sheet.getSheetName());
                if (dataRegionDefine == null) continue;
                customExcelCheckResultInfo.setSheetIndex(index);
                customExcelCheckResultInfo.setRegion(dataRegionDefine);
                this.checkTemplateInfo(sheet, dataRegionDefine, customExcelCheckResultInfo, objFormulaEvaluator, checkType);
                break;
            }
            if (customExcelCheckResultInfo.getSheetIndex() == -1) {
                errorMsg = "\u5bfc\u5165\u6587\u4ef6" + fileName + "\u5185\u672a\u627e\u5230\u6821\u9a8c\u6210\u529f\u7684Sheet\u9875\uff01\uff01\uff01";
                errorInfoList.add(errorMsg);
            }
        } else {
            sheetName = fileName.length() > sheetNameLength ? fileName.substring(0, 31) : fileName;
            if (titleDataRegionMap.containsKey(sheetName)) {
                Sheet sheet = workBook.getSheet(sheetName);
                if (sheet != null) {
                    DataRegionDefine dataRegionDefine = titleDataRegionMap.get(fileName);
                    customExcelCheckResultInfo.setRegion(dataRegionDefine);
                    customExcelCheckResultInfo.setSheetIndex(workBook.getSheetIndex(sheet));
                    this.checkTemplateInfo(sheet, dataRegionDefine, customExcelCheckResultInfo, objFormulaEvaluator, checkType);
                } else {
                    errorMsg = "\u6a21\u677f" + fileName + "\u5185\u6ca1\u6709\u5bf9\u5e94\u9875\u7b7e\uff01";
                }
            } else {
                errorMsg = "\u5f53\u524d\u65f6\u671f\u4e0b\u672a\u627e\u5230\u4e0e" + sheetName + "\u5bf9\u5e94\u7684\u62a5\u8868\u4fe1\u606f\uff01";
            }
        }
        if (StringUtils.isNotEmpty(errorMsg)) {
            if (checkType.equals("analysis")) {
                errorMsg = "\u6587\u4ef6\u6570\u636e\u65e0\u6cd5\u5bfc\u5165\uff01\uff01\uff01\uff01";
            }
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setErrorMsg(errorMsg);
            errorInfo.setFileName(fileName);
            customExcelCheckResultInfo.getErrorInfos().add(errorInfo);
        }
        if (errorInfoList.size() > 0) {
            for (String Info : errorInfoList) {
                ErrorInfo errorInfo = new ErrorInfo();
                errorInfo.setErrorMsg(Info);
                errorInfo.setFileName(fileName);
                customExcelCheckResultInfo.getErrorInfos().add(errorInfo);
            }
        }
        return customExcelCheckResultInfo;
    }

    @Override
    public CustomExcelAnalysisResultInfo analysisWorkbookData(String formSchemeKey, File file, String type) {
        CustomExcelAnalysisResultInfo customExcelAnalysisResultInfo = new CustomExcelAnalysisResultInfo();
        Workbook workbook = null;
        try {
            workbook = ExcelUtils.create((InputStream)FileUtils.openInputStream(file));
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        if (workbook == null) {
            return customExcelAnalysisResultInfo;
        }
        String fileName = file.getName();
        CustomExcelCheckResultInfo checkResult = this.checkWorkbook(formSchemeKey, fileName, workbook, "analysis");
        customExcelAnalysisResultInfo.setCheckResult(checkResult);
        if (CollectionUtils.isEmpty(checkResult.getErrorInfos())) {
            this.analysisFileDataInfo(fileName, workbook, customExcelAnalysisResultInfo);
        }
        return customExcelAnalysisResultInfo;
    }

    private void analysisFileDataInfo(String fileName, Workbook workbook, CustomExcelAnalysisResultInfo customExcelAnalysisResultInfo) {
        Sheet sheet = null;
        try {
            if (fileName.endsWith(".xlsx")) {
                fileName = fileName.replace(".xlsx", "");
            } else if (fileName.endsWith(".xls")) {
                fileName = fileName.replace(".xls", "");
            }
            sheet = workbook.getSheetAt(customExcelAnalysisResultInfo.getCheckResult().getSheetIndex());
            customExcelAnalysisResultInfo.getSheetNames().put(fileName, sheet.getSheetName());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        CustomExcelCheckResultInfo checkResult = customExcelAnalysisResultInfo.getCheckResult();
        List<FieldDefine> fileFields = checkResult.getFileFields();
        List<Integer> fileFieldIndexList = checkResult.getFileFieldIndex();
        ArrayList<List<Object>> dataRows = new ArrayList<List<Object>>();
        BaseFormulaEvaluator objFormulaEvaluator = null;
        if (workbook instanceof XSSFWorkbook) {
            objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook)workbook);
        } else if (workbook instanceof HSSFWorkbook) {
            objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook)workbook);
        }
        ArrayList<EntityDefaultValue> entityDefaultValues = new ArrayList();
        RegionSettingDefine regionSettingDefine = this.runtimeViewController.getRegionSetting(checkResult.getRegion().getKey());
        if (regionSettingDefine != null) {
            entityDefaultValues = regionSettingDefine.getEntityDefaultValue();
        }
        for (int index = checkResult.getDimFieldIndex() + 1; index <= sheet.getLastRowNum(); ++index) {
            Row row = sheet.getRow(index);
            List<Object> rowData = this.getRowData(row, objFormulaEvaluator, customExcelAnalysisResultInfo, entityDefaultValues);
            customExcelAnalysisResultInfo.getCheckResult().getFileFields().removeIf(fieldDefine -> fieldDefine == null);
            dataRows.add(rowData);
        }
        customExcelAnalysisResultInfo.setDataRows(dataRows);
    }

    private void checkTemplateInfo(Sheet templateSheet, DataRegionDefine dataRegionDefine, CustomExcelCheckResultInfo customExcelCheckResultInfo, FormulaEvaluator objFormulaEvaluator, String checkType) {
        if (templateSheet == null) {
            String errMsg = "\u5f53\u524d\u6a21\u677f(" + templateSheet.getSheetName() + ")\u5185\u5bb9\u4e3a\u7a7a\uff01";
            if (checkType.equals("analysis")) {
                errMsg = "\u6587\u4ef6\u6570\u636e\u65e0\u6cd5\u5bfc\u5165\uff01\uff01\uff01\uff01";
            }
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setErrorMsg(errMsg);
            errorInfo.setFileName(templateSheet.getSheetName());
            customExcelCheckResultInfo.getErrorInfos().add(errorInfo);
        } else {
            Map<String, List<FieldDefine>> titleEntityListMap = this.customExcelRegionTitleService.getDimFieldDefineMap(dataRegionDefine, customExcelCheckResultInfo);
            Set<String> entityNameList = titleEntityListMap.keySet();
            List<FieldDefine> regionFiledList = this.customExcelRegionTitleService.getRegionFieldDefineList(dataRegionDefine);
            List<EntityDefaultValue> entityDefaultValues = new ArrayList();
            RegionSettingDefine regionSettingDefine = this.runtimeViewController.getRegionSetting(dataRegionDefine.getKey());
            if (regionSettingDefine != null && (entityDefaultValues = regionSettingDefine.getEntityDefaultValue()) != null && !entityDefaultValues.isEmpty()) {
                for (EntityDefaultValue entityDefaultValue : entityDefaultValues) {
                    FieldDefine fieldDefine = null;
                    try {
                        fieldDefine = this.runtimeViewController.queryFieldDefine(entityDefaultValue.getFieldKey());
                    }
                    catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                    if (fieldDefine == null || regionFiledList.contains(fieldDefine)) continue;
                    regionFiledList.add(fieldDefine);
                }
            }
            customExcelCheckResultInfo.setDimFields(titleEntityListMap);
            customExcelCheckResultInfo.setFields(regionFiledList);
            HashMap<String, FieldDefine> filedInfoMap = new HashMap<String, FieldDefine>();
            for (FieldDefine fieldDefine : regionFiledList) {
                List deployInfos = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldDefine.getKey()});
                filedInfoMap.put(((DataFieldDeployInfo)deployInfos.get(0)).getTableName() + "[" + fieldDefine.getCode() + "]", fieldDefine);
            }
            Integer sheetRowNum = templateSheet.getLastRowNum();
            if (sheetRowNum > 20) {
                sheetRowNum = 20;
            }
            Integer dimFieldIndex = -1;
            for (int index = 0; index <= sheetRowNum; ++index) {
                boolean isDimRow = true;
                Row row = templateSheet.getRow(index);
                List<Object> rowData = this.getRowData(row, objFormulaEvaluator, null, entityDefaultValues);
                if (!CollectionUtils.isEmpty(entityDefaultValues)) {
                    for (EntityDefaultValue entityDefaultValue : entityDefaultValues) {
                        FieldDefine fieldDefine = null;
                        try {
                            fieldDefine = this.runtimeViewController.queryFieldDefine(entityDefaultValue.getFieldKey());
                        }
                        catch (Exception e) {
                            log.error(e.getMessage(), e);
                        }
                        if (fieldDefine == null || !regionFiledList.contains(fieldDefine)) continue;
                        List deployInfos = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldDefine.getKey()});
                        rowData.add(((DataFieldDeployInfo)deployInfos.get(0)).getTableName() + "[" + fieldDefine.getCode() + "]");
                    }
                }
                for (String entityName : entityNameList) {
                    if (rowData.contains(entityName)) continue;
                    isDimRow = false;
                }
                if (isDimRow) {
                    ArrayList<FieldDefine> fieldDefineList = new ArrayList<FieldDefine>();
                    ArrayList<Integer> fieldValueIndex = new ArrayList<Integer>();
                    for (int indexOfCell = 0; indexOfCell < rowData.size(); ++indexOfCell) {
                        String cellData = rowData.get(indexOfCell).toString();
                        if (StringUtils.isEmpty((String)cellData) || !titleEntityListMap.containsKey(cellData) && !filedInfoMap.containsKey(cellData)) continue;
                        if (titleEntityListMap.containsKey(cellData)) {
                            fieldDefineList.add(titleEntityListMap.get(cellData).get(0));
                            fieldValueIndex.add(indexOfCell);
                        }
                        if (!filedInfoMap.containsKey(cellData)) continue;
                        fieldDefineList.add((FieldDefine)filedInfoMap.get(cellData));
                        fieldValueIndex.add(indexOfCell);
                    }
                    customExcelCheckResultInfo.setFileFields(fieldDefineList);
                    customExcelCheckResultInfo.setFileFieldIndex(fieldValueIndex);
                }
                if (!isDimRow) continue;
                dimFieldIndex = index;
                customExcelCheckResultInfo.setDimFieldIndex(dimFieldIndex);
                break;
            }
            if (dimFieldIndex < 0) {
                String errMsg = "\u5f53\u524d\u6a21\u677f(" + templateSheet.getSheetName() + ")\u5185\u672a\u627e\u5230\u7ef4\u5ea6\u6307\u6807\u884c";
                if (checkType.equals("analysis")) {
                    customExcelCheckResultInfo.getErrorInfos().clear();
                    errMsg = "\u6587\u4ef6\u6570\u636e\u65e0\u6cd5\u5bfc\u5165\uff01\uff01\uff01\uff01";
                }
                ErrorInfo errorInfo = new ErrorInfo();
                errorInfo.setFileName(templateSheet.getSheetName());
                errorInfo.setErrorMsg(errMsg);
                customExcelCheckResultInfo.getErrorInfos().add(errorInfo);
            }
        }
    }

    private List<Object> getRowData(Row row, FormulaEvaluator objFormulaEvaluator, CustomExcelAnalysisResultInfo customExcelAnalysisResultInfo, List<EntityDefaultValue> entityDefaultValues) {
        ArrayList<Object> dataRowList = new ArrayList<Object>();
        int cellNum = row.getLastCellNum();
        List<FieldDefine> fieldDefineList = null;
        List<Integer> fileFieldIndex = null;
        if (customExcelAnalysisResultInfo != null && customExcelAnalysisResultInfo.getCheckResult().getFileFields() != null) {
            fileFieldIndex = customExcelAnalysisResultInfo.getCheckResult().getFileFieldIndex();
            fieldDefineList = customExcelAnalysisResultInfo.getCheckResult().getFileFields();
            cellNum = fileFieldIndex.size();
        }
        if (fieldDefineList != null && cellNum < fieldDefineList.size()) {
            cellNum = fieldDefineList.size();
        }
        DataFormatter objDefaultFormat = new DataFormatter();
        boolean hasDealMdCode = false;
        for (int indexOfCell = 0; indexOfCell < cellNum; ++indexOfCell) {
            Object cellValueStr;
            FieldDefine fieldDefine;
            if (fieldDefineList != null && fileFieldIndex != null && fileFieldIndex.contains(indexOfCell) && (fieldDefine = fieldDefineList.get(fileFieldIndex.indexOf(indexOfCell))) != null) {
                cellValueStr = "";
                if (row.getCell(indexOfCell) != null) {
                    Cell cell = row.getCell(indexOfCell);
                    objFormulaEvaluator.evaluate(cell);
                    switch (cell.getCellType()) {
                        case STRING: {
                            cellValueStr = cell.getStringCellValue();
                            break;
                        }
                        case NUMERIC: {
                            if (!hasDealMdCode && "MDCODE".equals(fieldDefine.getCode())) {
                                cellValueStr = objDefaultFormat.formatCellValue(cell);
                                hasDealMdCode = true;
                                break;
                            }
                            if (DateUtil.isCellDateFormatted(cell)) {
                                cellValueStr = DateUtil.getJavaDate(cell.getNumericCellValue());
                                break;
                            }
                            DecimalFormat df = new DecimalFormat("0.####################");
                            String value = df.format(cell.getNumericCellValue());
                            BigDecimal bd1 = new BigDecimal(value);
                            cellValueStr = bd1.stripTrailingZeros().toPlainString();
                            break;
                        }
                        case BOOLEAN: {
                            cellValueStr = String.valueOf(cell.getBooleanCellValue());
                            break;
                        }
                        case FORMULA: {
                            cellValueStr = cell.getCellFormula();
                            break;
                        }
                    }
                } else if (!CollectionUtils.isEmpty(entityDefaultValues)) {
                    for (EntityDefaultValue entityDefaultValue : entityDefaultValues) {
                        if (!fieldDefineList.get(fileFieldIndex.indexOf(indexOfCell)).getKey().equals(entityDefaultValue.getFieldKey())) continue;
                        cellValueStr = entityDefaultValue.getItemValue();
                        break;
                    }
                }
                dataRowList.add(cellValueStr);
            }
            if (fieldDefineList != null) continue;
            Cell cell = row.getCell(indexOfCell);
            objFormulaEvaluator.evaluate(cell);
            cellValueStr = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
            switch (cell.getCellType()) {
                case STRING: {
                    cellValueStr = cell.getStringCellValue();
                    break;
                }
                case NUMERIC: {
                    cellValueStr = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case BOOLEAN: {
                    cellValueStr = String.valueOf(cell.getBooleanCellValue());
                    break;
                }
                case FORMULA: {
                    cellValueStr = cell.getCellFormula();
                    break;
                }
            }
            dataRowList.add(cellValueStr);
        }
        return dataRowList;
    }
}

