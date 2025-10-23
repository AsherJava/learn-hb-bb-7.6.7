/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.datamodelmanage.service.impl.ImportExportTableServiceImpl
 *  com.jiuqi.nvwa.cellbook.model.Cell
 */
package com.jiuqi.nr.task.form.formio.service.impl;

import com.jiuqi.datamodelmanage.service.impl.ImportExportTableServiceImpl;
import com.jiuqi.nr.task.form.formio.common.ImportCellType;
import com.jiuqi.nr.task.form.formio.common.ImportJudgeRule;
import com.jiuqi.nr.task.form.formio.context.FormImportContext;
import com.jiuqi.nr.task.form.formio.dto.ImportBaseDTO;
import com.jiuqi.nr.task.form.formio.dto.ImportDropDownDTO;
import com.jiuqi.nr.task.form.formio.dto.ImportFormulaDTO;
import com.jiuqi.nr.task.form.formio.dto.ImportLinkDTO;
import com.jiuqi.nr.task.form.formio.format.FormatDTO;
import com.jiuqi.nr.task.form.formio.format.IFormatParser;
import com.jiuqi.nr.task.form.formio.service.IExcelToCellSheetProviderExtend;
import com.jiuqi.nr.task.form.formio.service.impl.FormImportServiceImpl;
import com.jiuqi.nr.task.form.util.FormImportUtils;
import com.jiuqi.nvwa.cellbook.model.Cell;
import java.util.List;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class ExcelToCellSheetProviderExtend
implements IExcelToCellSheetProviderExtend {
    private static Logger logger = LoggerFactory.getLogger(FormImportServiceImpl.class);
    private final Workbook workbook;
    private final DataFormat dataFormat;
    private FormImportContext importContext;
    private final boolean generateField;
    private final IFormatParser formatParser;
    private final FormImportUtils formImportUtils = new FormImportUtils(null, null);

    public ExcelToCellSheetProviderExtend(Workbook workbook) {
        this(workbook, false);
    }

    public ExcelToCellSheetProviderExtend(Workbook workbook, boolean generateField) {
        this.workbook = workbook;
        this.generateField = generateField;
        this.dataFormat = workbook.createDataFormat();
        this.importContext = new FormImportContext();
        this.formatParser = IFormatParser.Factory.newInstance();
    }

    public void readCellDataAfter(org.apache.poi.ss.usermodel.Cell cell, Cell nvwaCell) {
        if (!this.generateField) {
            return;
        }
        FormatDTO formatDTO = this.getCellFormat(cell);
        ImportCellType cellType = this.getCellType(formatDTO);
        String value = ImportExportTableServiceImpl.getCellValue((org.apache.poi.ss.usermodel.Cell)cell);
        logger.debug("\u89e3\u6790\u5355\u5143\u683c\u3010{}\u3011\u7c7b\u578b\u4e3a\u3010{}\u3011\uff0c\u5185\u5bb9\u4e3a\u3010{}\u3011", cell.getAddress(), cellType.getTitle(), value);
        if (this.isDropDownCell(cell)) {
            nvwaCell.setValue("");
            return;
        }
        if (cell.getCellType() == CellType.FORMULA) {
            ImportFormulaDTO importFormulaDTO = new ImportFormulaDTO();
            importFormulaDTO.setPosx(cell.getColumnIndex() + 1);
            importFormulaDTO.setPosy(cell.getRowIndex() + 1);
            importFormulaDTO.setCellType(ImportCellType.FORM_STYLE == cellType ? ImportCellType.BIG_DECIMAL : cellType);
            importFormulaDTO.getCellAttr().setTitle(this.formImportUtils.getFieldTitle(this.getFormImportContext().getCurrentSheet(), cell));
            importFormulaDTO.setAddress(cell.getAddress().toString());
            importFormulaDTO.setFormula(nvwaCell.getFormula());
            this.syncOtherAttr(importFormulaDTO, formatDTO);
            this.getFormImportContext().getFormulaCells().add(importFormulaDTO);
            nvwaCell.setValue("");
            logger.debug("\u89e3\u6790\u516c\u5f0f\u3010{}\u3011", (Object)importFormulaDTO.getCellAttr().getTitle());
            return;
        }
        if (cellType != ImportCellType.FORM_STYLE) {
            ImportLinkDTO importLinkDTO = new ImportLinkDTO();
            importLinkDTO.setPosx(cell.getColumnIndex() + 1);
            importLinkDTO.setPosy(cell.getRowIndex() + 1);
            importLinkDTO.setCellType(cellType);
            this.getFormImportContext().getLinkCells().add(importLinkDTO);
            this.syncOtherAttr(importLinkDTO, formatDTO);
            importLinkDTO.getCellAttr().setTitle(this.formImportUtils.getFieldTitle(this.getFormImportContext().getCurrentSheet(), cell));
            nvwaCell.setValue("");
            logger.debug("\u89e3\u6790\u6307\u6807\u3010{}\u3011", (Object)importLinkDTO.getCellAttr().getTitle());
        }
    }

    private FormatDTO getCellFormat(org.apache.poi.ss.usermodel.Cell cell) {
        String format = this.dataFormat.getFormat(cell.getCellStyle().getDataFormat());
        return this.formatParser.parse(format);
    }

    private ImportCellType getCellType(FormatDTO parse) {
        switch (parse.getFormatType()) {
            case NUMBER: 
            case ACCOUNTING: 
            case CURRENCY: 
            case PERCENTAGE: {
                return ImportCellType.BIG_DECIMAL;
            }
            case DATE: {
                return ImportCellType.DATE;
            }
            case DATE_TIME: {
                return ImportCellType.DATE_TIME;
            }
            case TEXT: {
                return ImportCellType.STRING;
            }
        }
        return ImportCellType.FORM_STYLE;
    }

    private boolean isDropDownCell(org.apache.poi.ss.usermodel.Cell cell) {
        List<ImportDropDownDTO> dropDownCells = this.getFormImportContext().getDropDownCells();
        if (CollectionUtils.isEmpty(dropDownCells)) {
            return false;
        }
        int rowIndex = cell.getRowIndex();
        int columnIndex = cell.getColumnIndex();
        for (ImportDropDownDTO dropDownCell : dropDownCells) {
            if (dropDownCell.getPosx() - 1 != columnIndex || dropDownCell.getPosy() - 1 != rowIndex) continue;
            return true;
        }
        return false;
    }

    @Deprecated
    private String getFieldTitle(org.apache.poi.ss.usermodel.Cell cell, Cell nvwaCell) {
        try {
            String trim;
            String finallyTitle;
            int rowIndex = cell.getRowIndex();
            int columnIndex = cell.getColumnIndex();
            StringBuilder leftTitle = new StringBuilder();
            if (columnIndex > 0) {
                Row row = cell.getRow();
                for (int i = 0; i < columnIndex; ++i) {
                    org.apache.poi.ss.usermodel.Cell leftCell = row.getCell(i);
                    if (leftCell == null || leftCell.getCellType() != CellType.STRING) continue;
                    leftTitle.append(leftCell.getStringCellValue()).append(" ");
                }
            }
            StringBuilder topTitle = new StringBuilder();
            if (rowIndex > 0) {
                Sheet currentSheet = this.getFormImportContext().getCurrentSheet();
                for (int j = 0; j < rowIndex; ++j) {
                    org.apache.poi.ss.usermodel.Cell topCell;
                    Row row = currentSheet.getRow(j);
                    if (row == null || (topCell = row.getCell(columnIndex)) == null || topCell.getCellType() != CellType.STRING) continue;
                    if (StringUtils.hasText(topTitle)) {
                        topTitle.append(".");
                    }
                    topTitle.append(topCell.getStringCellValue());
                }
            }
            if ((finallyTitle = leftTitle + " " + topTitle).length() > 200) {
                finallyTitle = finallyTitle.substring(0, 200);
            }
            if (StringUtils.hasText(trim = finallyTitle.trim())) {
                return trim;
            }
            return cell.getAddress().toString();
        }
        catch (Exception e) {
            String sheetName = this.getFormImportContext().getCurrentSheet().getSheetName();
            int rowIndex = cell.getRowIndex();
            int columnIndex = cell.getColumnIndex();
            logger.error("\u9875\u7b7e[{}]\uff0c\u5355\u5143\u683c[{}]\u751f\u6210\u6307\u6807\u6807\u9898\u51fa\u9519", (Object)sheetName, (Object)(rowIndex + "," + columnIndex));
            return "\u672a\u547d\u540d";
        }
    }

    private void syncOtherAttr(ImportBaseDTO importBaseDTO, FormatDTO format) {
        importBaseDTO.getCellAttr().setFormat(format);
    }

    private ImportCellType getCellType(org.apache.poi.ss.usermodel.Cell cell, Cell nvwaCell) {
        return ImportJudgeRule.judgeType(this.getDataFormat(), cell, nvwaCell);
    }

    public void readCellStyleAfter(org.apache.poi.ss.usermodel.Cell cell, Cell nvwaCell) {
    }

    @Override
    public boolean isGenerateFields() {
        return this.generateField;
    }

    @Override
    public void refresh(Sheet sheet) {
        this.importContext = new FormImportContext(sheet);
    }

    @Override
    public FormImportContext getFormImportContext() {
        return this.importContext;
    }

    public DataFormat getDataFormat() {
        return this.dataFormat;
    }

    public Workbook getWorkbook() {
        return this.workbook;
    }
}

