/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.service.VerificationBuilder
 *  com.jiuqi.nr.datascheme.api.type.CompareType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.api.service.VerificationBuilder;
import com.jiuqi.nr.datascheme.api.type.CompareType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import org.apache.shiro.util.Assert;

public class DefaultVerificationBuilder
implements VerificationBuilder {
    protected String tableCode;
    protected String fieldCode;
    protected String domain;
    protected StringBuilder formula;
    protected DataFieldType fieldType;

    public static VerificationBuilder getInstance(String tableCode, String fieldCode, DataFieldType fieldType) {
        return new DefaultVerificationBuilder(tableCode, fieldCode, fieldType);
    }

    private DefaultVerificationBuilder(String tableCode, String fieldCode, DataFieldType fieldType) {
        this.setDomain(tableCode, fieldCode, fieldType);
    }

    public VerificationBuilder setDomain(String tableCode, String fieldCode, DataFieldType fieldType) {
        Assert.notNull((Object)tableCode, (String)"tableCode must not be null");
        Assert.notNull((Object)fieldCode, (String)"fieldCode must not be null");
        Assert.notNull((Object)fieldType, (String)"fieldType must not be null");
        this.tableCode = tableCode;
        this.fieldCode = fieldCode;
        this.domain = tableCode + "[" + fieldCode + "]";
        this.formula = new StringBuilder();
        this.fieldType = fieldType;
        return this;
    }

    public VerificationBuilder equal(String value) {
        this.appendFormula(CompareType.EQUAL.getSign(), value);
        return this;
    }

    public VerificationBuilder notEqual(String value) {
        this.appendFormula(CompareType.NOT_EQUAL.getSign(), value);
        return this;
    }

    public VerificationBuilder moreThan(String value) {
        this.appendFormula(CompareType.MORE_THAN.getSign(), value);
        return this;
    }

    public VerificationBuilder moreThanOrEqual(String value) {
        this.appendFormula(CompareType.MORE_THAN_OR_EQUAL.getSign(), value);
        return this;
    }

    public VerificationBuilder lessThan(String value) {
        this.appendFormula(CompareType.LESS_THAN.getSign(), value);
        return this;
    }

    public VerificationBuilder lessThanOrEqual(String value) {
        this.appendFormula(CompareType.LESS_THAN_OR_EQUAL.getSign(), value);
        return this;
    }

    public VerificationBuilder contains(String value) {
        Assert.notNull((Object)value, (String)"value must not be null");
        Assert.notNull((Object)this.domain, (String)"domain must not be null");
        this.formula.append(CompareType.CONTAINS.getSign()).append("\"").append(value).append("\",").append(this.domain).append(CompareType.CONTAINS.getSign2());
        return this;
    }

    public VerificationBuilder notContains(String value) {
        Assert.notNull((Object)value, (String)"value must not be null");
        Assert.notNull((Object)this.domain, (String)"domain must not be null");
        this.formula.append(CompareType.NOT_CONTAINS.getSign()).append("\"").append(value).append("\",").append(this.domain).append(CompareType.NOT_CONTAINS.getSign2());
        return this;
    }

    public VerificationBuilder in(String[] values) {
        Assert.notNull((Object)values, (String)"values must not be null");
        Assert.notNull((Object)this.domain, (String)"domain must not be null");
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

    public VerificationBuilder isMobilePhone() {
        this.formula.append(CompareType.MOBILEPHONE.getSign()).append(this.domain).append(CompareType.MOBILEPHONE.getSign2());
        return this;
    }

    public VerificationBuilder notNull() {
        this.formula.append(CompareType.NOTNULL.getSign()).append(this.domain).append(CompareType.NOTNULL.getSign2());
        return this;
    }

    public VerificationBuilder maxLen(int length) {
        this.formula.append(CompareType.MAXLEN.getSign()).append(this.domain).append(CompareType.MAXLEN.getSign2()).append(length);
        return this;
    }

    public VerificationBuilder between(String min, String max) {
        this.moreThanOrEqual(min);
        this.formula.append(" and ");
        this.lessThanOrEqual(max);
        return this;
    }

    public VerificationBuilder notInBetween(String min, String max) {
        this.lessThan(min);
        this.formula.append(" or ");
        this.moreThan(max);
        return this;
    }

    public VerificationBuilder clean() {
        this.formula = new StringBuilder();
        return this;
    }

    public String build() {
        if (this.formula.length() == 0) {
            return null;
        }
        return this.formula.toString();
    }

    private void appendFormula(String sign, String value) {
        Assert.notNull((Object)value, (String)"value must not be null");
        Assert.notNull((Object)this.domain, (String)"domain must not be null");
        this.formula.append(this.domain).append(sign);
        this.appendValue(this.formula, value);
    }

    private void appendValue(StringBuilder builder, String value) {
        if (this.fieldType == DataFieldType.STRING || this.fieldType == DataFieldType.CLOB) {
            builder.append("\"").append(value).append("\"");
        } else {
            builder.append(value);
        }
    }

    public String toString() {
        return this.formula.toString();
    }
}

