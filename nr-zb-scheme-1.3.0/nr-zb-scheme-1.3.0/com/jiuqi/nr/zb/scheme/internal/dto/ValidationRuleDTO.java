/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.zb.scheme.internal.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.zb.scheme.common.CompareType;
import com.jiuqi.nr.zb.scheme.common.ZbDataType;
import com.jiuqi.nr.zb.scheme.core.ValidationRule;
import com.jiuqi.nr.zb.scheme.exception.ZbSchemeException;
import com.jiuqi.nr.zb.scheme.utils.field.DefaultVerificationBuilder;
import com.jiuqi.nr.zb.scheme.utils.field.VerificationBuilder;
import java.util.List;
import org.springframework.util.StringUtils;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ValidationRuleDTO
implements ValidationRule {
    protected String message;
    protected CompareType compareType;
    protected String leftValue;
    protected String rightValue;
    protected String value;
    protected List<String> inValues;
    protected String verification;
    @JsonIgnore
    protected String fieldCode;
    @JsonIgnore
    protected String fieldTitle;
    @JsonIgnore
    protected String tableCode;
    @JsonIgnore
    protected ZbDataType fieldType;
    private static final String DOMAIN = "\u6307\u6807\uff1a";

    @Override
    public String getVerification() {
        if (this.fieldCode == null) {
            return this.verification;
        }
        if (this.tableCode == null) {
            return this.verification;
        }
        if (this.fieldType == null) {
            return this.verification;
        }
        return this.toVerification(this.tableCode, this.fieldCode, this.fieldType);
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    @Override
    public String getMessage() {
        if (StringUtils.hasLength(this.message)) {
            return this.message;
        }
        if (this.compareType == null) {
            return "\u6821\u9a8c\u4e0d\u901a\u8fc7";
        }
        StringBuilder msg = new StringBuilder(DOMAIN);
        if (this.fieldTitle != null) {
            msg.append(this.fieldTitle);
        } else {
            msg.append("\u503c");
        }
        if (this.compareType == CompareType.NOTNULL) {
            msg.append(this.compareType.getTitle());
            return msg.toString();
        }
        switch (this.compareType) {
            case BETWEEN: 
            case NOT_BETWEEN: {
                msg.append("\u5e94\u8be5").append(this.compareType.getTitle());
                msg.append("[").append(this.leftValue).append("\u5230").append(this.rightValue).append("]");
                break;
            }
            case MOBILEPHONE: {
                msg.append("\u5e94\u8be5\u662f\u4e00\u4e2a").append(this.compareType.getTitle());
                break;
            }
            case CONTAINS: 
            case NOT_CONTAINS: 
            case EQUAL: 
            case NOT_EQUAL: 
            case LESS_THAN: 
            case MORE_THAN: 
            case LESS_THAN_OR_EQUAL: 
            case MORE_THAN_OR_EQUAL: {
                msg.append(this.compareType.getTitle()).append(this.value);
                break;
            }
            case MAXLEN: {
                msg.append(this.compareType.getTitle()).append("\u4e3a").append(this.value);
                break;
            }
            case IN: {
                msg.append("\u5e94\u8be5").append(CompareType.IN.getTitle()).append("[");
                if (this.inValues != null && !this.inValues.isEmpty()) {
                    for (String inValue : this.inValues) {
                        msg.append(inValue).append(",");
                    }
                    msg.setLength(msg.length() - 1);
                }
                msg.append("]");
                break;
            }
        }
        return msg.toString();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public CompareType getCompareType() {
        return this.compareType;
    }

    public void setCompareType(CompareType compareType) {
        this.compareType = compareType;
    }

    @Override
    public String getLeftValue() {
        return this.leftValue;
    }

    public void setLeftValue(String leftValue) {
        this.leftValue = leftValue;
    }

    @Override
    public String getRightValue() {
        return this.rightValue;
    }

    public void setRightValue(String rightValue) {
        this.rightValue = rightValue;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public List<String> getInValues() {
        return this.inValues;
    }

    public void setInValues(List<String> inValues) {
        this.inValues = inValues;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public ZbDataType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(ZbDataType fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    @Override
    @JsonIgnore
    public String toVerification(String tableCode, String fieldCode, ZbDataType dataType) {
        if (this.compareType == null) {
            return null;
        }
        VerificationBuilder builder = DefaultVerificationBuilder.getInstance(tableCode, fieldCode, dataType);
        switch (this.compareType) {
            case BETWEEN: {
                if (this.leftValue == null || this.rightValue == null) {
                    return null;
                }
                builder.between(this.leftValue, this.rightValue);
                break;
            }
            case NOT_BETWEEN: {
                if (this.leftValue == null || this.rightValue == null) {
                    return null;
                }
                builder.notInBetween(this.leftValue, this.rightValue);
                break;
            }
            case EQUAL: {
                if (this.valueIsNull()) {
                    return null;
                }
                builder.equal(this.value);
                break;
            }
            case NOT_EQUAL: {
                if (this.valueIsNull()) {
                    return null;
                }
                builder.notEqual(this.value);
                break;
            }
            case MORE_THAN: {
                if (this.valueIsNull()) {
                    return null;
                }
                builder.moreThan(this.value);
                break;
            }
            case LESS_THAN: {
                if (this.valueIsNull()) {
                    return null;
                }
                builder.lessThan(this.value);
                break;
            }
            case MORE_THAN_OR_EQUAL: {
                if (this.valueIsNull()) {
                    return null;
                }
                builder.moreThanOrEqual(this.value);
                break;
            }
            case LESS_THAN_OR_EQUAL: {
                if (this.valueIsNull()) {
                    return null;
                }
                builder.lessThanOrEqual(this.value);
                break;
            }
            case CONTAINS: {
                if (this.valueIsNull()) {
                    return null;
                }
                builder.contains(this.value);
                break;
            }
            case NOT_CONTAINS: {
                if (this.valueIsNull()) {
                    return null;
                }
                builder.notContains(this.value);
                break;
            }
            case IN: {
                builder.in(this.getInValues().toArray(new String[0]));
                break;
            }
            case MOBILEPHONE: {
                builder.isMobilePhone();
                break;
            }
            case NOTNULL: {
                builder.notNull();
                break;
            }
            case MAXLEN: {
                try {
                    builder.maxLen(Integer.parseInt(this.value));
                    break;
                }
                catch (NumberFormatException e) {
                    throw new ZbSchemeException(e);
                }
            }
            default: {
                throw new UnsupportedOperationException("\u6682\u4e0d\u652f\u6301");
            }
        }
        return builder.build();
    }

    @JsonIgnore
    private boolean valueIsNull() {
        return this.value == null;
    }
}

