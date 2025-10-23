/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IViewDeployController
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition
 *  com.jiuqi.nr.definition.facade.formula.FormulaCondition
 *  com.jiuqi.nr.task.api.dto.IPageDTO
 *  com.jiuqi.nr.task.api.file.IFileAreaService
 *  com.jiuqi.nr.task.api.file.dto.FileAreaDTO
 *  com.jiuqi.nr.task.api.file.dto.FileInfoDTO
 *  com.jiuqi.util.OrderGenerator
 *  org.apache.shiro.util.Assert
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.formula.service.impl;

import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IViewDeployController;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.facade.formula.FormulaCondition;
import com.jiuqi.nr.formula.dto.FormulaConditionDTO;
import com.jiuqi.nr.formula.service.IFormulaConditionService;
import com.jiuqi.nr.formula.utils.convert.FormulaConvert;
import com.jiuqi.nr.formula.web.vo.ConditionImportResult;
import com.jiuqi.nr.formula.web.vo.UpdateResult;
import com.jiuqi.nr.task.api.dto.IPageDTO;
import com.jiuqi.nr.task.api.file.IFileAreaService;
import com.jiuqi.nr.task.api.file.dto.FileAreaDTO;
import com.jiuqi.nr.task.api.file.dto.FileInfoDTO;
import com.jiuqi.util.OrderGenerator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.util.Assert;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FormulaConditionServiceImpl
implements IFormulaConditionService {
    @Autowired
    private IDesignTimeFormulaController formulaController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IViewDeployController viewDeployController;
    @Autowired
    private IFileAreaService fileAreaService;
    private static final String[] HEADERS = new String[]{"\u6807\u8bc6", "\u6807\u9898", "\u9002\u7528\u6761\u4ef6"};
    private static final String[] ERROR_HEADERS = new String[]{"\u5e8f\u53f7", "\u6807\u8bc6", "\u6807\u9898", "\u9002\u7528\u6761\u4ef6", "\u9519\u8bef\u4fe1\u606f"};
    private static final Pattern PATTERN = Pattern.compile("^[A-Z][A-Z0-9_]{0,12}$");
    private static final String ERROR_SHEET = "\u9519\u8bef\u4fe1\u606f";
    private static final String PREFIX = "FC";

    @Override
    public UpdateResult updateFormulaCondition(FormulaConditionDTO obj) {
        DesignFormulaCondition condition = this.convert(obj);
        this.formulaController.updateFormulaCondition(condition);
        return this.buildUpdateResult(condition);
    }

    @Override
    public List<FormulaConditionDTO> list(List<String> keys) {
        return this.formulaController.listFormulaConditionByKey(new ArrayList<String>(new HashSet<String>(keys))).stream().map(FormulaConvert::convertCondition).collect(Collectors.toList());
    }

    @Override
    public IPageDTO<FormulaConditionDTO> queryConditionsByTask(String task, Long start, Long num) {
        IPageDTO pageObj = new IPageDTO();
        List conditions = this.formulaController.listFormulaConditionByTask(task);
        List<Object> conditionObjs = new ArrayList(conditions.size());
        if (!CollectionUtils.isEmpty(conditions)) {
            conditionObjs = start == null || num == null ? conditions.stream().map(FormulaConvert::convertCondition).collect(Collectors.toList()) : conditions.stream().skip((start - 1L) * num).limit(num).map(FormulaConvert::convertCondition).sorted(Comparator.comparing(FormulaConditionDTO::getCode)).collect(Collectors.toList());
        }
        pageObj.setTotal(Integer.valueOf(conditions.size()));
        pageObj.setData(conditionObjs);
        return pageObj;
    }

    @Override
    public void updateFormulaConditions(List<FormulaConditionDTO> objs) {
        this.formulaController.updateFormulaConditions(objs.stream().map(this::convert).collect(Collectors.toList()));
    }

    @Override
    public void deleteFormulaCondition(String key) {
        this.formulaController.deleteFormulaCondition(key);
        this.formulaController.deleteFormulaConditionLinkByCondition(new String[]{key});
    }

    @Override
    public void deleteFormulaConditions(List<String> keys) {
        this.formulaController.deleteFormulaConditions(keys);
        this.formulaController.deleteFormulaConditionLinkByCondition(keys.toArray(new String[0]));
    }

    @Override
    public UpdateResult insertFormulaCondition(FormulaConditionDTO obj) {
        Assert.notNull((Object)obj, (String)"\u516c\u5f0f\u9002\u7528\u6761\u4ef6\u4e0d\u80fd\u4e3a\u7a7a");
        DesignFormulaCondition designFormulaCondition = this.formulaController.initFormulaCondition();
        designFormulaCondition.setCode(obj.getCode());
        designFormulaCondition.setTaskKey(obj.getTaskKey());
        designFormulaCondition.setTitle(obj.getTitle());
        designFormulaCondition.setFormulaCondition(obj.getCondition());
        this.formulaController.insertFormulaCondition(designFormulaCondition);
        return this.buildUpdateResult(designFormulaCondition);
    }

    @NotNull
    private UpdateResult buildUpdateResult(DesignFormulaCondition condition) {
        UpdateResult result = new UpdateResult();
        result.setKey(condition.getKey());
        List conditions = this.formulaController.listFormulaConditionByTask(condition.getTaskKey());
        for (int i = conditions.size() - 1; i >= 0; --i) {
            if (!((DesignFormulaCondition)conditions.get(i)).getKey().equals(condition.getKey())) continue;
            result.setIndex(i);
            break;
        }
        result.setTotal(conditions.size());
        return result;
    }

    @Override
    public void exportConditions(OutputStream outputStream, String task) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();){
            Sheet sheet = workbook.createSheet();
            List conditions = this.formulaController.listFormulaConditionByTask(task);
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
    public FileInfoDTO uploadFile(MultipartFile file) {
        try {
            return this.fileAreaService.fileUpload(file.getOriginalFilename(), file.getInputStream(), new FileAreaDTO(true));
        }
        catch (IOException e) {
            throw new RuntimeException("\u4e0a\u4f20\u6587\u4ef6\u5931\u8d25\uff1a", e);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ConditionImportResult importAddConditions(String fileKey, String taskKey) {
        byte[] file = this.fileAreaService.download(fileKey, new FileAreaDTO(true));
        try (ByteArrayInputStream fileStream = new ByteArrayInputStream(file);){
            ConditionImportResult conditionImportResult = this.importAddConditions(fileStream, taskKey);
            return conditionImportResult;
        }
        catch (Exception e) {
            throw new RuntimeException("\u83b7\u53d6\u5de5\u4f5c\u8868\u5931\u8d25:" + e.getMessage());
        }
    }

    public ConditionImportResult importAddConditions(InputStream inputStream, String task) {
        ConditionImportResult result = new ConditionImportResult();
        try (Workbook workbook = WorkbookFactory.create(inputStream);){
            this.readFromBook(workbook, result);
            if (Boolean.FALSE.equals(result.getSuccess())) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                workbook.write(stream);
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
        List oldConditions = this.formulaController.listFormulaConditionByTask(task);
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
        this.formulaController.insertFormulaConditions(aConditions);
        this.formulaController.updateFormulaConditions(uConditions);
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
            DesignFormulaCondition condition = this.formulaController.initFormulaCondition();
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
        row.createCell(0).setCellValue(lastRowNum);
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
        return !this.formulaController.listFormulaConditionByTask(task).isEmpty();
    }

    @Override
    public String generatorCode(String taskKey) {
        List conditions = this.formulaController.listFormulaConditionByTask(taskKey);
        List conditionCodes = conditions.stream().map(FormulaCondition::getCode).filter(code -> code.startsWith(PREFIX)).map(code -> code.substring(PREFIX.length())).sorted().collect(Collectors.toList());
        int maxNum = 1;
        for (int i = conditionCodes.size() - 1; i >= 0; --i) {
            String code2 = (String)conditionCodes.get(i);
            if (!code2.matches("\\d+")) continue;
            maxNum = Integer.parseInt(code2) + 1;
            break;
        }
        return maxNum < 1000 ? PREFIX + String.format("%03d", maxNum) : PREFIX + maxNum;
    }

    @Override
    public FormulaConditionDTO getCondition(String taskKey, String code) {
        List conditions = this.formulaController.listFormulaConditionByTaskAndCode(taskKey, new String[]{code});
        if (CollectionUtils.isEmpty(conditions)) {
            return null;
        }
        return FormulaConvert.convertCondition((DesignFormulaCondition)conditions.get(0));
    }

    @Override
    public List<FormulaConditionDTO> listConditionsByCode(String taskKey, List<String> codes) {
        return this.formulaController.listFormulaConditionByTaskAndCode(taskKey, codes.toArray(new String[0])).stream().map(FormulaConvert::convertCondition).collect(Collectors.toList());
    }

    private void checkHead(Sheet sheet, int firstRowNum) {
        Row row = sheet.getRow(firstRowNum);
        for (int i1 = 0; i1 < HEADERS.length; ++i1) {
            Cell cell = row.getCell(i1);
            if (cell != null && HEADERS[i1].equals(row.getCell(i1).getStringCellValue())) continue;
            throw new RuntimeException("\u5bfc\u5165\u6587\u4ef6\u6a21\u677f\u683c\u5f0f\u4e0d\u7b26\uff0c\u8bf7\u68c0\u67e5");
        }
    }

    private DesignFormulaCondition convert(FormulaConditionDTO obj) {
        DesignFormulaCondition designFormulaCondition = this.formulaController.initFormulaCondition();
        designFormulaCondition.setKey(obj.getKey());
        designFormulaCondition.setTitle(obj.getTitle());
        designFormulaCondition.setCode(obj.getCode());
        designFormulaCondition.setFormulaCondition(obj.getCondition());
        designFormulaCondition.setTaskKey(obj.getTaskKey());
        designFormulaCondition.setOrder(OrderGenerator.newOrder());
        designFormulaCondition.setUpdateTime(new Date());
        return designFormulaCondition;
    }
}

