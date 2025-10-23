/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.format.FixMode
 *  com.jiuqi.np.definition.internal.format.NegativeStyle
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.definition.internal.parser.NumberFormatParser
 */
package com.jiuqi.nr.task.form.util;

import com.jiuqi.np.definition.internal.format.FixMode;
import com.jiuqi.np.definition.internal.format.NegativeStyle;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.parser.NumberFormatParser;
import com.jiuqi.nr.task.form.link.dto.FormatDTO;
import java.util.HashMap;

public class FormatPropertiesUtils {
    public static final int DEFAULT_FORMAT = 0;
    public static final int DEFAULT_FORMAT1 = 1;
    public static final int DEFAULT_FORMAT2 = 2;
    public static final int DEFAULT_FORMAT3 = 3;
    public static final int DEFAULT_FORMAT4 = 4;
    public static final int DEFAULT_FORMAT5 = 5;
    public static final int DEFAULT_FORMAT6 = 6;

    public static FormatDTO convert(FormatProperties formatProperties) {
        NegativeStyle negativeStyle;
        FixMode fixMode;
        if (formatProperties == null) {
            return null;
        }
        NumberFormatParser parse = NumberFormatParser.parse((FormatProperties)formatProperties);
        FormatDTO formatVO = new FormatDTO();
        formatVO.setFormatType(parse.getFormatType());
        formatVO.setCurrency(parse.getCurrency());
        formatVO.setThousands(parse.isThousands());
        Integer displayDigits = parse.getDisplayDigits();
        if (displayDigits != null) {
            formatVO.setDisplayDigits(displayDigits);
        }
        if ((fixMode = parse.getFixMode()) != null) {
            formatVO.setFixMode(fixMode.getValue());
        }
        if ((negativeStyle = parse.getNegativeStyle()) != null) {
            formatVO.setNegativeStyle(negativeStyle.getValue());
        }
        return formatVO;
    }

    public static FormatProperties convert(FormatDTO formatDTO) {
        if (formatDTO == null) {
            return null;
        }
        Integer formatType = formatDTO.getFormatType();
        if (formatType == null) {
            return null;
        }
        FormatProperties formatProperties = new FormatProperties();
        formatProperties.setFormatType(formatType);
        StringBuilder pattern = new StringBuilder();
        HashMap<String, String> map = new HashMap<String, String>(2);
        switch (formatType) {
            case 2: 
            case 3: {
                if (formatDTO.getCurrency() != null) {
                    pattern.append(formatDTO.getCurrency());
                } else {
                    throw new IllegalArgumentException("\u53c2\u6570\u4e0d\u5b8c\u6574");
                }
            }
            case 1: {
                FixMode fixModeEn;
                if (Boolean.TRUE.equals(formatDTO.getThousands())) {
                    pattern.append("#,##0");
                } else {
                    pattern.append("0");
                }
                if (formatDTO.getDisplayDigits() > 0) {
                    pattern.append(".");
                    for (int i = 0; i < formatDTO.getDisplayDigits(); ++i) {
                        pattern.append("0");
                    }
                }
                formatProperties.setPattern(pattern.toString());
                NegativeStyle byValue = NegativeStyle.getByValue((String)formatDTO.getNegativeStyle());
                if (byValue != null) {
                    map.put("negativeStyle", byValue.getValue());
                }
                if ((fixModeEn = FixMode.getByValue((String)formatDTO.getFixMode())) != null) {
                    map.put("fixMode", fixModeEn.getValue());
                }
                formatProperties.setProperties(map);
                return formatProperties;
            }
            case 4: {
                pattern.append("0");
                if (formatDTO.getDisplayDigits() > 0) {
                    pattern.append(".");
                    for (int i = 0; i < formatDTO.getDisplayDigits(); ++i) {
                        pattern.append("0");
                    }
                }
                pattern.append("%");
                formatProperties.setPattern(pattern.toString());
                FixMode fixModeEn = FixMode.getByValue((String)formatDTO.getFixMode());
                if (fixModeEn != null) {
                    map.put("fixMode", fixModeEn.getValue());
                }
                formatProperties.setProperties(map);
                return formatProperties;
            }
            case 5: {
                pattern.append("0");
                if (formatDTO.getDisplayDigits() > 0) {
                    pattern.append(".");
                    for (int i = 0; i < formatDTO.getDisplayDigits(); ++i) {
                        pattern.append("0");
                    }
                }
                pattern.append("\u2030");
                formatProperties.setPattern(pattern.toString());
                FixMode fixModeEn = FixMode.getByValue((String)formatDTO.getFixMode());
                if (fixModeEn != null) {
                    map.put("fixMode", fixModeEn.getValue());
                }
                formatProperties.setProperties(map);
                return formatProperties;
            }
            case 6: {
                formatProperties.setPattern("yyyy-MM");
                return formatProperties;
            }
        }
        return formatProperties;
    }
}

