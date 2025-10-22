/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.format;

import com.jiuqi.np.definition.internal.format.FixMode;
import com.jiuqi.np.definition.internal.format.NegativeStyle;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.parser.FormatPropertiesParser;
import java.util.HashMap;
import java.util.Map;

public class FormatPropertiesBuilder {
    private int formatType = 0;
    private boolean thousands = true;
    private int displayDigits = 0;
    private String currency = "\u00a5";
    private String negativeStyle = "0";
    private String fixMode;
    private String pattern;
    public static final int DEFAULT_FORMAT = 0;
    public static final int DEFAULT_FORMAT1 = 1;
    public static final int DEFAULT_FORMAT2 = 2;
    public static final int DEFAULT_FORMAT3 = 3;
    public static final int DEFAULT_FORMAT4 = 4;
    public static final int DEFAULT_FORMAT5 = 5;
    public static final int DEFAULT_FORMAT6 = 6;

    public FormatPropertiesBuilder setFormatType(int formatType) {
        this.formatType = formatType;
        return this;
    }

    public FormatPropertiesBuilder setThousands(boolean thousands) {
        this.thousands = thousands;
        return this;
    }

    public FormatPropertiesBuilder setDisplayDigits(int displayDigits) {
        this.displayDigits = displayDigits;
        return this;
    }

    public FormatPropertiesBuilder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public FormatPropertiesBuilder setNegativeStyle(String negativeStyle) {
        this.negativeStyle = negativeStyle;
        return this;
    }

    public FormatPropertiesBuilder setFixMode(String fixMode) {
        this.fixMode = fixMode;
        return this;
    }

    public FormatPropertiesBuilder setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public FormatProperties build() {
        if (this.formatType <= 0 || this.formatType > 6) {
            return null;
        }
        FormatProperties formatProperties = new FormatProperties();
        formatProperties.setFormatType(this.formatType);
        StringBuilder pattern = new StringBuilder();
        HashMap<String, String> map = new HashMap<String, String>();
        switch (this.formatType) {
            case 2: 
            case 3: {
                formatProperties.setProperties(map);
                if (this.currency == null) {
                    throw new IllegalArgumentException("\u53c2\u6570\u4e0d\u5b8c\u6574");
                }
                pattern.append(this.currency);
                this.numberFormat(formatProperties, pattern);
                return formatProperties;
            }
            case 1: {
                formatProperties.setProperties(map);
                this.numberFormat(formatProperties, pattern);
                return formatProperties;
            }
            case 4: {
                pattern.append("0");
                if (this.displayDigits > 0) {
                    pattern.append(".");
                    for (int i = 0; i < this.displayDigits; ++i) {
                        pattern.append("0");
                    }
                }
                pattern.append("%");
                formatProperties.setPattern(pattern.toString());
                FixMode fixModeEn = FixMode.getByValue(this.fixMode);
                if (fixModeEn != null) {
                    map.put("fixMode", fixModeEn.getValue());
                }
                formatProperties.setProperties(map);
                return formatProperties;
            }
            case 5: {
                pattern.append("0");
                if (this.displayDigits > 0) {
                    pattern.append(".");
                    for (int i = 0; i < this.displayDigits; ++i) {
                        pattern.append("0");
                    }
                }
                pattern.append("\u2030");
                formatProperties.setPattern(pattern.toString());
                FixMode fixModeEn = FixMode.getByValue(this.fixMode);
                if (fixModeEn != null) {
                    map.put("fixMode", fixModeEn.getValue());
                }
                formatProperties.setProperties(map);
                return formatProperties;
            }
            case 6: {
                formatProperties.setPattern(this.pattern);
                return formatProperties;
            }
        }
        return null;
    }

    private void numberFormat(FormatProperties formatProperties, StringBuilder pattern) {
        FixMode fixModeEn;
        Map<String, String> map = formatProperties.getProperties();
        if (map == null) {
            map = new HashMap<String, String>();
        }
        if (this.thousands) {
            pattern.append("#,##0");
        } else {
            pattern.append("0");
        }
        if (this.displayDigits > 0) {
            pattern.append(".");
            for (int i = 0; i < this.displayDigits; ++i) {
                pattern.append("0");
            }
        }
        formatProperties.setPattern(pattern.toString());
        NegativeStyle byValue = NegativeStyle.getByValue(this.negativeStyle);
        if (byValue != null) {
            map.put("negativeStyle", byValue.getValue());
        }
        if ((fixModeEn = FixMode.getByValue(this.fixMode)) != null) {
            map.put("fixMode", fixModeEn.getValue());
        }
        formatProperties.setProperties(map);
    }

    public FormatPropertiesBuilder clean() {
        this.formatType = 0;
        this.thousands = true;
        this.displayDigits = 0;
        this.currency = "\u00a5";
        this.negativeStyle = "0";
        this.fixMode = null;
        return this;
    }

    public FormatPropertiesBuilder() {
    }

    public FormatPropertiesBuilder(FormatProperties properties) {
        FormatPropertiesParser parse = FormatPropertiesParser.parse(properties);
        this.formatType = parse.getFormatType();
        this.thousands = parse.isThousands();
        this.displayDigits = parse.getDisplayDigits() != null ? parse.getDisplayDigits() : 0;
        this.currency = parse.getCurrency();
        if (parse.getNegativeStyle() != null) {
            this.negativeStyle = parse.getNegativeStyle().getValue();
        }
        if (parse.getFixMode() != null) {
            this.fixMode = parse.getFixMode().getValue();
        }
        this.pattern = properties.getPattern();
    }
}

