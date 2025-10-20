/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IViewDeployController
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition
 *  com.jiuqi.nr.definition.facade.formula.FormulaCondition
 *  org.apache.poi.common.usermodel.HyperlinkType
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CreationHelper
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.Hyperlink
 *  org.apache.poi.ss.usermodel.IndexedColors
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IViewDeployController;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.facade.formula.FormulaCondition;
import com.jiuqi.nr.designer.service.IFormulaConditionService;
import com.jiuqi.nr.designer.web.facade.ConditionImportResult;
import com.jiuqi.nr.designer.web.facade.FormulaConditionObj;
import com.jiuqi.nr.designer.web.facade.FormulaConditionPageObj;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FormulaConditionService
implements IFormulaConditionService {
    @Autowired
    private IFormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private IViewDeployController viewDeployController;
    private static final String[] HEADERS = new String[]{"\u6807\u8bc6", "\u6807\u9898", "\u9002\u7528\u6761\u4ef6"};
    private static final String[] ERROR_HEADERS = new String[]{"\u5e8f\u53f7", "\u6807\u8bc6", "\u6807\u9898", "\u9002\u7528\u6761\u4ef6", "\u9519\u8bef\u4fe1\u606f"};
    private static final Pattern PATTERN = Pattern.compile("^[A-Z][A-Z0-9_]{0,12}$");
    private static final String ERROR_SHEET = "\u9519\u8bef\u4fe1\u606f";

    @Override
    public void updateFormulaCondition(FormulaConditionObj obj) {
        this.formulaDesignTimeController.updateFormulaCondition(this.convert(obj));
    }

    @Override
    public FormulaConditionPageObj queryConditionsByTask(String task, Long start, Long num) {
        FormulaConditionPageObj pageObj = new FormulaConditionPageObj();
        List conditions = this.formulaDesignTimeController.listFormulaConditionByTask(task);
        List<FormulaConditionObj> conditionObjs = start == null || num == null ? conditions.stream().map(this::convert).collect(Collectors.toList()) : conditions.stream().skip((start - 1L) * num).limit(num).map(this::convert).sorted(Comparator.comparing(FormulaConditionObj::getCode)).collect(Collectors.toList());
        pageObj.setTotal(conditions.size());
        pageObj.setData(conditionObjs);
        return pageObj;
    }

    @Override
    public void updateFormulaConditions(List<FormulaConditionObj> objs) {
        this.formulaDesignTimeController.updateFormulaConditions(objs.stream().map(this::convert).collect(Collectors.toList()));
    }

    @Override
    public void deleteFormulaCondition(String key) {
        this.formulaDesignTimeController.deleteFormulaCondition(key);
    }

    @Override
    public void deleteFormulaConditions(List<String> keys) {
        this.formulaDesignTimeController.deleteFormulaConditions(keys);
    }

    @Override
    public void insertFormulaCondition(FormulaConditionObj obj) {
        if (obj != null) {
            DesignFormulaCondition designFormulaCondition = this.formulaDesignTimeController.initFormulaCondition();
            designFormulaCondition.setCode(obj.getCode());
            designFormulaCondition.setTaskKey(obj.getTaskKey());
            designFormulaCondition.setTitle(obj.getTitle());
            designFormulaCondition.setFormulaCondition(obj.getCondition());
            this.formulaDesignTimeController.insertFormulaCondition(designFormulaCondition);
        }
    }

    @Override
    public void exportConditions(OutputStream outputStream, String task) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();){
            Sheet sheet = workbook.createSheet();
            List conditions = this.formulaDesignTimeController.listFormulaConditionByTask(task);
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; ++i) {
                headerRow.createCell(i).setCellValue(HEADERS[i]);
            }
            int rowNum = 1;
            for (DesignFormulaCondition condition : conditions) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(condition.getCode());
                row.createCell(1).setCellValue(condition.getTitle());
                row.createCell(2).setCellValue(condition.getFormulaCondition());
            }
            workbook.write(outputStream);
        }
        catch (IOException e) {
            throw new RuntimeException("\u5bfc\u51fa\u9002\u7528\u6761\u4ef6\u5931\u8d25", e);
        }
    }

    @Override
    public ConditionImportResult importAddConditions(InputStream inputStream, String task) {
        ConditionImportResult result = new ConditionImportResult();
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream);){
            this.readFromBook((Workbook)workbook, result);
            if (Boolean.FALSE.equals(result.getSuccess())) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                workbook.write((OutputStream)stream);
                result.setExcel(stream.toByteArray());
            } else {
                this.importConditions(task, result);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private void importConditions(String task, ConditionImportResult result) {
        List<DesignFormulaCondition> data = result.getData();
        List oldConditions = this.formulaDesignTimeController.listFormulaConditionByTask(task);
        Map<String, DesignFormulaCondition> cMap = oldConditions.stream().collect(Collectors.toMap(FormulaCondition::getCode, f -> f));
        Set<String> codeSet = cMap.keySet();
        ArrayList<DesignFormulaCondition> aConditions = new ArrayList<DesignFormulaCondition>();
        ArrayList<DesignFormulaCondition> uConditions = new ArrayList<DesignFormulaCondition>();
        for (DesignFormulaCondition condition : data) {
            if (codeSet.contains(condition.getCode())) {
                DesignFormulaCondition temp = cMap.get(condition.getCode());
                temp.setUpdateTime(new Date());
                temp.setTitle(condition.getTitle());
                temp.setFormulaCondition(condition.getFormulaCondition());
                uConditions.add(temp);
                continue;
            }
            condition.setTaskKey(task);
            aConditions.add(condition);
        }
        this.formulaDesignTimeController.insertFormulaConditions(aConditions);
        this.formulaDesignTimeController.updateFormulaConditions(uConditions);
    }

    private void readFromBook(Workbook workbook, ConditionImportResult result) {
        Sheet sheet = workbook.getSheetAt(0);
        String sheetName = sheet.getSheetName();
        HashSet<String> cSet = new HashSet<String>();
        int lastRowNum = sheet.getLastRowNum();
        int firstRowNum = sheet.getFirstRowNum();
        this.checkHead(sheet, firstRowNum);
        for (int i1 = firstRowNum + 1; i1 <= lastRowNum; ++i1) {
            Row row = sheet.getRow(i1);
            if (row == null) continue;
            DesignFormulaCondition condition = this.formulaDesignTimeController.initFormulaCondition();
            String codeValue = this.getCellData(row.getCell(0));
            if (StringUtils.hasText(codeValue)) {
                codeValue = codeValue.toUpperCase(Locale.ROOT);
            }
            condition.setCode(codeValue);
            condition.setTitle(this.getCellData(row.getCell(1)));
            condition.setFormulaCondition(this.getCellData(row.getCell(2)));
            String s = this.checkRowData(cSet, condition);
            if (StringUtils.hasLength(s)) {
                ConditionImportResult.ErrorInfo errorInfo = new ConditionImportResult.ErrorInfo(i1 + 1, s);
                errorInfo.setCode(condition.getCode());
                errorInfo.setTitle(condition.getTitle());
                errorInfo.setCondition(condition.getFormulaCondition());
                result.addErrInfo(errorInfo);
                this.addErrorRow(workbook, sheetName, errorInfo);
                continue;
            }
            result.addData(condition);
        }
    }

    private void addErrorRow(Workbook workbook, String sheetName, ConditionImportResult.ErrorInfo error) {
        Sheet sheet = workbook.getSheet(ERROR_SHEET);
        if (sheet == null) {
            sheet = workbook.createSheet(ERROR_SHEET);
            workbook.setSheetOrder(ERROR_SHEET, 0);
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < ERROR_HEADERS.length; ++i) {
                headerRow.createCell(i).setCellValue(ERROR_HEADERS[i]);
            }
        }
        int lastRowNum = sheet.getLastRowNum();
        Row row = sheet.createRow(++lastRowNum);
        CreationHelper creationHelper = workbook.getCreationHelper();
        row.createCell(0).setCellValue((double)lastRowNum);
        row.createCell(1).setCellValue(error.getCode());
        row.createCell(2).setCellValue(error.getTitle());
        row.createCell(3).setCellValue(error.getCondition());
        Cell cell = row.createCell(4);
        cell.setCellValue(error.getMessage());
        Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.DOCUMENT);
        hyperlink.setAddress(String.format("%s!A%s:C%s", sheetName, error.getLineNum(), error.getLineNum()));
        cell.setHyperlink(hyperlink);
        cell.setCellStyle(this.getCellStyle(workbook));
        error.setLineNum(lastRowNum);
    }

    private CellStyle getCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setUnderline((byte)1);
        font.setColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFont(font);
        return cellStyle;
    }

    private String checkRowData(Set<String> cSet, DesignFormulaCondition condition) {
        if (condition == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.hasLength(condition.getCode())) {
            sb.append("\u6807\u8bc6\u4e3a\u7a7a\uff1b");
        } else if (condition.getCode().length() > 12) {
            sb.append("\u6807\u8bc6\u8d85\u51fa\u957f\u5ea6\uff1b");
        } else if (!PATTERN.matcher(condition.getCode()).matches()) {
            sb.append("\u6807\u8bc6\u5185\u5bb9\u683c\u5f0f\u9519\u8bef\uff08\u4ee5\u5927\u5199\u5b57\u6bcd\u5f00\u5934\uff0c\u53ef\u4ee5\u5305\u542b\u5927\u5199\u5b57\u6bcd\u3001\u6570\u5b57\u548c\u4e0b\u5212\u7ebf\uff0c\u957f\u5ea6\u6700\u957f\u4e3a12\u4f4d\uff09\uff1b");
        }
        if (cSet.contains(condition.getCode())) {
            sb.append("\u6807\u8bc6\u91cd\u590d\uff1b");
        } else {
            cSet.add(condition.getCode());
        }
        if (!StringUtils.hasLength(condition.getTitle())) {
            sb.append("\u6807\u9898\u4e3a\u7a7a\uff1b");
        } else if (condition.getTitle().length() > 40) {
            sb.append("\u6807\u9898\u8d85\u51fa\u957f\u5ea6\uff1b");
        }
        if (!StringUtils.hasLength(condition.getFormulaCondition())) {
            sb.append("\u9002\u5e94\u6761\u4ef6\u4e3a\u7a7a\uff1b");
        } else if (condition.getFormulaCondition().length() > 2000) {
            sb.append("\u9002\u5e94\u6761\u4ef6\u8d85\u51fa\u957f\u5ea6\uff1b");
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : null;
    }

    private String getCellData(Cell cell) {
        return cell == null ? null : cell.toString();
    }

    @Override
    public void deployFormulaConditions(String task, String[] conditionKeys) {
        this.viewDeployController.deployFormulaConditions(task, conditionKeys);
    }

    @Override
    public boolean isConditionExist(String task) {
        return !this.formulaDesignTimeController.listFormulaConditionByTask(task).isEmpty();
    }

    private DesignFormulaCondition convert(FormulaConditionObj obj) {
        DesignFormulaCondition designFormulaCondition = this.formulaDesignTimeController.initFormulaCondition();
        designFormulaCondition.setKey(obj.getKey());
        designFormulaCondition.setTitle(obj.getTitle());
        designFormulaCondition.setCode(obj.getCode());
        designFormulaCondition.setFormulaCondition(obj.getCondition());
        designFormulaCondition.setTaskKey(obj.getTaskKey());
        designFormulaCondition.setOrder(obj.getOrder());
        designFormulaCondition.setUpdateTime(new Date());
        return designFormulaCondition;
    }

    private FormulaConditionObj convert(DesignFormulaCondition designFormulaCondition) {
        FormulaConditionObj formulaConditionObj = new FormulaConditionObj();
        formulaConditionObj.setKey(designFormulaCondition.getKey());
        formulaConditionObj.setCode(designFormulaCondition.getCode());
        formulaConditionObj.setTitle(designFormulaCondition.getTitle());
        formulaConditionObj.setCondition(designFormulaCondition.getFormulaCondition());
        formulaConditionObj.setOrder(designFormulaCondition.getOrder());
        formulaConditionObj.setTaskKey(designFormulaCondition.getTaskKey());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formulaConditionObj.setUpdateTime(simpleDateFormat.format(designFormulaCondition.getUpdateTime()));
        return formulaConditionObj;
    }

    private void checkHead(Sheet sheet, int firstRowNum) {
        Row row = sheet.getRow(firstRowNum);
        for (int i1 = 0; i1 < HEADERS.length; ++i1) {
            Cell cell = row.getCell(i1);
            if (cell != null && HEADERS[i1].equals(row.getCell(i1).getStringCellValue())) continue;
            throw new RuntimeException("\u5bfc\u5165\u6587\u4ef6\u6a21\u677f\u683c\u5f0f\u4e0d\u7b26\uff0c\u8bf7\u68c0\u67e5");
        }
    }
}

