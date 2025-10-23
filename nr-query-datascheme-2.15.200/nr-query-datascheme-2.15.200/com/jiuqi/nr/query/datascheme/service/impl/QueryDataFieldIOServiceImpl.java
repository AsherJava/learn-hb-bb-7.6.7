/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.query.datascheme.service.impl;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.query.datascheme.service.IDesignQueryDataSchemeService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class QueryDataFieldIOServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(QueryDataFieldIOServiceImpl.class);
    private static String[] header = new String[]{"\u540d\u79f0", "\u4ee3\u7801", "\u6570\u636e\u7c7b\u578b", "\u957f\u5ea6/\u7cbe\u5ea6", "\u5c0f\u6570\u4f4d", "\u9690\u85cf", "\u5173\u8054\u679a\u4e3e"};
    private static final int MAX_WIDTH = 65280;
    public static final int LAST_INDEX = 7;
    private static final String TRUE_FORMAT = "\u662f";
    private static final String FALSE_FORMAT = "\u5426";
    @Autowired
    private IDesignQueryDataSchemeService designDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    private static String[] updateHeader = new String[]{"\u4ee3\u7801", "\u540d\u79f0", "\u5173\u8054\u679a\u4e3e", "\u957f\u5ea6"};

    public String export(String tableKey, Sheet tableSheet) {
        int i;
        Row row = tableSheet.createRow(0);
        for (int i2 = 0; i2 < header.length; ++i2) {
            Cell cell = row.createCell(i2);
            cell.setCellValue(header[i2]);
        }
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(tableKey);
        if (dataTable == null) {
            return "\u6a21\u677f";
        }
        CellStyle cellStyle = tableSheet.getWorkbook().createCellStyle();
        DataFormat format = tableSheet.getWorkbook().createDataFormat();
        cellStyle.setDataFormat(format.getFormat("0"));
        int offset = 1;
        List fields = this.designDataSchemeService.getDataFieldByTableKeyAndKind(tableKey, new DataFieldKind[]{DataFieldKind.FIELD, DataFieldKind.FIELD_ZB, DataFieldKind.TABLE_FIELD_DIM});
        for (i = 0; i < fields.size(); ++i) {
            Row dataRow = tableSheet.createRow(i + offset);
            DesignDataField dataField = (DesignDataField)fields.get(i);
            this.createRow(dataField, dataRow, cellStyle);
        }
        for (i = 0; i < header.length; ++i) {
            tableSheet.autoSizeColumn(i);
            int cWidth = tableSheet.getColumnWidth(i) * 17 / 10;
            if (tableSheet.getColumnWidth(i) * 17 / 10 > 65280) {
                cWidth = 65280;
            }
            tableSheet.setColumnWidth(i, cWidth);
        }
        return dataTable.getTitle() + "[" + dataTable.getCode() + "]";
    }

    private void createRow(DesignDataField dataField, Row dataRow, CellStyle cellStyle) {
        IEntityDefine entityDefine;
        int row = 0;
        Cell cell = dataRow.createCell(row++);
        cell.setCellValue(dataField.getTitle());
        cell = dataRow.createCell(row++);
        cell.setCellValue(dataField.getCode());
        cell = dataRow.createCell(row++);
        if (dataField.getDataFieldType() != null) {
            cell.setCellValue(dataField.getDataFieldType().getTitle());
        }
        cell = dataRow.createCell(row++);
        if (dataField.getPrecision() != null) {
            cell.setCellValue(dataField.getPrecision().intValue());
            cell.setCellStyle(cellStyle);
        }
        cell = dataRow.createCell(row++);
        Integer decimal = dataField.getDecimal();
        if (decimal != null) {
            cell.setCellValue(decimal.intValue());
            cell.setCellStyle(cellStyle);
        }
        cell = dataRow.createCell(row++);
        Boolean visible = dataField.getVisible();
        if (visible != null) {
            String is = visible != false ? FALSE_FORMAT : TRUE_FORMAT;
            cell.setCellValue(is);
        }
        cell = dataRow.createCell(row++);
        String refDataEntityKey = dataField.getRefDataEntityKey();
        if (StringUtils.hasText(refDataEntityKey) && (entityDefine = this.entityMetaService.queryEntity(refDataEntityKey)) != null) {
            cell.setCellValue(entityDefine.getCode());
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void imports(String tableKey, Sheet tableSheet) {
        int[] updateIndex = new int[]{-1, -1, -1, -1};
        Row row = this.checkHead(tableSheet, updateIndex);
        if (row == null) {
            return;
        }
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(tableKey);
        if (dataTable == null) {
            throw new SchemeDataException("\u5bfc\u5165\u7684\u8868\u4e0d\u5b58\u5728");
        }
        String dataSchemeKey = dataTable.getDataSchemeKey();
        this.designDataSchemeService.checkDeployStatus(dataSchemeKey);
        Map<String, DesignDataField> designDataFields = this.designDataSchemeService.getDataFieldByTable(tableKey).stream().collect(Collectors.toMap(Basic::getCode, DesignDataField2 -> DesignDataField2));
        ArrayList<DesignDataField> updateDataFields = new ArrayList<DesignDataField>();
        int index = 0;
        for (Row rowData : tableSheet) {
            int rowNum = rowData.getRowNum();
            if (rowNum == 0) continue;
            ++index;
            DesignDataField updateDataField = this.getUpdateDataField(rowData, updateIndex, designDataFields);
            if (updateDataField == null) continue;
            updateDataFields.add(updateDataField);
        }
        if (!CollectionUtils.isEmpty(updateDataFields)) {
            this.save(updateDataFields);
        }
    }

    private void save(List<DesignDataField> update) {
        this.designDataSchemeService.updateQueryDataFields(update);
    }

    private Row checkHead(Sheet tableSheet, int[] updateIndex) {
        Assert.notNull((Object)tableSheet, "tableSheet must not be null.");
        int rowAllNum = tableSheet.getPhysicalNumberOfRows();
        if (rowAllNum < 2) {
            return null;
        }
        Row row = tableSheet.getRow(0);
        for (int i = 0; i < row.getLastCellNum(); ++i) {
            String cellValue = row.getCell(i).getStringCellValue();
            if (cellValue.startsWith(updateHeader[0])) {
                updateIndex[0] = i;
                continue;
            }
            if (cellValue.startsWith(updateHeader[1])) {
                updateIndex[1] = i;
                continue;
            }
            if (cellValue.startsWith(updateHeader[2])) {
                updateIndex[2] = i;
                continue;
            }
            if (!cellValue.startsWith(updateHeader[3])) continue;
            updateIndex[3] = i;
        }
        this.initMessage(row);
        return row;
    }

    private DesignDataField getUpdateDataField(Row dataRow, int[] updateIndex, Map<String, DesignDataField> designDataFields) {
        String length;
        String refDataEntityCode;
        Cell cell = dataRow.getCell(updateIndex[0]);
        String code = this.getStringValue(cell, true);
        if (!StringUtils.hasLength(code)) {
            this.appendMessage(dataRow, "\u5b57\u6bb5\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a\uff1b");
            return null;
        }
        if (!designDataFields.containsKey(code)) {
            this.appendMessage(dataRow, "\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1b");
            return null;
        }
        boolean update = false;
        DesignDataField designDataField = designDataFields.get(code);
        cell = dataRow.getCell(updateIndex[1]);
        String title = this.getStringValue(cell, true);
        if (StringUtils.hasLength(title) && !designDataField.getTitle().equals(title)) {
            designDataField.setTitle(title);
            update = true;
        }
        if (!StringUtils.hasLength(refDataEntityCode = this.getStringValue(cell = dataRow.getCell(updateIndex[2]), true))) {
            if (StringUtils.hasLength(designDataField.getRefDataEntityKey())) {
                designDataField.setRefDataEntityKey(null);
                update = true;
            }
        } else {
            try {
                IEntityDefine entityDefine = this.entityMetaService.queryEntityByCode(refDataEntityCode);
                if (!designDataField.getRefDataEntityKey().equals(entityDefine.getId())) {
                    designDataField.setRefDataEntityKey(entityDefine.getId());
                    TableModelDefine tableModel = this.entityMetaService.getTableModel(entityDefine.getId());
                    String bizKeys = tableModel.getBizKeys();
                    designDataField.setRefDataFieldKey(bizKeys);
                    update = true;
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                update = false;
                this.appendMessage(dataRow, "\u5173\u8054\u679a\u4e3e\u672a\u627e\u5230\uff1b");
            }
        }
        if (StringUtils.hasLength(length = this.getStringValue(cell = dataRow.getCell(updateIndex[3]), true)) && (designDataField.getDataFieldType() == DataFieldType.BIGDECIMAL || designDataField.getDataFieldType() == DataFieldType.STRING || designDataField.getDataFieldType() == DataFieldType.INTEGER)) {
            try {
                int precision = Integer.parseInt(length);
                if (precision >= 1 && precision <= 2000) {
                    update = true;
                    designDataField.setPrecision(Integer.valueOf(precision));
                } else {
                    update = false;
                    this.appendMessage(dataRow, "\u957f\u5ea6\u8303\u56f41-2000\uff1b");
                }
            }
            catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
                update = false;
                this.appendMessage(dataRow, "\u957f\u5ea6\u6570\u503c\u683c\u5f0f\u9519\u8bef\uff1b");
            }
        }
        if (update) {
            return designDataField;
        }
        this.appendMessage(dataRow, "\u5c5e\u6027\u503c\u672a\u4fee\u6539\uff0c\u4e0d\u9700\u8981\u4fee\u6539");
        return null;
    }

    private void appendMessage(Row dataRow, String messageStr) {
        Cell error = dataRow.getCell(7);
        StringBuilder message = new StringBuilder();
        if (error == null) {
            error = dataRow.createCell(7);
        } else {
            message.append(this.getStringValue(error, false));
        }
        message.append(messageStr);
        error.setCellValue(message.toString());
    }

    private void initMessage(Row row) {
        Sheet sheet = row.getSheet();
        for (Row rowItem : sheet) {
            Cell cell = rowItem.getCell(7);
            if (cell == null) continue;
            rowItem.removeCell(cell);
        }
        Cell error = row.getCell(7);
        if (error == null) {
            error = row.createCell(7);
        }
        error.setCellValue("\u9519\u8bef\u8be6\u60c5\n");
    }

    private String getStringValue(Cell cell, boolean regex) {
        if (cell != null) {
            String value = null;
            CellType cellType = cell.getCellType();
            if (cellType == CellType.STRING) {
                value = cell.getStringCellValue();
            }
            if (cellType == CellType.NUMERIC) {
                double number = cell.getNumericCellValue();
                value = String.valueOf((int)number);
            }
            if (value == null) {
                return null;
            }
            if (regex) {
                String trim = value.trim();
                value = trim.replaceAll("\r\n|\r|\n", "");
            }
            return value;
        }
        return null;
    }
}

