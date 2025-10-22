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

public class FormatPropertiesParser {
    public static final int DEFALUT = 0;
    public static final int NUMBER = 1;
    public static final int CURRENCY = 2;
    public static final int ACCOUNT = 3;
    public static final int PERCENTAGE = 4;
    public static final int THOUSANDPER = 5;
    public static final int CUSTOM = 6;
    public static char CURRENCY_1 = (char)165;
    public static char CURRENCY_2 = (char)36;
    public static char CURRENCY_3 = (char)8364;
    private Integer formatType;
    private String currency;
    private Boolean thousands;
    private Boolean percentage;
    private Boolean thousandPer;
    private Integer displayDigits;
    private FixMode fixMode;
    private NegativeStyle negativeStyle;
    private static final FormatPropertiesParser DEFAULT = new FormatPropertiesParser();
    private static final Set<Character> CURRENCYS = new HashSet<Character>();

    public static FormatPropertiesParser parse(FormatProperties formatProperties) {
        if (formatProperties == null) {
            return DEFAULT;
        }
        if (formatProperties.getPattern() == null) {
            return DEFAULT;
        }
        return new FormatPropertiesParser(formatProperties);
    }

    public static FormatPropertiesParser parse(String pattern) {
        if (!StringUtils.hasLength(pattern)) {
            return DEFAULT;
        }
        FormatProperties formatProperties = new FormatProperties();
        formatProperties.setPattern(pattern);
        return new FormatPropertiesParser(formatProperties);
    }

    public NegativeStyle getNegativeStyle() {
        return this.negativeStyle;
    }

    public FixMode getFixMode() {
        return this.fixMode;
    }

    public Integer getDisplayDigits() {
        return this.displayDigits;
    }

    public Boolean getThousandPer() {
        return this.thousandPer;
    }

    public boolean isThousandPer() {
        return this.getThousandPer() != null && this.getThousandPer() != false;
    }

    public Boolean getPercentage() {
        return this.percentage;
    }

    public boolean isPercentage() {
        return this.getPercentage() != null && this.isPercentage();
    }

    public Boolean getThousands() {
        return this.thousands;
    }

    public boolean isThousands() {
        return this.getThousands() != null && this.getThousands() != false;
    }

    public String getCurrency() {
        return this.currency;
    }

    public int getFormatType() {
        return this.formatType == null ? 0 : this.formatType;
    }

    private FormatPropertiesParser() {
        this.formatType = 0;
        this.thousands = true;
        this.percentage = false;
        this.thousandPer = false;
    }

    private FormatPropertiesParser(FormatProperties formatProperties) {
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
                FormatPropertiesParser item = this;
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

    public String toString() {
        return "FormatPropertiesParser{formatType=" + this.formatType + ", currency='" + this.currency + '\'' + ", thousands=" + this.thousands + ", percentage=" + this.percentage + ", thousandPer=" + this.thousandPer + ", displayDigits=" + this.displayDigits + ", fixMode=" + (Object)((Object)this.fixMode) + ", negativeStyle=" + (Object)((Object)this.negativeStyle) + '}';
    }

    static {
        CURRENCYS.add(Character.valueOf(CURRENCY_1));
        CURRENCYS.add(Character.valueOf(CURRENCY_2));
        CURRENCYS.add(Character.valueOf(CURRENCY_3));
    }
}

