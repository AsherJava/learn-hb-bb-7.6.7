/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.common.DataSchemeUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

public abstract class AbstractDataValidator {
    public static final int PRECISION_MIN = 1;
    public static final int PRECISION_MAX = 2000;
    public static final int PRECISION_MAX_1 = 38;
    public static final int DECIMAL_MIN = 0;
    public static final int DECIMAL_MAX = 127;
    public static final int VALID_MESSAGE_MAX_LEN = 50;
    private final Logger logger = LoggerFactory.getLogger(AbstractDataValidator.class);
    @Autowired
    @Qualifier(value="RuntimeDataSchemeNoCacheServiceImpl-NO_CACHE")
    protected IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    protected IDesignDataSchemeService iDesignDataSchemeService;
    private static final List<String> MD_INFO_FIELD_CODE_KEYWORD = Arrays.asList("ID", "VER", "CODE", "ORGCODE", "NAME", "SHORTNAME", "VALIDTIME", "INVALIDTIME", "PARENTCODE", "STOPFLAG", "RECOVERFLAG", "ORDINAL", "CREATEUSER", "CREATETIME", "PARENTS", "BBLX");

    protected List<String> checkField0(DesignDataField field, boolean step) {
        String code = field.getCode();
        if (code != null) {
            field.setCode(code.toUpperCase());
        }
        ArrayList<String> message = new ArrayList<String>();
        DataFieldType dataFieldType = field.getDataFieldType();
        DataFieldGatherType gatherType = field.getDataFieldGatherType();
        if (gatherType == null) {
            field.setDataFieldGatherType(DataFieldGatherType.NONE);
        }
        Integer precision = field.getPrecision();
        Boolean allowMultipleSelect = field.getAllowMultipleSelect();
        Boolean nullable = field.getNullable();
        if (nullable == null) {
            field.setNullable(Boolean.valueOf(true));
        }
        Integer decimal = field.getDecimal();
        if (field.getUseAuthority() == null) {
            field.setUseAuthority(Boolean.valueOf(false));
        }
        StringBuilder trMsg = new StringBuilder("\u6807\u8bc6\u4e3a\u3010" + field.getCode() + "\u3011\u7684");
        if (field.getDataFieldKind() != null) {
            trMsg.append(field.getDataFieldKind().getTitle());
        } else {
            trMsg.append("\u6307\u6807");
        }
        trMsg.append(",");
        if (DataFieldType.STRING.equals((Object)dataFieldType)) {
            if (precision == null) {
                if (step) {
                    this.logger.error("\u6307\u6807\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a,\u957f\u5ea6\u5fc5\u987b\u4e3a1-2000\uff0c\u6307\u6807\u4fe1\u606f :{}", (Object)field);
                    throw new IllegalArgumentException(trMsg.append("\u6307\u6807\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a,\u957f\u5ea6\u5fc5\u987b\u4e3a1-2000").toString());
                }
                message.add("\u6307\u6807\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a,\u957f\u5ea6\u5fc5\u987b\u4e3a1-2000");
            } else if (precision < 1 || precision > 2000) {
                if (step) {
                    this.logger.error("\u5b57\u7b26\u7c7b\u578b\u7684\u957f\u5ea6\u5fc5\u987b\u4e3a1-2000\uff0c\u6307\u6807\u4fe1\u606f :{}", (Object)field);
                    throw new IllegalArgumentException(trMsg.append("\u5b57\u7b26\u7c7b\u578b\u7684\u957f\u5ea6\u5fc5\u987b\u4e3a1-2000").toString());
                }
                message.add("\u5b57\u7b26\u7c7b\u578b\u7684\u957f\u5ea6\u5fc5\u987b\u4e3a1-2000");
            }
            if (allowMultipleSelect == null) {
                field.setAllowMultipleSelect(Boolean.valueOf(false));
            }
            field.setDecimal(null);
            field.setMeasureUnit(null);
            field.setDataFieldApplyType(null);
        } else if (DataFieldType.BIGDECIMAL.equals((Object)dataFieldType)) {
            this.checkPrecision(step, message, field);
            if (decimal == null) {
                if (step) {
                    this.logger.error("\u6570\u503c\u7c7b\u578b\u7684\u5c0f\u6570\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a,\u5fc5\u987b\u4e3a0-127\uff0c\u6307\u6807\u4fe1\u606f :{}", (Object)field);
                    throw new IllegalArgumentException(trMsg.append("\u6570\u503c\u7c7b\u578b\u7684\u5c0f\u6570\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a,\u5fc5\u987b\u4e3a0-127").toString());
                }
                message.add("\u6570\u503c\u7c7b\u578b\u7684\u5c0f\u6570\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a,\u5fc5\u987b\u4e3a0-127");
            } else if (decimal < 0 || decimal > 127) {
                if (step) {
                    this.logger.error("\u6570\u503c\u7c7b\u578b\u7684\u5c0f\u6570\u4f4d\u5fc5\u987b\u4e3a0-127\uff0c\u6307\u6807\u4fe1\u606f :{}", (Object)field);
                    throw new IllegalArgumentException(trMsg.append("\u6570\u503c\u7c7b\u578b\u7684\u5c0f\u6570\u4f4d\u5fc5\u987b\u4e3a0-127").toString());
                }
                message.add("\u6570\u503c\u7c7b\u578b\u7684\u5c0f\u6570\u4f4d\u5fc5\u987b\u4e3a0-127");
            }
            field.setRefDataFieldKey(null);
            field.setAllowMultipleSelect(null);
            field.setAllowUndefinedCode(null);
        } else {
            if (DataFieldType.INTEGER.equals((Object)dataFieldType)) {
                this.checkPrecision(step, message, field);
            } else {
                field.setPrecision(null);
                field.setDataFieldApplyType(null);
                field.setMeasureUnit(null);
            }
            field.setDecimal(null);
            field.setRefDataFieldKey(null);
            field.setAllowMultipleSelect(null);
            field.setAllowUndefinedCode(null);
        }
        String measureUnit = field.getMeasureUnit();
        if (StringUtils.hasLength(measureUnit) && 1 == measureUnit.split(";").length) {
            field.setMeasureUnit("9493b4eb-6516-48a8-a878-25a63a23e63a;" + measureUnit);
        }
        return message;
    }

    protected String checkField2(DesignDataTable table, DesignDataField field, boolean step) {
        if (DataFieldKind.FIELD != field.getDataFieldKind()) {
            return null;
        }
        DataField runtimeField = this.iRuntimeDataSchemeService.getDataFieldByTableKeyAndCode(table.getKey(), field.getCode());
        if (null == runtimeField) {
            return null;
        }
        if (field.isChangeWithPeriod() == runtimeField.isChangeWithPeriod()) {
            return null;
        }
        String message = "\u53f0\u8d26\u8868\u4e2d\uff0c\u5df2\u7ecf\u53d1\u5e03\u7684\u5b57\u6bb5\u4e0d\u5141\u8bb8\u4fee\u6539\u5b57\u6bb5\u7c7b\u578b\u5c5e\u6027";
        if (step) {
            this.logger.error("{},\u6307\u6807\u4fe1\u606f :{}", (Object)message, (Object)field);
            throw new SchemeDataException(message);
        }
        return message;
    }

    protected String checkField3(DesignDataTable table, DesignDataField field, boolean step) {
        DataFieldType dataFieldType;
        if (DataFieldKind.FIELD_ZB != field.getDataFieldKind()) {
            return null;
        }
        String message = null;
        if (MD_INFO_FIELD_CODE_KEYWORD.contains(field.getCode())) {
            message = "\u6807\u8bc6\u4e0e\u5173\u952e\u5b57\u51b2\u7a81";
        }
        if ((dataFieldType = field.getDataFieldType()) != DataFieldType.STRING && dataFieldType != DataFieldType.CLOB && dataFieldType != DataFieldType.INTEGER && dataFieldType != DataFieldType.BIGDECIMAL && dataFieldType != DataFieldType.BOOLEAN && dataFieldType != DataFieldType.DATE) {
            message = "\u65e0\u6548\u6570\u636e\u7c7b\u578b";
        }
        if (null != message && step) {
            this.logger.error("{},\u6307\u6807\u4fe1\u606f :{}", (Object)message, (Object)field);
            throw new SchemeDataException(message);
        }
        return message;
    }

    protected String checkField4(DesignDataScheme scheme, DesignDataField field, boolean stop) {
        if (!field.isEncrypted()) {
            return null;
        }
        String message = null;
        if (!StringUtils.hasText(scheme.getEncryptScene())) {
            message = "\u6570\u636e\u65b9\u6848\u672a\u9009\u62e9\u52a0\u5bc6\u573a\u666f";
        }
        if (DataFieldKind.FIELD != field.getDataFieldKind() && DataFieldKind.FIELD_ZB != field.getDataFieldKind() || DataFieldType.STRING != field.getDataFieldType()) {
            message = "\u4ec5\u5b57\u7b26\u578b\u6307\u6807\u6216\u5b57\u6bb5\u652f\u6301\u52a0\u5bc6";
        }
        if (StringUtils.hasText(field.getRefDataEntityKey())) {
            message = "\u5173\u8054\u679a\u4e3e\u6307\u6807\u6216\u5b57\u6bb5\u4e0d\u652f\u6301\u52a0\u5bc6";
        }
        if (null != message && stop) {
            this.logger.error("{},\u6307\u6807\u4fe1\u606f :{}", (Object)message, (Object)field);
            throw new SchemeDataException(message);
        }
        return message;
    }

    protected Set<String> linkageCheck(DesignDataField field, boolean step) {
        DataFieldType dataFieldType = field.getDataFieldType();
        DataFieldGatherType dataFieldGatherType = field.getDataFieldGatherType();
        if (dataFieldGatherType == null) {
            return null;
        }
        HashSet<String> message = new HashSet<String>(2);
        String messageItem = dataFieldType.getTitle() + "\u6ca1\u6709" + dataFieldGatherType.getTitle();
        switch (dataFieldType) {
            case STRING: {
                if (dataFieldGatherType == DataFieldGatherType.COUNT || dataFieldGatherType == DataFieldGatherType.NONE) break;
                if (step) {
                    this.logger.error("{} :{}", (Object)messageItem, (Object)field);
                    throw new IllegalArgumentException(messageItem);
                }
                message.add(messageItem);
                break;
            }
            case BOOLEAN: 
            case BIGDECIMAL: 
            case INTEGER: 
            case DATE: 
            case PICTURE: 
            case FILE: {
                if (dataFieldGatherType == DataFieldGatherType.COUNT || dataFieldGatherType == DataFieldGatherType.NONE || dataFieldGatherType == DataFieldGatherType.AVERAGE || dataFieldGatherType == DataFieldGatherType.MIN || dataFieldGatherType == DataFieldGatherType.MAX || dataFieldGatherType == DataFieldGatherType.DISTINCT_COUNT || dataFieldGatherType == DataFieldGatherType.SUM) break;
                if (step) {
                    this.logger.error("{} :{}", (Object)messageItem, (Object)field);
                    throw new IllegalArgumentException(messageItem);
                }
                message.add(messageItem);
                break;
            }
        }
        if (message.isEmpty()) {
            return null;
        }
        return message;
    }

    private void checkPrecision(boolean step, List<String> message, DesignDataField field) {
        int max;
        int min;
        String scope;
        Integer precision = field.getPrecision();
        DataFieldType dataFieldType = field.getDataFieldType();
        StringBuilder msg = new StringBuilder();
        if (DataFieldType.INTEGER.equals((Object)dataFieldType)) {
            msg.append("\u6574\u6570\u7c7b\u578b\u7684\u7cbe\u5ea6");
            scope = "1-2000";
            min = 1;
            max = 2000;
        } else if (DataFieldType.BIGDECIMAL.equals((Object)dataFieldType)) {
            msg.append("\u6570\u503c\u7c7b\u578b\u7684\u957f\u5ea6");
            scope = "1-38";
            min = 1;
            max = 38;
        } else {
            return;
        }
        StringBuilder trMsg = new StringBuilder("\u6807\u8bc6\u4e3a\u3010" + field.getCode() + "\u3011\u7684");
        if (field.getDataFieldKind() != null) {
            trMsg.append(field.getDataFieldKind().getTitle());
        } else {
            trMsg.append("\u6307\u6807");
        }
        trMsg.append(",");
        if (precision == null) {
            msg.append("\u4e0d\u5141\u8bb8\u4e3a\u7a7a,\u5fc5\u987b\u4e3a").append(scope);
            if (step) {
                this.logger.error("{}\uff0c\u6307\u6807\u4fe1\u606f :{}", (Object)trMsg, (Object)field);
                throw new IllegalArgumentException(trMsg.append((CharSequence)msg).toString());
            }
            message.add(msg.toString());
        } else if (precision < min || precision > max) {
            msg.append("\u4e0d\u5141\u8bb8\u4e3a\u7a7a,\u5fc5\u987b\u4e3a").append(scope);
            if (step) {
                this.logger.error("{}\uff0c\u6307\u6807\u4fe1\u606f :{}", (Object)trMsg, (Object)field);
                throw new IllegalArgumentException(trMsg.append((CharSequence)msg).toString());
            }
            message.add(msg.toString());
        }
    }

    protected void levelCheck(DesignDataField field, int value) {
        if (value != 0 && this.isSet(field.getLevel()) && !String.valueOf(value).equals(field.getLevel())) {
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DS_1_7.getMessage());
        }
    }

    protected boolean isSet(String level) {
        return DataSchemeUtils.isSet(level);
    }
}

