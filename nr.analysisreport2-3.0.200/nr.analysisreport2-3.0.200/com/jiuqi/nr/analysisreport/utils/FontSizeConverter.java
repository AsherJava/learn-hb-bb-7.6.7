/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FontSizeConverter {
    private static Logger logger = LoggerFactory.getLogger(FontSizeConverter.class);
    private static final double ZREO_FONT_SIZE = 0.0;
    private static final Pattern UNIT_PATTERN = Pattern.compile("^(\\d+\\.?\\d*|\\.\\d+)(px|pt|in|cm|mm|pc|em|rem|%)$", 2);
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+\\.?\\d*|\\.\\d+");
    private static final Map<String, Double> STR_FONT_SIZE_TO_PT = new HashMap<String, Double>();
    private static final Map<String, Double> UNIT_TO_PT = new HashMap<String, Double>();

    public static double convertToPt(String fontSize) {
        try {
            String input = fontSize.trim().toLowerCase().replace(" ", "");
            if (STR_FONT_SIZE_TO_PT.containsKey(input)) {
                return STR_FONT_SIZE_TO_PT.get(input);
            }
            Matcher unitMatcher = UNIT_PATTERN.matcher(input);
            if (unitMatcher.matches()) {
                double value = Double.parseDouble(unitMatcher.group(1));
                String unit = unitMatcher.group(2);
                return FontSizeConverter.roundToOneDecimal(value * UNIT_TO_PT.get(unit));
            }
            Matcher numMatcher = NUMBER_PATTERN.matcher(input);
            if (numMatcher.find()) {
                return FontSizeConverter.roundToOneDecimal(Double.parseDouble(numMatcher.group()));
            }
            return 0.0;
        }
        catch (Exception e) {
            logger.error("\u5b57\u53f7\u8f6c\u6362\u5f02\u5e38\uff1afontsize" + fontSize + "," + e.getMessage());
            return 0.0;
        }
    }

    private static double roundToOneDecimal(double value) {
        return (double)Math.round(value * 10.0) / 10.0;
    }

    static {
        UNIT_TO_PT.put("px", 0.75);
        UNIT_TO_PT.put("pt", 1.0);
        UNIT_TO_PT.put("in", 72.0);
        UNIT_TO_PT.put("cm", 28.3465);
        UNIT_TO_PT.put("mm", 2.8346);
        UNIT_TO_PT.put("pc", 12.0);
        UNIT_TO_PT.put("em", 1.0);
        UNIT_TO_PT.put("rem", 1.0);
        UNIT_TO_PT.put("%", 1.0);
        STR_FONT_SIZE_TO_PT.put("xx-small", 6.75);
        STR_FONT_SIZE_TO_PT.put("x-small", 7.5);
        STR_FONT_SIZE_TO_PT.put("small", 10.0);
        STR_FONT_SIZE_TO_PT.put("medium", 12.0);
        STR_FONT_SIZE_TO_PT.put("large", 13.5);
        STR_FONT_SIZE_TO_PT.put("x-large", 18.0);
        STR_FONT_SIZE_TO_PT.put("xx-large", 24.0);
        STR_FONT_SIZE_TO_PT.put("xxx-large", 36.0);
    }
}

