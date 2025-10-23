/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.utils.field;

import com.jiuqi.nr.zb.scheme.common.CompareType;
import com.jiuqi.nr.zb.scheme.common.ZbDataType;
import com.jiuqi.nr.zb.scheme.utils.field.VerificationBuilder;
import org.springframework.util.Assert;

public class DefaultVerificationBuilder
implements VerificationBuilder {
    protected String tableCode;
    protected String fieldCode;
    protected String domain;
    protected StringBuilder formula;
    protected ZbDataType fieldType;

    public static VerificationBuilder getInstance(String tableCode, String fieldCode, ZbDataType fieldType) {
        return new DefaultVerificationBuilder(tableCode, fieldCode, fieldType);
    }

    private DefaultVerificationBuilder(String tableCode, String fieldCode, ZbDataType fieldType) {
        this.setDomain(tableCode, fieldCode, fieldType);
    }

    @Override
    public VerificationBuilder setDomain(String tableCode, String fieldCode, ZbDataType fieldType) {
        Assert.notNull((Object)tableCode, "tableCode must not be null");
        Assert.notNull((Object)fieldCode, "fieldCode must not be null");
        Assert.notNull((Object)fieldType, "fieldType must not be null");
        this.tableCode = tableCode;
        this.fieldCode = fieldCode;
        this.domain = tableCode + "[" + fieldCode + "]";
        this.formula = new StringBuilder();
        this.fieldType = fieldType;
        return this;
    }

    @Override
    public VerificationBuilder equal(String value) {
        this.appendFormula(CompareType.EQUAL.getSign(), value);
        return this;
    }

    @Override
    public VerificationBuilder notEqual(String value) {
        this.appendFormula(CompareType.NOT_EQUAL.getSign(), value);
        return this;
    }

    @Override
    public VerificationBuilder moreThan(String value) {
        this.appendFormula(CompareType.MORE_THAN.getSign(), value);
        return this;
    }

    @Override
    public VerificationBuilder moreThanOrEqual(String value) {
        this.appendFormula(CompareType.MORE_THAN_OR_EQUAL.getSign(), value);
        return this;
    }

    @Override
    public VerificationBuilder lessThan(String value) {
        this.appendFormula(CompareType.LESS_THAN.getSign(), value);
        return this;
    }

    @Override
    public VerificationBuilder lessThanOrEqual(String value) {
        this.appendFormula(CompareType.LESS_THAN_OR_EQUAL.getSign(), value);
        return this;
    }

    @Override
    public VerificationBuilder contains(String value) {
        Assert.notNull((Object)value, "value must not be null");
        Assert.notNull((Object)this.domain, "domain must not be null");
        this.formula.append(CompareType.CONTAINS.getSign()).append("\"").append(value).append("\",").append(this.domain).append(CompareType.CONTAINS.getSign2());
        return this;
    }

    @Override
    public VerificationBuilder notContains(String value) {
        Assert.notNull((Object)value, "value must not be null");
        Assert.notNull((Object)this.domain, "domain must not be null");
        this.formula.append(CompareType.NOT_CONTAINS.getSign()).append("\"").append(value).append("\",").append(this.domain).append(CompareType.NOT_CONTAINS.getSign2());
        return this;
    }

    @Override
    public VerificationBuilder in(String[] values) {
        Assert.notNull((Object)values, "values must not be null");
        Assert.notNull((Object)this.domain, "domain must not be null");
        this.formula.append(this.domain).append(CompareType.IN.getSign());
        if (values.length > 0) {
            for (String value : values) {
                this.appendValue(this.formula, value);
                this.formula.append(",");
            }
            this.formula.setLength(this.formula.length() - 1);
        }
        this.formula.append(CompareType.IN.getSign2());
        return this;
    }

    @Override
    public VerificationBuilder isMobilePhone() {
        this.formula.append(CompareType.MOBILEPHONE.getSign()).append(this.domain).append(CompareType.MOBILEPHONE.getSign2());
        return this;
    }

    @Override
    public VerificationBuilder notNull() {
        this.formula.append(CompareType.NOTNULL.getSign()).append(this.domain).append(CompareType.NOTNULL.getSign2());
        return this;
    }

    @Override
    public VerificationBuilder maxLen(int length) {
        this.formula.append(CompareType.MAXLEN.getSign()).append(this.domain).append(CompareType.MAXLEN.getSign2()).append(length);
        return this;
    }

    @Override
    public VerificationBuilder between(String min, String max) {
        this.moreThanOrEqual(min);
        this.formula.append(" and ");
        this.lessThanOrEqual(max);
        return this;
    }

    @Override
    public VerificationBuilder notInBetween(String min, String max) {
        this.lessThan(min);
        this.formula.append(" or ");
        this.moreThan(max);
        return this;
    }

    @Override
    public VerificationBuilder clean() {
        this.formula = new StringBuilder();
        return this;
    }

    @Override
    public String build() {
        if (this.formula.length() == 0) {
            return null;
        }
        return this.formula.toString();
    }

    private void appendFormula(String sign, String value) {
        Assert.notNull((Object)value, "value must not be null");
        Assert.notNull((Object)this.domain, "domain must not be null");
        this.formula.append(this.domain).append(sign);
        this.appendValue(this.formula, value);
    }

    private void appendValue(StringBuilder builder, String value) {
        if (this.fieldType.getValue() == ZbDataType.STRING.getValue() || this.fieldType.getValue() == ZbDataType.CLOB.getValue()) {
            builder.append("\"").append(value).append("\"");
        } else {
            builder.append(value);
        }
    }

    public String toString() {
        return this.formula.toString();
    }
}

