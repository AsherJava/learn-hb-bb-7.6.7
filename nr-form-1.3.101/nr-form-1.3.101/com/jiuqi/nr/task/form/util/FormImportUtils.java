/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.progress.ProgressItem
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.common.RegionEnterNext
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.impl.FormExtendPropsDefaultValue
 *  com.jiuqi.nr.pinyin.util.HanyuPinyinHelper
 *  com.jiuqi.nr.task.api.cellbook.CellBookInit
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter
 *  com.jiuqi.nvwa.cellbook.excel.ExcelToCellSheet
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.cellbook.model.CellSheet
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBorderStyle
 *  com.jiuqi.util.OrderGenerator
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.nr.task.form.util;

import com.jiuqi.np.definition.progress.ProgressItem;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.common.RegionEnterNext;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.impl.FormExtendPropsDefaultValue;
import com.jiuqi.nr.pinyin.util.HanyuPinyinHelper;
import com.jiuqi.nr.task.api.cellbook.CellBookInit;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.form.common.ExcelType;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeDTO;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeParam;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeRegionDTO;
import com.jiuqi.nr.task.form.dto.FormImportAnalysisDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import com.jiuqi.nr.task.form.formio.common.ImportCellType;
import com.jiuqi.nr.task.form.formio.common.ImportDropDownType;
import com.jiuqi.nr.task.form.formio.context.FormImportContext;
import com.jiuqi.nr.task.form.formio.dto.ExcelDropDownOption;
import com.jiuqi.nr.task.form.formio.dto.ImportBaseDTO;
import com.jiuqi.nr.task.form.formio.dto.ImportBaseDataDTO;
import com.jiuqi.nr.task.form.formio.dto.ImportDropDownDTO;
import com.jiuqi.nr.task.form.formio.dto.ImportFormulaDTO;
import com.jiuqi.nr.task.form.formio.dto.ImportLinkDTO;
import com.jiuqi.nr.task.form.formio.dto.ImportReverseResultDTO;
import com.jiuqi.nr.task.form.formio.dto.ProgressItemDTO;
import com.jiuqi.nr.task.form.formio.format.FormatDTO;
import com.jiuqi.nr.task.form.formio.format.FormatType;
import com.jiuqi.nr.task.form.formio.internal.MergeBaseData;
import com.jiuqi.nr.task.form.link.dto.DataLinkSettingDTO;
import com.jiuqi.nr.task.form.service.IReverseModelingService;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter;
import com.jiuqi.nvwa.cellbook.excel.ExcelToCellSheet;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.util.OrderGenerator;
import com.jiuqi.util.StringUtils;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class FormImportUtils {
    private static final Logger logger = LoggerFactory.getLogger(FormImportUtils.class);
    private static final String FONT_NAME = "\u5fae\u8f6f\u96c5\u9ed1";
    private static final String BORDER_COLOR_DEFAULT = "D4D4D4";
    private static final String BACK_COLOR_DEFAULT_HEADER = "EBEBEB";
    private static final String BACK_COLOR_DEFAULT_CELL = "FFFFFF";
    private static final String FONT_COLOR_DEFAULT = "494949";
    private static final String[] ABC_ARR = new String[]{"Z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static final int FONT_SIZE = 16;
    private static final String MD_ = "MD_";
    private final IDesignTimeViewController designTimeViewController;
    private final IReverseModelingService reverseModelingService;

    public FormImportUtils(IDesignTimeViewController designTimeViewController, IReverseModelingService reverseModelingService) {
        this.designTimeViewController = designTimeViewController;
        this.reverseModelingService = reverseModelingService;
    }

    public String getFormTitle(String formTitle, String formSchemeKey) {
        int index = 1;
        String newFormTitle = formTitle;
        List formList = this.designTimeViewController.listFormByFormScheme(formSchemeKey);
        if (!this.formTitleExist(formTitle, formList)) {
            return newFormTitle;
        }
        StringBuffer newTitle = new StringBuffer();
        newFormTitle = newTitle.append(formTitle).append("_").append(index).toString();
        while (this.formTitleExist(newFormTitle, formList)) {
            newTitle.delete(0, newTitle.length());
            newFormTitle = newTitle.append(formTitle).append("_").append(++index).toString();
        }
        return newFormTitle;
    }

    public String getFormCode(String formCode, String formTitle, String formSchemeKey) {
        StringBuilder newCode;
        List formList = this.designTimeViewController.listFormByFormScheme(formSchemeKey);
        Set codes = formList.stream().map(FormDefine::getFormCode).collect(Collectors.toSet());
        if (StringUtils.isEmpty((String)formCode)) {
            String firstLettersUp = HanyuPinyinHelper.getFirstLettersUp((String)formTitle);
            newCode = new StringBuilder(firstLettersUp);
        } else {
            newCode = new StringBuilder(formCode);
        }
        int index = 1;
        while (codes.contains(newCode.toString())) {
            int li = newCode.lastIndexOf("_");
            if (li > 0) {
                newCode.delete(li, newCode.length());
            }
            newCode.append("_").append(index++);
        }
        return newCode.length() <= 20 ? newCode.toString() : newCode.substring(0, 20);
    }

    private boolean formTitleExist(String formTitle, List<DesignFormDefine> formList) {
        if (formList != null) {
            for (DesignFormDefine designFormDefine : formList) {
                if (!designFormDefine.getTitle().equals(formTitle)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean isExistFormByCode(String formCode, List<DesignFormDefine> formList) {
        if (formList != null) {
            for (DesignFormDefine designFormDefine : formList) {
                if (!designFormDefine.getFormCode().equals(formCode)) continue;
                return true;
            }
        }
        return false;
    }

    public DesignFormDefine buildFormDefine(Map<String, String> formInfo, String formSchemeKey) {
        DesignFormDefine designFormDefine = this.designTimeViewController.initForm();
        designFormDefine.setTitle(formInfo.get("FORM_TITLE"));
        designFormDefine.setOrder(OrderGenerator.newOrder());
        if (StringUtils.isNotEmpty((String)formInfo.get("FORM_CODE"))) {
            designFormDefine.setFormCode(formInfo.get("FORM_CODE"));
        } else {
            designFormDefine.setFormCode(OrderGenerator.newOrder());
        }
        designFormDefine.setIsGather(true);
        designFormDefine.setMasterEntitiesKey(FormExtendPropsDefaultValue.MASTER_ENTITIES_KEY_EXTEND_VALUE);
        designFormDefine.setMeasureUnit(FormExtendPropsDefaultValue.MEASURE_UNIT_EXTEND_VALUE);
        designFormDefine.setFormType(FormType.FORM_TYPE_FIX);
        designFormDefine.setFormScheme(formSchemeKey);
        return designFormDefine;
    }

    public Grid2Data buildGridData(FormImportAnalysisDTO analysisDTO, String formCode, Sheet sheet) {
        CellBook cellBook = CellBookInit.init();
        Grid2Data grid2Data = new Grid2Data();
        ExcelToCellSheet excelImportUtil = analysisDTO.getExcelImportUtil();
        this.doAnalysisDropDown(analysisDTO, formCode, sheet);
        CellSheet cellSheet = excelImportUtil.addCellSheet(sheet.getSheetName(), cellBook);
        CellBookGrid2dataConverter.cellBookToGrid2Data((CellSheet)cellSheet, (Grid2Data)grid2Data);
        grid2Data.insertRows(0, 1);
        grid2Data.insertColumns(0, 1);
        return grid2Data;
    }

    private void doAnalysisDropDown(FormImportAnalysisDTO analysisDTO, String formCode, Sheet sheet) {
        analysisDTO.getExcelToCellSheetProviderExtend().refresh(sheet);
        if (!analysisDTO.getExcelToCellSheetProviderExtend().isGenerateFields()) {
            return;
        }
        this.analysisDropDown(analysisDTO, formCode, sheet);
    }

    private void analysisDropDown(FormImportAnalysisDTO analysisDTO, String formCode, Sheet sheet) {
        List<? extends DataValidation> dataValidations = sheet.getDataValidations();
        FormImportContext formImportContext = analysisDTO.getExcelToCellSheetProviderExtend().getFormImportContext();
        if (!CollectionUtils.isEmpty(dataValidations)) {
            for (DataValidation dataValidation : dataValidations) {
                if (3 == dataValidation.getValidationConstraint().getValidationType()) {
                    String[] explicitListValues = dataValidation.getValidationConstraint().getExplicitListValues();
                    String formula1 = dataValidation.getValidationConstraint().getFormula1();
                    if ((explicitListValues == null || explicitListValues.length == 0) && StringUtils.isEmpty((String)formula1)) continue;
                }
                CellRangeAddressList regions = dataValidation.getRegions();
                for (CellRangeAddress genericChild : regions.getGenericChildren()) {
                    for (int i = genericChild.getFirstRow(); i <= genericChild.getLastRow(); ++i) {
                        for (int j = genericChild.getFirstColumn(); j <= genericChild.getLastColumn(); ++j) {
                            ImportDropDownDTO importDropDown = new ImportDropDownDTO();
                            importDropDown.setCellType(ImportCellType.STRING);
                            importDropDown.setPosy(i + 1);
                            importDropDown.setPosx(j + 1);
                            this.analysisTitleAndCode2(sheet, formCode, importDropDown);
                            this.analysisFormula(dataValidation, genericChild, importDropDown, sheet, analysisDTO.getWorkbook());
                            formImportContext.getDropDownCells().add(importDropDown);
                        }
                    }
                }
            }
        }
    }

    private void analysisTitleAndCode2(Sheet sheet, String formCode, ImportDropDownDTO importDropDown) {
        Cell cell;
        int posx = importDropDown.getPosx();
        int posy = importDropDown.getPosy();
        String fieldTitle = null;
        Row row = sheet.getRow(posy - 1);
        if (row != null && (cell = row.getCell(posx - 1)) != null) {
            fieldTitle = this.getFieldTitle(sheet, cell);
            if (StringUtils.isEmpty((String)fieldTitle)) {
                fieldTitle = cell.getAddress().toString();
                importDropDown.setSuffixCode(fieldTitle);
            } else if (this.isEnglishSimple(fieldTitle)) {
                importDropDown.setSuffixCode(fieldTitle.toUpperCase(Locale.ROOT));
            } else {
                importDropDown.setSuffixCode(HanyuPinyinHelper.getFirstLettersUp((String)fieldTitle));
            }
        }
        if (StringUtils.isEmpty(fieldTitle)) {
            String columnTitle = FormImportUtils.convertToLetters(posx);
            fieldTitle = columnTitle + posy;
            importDropDown.setSuffixCode(fieldTitle);
        }
        importDropDown.setSuffixCode(this.filterErrorChar(importDropDown.getSuffixCode()));
        importDropDown.getCellAttr().setCode(MD_ + importDropDown.getSuffixCode());
        importDropDown.getCellAttr().setTitle(fieldTitle);
    }

    private String filterErrorChar(String fieldCode) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fieldCode.length(); ++i) {
            String str = fieldCode.substring(i, i + 1);
            if (str.matches("[\u4e00-\u9fa5]+")) {
                builder.append(str);
                continue;
            }
            char c = fieldCode.charAt(i);
            if (c == '_' || c == ' ') {
                if (builder.length() <= 1 || builder.charAt(builder.length() - 1) == '_') continue;
                builder.append("_");
                continue;
            }
            if (('0' > c || '9' < c) && ('A' > c || 'Z' < c)) continue;
            builder.append(c);
        }
        return builder.toString();
    }

    @Deprecated
    private void analysisTitleAndCode(Sheet sheet, String formCode, ImportDropDownDTO importDropDown) {
        Cell cell;
        int posx = importDropDown.getPosx();
        int posy = importDropDown.getPosy();
        String finallyTitle = "";
        Row row = sheet.getRow(posy - 1);
        for (int i = posx - 2; i >= 0; --i) {
            cell = row.getCell(i);
            if (cell == null || cell.getCellType() != CellType.STRING || !StringUtils.isNotEmpty((String)cell.getStringCellValue())) continue;
            finallyTitle = cell.getStringCellValue();
            break;
        }
        if (StringUtils.isNotEmpty((String)finallyTitle)) {
            importDropDown.getCellAttr().setTitle(finallyTitle);
        } else {
            for (int j = posy - 2; j >= 0; --j) {
                cell = sheet.getRow(j).getCell(posx - 1);
                if (cell == null || cell.getCellType() == CellType.FORMULA || !StringUtils.isNotEmpty((String)cell.getStringCellValue())) continue;
                finallyTitle = cell.getStringCellValue();
                break;
            }
            if (StringUtils.isNotEmpty((String)finallyTitle)) {
                importDropDown.getCellAttr().setTitle(finallyTitle);
            } else {
                importDropDown.getCellAttr().setTitle(OrderGenerator.newOrder());
            }
        }
        importDropDown.getCellAttr().setCode(this.generateCode(finallyTitle, posx, posy, formCode));
    }

    public String getFieldTitle(Sheet sheet, Cell cell) {
        try {
            String trim;
            String finallyTitle;
            int rowIndex = cell.getRowIndex();
            int columnIndex = cell.getColumnIndex();
            StringBuilder leftTitle = new StringBuilder();
            if (columnIndex > 0) {
                Row row = cell.getRow();
                for (int i = 0; i < columnIndex; ++i) {
                    Cell leftCell = row.getCell(i);
                    if (leftCell == null || leftCell.getCellType() != CellType.STRING) continue;
                    leftTitle.append(leftCell.getStringCellValue());
                    if (leftTitle.lastIndexOf(" ") == leftTitle.length() - 1) continue;
                    leftTitle.append(" ");
                }
            }
            StringBuilder topTitle = new StringBuilder();
            if (rowIndex > 0) {
                Sheet currentSheet = sheet;
                for (int j = 0; j < rowIndex; ++j) {
                    Cell topCell;
                    Row row = currentSheet.getRow(j);
                    if (row == null || (topCell = row.getCell(columnIndex)) == null || topCell.getCellType() != CellType.STRING) continue;
                    String s = this.filterErrorChar(topCell.getStringCellValue());
                    if (!StringUtils.isEmpty((String)topTitle.toString()) && !StringUtils.isEmpty((String)s)) {
                        topTitle.append(".");
                    }
                    topTitle.append(s);
                }
            }
            if ((finallyTitle = leftTitle + " " + topTitle).length() > 200) {
                finallyTitle = finallyTitle.substring(0, 200);
            }
            if (org.springframework.util.StringUtils.hasText(trim = finallyTitle.trim())) {
                return trim;
            }
            return cell.getAddress().toString();
        }
        catch (Exception e) {
            String sheetName = sheet.getSheetName();
            int rowIndex = cell.getRowIndex();
            int columnIndex = cell.getColumnIndex();
            logger.error("\u9875\u7b7e[{}]\uff0c\u5355\u5143\u683c[{}]\u751f\u6210\u6307\u6807\u6807\u9898\u51fa\u9519", (Object)sheetName, (Object)(rowIndex + "," + columnIndex));
            return "\u672a\u547d\u540d";
        }
    }

    @Deprecated
    private String generateCode(String title, int posx, int posy, String formCode) {
        String code = MD_;
        code = this.isEnglishSimple(title) ? code + title.toUpperCase(Locale.ROOT) : code + HanyuPinyinHelper.getFirstLettersUp((String)title);
        if (!StringUtils.isNotEmpty((String)code)) {
            String columnTitle = FormImportUtils.convertToLetters(posx);
            String posName = columnTitle + posy;
            code = code + posName;
        }
        return code;
    }

    public static String convertToLetters(int columnNumber) {
        if (columnNumber < 1) {
            return "";
        }
        StringBuilder letters = new StringBuilder();
        while (columnNumber > 0) {
            letters.insert(0, (char)(65 + --columnNumber % 26));
            columnNumber /= 26;
        }
        return letters.toString();
    }

    private boolean isEnglishSimple(String str) {
        return str != null && str.matches("\\A\\p{ASCII}*\\z");
    }

    private void analysisFormula(DataValidation dataValidation, CellRangeAddress genericChild, ImportDropDownDTO importDropDown, Sheet sheet, Workbook workbook) {
        String formula = dataValidation.getValidationConstraint().getFormula1();
        if (formula != null) {
            int validationType = dataValidation.getValidationConstraint().getValidationType();
            importDropDown.setFormula(formula);
            if (3 == validationType) {
                ArrayList<String> options = new ArrayList<String>();
                if (formula.startsWith("\"") && formula.endsWith("\"")) {
                    String[] items;
                    importDropDown.setDropDownType(ImportDropDownType.DROP_DOWN);
                    for (String item : items = formula.substring(1, formula.length() - 1).split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")) {
                        options.add(item.replace("\"", "").trim());
                    }
                } else {
                    options.addAll(this.parseFormula(formula, sheet, workbook));
                    importDropDown.setDropDownType(ImportDropDownType.DATA_VALIDATION);
                }
                importDropDown.generateDropDownOptions(options);
            }
            return;
        }
        String[] explicitListValues = dataValidation.getValidationConstraint().getExplicitListValues();
        if (explicitListValues != null) {
            importDropDown.setDropDownType(ImportDropDownType.DROP_DOWN);
            ArrayList<String> options = new ArrayList<String>(Arrays.asList(explicitListValues));
            importDropDown.generateDropDownOptions(options);
        }
    }

    private List<String> parseFormula(String formula, Sheet sheet, Workbook workbook) {
        try {
            if (formula.contains("!")) {
                String[] parts = formula.split("!");
                String sheetName = parts[0];
                sheet = workbook.getSheet(sheetName);
            }
            if (formula.matches("^\\$?[A-Z]+:\\$?[A-Z]+$")) {
                String columnLetter = formula.replaceAll("^\\$?([A-Z]+):.*$", "$1");
                return this.parseEntireColumn(columnLetter, sheet);
            }
            if (formula.matches("^\\$?\\d+:\\$?\\d+$")) {
                int rowNum = Integer.parseInt(formula.replaceAll("^\\$?(\\d+):.*$", "$1")) - 1;
                return this.parseEntireRow(rowNum, sheet);
            }
            return this.parseRegularArea(formula, sheet, workbook);
        }
        catch (Exception e) {
            logger.error("\u89e3\u6790\u533a\u57df\u516c\u5f0f\u9519\u8bef: {}", (Object)e.getMessage());
            return Collections.emptyList();
        }
    }

    private List<String> parseRegularArea(String formula, Sheet sheet, Workbook workbook) {
        CellReference[] cellRefs;
        ArrayList<String> values = new ArrayList<String>();
        AreaReference areaRef = new AreaReference(formula, workbook.getSpreadsheetVersion());
        for (CellReference cellRef : cellRefs = areaRef.getAllReferencedCells()) {
            Cell cell;
            Row row;
            if (sheet == null || (row = sheet.getRow(cellRef.getRow())) == null || (cell = row.getCell(cellRef.getCol())) == null || cell.getCellType() != CellType.STRING || !StringUtils.isNotEmpty((String)cell.getStringCellValue())) continue;
            values.add(cell.getStringCellValue());
        }
        return values;
    }

    private List<String> parseEntireColumn(String columnLetter, Sheet sheet) {
        ArrayList<String> values = new ArrayList<String>();
        int colIndex = CellReference.convertColStringToIndex(columnLetter);
        for (int i = colIndex - 1; i >= 0; --i) {
            Cell cell;
            Row row = sheet.getRow(i);
            if (row == null || (cell = row.getCell(colIndex)) == null || cell.getCellType() != CellType.STRING || !StringUtils.isNotEmpty((String)cell.getStringCellValue())) continue;
            values.add(cell.getStringCellValue());
        }
        return values;
    }

    private List<String> parseEntireRow(int rowNum, Sheet sheet) {
        ArrayList<String> values = new ArrayList<String>();
        Row row = sheet.getRow(rowNum);
        if (row != null) {
            for (Cell cell : row) {
                if (cell == null || cell.getCellType() != CellType.STRING || !StringUtils.isNotEmpty((String)cell.getStringCellValue())) continue;
                values.add(cell.getStringCellValue());
            }
        }
        return values;
    }

    public ImportReverseResultDTO generateField(FormImportContext formImportContext) {
        List<Integer> fieldTypes;
        ImportReverseResultDTO importReverseResultDTO = formImportContext.getReverseModelDataProvider().getCurrentImportReverseResultDTO();
        if (importReverseResultDTO == null) {
            importReverseResultDTO = new ImportReverseResultDTO(formImportContext.getCurrentForm());
        }
        if ((fieldTypes = this.getCreateFieldTypes(formImportContext, importReverseResultDTO)).isEmpty()) {
            return importReverseResultDTO;
        }
        this.doReverseModal(formImportContext, importReverseResultDTO, fieldTypes);
        this.setRefEntityKey(formImportContext, importReverseResultDTO);
        return importReverseResultDTO;
    }

    private List<Integer> getCreateFieldTypes(FormImportContext formImportContext, ImportReverseResultDTO importReverseResultDTO) {
        List<ImportLinkDTO> list;
        List<ImportFormulaDTO> formulaCells;
        ArrayList<Integer> fieldTypes = new ArrayList<Integer>();
        List<ImportDropDownDTO> dropDownCells = formImportContext.getDropDownCells();
        if (!CollectionUtils.isEmpty(dropDownCells)) {
            for (ImportDropDownDTO importDropDownDTO : dropDownCells) {
                int value = this.getDataFieldType(importDropDownDTO.getCellType()).getValue();
                fieldTypes.add(value);
                importReverseResultDTO.getCellTypeMap().put(importDropDownDTO.getPosy() + "," + importDropDownDTO.getPosx(), importDropDownDTO.getCellType());
            }
        }
        if (!CollectionUtils.isEmpty(formulaCells = formImportContext.getFormulaCells())) {
            for (ImportFormulaDTO formulaCell : formulaCells) {
                int value = this.getDataFieldType(formulaCell.getCellType()).getValue();
                fieldTypes.add(value);
                importReverseResultDTO.getCellTypeMap().put(formulaCell.getPosy() + "," + formulaCell.getPosx(), formulaCell.getCellType());
            }
        }
        if (!CollectionUtils.isEmpty(list = formImportContext.getLinkCells())) {
            for (ImportLinkDTO linkCell : list) {
                int value = this.getDataFieldType(linkCell.getCellType()).getValue();
                fieldTypes.add(value);
                importReverseResultDTO.getCellTypeMap().put(linkCell.getPosy() + "," + linkCell.getPosx(), linkCell.getCellType());
            }
        }
        return fieldTypes;
    }

    private void setRefEntityKey(FormImportContext formImportContext, ImportReverseResultDTO importReverseResultDTO) {
        MergeBaseData mergeBaseData = formImportContext.getReverseModelDataProvider().getMergeBaseData();
        List<ImportDropDownDTO> dropDownCells = formImportContext.getDropDownCells();
        if (!CollectionUtils.isEmpty(dropDownCells)) {
            HashMap<String, BaseDataDefineDTO> baseDataPosMap = new HashMap<String, BaseDataDefineDTO>();
            for (ImportDropDownDTO dropDownCell : dropDownCells) {
                logger.debug("\u6784\u5efa\u679a\u4e3e\u5b9a\u4e49\uff1acode={}, title={}", (Object)dropDownCell.getCellAttr().getCode(), (Object)dropDownCell.getCellAttr().getTitle());
                String pos = dropDownCell.getPosy() + "," + dropDownCell.getPosx();
                ImportBaseDataDTO importBaseDataDTO = new ImportBaseDataDTO();
                ImportBaseDataDTO.BaseDataDefineDTOMap baseDataDefineDTO = new ImportBaseDataDTO.BaseDataDefineDTOMap();
                baseDataDefineDTO.setStructtype(0);
                baseDataDefineDTO.setSharetype(0);
                baseDataDefineDTO.setTitle(dropDownCell.getCellAttr().getTitle());
                baseDataDefineDTO.setName(dropDownCell.getCellAttr().getCode());
                importBaseDataDTO.setBaseDataDefine(baseDataDefineDTO);
                for (ExcelDropDownOption option : dropDownCell.getOptions()) {
                    logger.debug("\u6784\u5efa\u679a\u4e3e\u6570\u636e\u9879\uff1acode={}, title={}", (Object)option.getCode(), (Object)option.getTitle());
                    ImportBaseDataDTO.BaseDataDTOMap baseDataDTO = new ImportBaseDataDTO.BaseDataDTOMap();
                    baseDataDTO.setName(option.getTitle());
                    baseDataDTO.setCode(option.getCode());
                    baseDataDTO.setObjectcode(option.getCode());
                    baseDataDTO.setTableName(baseDataDefineDTO.getName());
                    importBaseDataDTO.getBaseData().add(baseDataDTO);
                }
                importBaseDataDTO = mergeBaseData.mergeBaseData(importBaseDataDTO);
                baseDataPosMap.put(pos, importBaseDataDTO.getBaseDataDefine());
            }
            Map<String, DataFieldSettingDTO> fieldPosMap = importReverseResultDTO.getFieldPosMap();
            for (Map.Entry entry : baseDataPosMap.entrySet()) {
                String pos = (String)entry.getKey();
                DataFieldSettingDTO dataFieldDTO = fieldPosMap.get(pos);
                dataFieldDTO.setRefDataEntityKey(((BaseDataDefineDTO)baseDataPosMap.get(pos)).getName() + "@BASE");
                dataFieldDTO.setRefDataEntityTitle(((BaseDataDefineDTO)baseDataPosMap.get(pos)).getTitle());
            }
        }
    }

    public void doReverseModal(FormImportContext formImportContext, ImportReverseResultDTO importReverseResultDTO, List<Integer> fieldTypes) {
        DataTableDTO dataTableDTO = importReverseResultDTO.getDataTableDTO();
        String tableKey = dataTableDTO == null ? null : dataTableDTO.getKey();
        String tableCode = dataTableDTO == null ? null : dataTableDTO.getCode();
        Map<String, DataFieldSettingDTO> fieldPosMap = importReverseResultDTO.getFieldPosMap();
        ArrayList<String> fieldCodes = CollectionUtils.isEmpty(fieldPosMap.values()) ? new ArrayList<String>() : fieldPosMap.values().stream().map(DataFieldDTO::getCode).collect(Collectors.toList());
        ReverseModeDTO reverseModeDTO = this.doReverseModal(formImportContext, tableKey, tableCode, fieldCodes, fieldTypes);
        logger.debug("\u53cd\u5411\u5efa\u6a21\u751f\u6210\u6307\u6807\uff1a{}", (Object)reverseModeDTO.getFields().size());
        Iterator<DataFieldSettingDTO> iterator = reverseModeDTO.getFields().iterator();
        logger.debug("\u6dfb\u52a0\u4e0b\u62c9\u6307\u6807\uff1a{}", (Object)formImportContext.getDropDownCells().size());
        this.addFieldAndTable(importReverseResultDTO, reverseModeDTO, dataTableDTO, formImportContext.getDropDownCells(), iterator);
        logger.debug("\u6dfb\u52a0\u516c\u5f0f\u6307\u6807\uff1a{}", (Object)formImportContext.getFormulaCells().size());
        this.addFieldAndTable(importReverseResultDTO, reverseModeDTO, dataTableDTO, formImportContext.getFormulaCells(), iterator);
        logger.debug("\u6dfb\u52a0\u666e\u901a\u6307\u6807\uff1a{}", (Object)formImportContext.getLinkCells().size());
        this.addFieldAndTable(importReverseResultDTO, reverseModeDTO, dataTableDTO, formImportContext.getLinkCells(), iterator);
    }

    private void addFieldAndTable(ImportReverseResultDTO importReverseResultDTO, ReverseModeDTO reverseModeDTO, DataTableDTO dataTableDTO, List<? extends ImportBaseDTO> cells, Iterator<DataFieldSettingDTO> iterator) {
        if (importReverseResultDTO.getDataTableDTO() == null && !CollectionUtils.isEmpty(reverseModeDTO.getTables())) {
            DataTableDTO curTable = reverseModeDTO.getTables().get(0);
            curTable.setState(Constants.DataStatus.NEW.ordinal());
            importReverseResultDTO.setDataTableDTO(curTable);
        }
        for (ImportBaseDTO importBaseDTO : cells) {
            if (!iterator.hasNext()) {
                return;
            }
            String cellPos = importBaseDTO.getPosy() + "," + importBaseDTO.getPosx();
            DataFieldSettingDTO curField = iterator.next();
            if (importBaseDTO instanceof ImportFormulaDTO) {
                ImportFormulaDTO formulaDTO = (ImportFormulaDTO)importBaseDTO;
                if (formulaDTO.getNeedReBuild() == null) {
                    formulaDTO.setNeedReBuild(importBaseDTO.getCellType() == ImportCellType.FORM_STYLE);
                    importBaseDTO.getCellAttr().setCode(curField.getCode());
                    importReverseResultDTO.getImportFormulaDTOMap().put(cellPos, formulaDTO);
                    curField.setState(Constants.DataStatus.NEW.ordinal());
                    curField.setTitle(importBaseDTO.getCellAttr().getTitle());
                    curField.setDataTableKey(importReverseResultDTO.getDataTableDTO().getKey());
                    logger.debug("\u6b63\u5728\u6dfb\u52a0\u5b57\u6bb5\uff1a{}\uff0c\u6807\u8bc6\uff1a{}\uff0c\u7c7b\u578b\uff1a{}\uff0c\u3010{}\u3011", curField.getTitle(), curField.getCode(), DataFieldType.valueOf((int)curField.getDataFieldType()).getTitle(), cellPos);
                    importReverseResultDTO.getFieldPosMap().put(cellPos, curField);
                } else if (Boolean.TRUE.equals(formulaDTO.getNeedReBuild())) {
                    if (!importReverseResultDTO.getFieldPosMap().containsKey(cellPos)) continue;
                    DataFieldSettingDTO dataFieldSettingDTO = importReverseResultDTO.getFieldPosMap().get(cellPos);
                    curField.setKey(dataFieldSettingDTO.getKey());
                    curField.setOrder(dataFieldSettingDTO.getOrder());
                    curField.setCode(dataFieldSettingDTO.getCode());
                    curField.setTitle(dataFieldSettingDTO.getTitle());
                    FormatDTO format = formulaDTO.getCellAttr().getFormat();
                    curField.setFieldFormat(this.convertFormat(format));
                    if (curField.getDataFieldType().intValue() == DataFieldType.BIGDECIMAL.getValue() && format.getFormatType() != FormatType.GENERAL) {
                        curField.setDecimal(format.getDecimal());
                    }
                    if (StringUtils.isEmpty((String)curField.getTitle())) {
                        curField.setTitle(formulaDTO.getCellAttr().getCode());
                    }
                    logger.debug("\u6b63\u5728\u66f4\u65b0\u5b57\u6bb5\uff1a" + curField.getTitle() + "\uff0c\u6807\u8bc6\uff1a" + curField.getCode() + "\uff0c\u7c7b\u578b\uff1a" + DataFieldType.valueOf((int)curField.getDataFieldType()).getTitle() + "\uff0c\u3010" + cellPos + "\u3011");
                    importReverseResultDTO.getFieldPosMap().put(cellPos, curField);
                    continue;
                }
            }
            if (importBaseDTO.getCellType() == ImportCellType.FORM_STYLE) continue;
            curField.setState(Constants.DataStatus.NEW.ordinal());
            curField.setTitle(importBaseDTO.getCellAttr().getTitle());
            curField.setDataTableKey(importReverseResultDTO.getDataTableDTO().getKey());
            FormatDTO format = importBaseDTO.getCellAttr().getFormat();
            curField.setFieldFormat(this.convertFormat(format));
            if (curField.getDataFieldType().intValue() == DataFieldType.BIGDECIMAL.getValue() && format.getFormatType() != FormatType.GENERAL) {
                curField.setDecimal(format.getDecimal());
            }
            curField.setFieldFormat(this.convertFormat(format));
            logger.debug("\u6b63\u5728\u6dfb\u52a0\u5b57\u6bb5\uff1a{}\uff0c\u6807\u8bc6\uff1a{}\uff0c\u7c7b\u578b\uff1a{}\uff0c\u3010{}\u3011", curField.getTitle(), curField.getCode(), DataFieldType.valueOf((int)curField.getDataFieldType()).getTitle(), cellPos);
            importReverseResultDTO.getFieldPosMap().put(cellPos, curField);
        }
    }

    private com.jiuqi.nr.task.form.link.dto.FormatDTO convertFormat(FormatDTO format) {
        com.jiuqi.nr.task.form.link.dto.FormatDTO formatDTO = new com.jiuqi.nr.task.form.link.dto.FormatDTO();
        if (format == null) {
            return formatDTO;
        }
        switch (format.getFormatType()) {
            case NUMBER: {
                formatDTO.setFormatType(1);
                break;
            }
            case DATE: {
                formatDTO.setFormatType(6);
                break;
            }
            case CURRENCY: {
                formatDTO.setFormatType(2);
                break;
            }
            case PERCENTAGE: {
                formatDTO.setFormatType(4);
                break;
            }
            case ACCOUNTING: {
                formatDTO.setFormatType(3);
                break;
            }
            default: {
                formatDTO.setFormatType(0);
            }
        }
        formatDTO.setThousands(format.isHasThousand());
        formatDTO.setDisplayDigits(format.getDecimal());
        switch (format.getCurrency()) {
            case CNY: {
                formatDTO.setCurrency("\u00a5");
                break;
            }
            case USD: 
            case EUR: {
                formatDTO.setCurrency(format.getCurrency().getValue());
            }
        }
        formatDTO.setNegativeStyle(format.getNegativeStyle() != null ? format.getNegativeStyle().getValue() : "");
        formatDTO.setFixMode(null);
        return formatDTO;
    }

    private ReverseModeDTO doReverseModal(FormImportContext formImportContext, String tableKey, String tableCode, List<String> fieldCodes, List<Integer> fieldTypes) {
        DesignDataRegionDefine dataRegion = formImportContext.getDataRegion();
        DesignDataScheme dataScheme = formImportContext.getDataScheme();
        DesignFormDefine currentForm = formImportContext.getCurrentForm();
        DesignFormSchemeDefine formSchemeDefine = formImportContext.getFormSchemeDefine();
        DesignFormGroupDefine groupDefine = formImportContext.getGroupDefine();
        ReverseModeParam reverseModeParam = new ReverseModeParam();
        ReverseModeRegionDTO region = new ReverseModeRegionDTO();
        region.setRegionKey(dataRegion.getKey());
        region.setRegionKind(dataRegion.getRegionKind().getValue());
        region.setRegionTop(dataRegion.getRegionTop());
        region.setRegionLeft(dataRegion.getRegionLeft());
        region.setTableKey(StringUtils.isNotEmpty((String)tableKey) ? tableKey : null);
        region.setTableCode(StringUtils.isNotEmpty((String)tableCode) ? tableCode : null);
        region.setFieldKind(DataFieldKind.FIELD_ZB.getValue());
        region.setFieldTypes(fieldTypes);
        region.setFieldNum(fieldTypes.size());
        region.setFieldCodes(fieldCodes);
        reverseModeParam.setRegions(Collections.singletonList(region));
        reverseModeParam.setDataSchemeKey(dataScheme.getKey());
        reverseModeParam.setDataSchemePrefix(dataScheme.getPrefix());
        reverseModeParam.setDataSchemeTitle(dataScheme.getTitle());
        reverseModeParam.setFormKey(currentForm.getKey());
        reverseModeParam.setFormTitle(currentForm.getTitle());
        reverseModeParam.setFormType(currentForm.getFormType().toString());
        reverseModeParam.setFormCode(currentForm.getFormCode());
        reverseModeParam.setFormGroupKey(groupDefine.getKey());
        reverseModeParam.setFormGroupTitle(groupDefine.getTitle());
        reverseModeParam.setFormSchemeKey(formSchemeDefine.getKey());
        reverseModeParam.setFormSchemeTitle(formSchemeDefine.getTitle());
        reverseModeParam.setReverseModelDataProvider(formImportContext.getReverseModelDataProvider());
        return this.reverseModelingService.reverseModeling(reverseModeParam);
    }

    private DataFieldType getDataFieldType(@NotNull ImportCellType cellType) {
        switch (cellType) {
            case INTEGER: {
                return DataFieldType.INTEGER;
            }
            case BIG_DECIMAL: {
                return DataFieldType.BIGDECIMAL;
            }
            case DATE_TIME: {
                return DataFieldType.DATE_TIME;
            }
            case DATE: {
                return DataFieldType.DATE;
            }
        }
        return DataFieldType.STRING;
    }

    public void generateLinks(ImportReverseResultDTO reverseResultDTO, DesignDataRegionDefine dataRegionDefine) {
        Map<String, DataFieldSettingDTO> fieldPosMap = reverseResultDTO.getFieldPosMap();
        Map<String, DataLinkSettingDTO> links = reverseResultDTO.getLinks();
        for (String pos : fieldPosMap.keySet()) {
            if (links.containsKey(pos)) continue;
            DataFieldSettingDTO dataFieldSettingDTO = fieldPosMap.get(pos);
            String[] split = pos.split(",");
            int posy = Integer.parseInt(split[0]);
            int posx = Integer.parseInt(split[1]);
            DataLinkSettingDTO dataLinkSettingDTO = new DataLinkSettingDTO();
            dataLinkSettingDTO.setType(1);
            dataLinkSettingDTO.setRegionKey(dataRegionDefine.getKey());
            dataLinkSettingDTO.setLinkExpression(dataFieldSettingDTO.getKey());
            dataLinkSettingDTO.setPosX(posx);
            dataLinkSettingDTO.setPosY(posy);
            dataLinkSettingDTO.setRowNum(posy);
            dataLinkSettingDTO.setColNum(posx);
            dataLinkSettingDTO.setState(Constants.DataStatus.NEW.ordinal());
            if (dataFieldSettingDTO.getRefDataEntityKey() != null) {
                dataLinkSettingDTO.setCaptionFieldsString("CODE;NAME");
                dataLinkSettingDTO.setDropDownFieldsString("CODE;NAME");
            }
            links.put(pos, dataLinkSettingDTO);
        }
    }

    private void setRowColNum(Sheet sheet, Grid2Data grid2Data) {
        int rowNum = sheet.getLastRowNum() + 2;
        short colNum = 0;
        for (int i = 0; i < rowNum; ++i) {
            Row row = sheet.getRow(i);
            if (null == row || colNum >= row.getLastCellNum()) continue;
            colNum = row.getLastCellNum();
        }
        grid2Data.setHeaderColumnCount(1);
        grid2Data.setHeaderRowCount(1);
        grid2Data.setFooterColumnCount(0);
        grid2Data.setFooterRowCount(0);
        grid2Data.setRowCount(rowNum);
        grid2Data.setColumnCount(colNum + 1);
    }

    private void setRowProperty(Sheet sheet, Grid2Data grid2Data) {
        for (int rowNum = 0; rowNum < grid2Data.getRowCount(); ++rowNum) {
            if (rowNum == 0) {
                grid2Data.setRowHeight(rowNum, 22);
                grid2Data.setRowHidden(rowNum, false);
                grid2Data.setRowAutoHeight(rowNum, false);
                grid2Data.setRowResizeEnabled(true);
                continue;
            }
            Row row = sheet.getRow(rowNum - 1);
            if (row == null) continue;
            grid2Data.setRowHeight(rowNum, row.getHeight() / 20 * 96 / 72);
            grid2Data.setRowHidden(rowNum, row.getRowStyle() == null ? false : row.getRowStyle().getHidden());
            grid2Data.setRowAutoHeight(rowNum, row.getZeroHeight());
        }
    }

    private void setColProperty(Sheet sheet, Grid2Data grid2Data) {
        int fontWidth = 8;
        for (int colNum = 0; colNum < grid2Data.getColumnCount(); ++colNum) {
            if (colNum == 0) {
                grid2Data.setColumnWidth(colNum, 36);
                grid2Data.setColumnAutoWidth(colNum, false);
                grid2Data.setColumnHidden(colNum, false);
                grid2Data.setColumnResizeEnabled(true);
                continue;
            }
            grid2Data.setColumnWidth(colNum, sheet.getColumnWidth(colNum - 1) / 256 * fontWidth);
            grid2Data.setColumnAutoWidth(colNum, false);
            grid2Data.setColumnHidden(colNum, sheet.getColumnStyle(colNum - 1) == null ? false : sheet.getColumnStyle(colNum - 1).getHidden());
        }
    }

    private void setCells(Sheet sheet, FormImportAnalysisDTO formImportAnalysisDTO, Grid2Data grid2Data) {
        for (int rowNum = 0; rowNum < grid2Data.getRowCount(); ++rowNum) {
            for (int colNum = 0; colNum < grid2Data.getColumnCount(); ++colNum) {
                Cell cell;
                GridCellData gridCellData = null;
                if (rowNum == 0 || colNum == 0) {
                    gridCellData = this.initHeaderRowOrCol(colNum, rowNum);
                    continue;
                }
                Row row = sheet.getRow(rowNum - 1);
                gridCellData = row == null ? this.initDefaultCell(colNum, rowNum) : ((cell = row.getCell(colNum - 1)) == null ? this.initDefaultCell(colNum, rowNum) : this.initCell(colNum, rowNum, cell, sheet, formImportAnalysisDTO));
                if (gridCellData == null) continue;
                grid2Data.setGridCellData(gridCellData, gridCellData.getRowIndex(), gridCellData.getColIndex());
            }
        }
    }

    private void setOtherProperty(Sheet sheet, Grid2Data grid2Data) {
        int mergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < mergeCount; ++i) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstC = ca.getFirstColumn() + 1;
            int lastC = ca.getLastColumn() + 1;
            int firstR = ca.getFirstRow() + 1;
            int lastR = ca.getLastRow() + 1;
            grid2Data.mergeCells(firstC, firstR, lastC, lastR);
        }
    }

    private GridCellData initHeaderRowOrCol(int colNum, int rowNum) {
        GridCellData gridCellData = new GridCellData(colNum, rowNum);
        if (rowNum == 0) {
            if (colNum == 0) {
                gridCellData.setRightBorderColor(Integer.parseInt(BORDER_COLOR_DEFAULT, 16));
                gridCellData.setBottomBorderColor(Integer.parseInt(BORDER_COLOR_DEFAULT, 16));
            } else {
                String colTitle = this.getColumnTitle(colNum);
                gridCellData.setShowText(colTitle);
                gridCellData.setBackGroundColor(Integer.parseInt(BACK_COLOR_DEFAULT_HEADER, 16));
                gridCellData.setBackGroundStyle(0);
                gridCellData.setBottomBorderColor(Integer.parseInt(BORDER_COLOR_DEFAULT, 16));
            }
        } else if (colNum == 0) {
            gridCellData.setShowText(String.valueOf(rowNum));
            gridCellData.setBackGroundColor(Integer.parseInt(BACK_COLOR_DEFAULT_HEADER, 16));
            gridCellData.setBackGroundStyle(0);
            gridCellData.setRightBorderColor(Integer.parseInt(BORDER_COLOR_DEFAULT, 16));
            gridCellData.setFontSize(16);
        }
        gridCellData.setRightBorderStyle(0);
        gridCellData.setBottomBorderStyle(0);
        gridCellData.setFontName(FONT_NAME);
        gridCellData.setHorzAlign(3);
        gridCellData.setVertAlign(3);
        return gridCellData;
    }

    private GridCellData initDefaultCell(int colNum, int rowNum) {
        GridCellData gridCellData = new GridCellData(colNum, rowNum);
        gridCellData.setMerged(false);
        gridCellData.setEditable(true);
        gridCellData.setBackGroundColor(Integer.parseInt(BACK_COLOR_DEFAULT_CELL, 16));
        gridCellData.setBackGroundStyle(0);
        gridCellData.setRightBorderStyle(-1);
        gridCellData.setBottomBorderStyle(-1);
        gridCellData.setRightBorderColor(Integer.parseInt(BORDER_COLOR_DEFAULT, 16));
        gridCellData.setBottomBorderColor(Integer.parseInt(BORDER_COLOR_DEFAULT, 16));
        gridCellData.setFontName(FONT_NAME);
        gridCellData.setForeGroundColor(Integer.parseInt(FONT_COLOR_DEFAULT, 16));
        gridCellData.setFontSize(16);
        gridCellData.setWrapLine(true);
        gridCellData.setHorzAlign(0);
        gridCellData.setVertAlign(0);
        gridCellData.setFitFontSize(false);
        return gridCellData;
    }

    private GridCellData initCell(int colNum, int rowNum, Cell cell, Sheet sheet, FormImportAnalysisDTO formImportAnalysisDTO) {
        ExcelType excelType = formImportAnalysisDTO.getExcelType();
        if (excelType == ExcelType.XLSHXXF) {
            HSSFCellStyle cellStyle = (HSSFCellStyle)cell.getCellStyle();
            HSSFFont font = cellStyle.getFont(formImportAnalysisDTO.getWorkbook());
            font.getHSSFColor((HSSFWorkbook)formImportAnalysisDTO.getWorkbook());
            return this.initGridCellData(formImportAnalysisDTO.getWorkbook(), sheet, cell, cellStyle, font, colNum, rowNum);
        }
        if (excelType == ExcelType.XLSXXSSF) {
            XSSFCellStyle cellStyle = (XSSFCellStyle)cell.getCellStyle();
            XSSFFont font = cellStyle.getFont();
            return this.initGridCellData(formImportAnalysisDTO.getWorkbook(), sheet, cell, cellStyle, font, colNum, rowNum);
        }
        return null;
    }

    private GridCellData initGridCellData(Workbook workbook, Sheet sheet, Cell cell, CellStyle cellStyle, Font font, int col, int row) {
        GridCellData gridCellData = new GridCellData(col, row);
        this.setCellText(cell, gridCellData);
        this.setCellIsMerged(sheet, cell, gridCellData);
        gridCellData.setEditable(true);
        this.setCellBackGroundColor(cellStyle, gridCellData);
        this.setCellStyle(cellStyle, workbook, gridCellData);
        this.setCellFontStyle(font, workbook, gridCellData);
        return gridCellData;
    }

    private void setCellText(Cell cell, GridCellData gridCellData) {
        String text = "";
        switch (cell.getCellType()) {
            case NUMERIC: {
                if (cell.getNumericCellValue() % 1.0 == 0.0) {
                    long codeTmp = (long)cell.getNumericCellValue();
                    text = String.valueOf(codeTmp);
                    break;
                }
                text = String.valueOf(cell.getNumericCellValue());
                break;
            }
            case STRING: {
                text = String.valueOf(cell.getStringCellValue());
                break;
            }
            case BOOLEAN: {
                text = String.valueOf(cell.getBooleanCellValue());
                break;
            }
            case FORMULA: {
                text = String.valueOf(cell.getCellFormula());
                break;
            }
        }
        if (!StringUtils.isEmpty((String)text)) {
            gridCellData.setShowText(text);
            gridCellData.setEditText(text);
        }
    }

    private void setCellIsMerged(Sheet sheet, Cell cell, GridCellData gridCellData) {
        int col = cell.getColumnIndex();
        int row = cell.getRowIndex();
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; ++i) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row != firstRow || row > lastRow || col != firstColumn || col > lastColumn) continue;
            gridCellData.setRowSpan(lastRow - firstRow + 1);
            gridCellData.setColSpan(lastColumn - firstColumn + 1);
            gridCellData.setMerged(true);
        }
        gridCellData.setMerged(false);
    }

    private void setCellBackGroundColor(CellStyle cellStyle, GridCellData gridCellData) {
        if (cellStyle.getFillPattern() == FillPatternType.SOLID_FOREGROUND) {
            gridCellData.setBackGroundColor(this.transBackGroundColor(cellStyle.getFillForegroundColorColor()));
        }
    }

    private void setCellStyle(CellStyle cellStyle, Workbook workbook, GridCellData gridCellData) {
        gridCellData.setRightBorderStyle(this.poiBorderStyleTransGridStyle(cellStyle.getBorderRight()));
        gridCellData.setBottomBorderStyle(this.poiBorderStyleTransGridStyle(cellStyle.getBorderBottom()));
        if (cellStyle instanceof XSSFCellStyle) {
            XSSFCellStyle xSSFCellStyle = (XSSFCellStyle)cellStyle;
            int rightColor = this.transBorderColorByXSSF(cellStyle, xSSFCellStyle.getRightBorderXSSFColor(), cellStyle.getBorderRight());
            int bottomColor = this.transBorderColorByXSSF(cellStyle, xSSFCellStyle.getBottomBorderXSSFColor(), cellStyle.getBorderRight());
            if (rightColor == 0xFFFFFF) {
                gridCellData.setRightBorderStyle(GridEnums.GridBorderStyle.NONE.getStyle());
            }
            if (bottomColor == 0xFFFFFF) {
                gridCellData.setBottomBorderStyle(GridEnums.GridBorderStyle.NONE.getStyle());
            }
            gridCellData.setRightBorderColor(rightColor);
            gridCellData.setBottomBorderColor(bottomColor);
        } else {
            int rightColor = this.transBorderColorByHSSF(workbook, cellStyle, cellStyle.getRightBorderColor());
            int bottomColor = this.transBorderColorByHSSF(workbook, cellStyle, cellStyle.getBottomBorderColor());
            if (rightColor == 0xFFFFFF) {
                gridCellData.setRightBorderStyle(GridEnums.GridBorderStyle.NONE.getStyle());
            }
            if (bottomColor == 0xFFFFFF) {
                gridCellData.setBottomBorderStyle(GridEnums.GridBorderStyle.NONE.getStyle());
            }
            gridCellData.setRightBorderColor(rightColor);
            gridCellData.setBottomBorderColor(bottomColor);
        }
        gridCellData.setWrapLine(cellStyle.getWrapText());
        gridCellData.setIndent((int)cellStyle.getIndention());
        if (cellStyle.getAlignment() == HorizontalAlignment.LEFT) {
            gridCellData.setHorzAlign(1);
        } else if (cellStyle.getAlignment() == HorizontalAlignment.CENTER) {
            gridCellData.setHorzAlign(3);
        } else if (cellStyle.getAlignment() == HorizontalAlignment.RIGHT) {
            gridCellData.setHorzAlign(2);
        } else if (cellStyle.getAlignment() == HorizontalAlignment.GENERAL) {
            String checkNumber = "^[0-9]*$";
            if (StringUtils.isNotEmpty((String)gridCellData.getShowText())) {
                if (Pattern.compile(checkNumber).matcher(gridCellData.getShowText()).matches()) {
                    gridCellData.setHorzAlign(2);
                } else {
                    gridCellData.setHorzAlign(1);
                }
            }
        }
        if (cellStyle.getVerticalAlignment() == VerticalAlignment.TOP) {
            gridCellData.setVertAlign(1);
        } else if (cellStyle.getVerticalAlignment() == VerticalAlignment.CENTER) {
            gridCellData.setVertAlign(3);
        } else if (cellStyle.getVerticalAlignment() == VerticalAlignment.BOTTOM) {
            gridCellData.setVertAlign(2);
        }
        gridCellData.setMultiLine(cellStyle.getWrapText());
    }

    private void setCellFontStyle(Font font, Workbook workbook, GridCellData gridCellData) {
        gridCellData.setFontName(font.getFontName());
        gridCellData.setForeGroundColor(this.transFontColor(workbook, font));
        gridCellData.setFontSize(font.getFontHeightInPoints() * 96 / 72);
        int fontStyle = 0;
        if (font.getBold()) {
            fontStyle ^= 2;
        }
        if (font.getItalic()) {
            fontStyle ^= 4;
        }
        if (font.getUnderline() == 1) {
            fontStyle ^= 8;
        }
        gridCellData.setFontStyle(fontStyle);
    }

    private int transBackGroundColor(org.apache.poi.ss.usermodel.Color color) {
        ColorInfo bgColor = this.excelColor2UOF(color);
        if (bgColor == null) {
            bgColor = new ColorInfo(0, 255, 255, 255);
        }
        if (bgColor.getR() == 0 && bgColor.getG() == 0 && bgColor.getB() == 0) {
            bgColor.setR(255);
            bgColor.setG(255);
            bgColor.setB(255);
        }
        return this.rgbColorToInt(bgColor);
    }

    private ColorInfo excelColor2UOF(org.apache.poi.ss.usermodel.Color color) {
        HSSFColor hc;
        short[] s;
        if (color == null) {
            return null;
        }
        ColorInfo ci = null;
        if (color instanceof XSSFColor) {
            XSSFColor xc = (XSSFColor)color;
            byte[] b = xc.getARGB();
            if (b != null) {
                if (b.length == 4) {
                    ci = ColorInfo.fromARGB((short)(b[0] & 0xFF), (short)(b[1] & 0xFF), (short)(b[2] & 0xFF), (short)(b[3] & 0xFF));
                } else if (b.length == 3) {
                    ci = ColorInfo.fromARGB((short)(b[0] & 0xFF), (short)(b[1] & 0xFF), (short)(b[2] & 0xFF));
                }
            }
        } else if (color instanceof HSSFColor && (s = (hc = (HSSFColor)color).getTriplet()) != null) {
            ci = ColorInfo.fromARGB(s[0], s[1], s[2]);
        }
        return ci;
    }

    private int transFontColor(Workbook workbook, Font font) {
        ColorInfo color = null;
        if (font instanceof XSSFFont) {
            XSSFFont f = (XSSFFont)font;
            color = this.excelColor2UOF(f.getXSSFColor());
            if (color != null) {
                if (color.getR() == 255 && color.getG() == 255 && color.getB() == 255) {
                    color.setR(0);
                    color.setG(0);
                    color.setB(0);
                }
            } else {
                color = new ColorInfo(0, 73, 73, 73);
            }
        } else {
            color = this.excel97Color2UOF(workbook, font.getColor());
        }
        if (color == null) {
            color = new ColorInfo(0, 73, 73, 73);
        }
        return this.rgbColorToInt(color);
    }

    private int poiBorderStyleTransGridStyle(BorderStyle poiBorderStyle) {
        if (poiBorderStyle == BorderStyle.THIN) {
            return GridEnums.GridBorderStyle.SOLID.getStyle();
        }
        if (poiBorderStyle == BorderStyle.DASHED) {
            return GridEnums.GridBorderStyle.DASH.getStyle();
        }
        if (poiBorderStyle == BorderStyle.THICK) {
            return GridEnums.GridBorderStyle.BOLD.getStyle();
        }
        if (poiBorderStyle == BorderStyle.DOUBLE) {
            return GridEnums.GridBorderStyle.DOUBLE.getStyle();
        }
        return GridEnums.GridBorderStyle.AUTO.getStyle();
    }

    private int transBorderColorByXSSF(CellStyle cellStyle, org.apache.poi.ss.usermodel.Color color, BorderStyle poiBorderStyle) {
        ColorInfo ci = null;
        if (cellStyle instanceof XSSFCellStyle) {
            ci = this.excelColor2UOF(color);
            if (ci == null) {
                ci = poiBorderStyle == BorderStyle.THIN ? new ColorInfo(0, 0, 0, 0) : new ColorInfo(0, 212, 212, 212);
            } else if (ci.A == -1 && ci.R == 0 && ci.G == 0 && ci.B == 0) {
                ci = new ColorInfo(0, 212, 212, 212);
            }
            return this.rgbColorToInt(ci);
        }
        return 0xFFFFFF;
    }

    private int transBorderColorByHSSF(Workbook workbook, CellStyle cellStyle, short borderColor) {
        ColorInfo ci = null;
        if (cellStyle instanceof HSSFCellStyle) {
            ci = this.excel97Color2UOF(workbook, borderColor);
            if (ci == null) {
                ci = new ColorInfo(0, 212, 212, 212);
            }
            return this.rgbColorToInt(ci);
        }
        return 0xFFFFFF;
    }

    private ColorInfo excel97Color2UOF(Workbook book, short color) {
        HSSFWorkbook hb;
        HSSFColor hc;
        if (book instanceof HSSFWorkbook && (hc = (hb = (HSSFWorkbook)book).getCustomPalette().getColor(color)) != null) {
            return this.excelColor2UOF(hc);
        }
        return null;
    }

    private int rgbColorToInt(ColorInfo color) {
        String colorR = Integer.toHexString(color.R).equals("0") ? "00" : Integer.toHexString(color.R);
        String colorG = Integer.toHexString(color.G).equals("0") ? "00" : Integer.toHexString(color.G);
        String colorB = Integer.toHexString(color.B).equals("0") ? "00" : Integer.toHexString(color.B);
        StringBuffer rgb = new StringBuffer();
        rgb.append(colorR).append(colorG).append(colorB);
        return Integer.parseInt(rgb.toString(), 16);
    }

    public String getColumnTitle(int colIndex) {
        String colTitle = "";
        if (colIndex < 0) {
            return "NEG";
        }
        if (colIndex <= 26) {
            colTitle = ABC_ARR[colIndex];
        } else {
            int sS = colIndex;
            int yS = -1;
            while (sS > 26) {
                if (yS == 0) {
                    yS = sS % 26;
                    --yS;
                } else {
                    yS = sS % 26;
                }
                sS = (int)Math.floor(sS / 26);
                colTitle = ABC_ARR[yS] + colTitle;
            }
            if (yS == 0) {
                --sS;
            }
            if (sS != 0) {
                colTitle = ABC_ARR[sS] + colTitle;
            }
        }
        return colTitle;
    }

    public DesignDataRegionDefine buildDataRegion(DesignFormDefine formDefine, Grid2Data grid2Data) {
        DesignDataRegionDefine dataRegionDefine = this.designTimeViewController.initDataRegion();
        dataRegionDefine.setFormKey(formDefine.getKey());
        dataRegionDefine.setTitle(formDefine.getTitle() + "\u9ed8\u8ba4\u56fa\u5b9a\u533a\u57df");
        dataRegionDefine.setRegionLeft(1);
        dataRegionDefine.setRegionTop(1);
        dataRegionDefine.setRegionRight(grid2Data.getColumnCount() - 1);
        dataRegionDefine.setRegionBottom(grid2Data.getRowCount() - 1);
        dataRegionDefine.setRegionKind(DataRegionKind.DATA_REGION_SIMPLE);
        dataRegionDefine.setRegionEnterNext(RegionEnterNext.BOTTOM);
        return dataRegionDefine;
    }

    public ProgressItem getProgressItem(String progressId) {
        ProgressItemDTO progressItem = new ProgressItemDTO();
        progressItem.setProgressId(progressId);
        progressItem.addStepTitle("\u8868\u6837\u5bfc\u5165");
        return progressItem;
    }

    public void generateFormula(FormImportContext formImportContext, DesignFormulaSchemeDefine defaultFormulaSchemeByFormScheme, Supplier<DesignFormulaDefine> supplier, ImportReverseResultDTO importReverseResultDTO) {
        int count = 1;
        List<ImportFormulaDTO> formulaCells = formImportContext.getFormulaCells();
        if (formulaCells != null) {
            for (ImportFormulaDTO formulaCell : formulaCells) {
                DesignFormulaDefine designFormulaDefine = supplier.get();
                importReverseResultDTO.getFormulaPosMap().put(formulaCell.getPosy() + "," + formulaCell.getPosx(), designFormulaDefine);
                designFormulaDefine.setFormKey(formImportContext.getCurrentForm().getKey());
                designFormulaDefine.setFormulaSchemeKey(defaultFormulaSchemeByFormScheme.getKey());
                designFormulaDefine.setSyntax(FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL.getValue());
                designFormulaDefine.setExpression(formulaCell.getFormula());
                designFormulaDefine.setUseCalculate(true);
                if (count < 999) {
                    designFormulaDefine.setCode(String.format("%s%03d", formImportContext.getCurrentForm().getFormCode(), count++));
                    continue;
                }
                designFormulaDefine.setCode(String.format("%s%04d", formImportContext.getCurrentForm().getFormCode(), count++));
            }
        }
    }

    static class ColorInfo {
        private int A;
        private int R;
        private int G;
        private int B;

        public int toRGB() {
            return this.R << 16 | this.G << 8 | this.B;
        }

        public Color toAWTColor() {
            return new Color(this.R, this.G, this.B, this.A);
        }

        public static ColorInfo fromARGB(int red, int green, int blue) {
            return new ColorInfo(255, red, green, blue);
        }

        public static ColorInfo fromARGB(int alpha, int red, int green, int blue) {
            return new ColorInfo(alpha, red, green, blue);
        }

        public ColorInfo(int a, int r, int g, int b) {
            this.A = a;
            this.B = b;
            this.R = r;
            this.G = g;
        }

        public int getA() {
            return this.A;
        }

        public void setA(int a) {
            this.A = a;
        }

        public int getR() {
            return this.R;
        }

        public void setR(int r) {
            this.R = r;
        }

        public int getG() {
            return this.G;
        }

        public void setG(int g) {
            this.G = g;
        }

        public int getB() {
            return this.B;
        }

        public void setB(int b) {
            this.B = b;
        }
    }
}

