/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 */
package com.jiuqi.nr.zb.scheme.internal.excel.impl;

import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.zb.scheme.common.CompareType;
import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.core.ValidationRule;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbInfoDTO;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWrapper;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWriter;
import com.jiuqi.nr.zb.scheme.utils.JsonUtils;
import com.jiuqi.nr.zb.scheme.utils.PropInfoUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class ZbExcelRowWriterImpl
implements IExcelRowWriter {
    private static final Map<String, BiConsumer<ZbInfoDTO, Cell>> map = new HashMap<String, BiConsumer<ZbInfoDTO, Cell>>();
    private static final Logger log = LoggerFactory.getLogger(ZbExcelRowWriterImpl.class);

    @Override
    public void write(IExcelRowWrapper rowWrapper, List<String> headers, Row row) {
        ZbInfoDTO zb = (ZbInfoDTO)rowWrapper.getData();
        for (int i = 0; i < headers.size(); ++i) {
            Cell cell = row.createCell(i);
            String head = headers.get(i);
            BiConsumer<ZbInfoDTO, Cell> zbCellBiConsumer = map.get(head);
            if (zbCellBiConsumer != null) {
                zbCellBiConsumer.accept(zb, cell);
                continue;
            }
            this.setExtValue(zb, cell, head);
        }
    }

    private void setExtValue(ZbInfoDTO zb, Cell cell, String head) {
        List<PropInfo> extProp = zb.getExtProp();
        if (extProp != null) {
            block6: for (PropInfo propInfo : extProp) {
                if (propInfo.getValue() == null || !propInfo.getTitle().equals(head)) continue;
                switch (propInfo.getDataType()) {
                    case STRING: 
                    case BOOLEAN: 
                    case CLOB: 
                    case BLOB: {
                        cell.setCellValue((String)PropInfoUtils.parseValue(propInfo));
                        continue block6;
                    }
                    case INTEGER: {
                        Integer i = (Integer)PropInfoUtils.parseValue(propInfo);
                        if (i == null) continue block6;
                        cell.setCellValue(i.intValue());
                        continue block6;
                    }
                    case DOUBLE: 
                    case BIG_DECIMAL: {
                        Double v = (Double)PropInfoUtils.parseValue(propInfo);
                        if (v == null) continue block6;
                        cell.setCellValue(v);
                        continue block6;
                    }
                    case DATETIME: {
                        String time = (String)PropInfoUtils.parseValue(propInfo);
                        if (time == null) continue block6;
                        cell.setCellValue(time);
                        continue block6;
                    }
                }
                log.debug("\u4e0d\u652f\u6301\u7684\u7c7b\u578b\uff1a{}", (Object)propInfo.getDataType());
            }
        }
    }

    private void setBaseValue(String head, ZbInfoDTO zb, Cell cell) {
        switch (head) {
            case "\u4ee3\u7801": {
                cell.setCellValue(zb.getCode());
                break;
            }
            case "\u540d\u79f0": {
                cell.setCellValue(zb.getTitle());
                break;
            }
            case "\u7236\u7ea7\u4ee3\u7801": {
                cell.setCellValue(zb.getParentKey());
                break;
            }
            case "\u662f\u5426\u4e3a\u5206\u7ec4": {
                cell.setCellValue("\u5426");
                break;
            }
            case "\u6307\u6807\u7c7b\u578b": {
                if (zb.getType() == null) break;
                cell.setCellValue(zb.getType().getTitle());
                break;
            }
            case "\u6570\u636e\u7c7b\u578b": {
                if (zb.getDataType() == null) break;
                cell.setCellValue(zb.getDataType().getTitle());
                break;
            }
            case "\u957f\u5ea6/\u7cbe\u5ea6": {
                if (zb.getPrecision() == null) break;
                cell.setCellValue(zb.getPrecision().intValue());
                break;
            }
            case "\u5c0f\u6570\u4f4d": {
                if (zb.getDecimal() == null) break;
                cell.setCellValue(zb.getDecimal().intValue());
                break;
            }
            case "\u53ef\u4e3a\u7a7a": {
                cell.setCellValue(zb.isNullable() ? "\u662f" : "\u5426");
                break;
            }
            case "\u63cf\u8ff0": {
                cell.setCellValue(zb.getDesc());
                break;
            }
            case "\u5173\u8054\u679a\u4e3e": {
                cell.setCellValue(zb.getRefEntityId());
                break;
            }
            case "\u6821\u9a8c\u8868\u8fbe\u5f0f": {
                List<ValidationRule> validationRules = zb.getValidationRules();
                if (CollectionUtils.isEmpty(validationRules)) break;
                StringBuilder validation = new StringBuilder();
                for (ValidationRule validationRule : validationRules) {
                    String verification;
                    if (validationRule.getCompareType() == CompareType.NOTNULL || !StringUtils.hasLength(verification = validationRule.toVerification("", zb.getCode(), zb.getDataType()))) continue;
                    validation.append(verification).append("\n");
                }
                validation.setLength(Math.max(validation.length() - 1, 0));
                cell.setCellValue(validation.toString());
                break;
            }
            case "\u6821\u9a8c\u8868\u8fbe\u5f0f\u8bf4\u660e": {
                List<ValidationRule> validationRules = zb.getValidationRules();
                if (CollectionUtils.isEmpty(validationRules)) break;
                StringBuilder validationMsg = new StringBuilder();
                for (ValidationRule validationRule : validationRules) {
                    String message;
                    String verification;
                    if (validationRule.getCompareType() == CompareType.NOTNULL || !StringUtils.hasLength(verification = validationRule.toVerification("", zb.getCode(), zb.getDataType())) || !StringUtils.hasLength(message = validationRule.getMessage())) continue;
                    validationMsg.append(message).append("\n");
                }
                validationMsg.setLength(Math.max(validationMsg.length() - 1, 0));
                cell.setCellValue(validationMsg.toString());
                break;
            }
            case "\u516c\u5f0f": {
                cell.setCellValue(zb.getFormula());
                break;
            }
            case "\u516c\u5f0f\u8bf4\u660e": {
                cell.setCellValue(zb.getFormulaDesc());
                break;
            }
            case "\u8ba1\u91cf\u7c7b\u522b": {
                String measureUnit = zb.getMeasureUnit();
                if (!StringUtils.hasLength(measureUnit) || measureUnit.endsWith("NotDimession") || measureUnit.endsWith("-")) break;
                cell.setCellValue("\u91d1\u989d");
                break;
            }
            case "\u8ba1\u91cf\u5355\u4f4d": {
                String measureUnit = zb.getMeasureUnit();
                if (!StringUtils.hasLength(measureUnit) || measureUnit.endsWith("NotDimession") || measureUnit.endsWith("-")) break;
                cell.setCellValue(zb.getMeasureUnit());
                break;
            }
            case "\u6c47\u603b\u65b9\u5f0f": {
                if (zb.getGatherType() == null) break;
                cell.setCellValue(zb.getGatherType().getTitle());
                break;
            }
            case "\u663e\u793a\u683c\u5f0f": {
                FormatProperties formatProperties = zb.getFormatProperties();
                if (formatProperties == null) break;
                cell.setCellValue(JsonUtils.toJson(formatProperties));
                break;
            }
            case "\u81ea\u5b9a\u4e49\u7f16\u7801": {
                cell.setCellValue(zb.isAllowUndefinedCode() ? "\u662f" : "\u5426");
                break;
            }
            case "\u5141\u8bb8\u591a\u9009": {
                cell.setCellValue(zb.isAllowMultipleSelect() ? "\u662f" : "\u5426");
                break;
            }
        }
    }

    static {
        map.put("\u540d\u79f0", (zb, cell) -> cell.setCellValue(zb.getTitle()));
        map.put("\u4ee3\u7801", (zb, cell) -> cell.setCellValue(zb.getCode()));
        map.put("\u7236\u7ea7\u4ee3\u7801", (zb, cell) -> cell.setCellValue(zb.getParentKey()));
        map.put("\u662f\u5426\u4e3a\u5206\u7ec4", (zb, cell) -> cell.setCellValue("\u5426"));
        map.put("\u6307\u6807\u7c7b\u578b", (zb, cell) -> {
            if (zb.getType() != null) {
                cell.setCellValue(zb.getType().getTitle());
            }
        });
        map.put("\u6570\u636e\u7c7b\u578b", (zb, cell) -> {
            if (zb.getDataType() != null) {
                cell.setCellValue(zb.getDataType().getTitle());
            }
        });
        map.put("\u957f\u5ea6/\u7cbe\u5ea6", (zb, cell) -> {
            if (zb.getPrecision() != null) {
                cell.setCellValue(zb.getPrecision().intValue());
            }
        });
        map.put("\u5c0f\u6570\u4f4d", (zb, cell) -> {
            if (zb.getDecimal() != null) {
                cell.setCellValue(zb.getDecimal().intValue());
            }
        });
        map.put("\u53ef\u4e3a\u7a7a", (zb, cell) -> cell.setCellValue(zb.isNullable() ? "\u662f" : "\u5426"));
        map.put("\u63cf\u8ff0", (zb, cell) -> cell.setCellValue(zb.getDesc()));
        map.put("\u5173\u8054\u679a\u4e3e", (zb, cell) -> cell.setCellValue(zb.getRefEntityId()));
        map.put("\u6821\u9a8c\u8868\u8fbe\u5f0f", (zb, cell) -> {
            List<ValidationRule> validationRules = zb.getValidationRules();
            if (!CollectionUtils.isEmpty(validationRules)) {
                StringBuilder validation = new StringBuilder();
                for (ValidationRule validationRule : validationRules) {
                    String verification;
                    if (validationRule.getCompareType() == CompareType.NOTNULL || !StringUtils.hasLength(verification = validationRule.toVerification("", zb.getCode(), zb.getDataType()))) continue;
                    validation.append(verification).append("\n");
                }
                validation.setLength(Math.max(validation.length() - 1, 0));
                cell.setCellValue(validation.toString());
            }
        });
        map.put("\u6821\u9a8c\u8868\u8fbe\u5f0f\u8bf4\u660e", (zb, cell) -> {
            List<ValidationRule> validationRules = zb.getValidationRules();
            if (!CollectionUtils.isEmpty(validationRules)) {
                StringBuilder validationMsg = new StringBuilder();
                for (ValidationRule validationRule : validationRules) {
                    String message;
                    String verification;
                    if (validationRule.getCompareType() == CompareType.NOTNULL || !StringUtils.hasLength(verification = validationRule.toVerification("", zb.getCode(), zb.getDataType())) || !StringUtils.hasLength(message = validationRule.getMessage())) continue;
                    validationMsg.append(message).append("\n");
                }
                validationMsg.setLength(Math.max(validationMsg.length() - 1, 0));
                cell.setCellValue(validationMsg.toString());
            }
        });
        map.put("\u516c\u5f0f", (zb, cell) -> cell.setCellValue(zb.getFormula()));
        map.put("\u516c\u5f0f\u8bf4\u660e", (zb, cell) -> cell.setCellValue(zb.getFormulaDesc()));
        map.put("\u8ba1\u91cf\u7c7b\u522b", (zb, cell) -> {
            String measureUnit = zb.getMeasureUnit();
            if (StringUtils.hasLength(measureUnit) && !measureUnit.endsWith("NotDimession") && !measureUnit.endsWith("-")) {
                cell.setCellValue("\u91d1\u989d");
            }
        });
        map.put("\u8ba1\u91cf\u5355\u4f4d", (zb, cell) -> {
            String measureUnit = zb.getMeasureUnit();
            if (StringUtils.hasLength(measureUnit) && !measureUnit.endsWith("NotDimession") && !measureUnit.endsWith("-")) {
                cell.setCellValue(zb.getMeasureUnit());
            }
        });
        map.put("\u6c47\u603b\u65b9\u5f0f", (zb, cell) -> {
            if (zb.getGatherType() != null) {
                cell.setCellValue(zb.getGatherType().getTitle());
            }
        });
        map.put("\u663e\u793a\u683c\u5f0f", (zb, cell) -> {
            FormatProperties formatProperties = zb.getFormatProperties();
            if (formatProperties != null) {
                cell.setCellValue(JsonUtils.toJson(formatProperties));
            }
        });
        map.put("\u81ea\u5b9a\u4e49\u7f16\u7801", (zb, cell) -> cell.setCellValue(zb.isAllowUndefinedCode() ? "\u662f" : "\u5426"));
        map.put("\u5141\u8bb8\u591a\u9009", (zb, cell) -> cell.setCellValue(zb.isAllowMultipleSelect() ? "\u662f" : "\u5426"));
    }
}

