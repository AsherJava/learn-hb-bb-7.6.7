/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 */
package com.jiuqi.nr.zb.scheme.internal.excel.impl;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.zb.scheme.common.ZbDataType;
import com.jiuqi.nr.zb.scheme.common.ZbGatherType;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import com.jiuqi.nr.zb.scheme.core.MetaItem;
import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.core.ValidationRule;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.internal.dto.PropInfoDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbInfoDTO;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowReader;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWrapper;
import com.jiuqi.nr.zb.scheme.internal.excel.impl.ExcelRowWrapperImpl;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.ExcelReaderValidator;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.ExcelZbReaderValidatorImpl;
import com.jiuqi.nr.zb.scheme.service.impl.DefaultVerificationParser;
import com.jiuqi.nr.zb.scheme.utils.JsonUtils;
import com.jiuqi.nr.zb.scheme.utils.PropInfoUtils;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class ZbExcelRowReader
implements IExcelRowReader {
    private static final Logger log = LoggerFactory.getLogger(ZbExcelRowReader.class);
    private final Map<String, PropInfo> propInfoMap;
    private final Map<String, ZbInfo> oldInfoMap;
    private static final Map<String, BiFunction<Cell, ZbInfoDTO, String>> zbMap = new HashMap<String, BiFunction<Cell, ZbInfoDTO, String>>();
    private final Set<String> codes;
    private final String schemeKey;
    private final String versionKey;
    private ExcelReaderValidator validator;

    public ZbExcelRowReader(List<PropInfo> propInfos, Map<String, ZbInfo> oldInfoMap, String schemeKey, String versionKey) {
        this.oldInfoMap = oldInfoMap != null ? oldInfoMap : Collections.emptyMap();
        this.propInfoMap = CollectionUtils.isEmpty(propInfos) ? Collections.emptyMap() : propInfos.stream().collect(Collectors.toMap(MetaItem::getTitle, f -> f, (o1, o2) -> o1));
        this.codes = new HashSet<String>();
        this.schemeKey = schemeKey;
        this.versionKey = versionKey;
    }

    @Override
    public ExcelReaderValidator getValidator() {
        if (this.validator == null) {
            this.validator = new ExcelZbReaderValidatorImpl();
        }
        return this.validator;
    }

    @Override
    public IExcelRowWrapper read(int rowNum, Map<String, Short> headers, Row row) {
        if (row == null) {
            return null;
        }
        ExcelRowWrapperImpl rowWrapper = new ExcelRowWrapperImpl();
        ZbInfoDTO zbInfo = new ZbInfoDTO();
        rowWrapper.setData(zbInfo);
        rowWrapper.setRowNum(rowNum);
        zbInfo.setKey(UUID.randomUUID().toString());
        zbInfo.setUpdateTime(Instant.now());
        zbInfo.setOrder(OrderGenerator.newOrder());
        zbInfo.setSchemeKey(this.schemeKey);
        zbInfo.setVersionKey(this.versionKey);
        for (Map.Entry<String, Short> entry : headers.entrySet()) {
            String head = entry.getKey();
            Short index = entry.getValue();
            if (!StringUtils.hasLength(head)) continue;
            try {
                if (zbMap.containsKey(head)) {
                    Cell cell = row.getCell(index.shortValue());
                    if (cell == null) continue;
                    BiFunction<Cell, ZbInfoDTO, String> biFunction = zbMap.get(head);
                    biFunction.apply(cell, zbInfo);
                    continue;
                }
                this.readOther(rowNum, headers, head, row, zbInfo);
                this.readExt(rowNum, headers, head, row, zbInfo);
            }
            catch (Exception e) {
                rowWrapper.addError(head + "\u683c\u5f0f\u4e0d\u6b63\u786e");
            }
        }
        try {
            this.checkAndUpdateOldInfo(zbInfo, rowWrapper);
            this.validate(zbInfo, rowWrapper);
            this.afterValidate(zbInfo);
            this.afterValidate(zbInfo, rowWrapper);
        }
        catch (Exception e) {
            log.error("\u8bfb\u53d6\u884c\u6570\u636e\u5f02\u5e38", e);
        }
        return rowWrapper;
    }

    private void afterValidate(ZbInfoDTO zbInfo, IExcelRowWrapper rowWrapper) {
        if (this.codes.contains(zbInfo.getCode())) {
            rowWrapper.addError("\u6307\u6807\u4ee3\u7801\u91cd\u590d" + zbInfo.getCode());
        } else {
            this.codes.add(zbInfo.getCode());
        }
        switch (zbInfo.getDataType()) {
            case BIGDECIMAL: {
                break;
            }
            case STRING: 
            case INTEGER: {
                zbInfo.setDecimal(null);
                break;
            }
            default: {
                zbInfo.setDecimal(null);
                zbInfo.setPrecision(null);
            }
        }
        if (zbInfo.getType() == ZbType.CALCULATE_ZB && zbInfo.getDataType() != ZbDataType.BIGDECIMAL && zbInfo.getDataType() != ZbDataType.INTEGER) {
            rowWrapper.addError("\u5206\u6790\u6307\u6807\u6570\u636e\u7c7b\u578b\u4e0d\u6b63\u786e");
        }
    }

    private void checkAndUpdateOldInfo(ZbInfoDTO zbInfo, IExcelRowWrapper rowWrapper) {
        if (this.oldInfoMap.containsKey(zbInfo.getCode())) {
            ZbInfo old = this.oldInfoMap.get(zbInfo.getCode());
            if (old.getDataType() == ZbDataType.BIGDECIMAL || old.getDataType() == ZbDataType.STRING || old.getDataType() == ZbDataType.INTEGER) {
                Integer decimal = old.getDecimal();
                decimal = decimal == null ? 0 : decimal;
                Integer precision = old.getPrecision();
                precision = precision == null ? 0 : precision;
                Integer decimal1 = zbInfo.getDecimal();
                decimal1 = decimal1 == null ? 0 : decimal1;
                Integer precision1 = zbInfo.getPrecision();
                precision1 = precision1 == null ? 0 : precision1;
                if (decimal1 < decimal || precision1 < precision || precision1 - decimal1 < precision - decimal) {
                    zbInfo.setDecimal(decimal);
                    zbInfo.setPrecision(precision);
                }
            }
            zbInfo.setType(old.getType());
            zbInfo.setDataType(old.getDataType());
            zbInfo.setNullable(old.isNullable());
            zbInfo.setCode(old.getCode());
            zbInfo.setMeasureUnit(old.getMeasureUnit());
        }
    }

    private void readOther(int rowNum, Map<String, Short> headers, String head, Row row, ZbInfoDTO zbInfo) throws SyntaxException {
        short index1;
        String value;
        short index = headers.get(head);
        Cell cell = row.getCell(index);
        if (cell == null) {
            return;
        }
        if ("\u6821\u9a8c\u8868\u8fbe\u5f0f".equals(head)) {
            DefaultVerificationParser verificationParser = new DefaultVerificationParser();
            String ruleStr = cell.getStringCellValue();
            String messageStr = null;
            short descIndex = headers.get("\u6821\u9a8c\u8868\u8fbe\u5f0f\u8bf4\u660e");
            if (descIndex > 0) {
                messageStr = row.getCell(descIndex).getStringCellValue();
            }
            if (StringUtils.hasLength(ruleStr)) {
                ArrayList<ValidationRuleDTO> rules = new ArrayList<ValidationRuleDTO>();
                String[] split = ruleStr.split("\n|\r|\r|\n");
                String[] split1 = null;
                if (StringUtils.hasLength(messageStr)) {
                    split1 = messageStr.split("\n|\r|\r|\n");
                }
                for (int j = 0; j < split.length; ++j) {
                    ValidationRuleDTO rule = (ValidationRuleDTO)verificationParser.parse(split[j], zbInfo);
                    if (split1 != null && j < split1.length - 1) {
                        rule.setMessage(split1[j]);
                    }
                    rules.add(rule);
                }
                zbInfo.setValidationRules(new ArrayList<ValidationRule>(rules));
            }
        } else if ("\u8ba1\u91cf\u7c7b\u522b".equals(head) && StringUtils.hasLength(value = cell.getStringCellValue()) && (index1 = headers.get("\u8ba1\u91cf\u5355\u4f4d").shortValue()) > 0) {
            cell = row.getCell(index1);
            zbInfo.setMeasureUnit(cell.getStringCellValue());
        }
    }

    private void readExt(int rowNum, Map<String, Short> headers, String head, Row row, ZbInfoDTO infoDTO) {
        short index = headers.get(head);
        Cell cell = row.getCell(index);
        if (cell == null) {
            return;
        }
        if (this.propInfoMap.containsKey(head)) {
            PropInfo propInfo = this.propInfoMap.get(head);
            PropInfoDTO propInfoDTO = PropInfoDTO.valueOf(propInfo);
            List<PropInfo> extProp = infoDTO.getExtProp();
            extProp.add(propInfoDTO);
            Object value = ZbExcelRowReader.getCellValue(cell);
            if (value == null) {
                return;
            }
            switch (propInfo.getDataType()) {
                case STRING: 
                case BLOB: 
                case CLOB: {
                    if (propInfoDTO.isMultiple()) {
                        String[] split = ((String)value).split(";");
                        propInfoDTO.setValue(new ArrayList<String>(Arrays.asList(split)));
                        break;
                    }
                    propInfoDTO.setValue(value);
                    break;
                }
                case DATETIME: {
                    value = cell.getStringCellValue();
                    if (!StringUtils.hasText((String)value)) break;
                    LocalDateTime parse = LocalDateTime.parse((String)value, PropInfoUtils.FORMATTER);
                    propInfoDTO.setValue(parse.toInstant(ZoneOffset.UTC));
                    break;
                }
                case INTEGER: 
                case BIG_DECIMAL: 
                case DOUBLE: {
                    propInfoDTO.setValue(cell.getNumericCellValue());
                    break;
                }
                case BOOLEAN: {
                    propInfoDTO.setValue("\u662f".equals(value) || "true".equals(value));
                    break;
                }
            }
        }
    }

    public static Object getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING: {
                return cell.getStringCellValue();
            }
            case NUMERIC: {
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }
                return cell.getNumericCellValue();
            }
            case BOOLEAN: {
                return cell.getBooleanCellValue();
            }
            case FORMULA: {
                return cell.getCellFormula();
            }
            case BLANK: {
                return null;
            }
        }
        log.debug("\u4e0d\u652f\u6301\u7684\u5355\u5143\u683c\u7c7b\u578b: {}", (Object)cell.getCellType());
        return null;
    }

    static {
        zbMap.put("\u540d\u79f0", (cell, zbInfoDO) -> {
            String value = cell.getStringCellValue();
            zbInfoDO.setTitle(value);
            return null;
        });
        zbMap.put("\u4ee3\u7801", (cell, zbInfoDO) -> {
            String value = cell.getStringCellValue();
            zbInfoDO.setCode(value);
            return null;
        });
        zbMap.put("\u7236\u7ea7\u4ee3\u7801", (cell, zbInfoDO) -> {
            String value = cell.getStringCellValue();
            zbInfoDO.setParentKey(value);
            return null;
        });
        zbMap.put("\u6307\u6807\u7c7b\u578b", (cell, zbInfoDO) -> {
            String value = cell.getStringCellValue();
            ZbType zbType = ZbType.forTitle(value);
            zbInfoDO.setType(zbType);
            return null;
        });
        zbMap.put("\u6570\u636e\u7c7b\u578b", (cell, zbInfoDO) -> {
            ZbDataType zbDataType = ZbDataType.titleOf(cell.getStringCellValue());
            zbInfoDO.setDataType(zbDataType);
            return null;
        });
        zbMap.put("\u957f\u5ea6/\u7cbe\u5ea6", (cell, zbInfoDO) -> {
            zbInfoDO.setPrecision((int)cell.getNumericCellValue());
            return null;
        });
        zbMap.put("\u5c0f\u6570\u4f4d", (cell, zbInfoDO) -> {
            zbInfoDO.setDecimal((int)cell.getNumericCellValue());
            return null;
        });
        zbMap.put("\u53ef\u4e3a\u7a7a", (cell, zbInfoDO) -> {
            String value = cell.getStringCellValue();
            zbInfoDO.setNullable("\u662f".equals(value));
            return null;
        });
        zbMap.put("\u6c47\u603b\u65b9\u5f0f", (cell, zbInfoDO) -> {
            String value = cell.getStringCellValue();
            if (StringUtils.hasLength(value)) {
                ZbGatherType zbGatherType = ZbGatherType.forTitle(value);
                if ("\u4efb\u610f".equals(value)) {
                    zbGatherType = ZbGatherType.MIN;
                }
                zbInfoDO.setGatherType(zbGatherType);
            }
            return null;
        });
        zbMap.put("\u516c\u5f0f", (cell, zbInfoDO) -> {
            String value = cell.getStringCellValue();
            zbInfoDO.setFormula(value);
            return null;
        });
        zbMap.put("\u516c\u5f0f\u8bf4\u660e", (cell, zbInfoDO) -> {
            String value = cell.getStringCellValue();
            zbInfoDO.setFormulaDesc(value);
            return null;
        });
        zbMap.put("\u663e\u793a\u683c\u5f0f", (cell, zbInfoDO) -> {
            String value = cell.getStringCellValue();
            if (StringUtils.hasLength(value)) {
                zbInfoDO.setFormatProperties(JsonUtils.fromJson(value, FormatProperties.class));
            }
            return null;
        });
        zbMap.put("\u63cf\u8ff0", (cell, zbInfoDO) -> {
            String value = cell.getStringCellValue();
            if (StringUtils.hasLength(value)) {
                zbInfoDO.setDesc(value);
            }
            return null;
        });
        zbMap.put("\u5173\u8054\u679a\u4e3e", (cell, zbInfoDO) -> {
            String value = cell.getStringCellValue();
            if (StringUtils.hasLength(value)) {
                zbInfoDO.setRefEntityId(value);
            }
            return null;
        });
        zbMap.put("\u5141\u8bb8\u591a\u9009", (cell, zbInfoDO) -> {
            String value = cell.getStringCellValue();
            zbInfoDO.setAllowMultipleSelect("\u662f".equals(value));
            return null;
        });
        zbMap.put("\u81ea\u5b9a\u4e49\u7f16\u7801", (cell, zbInfoDO) -> {
            String value = cell.getStringCellValue();
            zbInfoDO.setAllowMultipleSelect("\u662f".equals(value));
            return null;
        });
    }
}

