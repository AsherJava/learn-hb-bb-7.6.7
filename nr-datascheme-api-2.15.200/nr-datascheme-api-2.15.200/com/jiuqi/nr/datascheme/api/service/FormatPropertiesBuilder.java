/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.format.FixMode
 *  com.jiuqi.np.definition.internal.format.NegativeStyle
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.np.definition.internal.format.FixMode;
import com.jiuqi.np.definition.internal.format.NegativeStyle;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import java.util.HashMap;

public final class FormatPropertiesBuilder {
    private int formatType = 0;
    private boolean thousands = true;
    private int displayDigits = 0;
    private String currency = "\u00a5";
    private String negativeStyle = "0";
    private String fixMode;
    private DataFieldType fieldType;
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

    public FormatPropertiesBuilder setFieldType(DataFieldType fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public FormatPropertiesBuilder setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public FormatProperties build() {
        if (this.fieldType == null) {
            return null;
        }
        if (this.fieldType == DataFieldType.INTEGER) {
            this.displayDigits = 0;
        }
        if (this.formatType <= 0 || this.formatType > 6) {
            return null;
        }
        FormatProperties formatProperties = new FormatProperties();
        formatProperties.setFormatType(Integer.valueOf(this.formatType));
        StringBuilder pattern = new StringBuilder();
        HashMap<String, String> map = new HashMap<String, String>(2);
        switch (this.formatType) {
            case 2: 
            case 3: {
                if (this.currency != null) {
                    pattern.append(this.currency);
                } else {
                    throw new IllegalArgumentException("\u53c2\u6570\u4e0d\u5b8c\u6574");
                }
            }
            case 1: {
                FixMode fixModeEn;
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
                NegativeStyle byValue = NegativeStyle.getByValue((String)this.negativeStyle);
                if (byValue != null) {
                    map.put("negativeStyle", byValue.getValue());
                }
                if ((fixModeEn = FixMode.getByValue((String)this.fixMode)) != null) {
                    map.put("fixMode", fixModeEn.getValue());
                }
                formatProperties.setProperties(map);
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
                FixMode fixModeEn = FixMode.getByValue((String)this.fixMode);
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
                FixMode fixModeEn = FixMode.getByValue((String)this.fixMode);
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

    public FormatPropertiesBuilder clean() {
        this.formatType = 0;
        this.thousands = true;
        this.displayDigits = 0;
        this.currency = "\u00a5";
        this.negativeStyle = "0";
        this.fixMode = null;
        this.fieldType = null;
        return this;
    }

    public CurrentConditions getConditions() {
        return new CurrentConditions(this);
    }

    public static class CurrentConditions {
        private final FormatPropertiesBuilder formatPropertiesBuilder;

        private CurrentConditions(FormatPropertiesBuilder formatPropertiesBuilder) {
            this.formatPropertiesBuilder = formatPropertiesBuilder;
        }

        public int getFormatType() {
            return this.formatPropertiesBuilder.formatType;
        }

        public boolean isThousands() {
            return this.formatPropertiesBuilder.thousands;
        }

        public int getDisplayDigits() {
            return this.formatPropertiesBuilder.displayDigits;
        }

        public String getCurrency() {
            return this.formatPropertiesBuilder.currency;
        }

        public String getNegativeStyle() {
            return this.formatPropertiesBuilder.negativeStyle;
        }

        public String getFixMode() {
            return this.formatPropertiesBuilder.fixMode;
        }

        public DataFieldType getFieldType() {
            return this.formatPropertiesBuilder.fieldType;
        }
    }
}

