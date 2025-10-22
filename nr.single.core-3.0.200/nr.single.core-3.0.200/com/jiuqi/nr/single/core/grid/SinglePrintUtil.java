/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.grid;

import com.jiuqi.bi.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SinglePrintUtil {
    private static final Logger logger = LoggerFactory.getLogger(SinglePrintUtil.class);
    private static final double FONTSIZECE = 0.5;
    private static final double[] FONTSIZEARRAY2 = new double[]{5.0, 5.5, 6.5, 7.5, 9.0, 10.5, 12.0, 14.0, 15.0, 16.0, 18.0, 22.0, 24.0, 26.0, 36.0, 42.0};

    public static String replaceAll(String source) {
        try {
            if (StringUtils.isEmpty((String)source)) {
                return "";
            }
            int length = source.length();
            if (length > 7) {
                int beginIndex = source.indexOf("<D>");
                int endIndex = source.indexOf("</D>");
                if (-1 != beginIndex && -1 != endIndex) {
                    String begin = source.substring(0, beginIndex);
                    String repleaseString = source.substring(beginIndex + 3, endIndex);
                    String end = source.substring(endIndex + 4, source.length());
                    repleaseString = "sys_year".equals(repleaseString) ? "{[sys_year]}" : ("DWMC".equals(repleaseString) || "QYMC".equals(repleaseString) ? "{[sys_unittitle]}" : ("R$(YF,2)".equals(repleaseString) ? "{[cur_time]}" : "<D>" + repleaseString + "</D>"));
                    source = begin + repleaseString + end;
                }
            }
            source = source.replace("%d", "{#PageNumber}");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return source;
    }

    public static double getFontSizeByCE(double fontsize) {
        double newFontSize = fontsize;
        int intValue = (int)newFontSize;
        if ((double)intValue == newFontSize) {
            return newFontSize;
        }
        for (int i = 0; i < FONTSIZEARRAY2.length - 1; ++i) {
            double v1 = FONTSIZEARRAY2[i];
            double v2 = FONTSIZEARRAY2[i + 1];
            double v3 = (v2 + v1) / 2.0;
            double v31 = fontsize - v1;
            double v32 = v2 - fontsize;
            if (fontsize < v1) {
                return fontsize;
            }
            if (fontsize == v1) {
                return fontsize;
            }
            if (fontsize == v2) {
                return fontsize;
            }
            if (fontsize > v2) continue;
            if (fontsize > v3 && v32 < 0.5) {
                return v2;
            }
            if (fontsize == v3) {
                return fontsize;
            }
            if (!(v31 < 0.5)) continue;
            return v1;
        }
        return newFontSize;
    }
}

