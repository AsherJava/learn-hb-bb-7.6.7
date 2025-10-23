/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataWrapper
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.VerificationParser
 *  com.jiuqi.nr.datascheme.api.type.CompareType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.encryption.desensitization.bean.DesensitizationInfo
 *  com.jiuqi.nvwa.encryption.desensitization.service.DesensitizationStrategyManager
 *  com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager
 *  javax.validation.ConstraintViolation
 *  javax.validation.Validator
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataWrapper;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.VerificationParser;
import com.jiuqi.nr.datascheme.api.type.CompareType;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.BizKeyOrder;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.config.DataSchemeExcel;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.service.DataFieldIOService;
import com.jiuqi.nr.datascheme.internal.service.impl.AbstractDataValidator;
import com.jiuqi.nr.datascheme.internal.service.impl.cache.DefaultDataWrapper;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.encryption.desensitization.bean.DesensitizationInfo;
import com.jiuqi.nvwa.encryption.desensitization.service.DesensitizationStrategyManager;
import com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class DataFieldIOServiceImpl
extends AbstractDataValidator
implements DataFieldIOService {
    private static final Logger logger = LoggerFactory.getLogger(DataFieldIOServiceImpl.class);
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    @Qualifier(value="RuntimeDataSchemeNoCacheServiceImpl-NO_CACHE")
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDataFieldDao<DesignDataFieldDO> dataFieldDao;
    @Autowired
    private DataSchemeExcel dataSchemeExcel;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private Validator validator;
    @Autowired
    private VerificationParser verificationParser;
    @Autowired
    private IParamLevelManager paramLevelManager;
    @Autowired
    private DesensitizationStrategyManager desensitizationStrategyManager;
    private final ObjectMapper mapper = new ObjectMapper();
    private static final String CHANGE_WITH_PERIOD = "\u4e1a\u52a1\u6570\u636e";
    private static final String NOCHANGE_WITH_PERIOD = "\u57fa\u672c\u4fe1\u606f";
    private static final String TRUE_FORMAT = "\u662f";
    private static final String FALSE_FORMAT = "\u5426";
    private static final String MONEY = "\u91d1\u989d";
    private static final String NOTDIM = "\u4e0d\u8bbe\u7f6e\u91cf\u7eb2";
    public static final List<String> MONEY_LIST = Arrays.asList("YUAN", "BAIYUAN", "QIANYUAN", "WANYUAN", "BAIWAN", "QIANWAN", "YIYUAN");
    private static final int MAX_WIDTH = 65280;
    public static final int LAST_INDEX = 23;

    @Override
    public String export(String tableKey, Sheet tableSheet) {
        int i;
        Row row = tableSheet.createRow(0);
        String[] header = this.dataSchemeExcel.getHeader();
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
        Map<String, String> dataMaskNameMap = this.desensitizationStrategyManager.getAllStrategys().stream().collect(Collectors.toMap(DesensitizationInfo::getCode, DesensitizationInfo::getName));
        for (i = 0; i < fields.size(); ++i) {
            Row dataRow = tableSheet.createRow(i + offset);
            DesignDataField dataField = (DesignDataField)fields.get(i);
            this.createRow(dataField, dataRow, cellStyle, dataMaskNameMap);
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

    private void createRow(DesignDataField dataField, Row dataRow, CellStyle cellStyle, Map<String, String> dataMaskNameMap) {
        boolean isNotDim;
        List validationRules;
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
        Boolean nullable = dataField.getNullable();
        if (nullable != null) {
            String is = nullable != false ? TRUE_FORMAT : FALSE_FORMAT;
            cell.setCellValue(is);
        }
        cell = dataRow.createCell(row++);
        cell.setCellValue(dataField.getDefaultValue());
        cell = dataRow.createCell(row++);
        DataFieldApplyType dataFieldApplyType = dataField.getDataFieldApplyType();
        if (dataFieldApplyType != null) {
            cell.setCellValue(dataFieldApplyType.getTitle());
        }
        cell = dataRow.createCell(row++);
        cell.setCellValue(dataField.getDesc());
        cell = dataRow.createCell(row++);
        String refDataEntityKey = dataField.getRefDataEntityKey();
        if (StringUtils.hasText(refDataEntityKey) && (entityDefine = this.entityMetaService.queryEntity(refDataEntityKey)) != null) {
            cell.setCellValue(entityDefine.getCode());
        }
        if (!CollectionUtils.isEmpty(validationRules = dataField.getValidationRules())) {
            StringBuilder validation = new StringBuilder();
            StringBuilder validationMsg = new StringBuilder();
            for (ValidationRule validationRule : validationRules) {
                String verification;
                if (validationRule.getCompareType() == CompareType.NOTNULL || !StringUtils.hasLength(verification = validationRule.toVerification("", dataField.getCode(), dataField.getDataFieldType()))) continue;
                validation.append(verification).append("\n");
                String message = validationRule.getMessage();
                if (!StringUtils.hasLength(message)) continue;
                validationMsg.append(message).append("\n");
            }
            validation.setLength(Math.max(validation.length() - 1, 0));
            validationMsg.setLength(Math.max(validationMsg.length() - 1, 0));
            cell = dataRow.createCell(row++);
            cell.setCellValue(validation.toString());
            cell = dataRow.createCell(row++);
            cell.setCellValue(validationMsg.toString());
        } else {
            row += 2;
        }
        cell = dataRow.createCell(row++);
        String measureUnit = dataField.getMeasureUnit();
        boolean isNum = dataField.getDataFieldType() == DataFieldType.INTEGER || dataField.getDataFieldType() == DataFieldType.BIGDECIMAL;
        boolean bl = isNotDim = "NotDimession".equals(measureUnit) || "9493b4eb-6516-48a8-a878-25a63a23e63a;NotDimession".equals(measureUnit);
        if (isNum) {
            cell.setCellValue(isNotDim ? NOTDIM : MONEY);
        }
        cell = dataRow.createCell(row++);
        if (isNum) {
            if (isNotDim) {
                cell.setCellValue("");
            } else if (!StringUtils.hasText(measureUnit)) {
                cell.setCellValue("");
            } else if (MONEY_LIST.contains(measureUnit)) {
                cell.setCellValue("9493b4eb-6516-48a8-a878-25a63a23e63a;" + measureUnit);
            } else {
                int i = measureUnit.indexOf("9493b4eb-6516-48a8-a878-25a63a23e63a;");
                if (i < 0) {
                    cell.setCellValue("");
                } else {
                    String key = measureUnit.substring("9493b4eb-6516-48a8-a878-25a63a23e63a;".length());
                    if (MONEY_LIST.contains(key)) {
                        cell.setCellValue(measureUnit);
                    } else {
                        cell.setCellValue("");
                    }
                }
            }
        } else {
            cell.setCellValue(measureUnit);
        }
        cell = dataRow.createCell(row++);
        if (dataField.getDataFieldGatherType() != null) {
            cell.setCellValue(dataField.getDataFieldGatherType().getTitle());
        }
        cell = dataRow.createCell(row++);
        FormatProperties formatProperties = dataField.getFormatProperties();
        if (formatProperties != null) {
            try {
                cell.setCellValue(this.mapper.writeValueAsString((Object)formatProperties));
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        cell = dataRow.createCell(row++);
        Boolean allowMultipleSelect = dataField.getAllowMultipleSelect();
        if (allowMultipleSelect != null) {
            cell.setCellValue(allowMultipleSelect != false ? TRUE_FORMAT : FALSE_FORMAT);
        }
        cell = dataRow.createCell(row++);
        if (dataField.getDataFieldKind() != null) {
            cell.setCellValue(dataField.getDataFieldKind().getTitle());
        }
        cell = dataRow.createCell(row++);
        Boolean allowUndefinedCode = dataField.getAllowUndefinedCode();
        if (allowUndefinedCode != null) {
            cell.setCellValue(allowUndefinedCode != false ? TRUE_FORMAT : FALSE_FORMAT);
        }
        cell = dataRow.createCell(row++);
        cell.setCellValue(dataField.isChangeWithPeriod() ? CHANGE_WITH_PERIOD : NOCHANGE_WITH_PERIOD);
        cell = dataRow.createCell(row++);
        cell.setCellValue(dataField.isGenerateVersion() ? TRUE_FORMAT : FALSE_FORMAT);
        cell = dataRow.createCell(row++);
        cell.setCellValue(dataField.isEncrypted() ? TRUE_FORMAT : FALSE_FORMAT);
        cell = dataRow.createCell(row++);
        if (dataField.getDataMaskCode() != null) {
            cell.setCellValue(dataMaskNameMap.getOrDefault(dataField.getDataMaskCode(), ""));
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void imports(String tableKey, Sheet tableSheet) {
        Row row = this.checkHead(tableSheet);
        if (row == null) {
            return;
        }
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(tableKey);
        if (dataTable == null) {
            throw new SchemeDataException("\u5bfc\u5165\u7684\u8868\u4e0d\u5b58\u5728");
        }
        String dataSchemeKey = dataTable.getDataSchemeKey();
        this.designDataSchemeService.checkDeployStatus(dataSchemeKey);
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        ArrayList<DesignDataField> list = new ArrayList<DesignDataField>();
        boolean flag = true;
        Map<String, String> dataMaskCodeMap = this.desensitizationStrategyManager.getAllStrategys().stream().collect(Collectors.toMap(DesensitizationInfo::getName, DesensitizationInfo::getCode));
        for (Row rowData : tableSheet) {
            int rowNum = rowData.getRowNum();
            if (rowNum == 0) continue;
            DataWrapper<DesignDataField> wrapper = this.getRow(rowData, dataTable, dataScheme, dataMaskCodeMap);
            if (wrapper.isPresent()) {
                DesignDataField field = (DesignDataField)wrapper.get();
                if (field == null) continue;
                list.add(field);
                continue;
            }
            flag = false;
        }
        if (!flag) {
            tableSheet.autoSizeColumn(23);
            throw new SchemeDataException("\u6570\u636e\u9519\u8bef,\u8bf7\u4fee\u6539");
        }
        Map<String, DesignDataField> newField = this.checkCode(row, list, dataTable);
        if (newField == null) {
            tableSheet.autoSizeColumn(23);
            throw new SchemeDataException("\u6570\u636e\u9519\u8bef,\u8bf7\u4fee\u6539");
        }
        ArrayList<DesignDataFieldDO> update = new ArrayList<DesignDataFieldDO>();
        ArrayList<DesignDataFieldDO> insert = new ArrayList<DesignDataFieldDO>();
        this.update(row, dataTable, newField, update, insert);
        this.checkUpdate(tableSheet, dataTable, update);
        this.save(dataTable, insert, update);
    }

    private void checkUpdate(Sheet tableSheet, DesignDataTable dataTable, List<DesignDataFieldDO> update) {
        boolean checkData = this.runtimeDataSchemeService.dataTableCheckData(new String[]{dataTable.getKey()});
        if (checkData) {
            boolean error = false;
            if (!update.isEmpty()) {
                DataField runDataField;
                Map<String, DataField> runDataFieldMap = this.runtimeDataSchemeService.getDataFieldByTable(dataTable.getKey()).stream().collect(Collectors.toMap(Basic::getKey, v -> v));
                for (DesignDataFieldDO field : update) {
                    if (DataFieldKind.FIELD_ZB != field.getDataFieldKind() && DataFieldKind.FIELD != field.getDataFieldKind() || DataFieldType.STRING != field.getDataFieldType() || field.isEncrypted() || null == (runDataField = runDataFieldMap.get(field.getKey())) || !runDataField.isEncrypted()) continue;
                    Row curRow = this.getRow(tableSheet, field);
                    this.appendMessage(curRow, "\u5df2\u5f55\u5165\u6570\u636e\u4e0d\u5141\u8bb8\u53d6\u6d88\u52a0\u5bc6\n");
                    error = true;
                }
                for (DesignDataFieldDO field : update) {
                    Row curRow;
                    int runDecimal;
                    if (DataFieldType.STRING != field.getDataFieldType() && DataFieldType.INTEGER != field.getDataFieldType() && DataFieldType.BIGDECIMAL != field.getDataFieldType() || null == (runDataField = runDataFieldMap.get(field.getKey()))) continue;
                    int precision = null == field.getPrecision() ? 0 : field.getPrecision();
                    int runPrecision = null == runDataField.getPrecision() ? 0 : runDataField.getPrecision();
                    int decimal = null == field.getDecimal() ? 0 : field.getDecimal();
                    int n = runDecimal = null == runDataField.getDecimal() ? 0 : runDataField.getDecimal();
                    if (precision < runPrecision) {
                        curRow = this.getRow(tableSheet, field);
                        this.appendMessage(curRow, "\u5df2\u5f55\u5165\u6570\u636e\u4e0d\u5141\u8bb8\u6539\u5c0f\u957f\u5ea6/\u7cbe\u5ea6(" + runPrecision + ")\n");
                        error = true;
                    }
                    if (decimal < runDecimal) {
                        curRow = this.getRow(tableSheet, field);
                        this.appendMessage(curRow, "\u5df2\u5f55\u5165\u6570\u636e\u4e0d\u5141\u8bb8\u6539\u5c0f\u5c0f\u6570\u4f4d(" + runDecimal + ")\n");
                        error = true;
                    }
                    if (decimal <= runDecimal || decimal - runDecimal <= precision - runPrecision) continue;
                    curRow = this.getRow(tableSheet, field);
                    this.appendMessage(curRow, "\u5df2\u5f55\u5165\u6570\u636e\uff0c\u5c0f\u6570\u4f4d\u589e\u5927\u65f6\u957f\u5ea6\u5fc5\u987b\u540c\u6b65\u589e\u5927(" + precision + "," + runDecimal + ")\n");
                    error = true;
                }
            }
            if (error) {
                throw new SchemeDataException("\u6570\u636e\u9519\u8bef,\u8bf7\u4fee\u6539");
            }
        }
    }

    private Row getRow(Sheet sheet, DataField dataField) {
        int codeIndex = 0;
        String[] header = this.dataSchemeExcel.getHeader();
        for (int i = 0; i < header.length; ++i) {
            if (!this.dataSchemeExcel.getCode().equals(header[i])) continue;
            codeIndex = i;
            break;
        }
        for (Row row : sheet) {
            Cell cell = row.getCell(codeIndex);
            String code = this.getStringValue(cell, true);
            if (!dataField.getCode().equals(code)) continue;
            return row;
        }
        return sheet.getRow(0);
    }

    private void save(DesignDataTable dataTable, List<DesignDataFieldDO> insert, List<DesignDataFieldDO> update) {
        this.designDataSchemeService.updateDataTable(dataTable);
        this.dataFieldDao.batchInsert(insert);
        this.dataFieldDao.batchUpdate(update);
    }

    private Row checkHead(Sheet tableSheet) {
        Assert.notNull((Object)tableSheet, "tableSheet must not be null.");
        int rowAllNum = tableSheet.getPhysicalNumberOfRows();
        if (rowAllNum < 2) {
            return null;
        }
        Row row = tableSheet.getRow(0);
        String[] header = this.dataSchemeExcel.getHeader();
        for (int i = 0; i < header.length; ++i) {
            String head = header[i];
            Cell cell = row.getCell(i);
            if (cell == null) {
                throw new SchemeDataException("\u4e0d\u662f\u6b63\u786e\u7684\u6a21\u677f,\u65e0\u6cd5\u89e3\u6790");
            }
            if (head.equals(cell.getStringCellValue())) continue;
            throw new SchemeDataException("\u4e0d\u662f\u6b63\u786e\u7684\u6a21\u677f,\u65e0\u6cd5\u89e3\u6790");
        }
        this.initMessage(row);
        return row;
    }

    private void update(Row row, DesignDataTable dataTable, Map<String, DesignDataField> newField, List<DesignDataFieldDO> update, List<DesignDataFieldDO> insert) {
        List oldFiled = this.designDataSchemeService.getDataFieldByTable(dataTable.getKey());
        ArrayList<DesignDataField> biz = new ArrayList<DesignDataField>(5);
        HashSet<String> oldKeys = new HashSet<String>(oldFiled.size());
        HashSet<String> innerCode = new HashSet<String>(5);
        int value = 0;
        if (this.paramLevelManager.isOpenParamLevel() && (value = this.paramLevelManager.getLevel().getValue()) != 0 && this.isSet(dataTable.getLevel()) && !String.valueOf(value).equals(dataTable.getLevel())) {
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_7.getMessage());
        }
        for (DesignDataField old : oldFiled) {
            oldKeys.add(old.getKey());
            DataFieldKind dataFieldKind = old.getDataFieldKind();
            DesignDataField field = newField.get(old.getCode());
            if (field != null) {
                if (DataFieldKind.PUBLIC_FIELD_DIM == dataFieldKind || dataFieldKind == DataFieldKind.BUILT_IN_FIELD) {
                    innerCode.add(field.getCode());
                }
                this.levelCheck(old, value);
                field.setKey(old.getKey());
                Convert.update(old, field);
                field.setOrder(old.getOrder());
                field.setUpdateTime(null);
                update.add(Convert.iDf2Do(field));
                newField.remove(field.getCode());
                if (field.getDataFieldKind() != DataFieldKind.TABLE_FIELD_DIM) continue;
                biz.add(field);
                continue;
            }
            if (DataFieldKind.PUBLIC_FIELD_DIM != dataFieldKind && dataFieldKind != DataFieldKind.TABLE_FIELD_DIM) continue;
            biz.add(old);
        }
        boolean rollback = false;
        if (!innerCode.isEmpty()) {
            StringBuilder codeRepeat = new StringBuilder("\u5b58\u5728\u5185\u7f6e\u5b57\u6bb5\uff1a");
            innerCode.forEach(r -> codeRepeat.append((String)r).append(" "));
            this.appendMessage(row, codeRepeat.append("\n").toString());
            rollback = true;
        }
        for (DesignDataField r2 : newField.values()) {
            r2.setKey(UUIDUtils.getKey());
            r2.setOrder(OrderGenerator.newOrder());
            if (r2.getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM) {
                biz.add(r2);
            }
            insert.add(Convert.iDf2Do(r2));
        }
        dataTable.setBizKeys(BizKeyOrder.order(biz));
        dataTable.setUpdateTime(null);
        DataTableType dataTableType = dataTable.getDataTableType();
        if (!insert.isEmpty() && dataTableType == DataTableType.TABLE) {
            int number = 1000;
            int limit = (insert.size() + number - 1) / number;
            ArrayList<DesignDataFieldDO> fields = new ArrayList<DesignDataFieldDO>();
            Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
                List<String> subCodes = insert.stream().skip(i * number).limit(number).map(DataFieldDO::getCode).collect(Collectors.toList());
                fields.addAll(this.dataFieldDao.getByCondition(dataTable.getDataSchemeKey(), subCodes, DataFieldKind.TABLE));
            });
            fields.removeIf(next -> oldKeys.contains(next.getKey()));
            if (!fields.isEmpty()) {
                StringBuilder codeRepeat = new StringBuilder("\u65b9\u6848\u4e2d\u6807\u8bc6\u5b58\u5728\u91cd\u590d\uff1a");
                fields.stream().map(Basic::getCode).collect(Collectors.toSet()).forEach(r -> codeRepeat.append((String)r).append(" "));
                this.appendMessage(row, codeRepeat.append("\n").toString());
                rollback = true;
            }
        }
        if (rollback) {
            throw new SchemeDataException("\u65b9\u6848\u4e2d\u6807\u8bc6\u5b58\u5728\u91cd\u590d");
        }
    }

    private Map<String, DesignDataField> checkCode(Row row, List<DesignDataField> list, DesignDataTable dataTable) {
        DataTableType dataTableType = dataTable.getDataTableType();
        LinkedHashMap<String, DesignDataField> newField = new LinkedHashMap<String, DesignDataField>();
        StringBuilder codeRepeat = new StringBuilder("\u6587\u4ef6\u4e2d\u6807\u8bc6\u5b58\u5728\u91cd\u590d\uff1a");
        int fieldType = DataFieldKind.FIELD.getValue() | DataFieldKind.TABLE_FIELD_DIM.getValue();
        int zbType = DataFieldKind.FIELD_ZB.getValue();
        boolean flag = true;
        for (DesignDataField field : list) {
            DataFieldKind fieldKind = field.getDataFieldKind();
            if ((fieldKind.getValue() & zbType) != 0) {
                if (DataTableType.TABLE != dataTableType && DataTableType.MD_INFO != dataTableType) {
                    flag = false;
                }
            } else if ((fieldKind.getValue() & fieldType) != 0) {
                if (DataTableType.DETAIL != dataTableType && DataTableType.ACCOUNT != dataTableType) {
                    flag = false;
                }
            } else {
                flag = false;
            }
            if (!flag) {
                this.appendMessage(row, "\u4e0d\u80fd\u5c06" + fieldKind.getTitle() + "\u5bfc\u5165" + dataTable.getTitle() + "\n");
                return null;
            }
            String code = field.getCode();
            if (newField.containsKey(code)) {
                codeRepeat.append(code).append(" ");
                break;
            }
            newField.put(code, field);
        }
        if (newField.size() != list.size()) {
            this.appendMessage(row, codeRepeat.append("\n").toString());
            return null;
        }
        return newField;
    }

    private DataWrapper<DesignDataField> getRow(Row row, DesignDataTable dataTable, DesignDataScheme dataScheme, Map<String, String> dataMaskCodeMap) {
        DefaultDataWrapper<DesignDataField> wrapper = DefaultDataWrapper.empty();
        DesignDataField field = null;
        boolean flag = this.getFieldByRow(row, wrapper, dataMaskCodeMap);
        if (wrapper.isPresent()) {
            field = wrapper.get();
        }
        if (field == null) {
            return DefaultDataWrapper.valueOf(null);
        }
        field.setDataTableKey(dataTable.getKey());
        field.setDataSchemeKey(dataTable.getDataSchemeKey());
        boolean validate = this.validate(dataScheme, field, row) & this.validateAccount(dataTable, field, row) & this.validateMdInfo(dataTable, field, row);
        boolean link = this.linkageCheck(field, row);
        if (validate && link && flag) {
            return DefaultDataWrapper.valueOf(field);
        }
        return DefaultDataWrapper.empty();
    }

    private boolean linkageCheck(DesignDataField field, Row row) {
        if (field.getDataFieldType() == null) {
            return false;
        }
        Set<String> messages = this.linkageCheck(field, false);
        if (messages != null) {
            StringBuilder builder = new StringBuilder();
            for (String message : messages) {
                builder.append(message).append("\r\n");
            }
            this.appendMessage(row, builder.append("\n").toString());
            return false;
        }
        return true;
    }

    private boolean getFieldByRow(Row dataRow, DefaultDataWrapper<DesignDataField> wrapper, Map<String, String> dataMaskCodeMap) {
        Cell cell;
        String value;
        boolean flag = true;
        DesignDataField dataField = this.designDataSchemeService.initDataField();
        int row = 0;
        boolean empty = (value = this.getStringValue(cell = dataRow.getCell(row++), true)) == null;
        dataField.setTitle(value);
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, true);
        empty = empty && value == null;
        dataField.setCode(value);
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, true);
        if (value != null) {
            empty = false;
            DataFieldType dataFieldType = DataFieldType.titleOf((String)value);
            dataField.setDataFieldType(dataFieldType);
            if (dataFieldType == null) {
                flag = false;
                this.appendMessage(dataRow, "\u7c7b\u578b\u672a\u5b9a\u4e49\n");
            }
        }
        if ((value = this.getStringValue(cell = dataRow.getCell(row++), true)) != null) {
            empty = false;
            Integer precision = null;
            try {
                precision = Integer.valueOf(value);
            }
            catch (Exception e) {
                flag = false;
                this.appendMessage(dataRow, "\u957f\u5ea6/\u7cbe\u5ea6\u4e0d\u662f\u4e00\u4e2a\u6570\u5b57\n");
            }
            dataField.setPrecision(precision);
        }
        if ((value = this.getStringValue(cell = dataRow.getCell(row++), true)) != null) {
            empty = false;
            Integer decimal = null;
            try {
                decimal = Integer.valueOf(value);
            }
            catch (NumberFormatException e) {
                flag = false;
                this.appendMessage(dataRow, "\u5c0f\u6570\u4f4d\u4e0d\u662f\u4e00\u4e2a\u6570\u5b57\n");
            }
            dataField.setDecimal(decimal);
        }
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, true);
        empty = empty && value == null;
        dataField.setNullable(Boolean.valueOf(TRUE_FORMAT.equals(value)));
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, true);
        empty = empty && value == null;
        dataField.setDefaultValue(value);
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, true);
        if (value != null) {
            empty = false;
            DataFieldApplyType applyType = DataFieldApplyType.titleOf((String)value);
            dataField.setDataFieldApplyType(applyType);
            if (applyType == null) {
                flag = false;
                this.appendMessage(dataRow, "\u5e94\u7528\u7c7b\u578b\u672a\u5b9a\u4e49\n");
            }
        }
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, true);
        empty = empty && value == null;
        dataField.setDesc(value);
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, true);
        if (value != null) {
            empty = false;
            try {
                IEntityDefine entityDefine = this.entityMetaService.queryEntityByCode(value);
                dataField.setRefDataEntityKey(entityDefine.getId());
                TableModelDefine tableModel = this.entityMetaService.getTableModel(entityDefine.getId());
                String bizKeys = tableModel.getBizKeys();
                dataField.setRefDataFieldKey(bizKeys);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                flag = false;
                this.appendMessage(dataRow, "\u5173\u8054\u679a\u4e3e\u672a\u627e\u5230\n");
            }
        }
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, false);
        empty = empty && value == null;
        String validation = value;
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, false);
        boolean bl = empty = empty && value == null;
        if (StringUtils.hasLength(validation)) {
            String[] split = validation.split("\n|\r|\r\n");
            String[] messages = null;
            ArrayList<ValidationRule> rules = new ArrayList<ValidationRule>();
            if (StringUtils.hasLength(value)) {
                messages = value.split("\n|\r|\r\n");
            }
            for (int i = 0; i < split.length; ++i) {
                String valid = split[i];
                if (!StringUtils.hasLength(valid)) continue;
                try {
                    ValidationRule parse = this.verificationParser.parse(valid, (DataField)dataField);
                    if (parse == null) continue;
                    if (messages != null && messages.length > i) {
                        ((ValidationRuleDTO)parse).setMessage(messages[i]);
                    } else {
                        ((ValidationRuleDTO)parse).setMessage(null);
                    }
                    rules.add(parse);
                    continue;
                }
                catch (SyntaxException e) {
                    flag = false;
                    this.appendMessage(dataRow, "\u6821\u9a8c\u8868\u8fbe\u5f0f\u9519\u8bef\n");
                }
            }
            if (!rules.isEmpty()) {
                dataField.setValidationRules(rules);
            }
        }
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, true);
        if (dataField.getDataFieldType() == DataFieldType.INTEGER || dataField.getDataFieldType() == DataFieldType.BIGDECIMAL) {
            if (NOTDIM.equals(value)) {
                value = "NotDimession";
                ++row;
            } else if (MONEY.equals(value)) {
                String cellValue;
                if (StringUtils.hasText(cellValue = this.getStringValue(cell = dataRow.getCell(row++), true))) {
                    if (MONEY_LIST.contains(cellValue)) {
                        value = "9493b4eb-6516-48a8-a878-25a63a23e63a;" + cellValue;
                    } else {
                        int i = cellValue.indexOf("9493b4eb-6516-48a8-a878-25a63a23e63a;");
                        boolean measureUnitCheck = true;
                        if (i < 0) {
                            measureUnitCheck = false;
                        } else {
                            String key = cellValue.substring("9493b4eb-6516-48a8-a878-25a63a23e63a;".length());
                            if (MONEY_LIST.contains(key)) {
                                value = cellValue;
                            } else if ("NotDimession".equals(key)) {
                                value = "NotDimession";
                            } else {
                                measureUnitCheck = false;
                            }
                        }
                        if (!measureUnitCheck) {
                            this.appendMessage(dataRow, "\u8ba1\u91cf\u5355\u4f4d\u9519\u8bef\n");
                            flag = false;
                        }
                    }
                } else {
                    value = null;
                }
            } else {
                if (StringUtils.hasText(value)) {
                    this.appendMessage(dataRow, "\u8ba1\u91cf\u7c7b\u522b\u8bbe\u7f6e\u9519\u8bef\n");
                    flag = false;
                } else {
                    value = "NotDimession";
                }
                ++row;
            }
        } else {
            value = null;
            ++row;
        }
        empty = empty && value == null;
        dataField.setMeasureUnit(value);
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, true);
        if (value != null) {
            empty = false;
            DataFieldGatherType dataFieldGatherType = DataFieldGatherType.titleOf((String)value);
            dataField.setDataFieldGatherType(dataFieldGatherType);
            if (dataFieldGatherType == null) {
                flag = false;
                this.appendMessage(dataRow, "\u6c47\u603b\u7c7b\u578b\u672a\u5b9a\u4e49\n");
            }
        }
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, true);
        empty = empty && value == null;
        try {
            if (StringUtils.hasLength(value)) {
                FormatProperties formatProperties = (FormatProperties)this.mapper.readValue(value, FormatProperties.class);
                dataField.setFormatProperties(formatProperties);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, true);
        empty = empty && value == null;
        dataField.setAllowMultipleSelect(Boolean.valueOf(TRUE_FORMAT.equals(value)));
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, true);
        if (value != null) {
            empty = false;
            DataFieldKind dataFieldKind = DataFieldKind.titleOf((String)value);
            dataField.setDataFieldKind(dataFieldKind);
            if (dataFieldKind == null) {
                flag = false;
                this.appendMessage(dataRow, "\u7c7b\u522b\u672a\u5b9a\u4e49\n");
            }
        }
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, true);
        boolean bl2 = empty = empty && value == null;
        if (StringUtils.hasLength(value) && StringUtils.hasLength(dataField.getRefDataEntityKey())) {
            dataField.setAllowUndefinedCode(Boolean.valueOf(TRUE_FORMAT.equals(value)));
        }
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, true);
        empty = empty && value == null;
        dataField.setChangeWithPeriod(Boolean.valueOf(CHANGE_WITH_PERIOD.equals(value)));
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, true);
        empty = empty && value == null;
        dataField.setGenerateVersion(Boolean.valueOf(TRUE_FORMAT.equals(value)));
        cell = dataRow.getCell(row++);
        value = this.getStringValue(cell, true);
        empty = empty && value == null;
        dataField.setEncrypted(Boolean.valueOf(TRUE_FORMAT.equals(value)));
        cell = dataRow.getCell(row);
        value = this.getStringValue(cell, true);
        boolean bl3 = empty = empty && value == null;
        if (StringUtils.hasLength(value)) {
            String dataMaskCode = dataMaskCodeMap.get(value);
            dataField.setDataMaskCode(dataMaskCode);
        }
        if (empty) {
            wrapper.with(null);
            return true;
        }
        wrapper.with(dataField);
        return flag;
    }

    private void initMessage(Row row) {
        Sheet sheet = row.getSheet();
        for (Row rowItem : sheet) {
            Cell cell = rowItem.getCell(23);
            if (cell == null) continue;
            rowItem.removeCell(cell);
        }
        Cell error = row.getCell(23);
        if (error == null) {
            error = row.createCell(23);
        }
        error.setCellValue("\u9519\u8bef\u8be6\u60c5\n");
    }

    private void appendMessage(Row dataRow, String messageStr) {
        Cell error = dataRow.getCell(23);
        StringBuilder message = new StringBuilder();
        if (error == null) {
            error = dataRow.createCell(23);
        } else {
            message.append(this.getStringValue(error, false));
        }
        message.append(messageStr);
        error.setCellValue(message.toString());
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
                value = trim.replace("\r\n|\r|\n", "");
            }
            return value;
        }
        return null;
    }

    private boolean validate(DesignDataScheme dataScheme, DesignDataField field, Row row) {
        Set validate;
        List<String> messages = this.checkField0(field, false);
        String msg = this.checkField4(dataScheme, field, false);
        if (StringUtils.hasText(msg)) {
            messages.add(msg);
        }
        if ((validate = this.validator.validate((Object)field, new Class[0])) != null && !validate.isEmpty()) {
            for (ConstraintViolation item : validate) {
                String message = item.getMessage();
                messages.add(message);
            }
        }
        if (messages.isEmpty()) {
            return true;
        }
        StringBuilder builder = new StringBuilder();
        for (String message : messages) {
            builder.append(message).append("\r\n");
        }
        this.appendMessage(row, builder.toString());
        return false;
    }

    private boolean validateAccount(DesignDataTable table, DesignDataField field, Row row) {
        if (DataTableType.ACCOUNT == table.getDataTableType()) {
            return true;
        }
        String message = this.checkField2(table, field, false);
        if (StringUtils.hasText(message)) {
            this.appendMessage(row, message + "\r\n");
            return false;
        }
        return true;
    }

    private boolean validateMdInfo(DesignDataTable table, DesignDataField field, Row row) {
        if (DataTableType.MD_INFO != table.getDataTableType()) {
            return true;
        }
        String message = this.checkField3(table, field, false);
        if (StringUtils.hasText(message)) {
            this.appendMessage(row, message + "\r\n");
            return false;
        }
        return true;
    }
}

