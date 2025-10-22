/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.parser;

import com.jiuqi.np.definition.internal.format.FixMode;
import com.jiuqi.np.definition.internal.format.NegativeStyle;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.util.StringUtils;

public class NumberFormatParser {
    private Integer formatType;
    private String currency;
    private Boolean thousands;
    private Boolean percentage;
    private Boolean thousandPer;
    private Integer displayDigits;
    private FixMode fixMode;
    private NegativeStyle negativeStyle;
    private static final NumberFormatParser DEFAULT = new NumberFormatParser();
    private static final int NUM_TYPE0 = 1;
    private static final int NUM_TYPE = 4;
    private static final Set<Character> CURRENCYS = new HashSet<Character>(3);

    public Integer getFormatType() {
        return this.formatType;
    }

    public String getCurrency() {
        return this.currency;
    }

    public Boolean isThousands() {
        return this.thousands;
    }

    public Boolean isPercentage() {
        return this.percentage;
    }

    public Boolean getThousandPer() {
        return this.thousandPer;
    }

    public boolean isThousandPer() {
        return this.getThousandPer() != null && this.getThousandPer() != false;
    }

    public Integer getDisplayDigits() {
        return this.displayDigits;
    }

    public FixMode getFixMode() {
        return this.fixMode;
    }

    public NegativeStyle getNegativeStyle() {
        return this.negativeStyle;
    }

    public static NumberFormatParser parse(FormatProperties formatProperties) {
        if (formatProperties == null) {
            return DEFAULT;
        }
        if (formatProperties.getPattern() == null) {
            return DEFAULT;
        }
        return new NumberFormatParser(formatProperties);
    }

    public static NumberFormatParser parse(String pattern) {
        if (!StringUtils.hasLength(pattern)) {
            return DEFAULT;
        }
        FormatProperties formatProperties = new FormatProperties();
        formatProperties.setPattern(pattern);
        return new NumberFormatParser(formatProperties);
    }

    public String toString() {
        return "NumberFormatParser{formatType=" + this.formatType + ", currency='" + this.currency + '\'' + ", thousands=" + this.thousands + ", percentage=" + this.percentage + ", thousandPer=" + this.thousandPer + ", displayDigits=" + this.displayDigits + ", fixMode=" + (Object)((Object)this.fixMode) + ", negativeStyle=" + (Object)((Object)this.negativeStyle) + '}';
    }

    private NumberFormatParser() {
        this.formatType = 0;
        this.thousands = true;
        this.percentage = false;
        this.thousandPer = false;
    }

    private NumberFormatParser(FormatProperties formatProperties) {
        Map<String, String> properties;
        this.formatType = formatProperties.getFormatType();
        String pattern = formatProperties.getPattern();
        char currency = pattern.charAt(0);
        int index = 0;
        if (CURRENCYS.contains(Character.valueOf(currency))) {
            char[] v = new char[]{currency};
            this.currency = new String(v);
            index = 1;
        }
        int xsd = -1;
        this.thousands = false;
        this.percentage = false;
        this.thousandPer = false;
        while (index < pattern.length()) {
            char item = pattern.charAt(index);
            if (item == ',') {
                this.thousands = true;
            } else if (item == '.') {
                xsd = index;
            } else if (item == '%') {
                this.percentage = true;
                this.formatType = 4;
            } else if (item == '\u2030') {
                this.thousandPer = true;
            }
            ++index;
        }
        if (xsd != -1) {
            this.displayDigits = pattern.length() - xsd - 1;
            if (this.percentage.booleanValue() || this.thousandPer.booleanValue()) {
                NumberFormatParser item = this;
                Integer n = item.displayDigits;
                Integer n2 = item.displayDigits = Integer.valueOf(item.displayDigits - 1);
            }
        } else {
            this.displayDigits = 0;
        }
        if ((properties = formatProperties.getProperties()) != null) {
            this.fixMode = FixMode.getByValue(properties.get("fixMode"));
            this.negativeStyle = NegativeStyle.getByValue(properties.get("negativeStyle"));
        }
        if (this.formatType == null) {
            this.formatType = 1;
        }
    }

    static {
        CURRENCYS.add(Character.valueOf('$'));
        CURRENCYS.add(Character.valueOf('\u00a5'));
        CURRENCYS.add(Character.valueOf('\u20ac'));
    }
}

